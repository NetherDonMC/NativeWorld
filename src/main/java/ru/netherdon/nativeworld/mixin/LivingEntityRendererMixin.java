package ru.netherdon.nativeworld.mixin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.registries.NWAttachmentTypes;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>>
{
    @Inject(method = "isShaking", at = @At("RETURN"), cancellable = true)
    public void injectIsShaking(T entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (entity instanceof Player player)
        {
            SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);
            if (spatialDecay.isPlayerShaking())
            {
                cir.setReturnValue(true);
            }
        }
    }
}
