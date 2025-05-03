package ru.netherdon.nativeworld.mixin.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.services.SpatialDecayService;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>>
{
    @Inject(method = "isShaking", at = @At("RETURN"), cancellable = true)
    public void injectIsShaking(T entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (entity instanceof Player player)
        {
            SpatialDecay spatialDecay = SpatialDecayService.getSpatialDecay(player);
            if (spatialDecay.isPlayerShaking())
            {
                cir.setReturnValue(true);
            }
        }
    }
}
