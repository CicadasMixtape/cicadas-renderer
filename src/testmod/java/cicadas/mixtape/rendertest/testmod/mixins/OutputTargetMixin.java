package cicadas.mixtape.rendertest.testmod.mixins;

import cicadas.mixtape.rendertest.testmod.pass.HandsPass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OutputTarget.class)
public abstract class OutputTargetMixin {
    @Inject(method = "getRenderTarget", at = @At("RETURN"), cancellable = true)
    public void getRenderTarget(CallbackInfoReturnable<RenderTarget> cir) {
        if (HandsPass.INSTANCE.getToggled()) {
            cir.setReturnValue(HandsPass.INSTANCE.getTarget());
        }
    }
}