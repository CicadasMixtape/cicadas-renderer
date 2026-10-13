package cicadas.mixtape.rendertest.mixins;

import cicadas.mixtape.rendertest.shader.Types;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(BlockModelWrapper.class)
public abstract class BlockModelWrapperMixin {
    @Shadow @Final private Function<ItemStack, RenderType> renderType;

    @WrapWithCondition(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;setRenderType(Lnet/minecraft/client/renderer/rendertype/RenderType;)V"))
    public boolean update(ItemStackRenderState.LayerRenderState instance, RenderType renderType) {
        return false;
    }

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ModelRenderProperties;applyToLayer(Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;Lnet/minecraft/world/item/ItemDisplayContext;)V", shift = At.Shift.AFTER))
    public void update(ItemStackRenderState renderState, ItemStack stack, ItemModelResolver itemModelResolver, ItemDisplayContext displayContext, ClientLevel level, ItemOwner owner, int seed, CallbackInfo ci, @Local ItemStackRenderState.LayerRenderState layer) {
        var sprite = renderState.pickParticleIcon(RandomSource.create());
        layer.setRenderType(displayContext.firstPerson() && sprite != null ? Types.items(sprite.atlasLocation()) : renderType.apply(stack));
    }
}