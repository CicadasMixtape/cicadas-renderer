package cicadas.mixtape.renderer.testmod.mixins;

import cicadas.mixtape.renderer.testmod.pass.HandsPass;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "renderItemInHand", at = @At("HEAD"))
    public void renderItemInHand$head(float partialTick, boolean sleeping, Matrix4f projectionMatrix, CallbackInfo ci) {
        HandsPass.INSTANCE.begin();
    }

    @Inject(method = "renderItemInHand", at = @At("RETURN"))
    public void renderItemInHand$return(float partialTick, boolean sleeping, Matrix4f projectionMatrix, CallbackInfo ci) {
        HandsPass.INSTANCE.end();
    }
}