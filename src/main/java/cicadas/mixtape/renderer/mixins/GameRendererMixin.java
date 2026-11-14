package cicadas.mixtape.renderer.mixins;

import cicadas.mixtape.renderer.context.LevelContext;
import cicadas.mixtape.renderer.events.Render2DCallback;
import cicadas.mixtape.renderer.events.Render3DCallback;
import cicadas.mixtape.renderer.context.UIContext;
import cicadas.mixtape.renderer.pass.KawasePass;
import cicadas.mixtape.renderer.shader.projection.Projection;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.GameRenderState;
import net.minecraft.client.renderer.state.OptionsRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.util.profiling.Profiler;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private Camera mainCamera;
    @Shadow protected abstract void bobView(CameraRenderState cameraState, PoseStack poseStack);
    @Shadow protected abstract void bobHurt(CameraRenderState cameraState, PoseStack poseStack);
    @Shadow @Final private GameRenderState gameRenderState;

    @Inject(method = "render3dHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lnet/minecraft/client/renderer/state/level/PlayerRenderState;Lcom/mojang/renderpearl/api/textures/GpuTextureView;)V", shift = At.Shift.AFTER))
    public void render3dHud(CameraRenderState cameraState, PlayerRenderState playerState, OptionsRenderState optionsState, boolean consistentDepthRequired, CallbackInfo ci) {
        Profiler.get().push("cicadasrenderer-update-kawase");
        KawasePass.INSTANCE.update();
        Profiler.get().pop();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render()V", shift = At.Shift.AFTER))
    public void render(CallbackInfo ci) {
        if (!minecraft.isGameLoadFinished()) {
            return;
        }

        Profiler.get().push("cicadasrenderer-render-ui");


        var stack = new PoseStack();
        var projection = Projection.create(new Matrix4f().setOrtho(0f, minecraft.getWindow().getWidth() / 2f, minecraft.getWindow().getHeight() / 2f, 0f, -1000f, 1000f), RenderSystem.getModelViewStack());
        var handler = minecraft.mouseHandler;
        var inLevel = gameRenderState.shouldRenderLevel && minecraft.level != null && !(minecraft.gui.screen() instanceof LevelLoadingScreen) && !minecraft.debugEntries.isOverlayVisible() /* && !minecraft.options.hideGui*/;
        var context = UIContext.create(stack, projection, handler, () -> inLevel);

        Render2DCallback.EVENT.invoker().invoke(context);
        context.update();

        Profiler.get().pop();
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;render(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;ZLnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;Lorg/joml/Vector4f;ZZ)V", shift = At.Shift.AFTER))
    public void renderLevel2(CallbackInfo ci, @Local(name = "cameraState") CameraRenderState cameraState) {
        Profiler.get().push("cicadasrenderer-render-level");

        var stack = createStack(cameraState);
        var projection = Projection.create(cameraState.projectionMatrix, RenderSystem.getModelViewStack());
        var context = LevelContext.create(stack, projection);

        Render3DCallback.EVENT.invoker().invoke(context);
        context.update();

        Profiler.get().pop();
    }

    @Unique
    private PoseStack createStack(CameraRenderState cameraState) {
        var stack = new PoseStack();
        bobHurt(cameraState, stack);
        if (minecraft.options.bobView().get()) bobView(cameraState, stack);
        return stack;
    }
}