package ru.netherdon.nativeworld.misc;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import ru.netherdon.nativeworld.config.NWServerConfig;
import ru.netherdon.nativeworld.registries.NWRegistryKeys;
import ru.netherdon.nativeworld.registries.NWTags;

public final class DimensionHelper
{
    public static boolean isSafe(Level level)
    {
        Holder<DimensionType> dimensionType = level.dimensionTypeRegistration();
        if (dimensionType.is(NWTags.DimensionTypes.SAFE))
        {
            return true;
        }

        if (dimensionType.is(NWTags.DimensionTypes.UNSAFE))
        {
            return false;
        }

        return NWServerConfig.get().spatialDecay().isUnexploredDimensionAreSafe();
    }

    public static boolean hasLocalTotemFor(ResourceKey<Level> dimension, HolderLookup.Provider provider)
    {
        return provider.lookupOrThrow(NWRegistryKeys.TOTEM_KEY).listElements().anyMatch((element) ->
            element.value().canUseInDimension(dimension)
        );
    }
}
