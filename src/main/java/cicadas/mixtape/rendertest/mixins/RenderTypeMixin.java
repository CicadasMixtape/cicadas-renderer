package cicadas.mixtape.rendertest.mixins;

import cicadas.mixtape.rendertest.api.ExtensionsKt;
import cicadas.mixtape.rendertest.blur.BlurPass;
import cicadas.mixtape.rendertest.shader.Pipelines;
import cicadas.mixtape.rendertest.shader.uniform.ColorUniform;
import cicadas.mixtape.rendertest.shader.uniform.ScreenUniform;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(RenderType.class)
public abstract class RenderTypeMixin {
    @Unique private GpuBufferSlice screenData;
    @Unique private GpuBufferSlice colorData;

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/CommandEncoder;createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;", shift = At.Shift.BEFORE))
    public void draw(MeshData meshData, CallbackInfo ci) {
        screenData = ScreenUniform.INSTANCE.update(ExtensionsKt.getScreenSize(Minecraft.getInstance().getMainRenderTarget()));
        colorData = ColorUniform.INSTANCE.update(ExtensionsKt.toVector4f(new Color(255, 75, 75)));
    }

    @WrapOperation(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setPipeline(Lcom/mojang/blaze3d/pipeline/RenderPipeline;)V"))
    public void draw2(RenderPass pass, RenderPipeline pipeline, Operation<Void> original) {
        original.call(pass, pipeline);

        if (pipeline == Pipelines.HAND_PIPELINE || pipeline == Pipelines.ITEM_PIPELINE) {
            pass.bindTexture("sampler1", BlurPass.INSTANCE.texture(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
            pass.setUniform("ScreenData", screenData);
            pass.setUniform("ColorData", colorData);
        }
    }
}