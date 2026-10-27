package cicadas.mixtape.rendertest.mixins;

import cicadas.mixtape.rendertest.api.ExtensionsKt;
import cicadas.mixtape.rendertest.events.EventRender2D;
import cicadas.mixtape.rendertest.events.EventRender3D;
import cicadas.mixtape.rendertest.pass.KawasePass;
import cicadas.mixtape.rendertest.utility.render.Renderer2D;
import cicadas.mixtape.rendertest.utility.render.Renderer3D;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.renderer.CachedPerspectiveProjectionMatrixBuffer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PerspectiveProjectionMatrixBuffer;
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
    @Shadow @Final private PerspectiveProjectionMatrixBuffer levelProjectionMatrixBuffer;
    @Shadow @Final private CachedPerspectiveProjectionMatrixBuffer hud3dProjectionMatrixBuffer;
    @Shadow protected abstract float getFov(Camera camera, float f, boolean bl);
    @Shadow public abstract Matrix4f getProjectionMatrix(float f);

    @Inject(method = "renderItemInHand", at = @At("HEAD"))
    public void renderItemInHand$head(float partialTick, boolean sleeping, Matrix4f projectionMatrix, CallbackInfo ci) {
        Profiler.get().push("rendertest-kawase-update");
        KawasePass.INSTANCE.update();
        Profiler.get().pop();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", shift = At.Shift.AFTER))
    public void render(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        if (!renderLevel || minecraft.level == null || minecraft.screen instanceof LevelLoadingScreen || minecraft.debugEntries.isOverlayVisible() || minecraft.options.hideGui) {
            return;
        }

        Profiler.get().push("rendertest-render-2d");

        var stack = new PoseStack();
        Renderer2D.INSTANCE.stack(stack);
        EventRender2D.EVENT.invoker().invoke(stack);
        Renderer2D.INSTANCE.update();

        Profiler.get().pop();
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V", shift = At.Shift.AFTER))
    public void renderLevel(DeltaTracker deltaTracker, CallbackInfo ci, @Local(ordinal = 1) Matrix4f matrix4f) {
        Profiler.get().push("rendertest-render-3d");

        var partialTicks = ExtensionsKt.getPartialTicks(deltaTracker);
        var projection = getProjectionMatrix(getFov(mainCamera, partialTicks, true));
        var stack = bob(new PoseStack());
        stack.mulPose(matrix4f);

        RenderSystem.setProjectionMatrix(levelProjectionMatrixBuffer.getBuffer(projection), ProjectionType.PERSPECTIVE);
        Renderer3D.INSTANCE.stack(stack);
        EventRender3D.EVENT.invoker().invoke(stack);
        Renderer3D.INSTANCE.drawDefaults();
        Renderer3D.INSTANCE.drawVoxels();
        RenderSystem.setProjectionMatrix(hud3dProjectionMatrixBuffer.getBuffer(minecraft.getMainRenderTarget().width, minecraft.getMainRenderTarget().height, getFov(mainCamera, partialTicks, false)), ProjectionType.PERSPECTIVE);

        Profiler.get().pop();
    }

    @Inject(method = "renderLevel", at = @At("TAIL"))
    public void renderLevel$tail(DeltaTracker deltaTracker, CallbackInfo ci) {
        Renderer3D.INSTANCE.clearAll();
    }

    @Unique
    private PoseStack bob(PoseStack stack) {
        bobHurt(stack, mainCamera.getPartialTickTime());
        if (minecraft.options.bobView().get()) bobView(stack, mainCamera.getPartialTickTime());
        return stack;
    }
}