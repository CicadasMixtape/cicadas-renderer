package cicadas.mixtape.rendertest.mixins;

import cicadas.mixtape.rendertest.RenderManager;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiRenderer.class)
public abstract class GuiRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    public void render(GpuBufferSlice bufferSlice, CallbackInfo ci) {
        RenderManager.INSTANCE.onRender2D();
    }
}