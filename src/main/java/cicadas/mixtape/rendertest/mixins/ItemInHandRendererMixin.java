package cicadas.mixtape.rendertest.mixins;

import cicadas.mixtape.rendertest.mixins.accessors.ItemStackRenderStateAccessor;
import cicadas.mixtape.rendertest.shader.Types;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Inject(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemModelResolver;updateForTopItem(Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/ItemOwner;I)V", shift = At.Shift.AFTER))
    public void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, CallbackInfo ci, @Local ItemStackRenderState state) {
        var atlas = Types.items(stack.getItem() instanceof BlockItem ? TextureAtlas.LOCATION_BLOCKS : TextureAtlas.LOCATION_ITEMS);

        for (ItemStackRenderState.LayerRenderState layer : ((ItemStackRenderStateAccessor) state).getLayers()) {
            layer.setRenderType(atlas);
        }
    }
}