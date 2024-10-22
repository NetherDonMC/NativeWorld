package ru.netherdon.nativeworld.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public final class NWDamageTypes
{
    public static final ResourceKey<DamageType> SPATIAL_DECAY =
        ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocationHelper.mod("spatial_decay"));
}
