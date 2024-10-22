package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Registry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public final class NWDamageSources
{
    public static DamageSource spatialDecay(Registry<DamageType> damageTypes)
    {
        return new DamageSource(damageTypes.getHolderOrThrow(NWDamageTypes.SPATIAL_DECAY));
    }
}
