package cicadas.mixtape.renderer.mixins;

import cicadas.mixtape.renderer.ducks.LevelRendererDuck;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin implements LevelRendererDuck {
    @Unique private Frustum frustum;

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;prepareCullFrustum(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/client/renderer/culling/Frustum;"))
    public Frustum renderLevel(Frustum original) {
        return frustum = original;
    }

    @Override
    public @Nullable Frustum cicadas$getFrustum() {
        return frustum;
    }
}