package cicadas.mixtape.rendertest.mixins;

import cicadas.mixtape.rendertest.RenderManager;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CachedPerspectiveProjectionMatrixBuffer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PerspectiveProjectionMatrixBuffer;
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

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V", shift = At.Shift.AFTER))
    public void renderLevel(DeltaTracker deltaTracker, CallbackInfo ci, @Local(ordinal = 1) Matrix4f matrix4f) {
        var partial = deltaTracker.getGameTimeDeltaPartialTick(true);
        var projection = getProjectionMatrix(getFov(mainCamera, partial, true));
        var target = minecraft.getMainRenderTarget();
        var stack = bob(new PoseStack());
        stack.mulPose(matrix4f);

        RenderSystem.setProjectionMatrix(levelProjectionMatrixBuffer.getBuffer(projection), ProjectionType.PERSPECTIVE);
        RenderManager.INSTANCE.onRender3D(stack);
        RenderSystem.setProjectionMatrix(hud3dProjectionMatrixBuffer.getBuffer(target.width, target.height, getFov(mainCamera, partial, false)), ProjectionType.PERSPECTIVE);
    }

    @Unique
    private PoseStack bob(PoseStack stack) {
        bobHurt(stack, mainCamera.getPartialTickTime());
        if (minecraft.options.bobView().get()) bobView(stack, mainCamera.getPartialTickTime());
        return stack;
    }
}