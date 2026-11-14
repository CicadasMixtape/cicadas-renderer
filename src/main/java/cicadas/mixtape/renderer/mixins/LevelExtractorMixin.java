package cicadas.mixtape.renderer.mixins;

import cicadas.mixtape.renderer.ducks.LevelExtractorDuck;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.extract.LevelExtractor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public class LevelExtractorMixin implements LevelExtractorDuck {
    @Unique private Frustum frustum;

    @Inject(method = "applyFrustum", at = @At("TAIL"))
    public void renderLevel(Frustum frustum, CallbackInfo ci) {
        this.frustum = frustum;
    }

    @Override
    public @Nullable Frustum cicadas$getFrustum() {
        return frustum;
    }
}