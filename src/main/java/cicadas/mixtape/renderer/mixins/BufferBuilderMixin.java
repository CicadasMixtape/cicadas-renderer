package cicadas.mixtape.renderer.mixins;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import com.mojang.renderpearl.api.vertex.VertexFormatElement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin {
    @Mutable @Shadow @Final private static String[] elementNames;

    @Inject(method = "<init>", at = @At("HEAD"))
    private static void init$head(ByteBufferBuilder buffer, PrimitiveTopology primitiveTopology, VertexFormat format, CallbackInfo ci) {
        var list = new ArrayList<>(List.of(elementNames));

        for (VertexFormatElement element : format.getElements()) {
            String name = element.name();

            if (!list.contains(name)) {
                list.add(name);
            }
        }

        elementNames = list.toArray(String[]::new);
    }
}