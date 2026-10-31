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
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.renderer.GameRenderer;
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
    @Shadow protected abstract void bobView(PoseStack poseStack, float f);
    @Shadow protected abstract void bobHurt(PoseStack poseStack, float f);
    @Shadow protected abstract float getFov(Camera camera, float f, boolean bl);
    @Shadow public abstract Matrix4f getProjectionMatrix(float f);

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(FZLorg/joml/Matrix4f;)V", shift = At.Shift.AFTER))
    public void renderLevel(DeltaTracker deltaTracker, CallbackInfo ci) {
        Profiler.get().push("cicadasrenderer-update-kawase");
        KawasePass.INSTANCE.update();
        Profiler.get().pop();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", shift = At.Shift.AFTER))
    public void render(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        if (!minecraft.isGameLoadFinished()) {
            return;
        }

        Profiler.get().push("cicadasrenderer-render-ui");

        var stack = new PoseStack();
        var projection = Projection.create(new Matrix4f().setOrtho(0f, minecraft.getWindow().getWidth() / 2f, minecraft.getWindow().getHeight() / 2f, 0f, -1000f, 1000f), RenderSystem.getModelViewMatrix());
        var handler = minecraft.mouseHandler;
        var inLevel = renderLevel && minecraft.level != null && !(minecraft.screen instanceof LevelLoadingScreen) && !minecraft.debugEntries.isOverlayVisible() && !minecraft.options.hideGui;
        var context = UIContext.create(stack, projection, handler, () -> inLevel);

        Render2DCallback.EVENT.invoker().invoke(context);
        context.update();

        Profiler.get().pop();
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V", shift = At.Shift.AFTER))
    public void renderLevel2(DeltaTracker deltaTracker, CallbackInfo ci, @Local(ordinal = 1) Matrix4f matrix4f) {
        Profiler.get().push("cicadasrenderer-render-level");

        var stack = createStack(matrix4f);
        var projection = Projection.create(getProjectionMatrix(deltaTracker), RenderSystem.getModelViewMatrix());
        var context = LevelContext.create(stack, projection);

        Render3DCallback.EVENT.invoker().invoke(context);
        context.update();

        Profiler.get().pop();
    }

    @Unique
    private Matrix4f getProjectionMatrix(DeltaTracker tracker) {
        var partialTicks = tracker.getGameTimeDeltaPartialTick(false);
        var fov = getFov(mainCamera, partialTicks, true);
        return getProjectionMatrix(fov);
    }

    @Unique
    private PoseStack createStack(Matrix4f matrix4f) {
        var stack = new PoseStack();
        bobHurt(stack, mainCamera.getPartialTickTime());
        if (minecraft.options.bobView().get()) bobView(stack, mainCamera.getPartialTickTime());
        stack.mulPose(matrix4f);
        return stack;
    }
}