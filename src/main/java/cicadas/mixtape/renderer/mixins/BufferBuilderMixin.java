package cicadas.mixtape.renderer.mixins;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin {
    @Mutable @Shadow @Final private static String[] elementNames;

    @Inject(method = "<init>", at = @At("HEAD"))
    private static void init(ByteBufferBuilder buffer, PrimitiveTopology primitiveTopology, VertexFormat format, CallbackInfo ci) {
        elementNames = new String[]{"Position", "Color", "UV0", "UV1", "UV2", "UV3", "Normal", "LineWidth", "Size", "Radius", "Squirt"};
    }
}