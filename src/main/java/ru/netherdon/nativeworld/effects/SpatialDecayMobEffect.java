package ru.netherdon.nativeworld.effects;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import ru.netherdon.nativeworld.registries.NWDamageSources;

public class SpatialDecayMobEffect extends MobEffect
{
    public static final float MAX_DAMAGE_AMOUNT = 5f;

    public SpatialDecayMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier)
    {
        Registry<DamageType> damageTypes = livingEntity.level().registryAccess()
            .registryOrThrow(Registries.DAMAGE_TYPE);
        DamageSource damageSource = NWDamageSources.spatialDecay(damageTypes);
        float damageAmount = Math.min(MAX_DAMAGE_AMOUNT, 2f + amplifier * 1f);
        livingEntity.hurt(damageSource, damageAmount);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier)
    {
        int i = 30 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
