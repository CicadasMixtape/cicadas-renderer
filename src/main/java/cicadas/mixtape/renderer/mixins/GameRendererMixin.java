package cicadas.mixtape.renderer.mixins;

import cicadas.mixtape.renderer.events.Render2DCallback;
import cicadas.mixtape.renderer.events.Render3DCallback;
import cicadas.mixtape.renderer.objects.UIContext;
import cicadas.mixtape.renderer.pass.KawasePass;
import cicadas.mixtape.renderer.utility.render.Renderer2D;
import cicadas.mixtape.renderer.utility.render.Renderer3D;
import com.llamalad7.mixinextras.sugar.Local;
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

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", shift = At.Shift.AFTER))
    public void render(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        Profiler.get().push("cicadasrenderer-render-2d");

        var stack = new PoseStack();
        var inLevel = renderLevel && minecraft.level != null && !(minecraft.screen instanceof LevelLoadingScreen) && !minecraft.debugEntries.isOverlayVisible() && !minecraft.options.hideGui;
        KawasePass.INSTANCE.update();
        Render2DCallback.EVENT.invoker().invoke(UIContext.create(stack, minecraft.mouseHandler, () -> inLevel));
        Renderer2D.INSTANCE.update();

        Profiler.get().pop();
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V", shift = At.Shift.AFTER))
    public void renderLevel(DeltaTracker deltaTracker, CallbackInfo ci, @Local(ordinal = 1) Matrix4f matrix4f) {
        Profiler.get().push("cicadasrenderer-render-3d");

        var partialTicks = deltaTracker.getGameTimeDeltaPartialTick(false);
        var projection = getProjectionMatrix(getFov(mainCamera, partialTicks, true));
        var stack = bob(new PoseStack());
        stack.mulPose(matrix4f);

        Renderer3D.INSTANCE.stack(stack);
        Renderer3D.INSTANCE.projection(projection);
        Render3DCallback.EVENT.invoker().invoke(stack);
        Renderer3D.INSTANCE.drawBoxes();
        Renderer3D.INSTANCE.drawOutlines();

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