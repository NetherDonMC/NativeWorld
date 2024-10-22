package ru.netherdon.nativeworld.misc;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import ru.netherdon.nativeworld.config.NWCommonConfig;
import ru.netherdon.nativeworld.items.totems.ExtendedDimensionData;
import ru.netherdon.nativeworld.registries.NWRegistries;

public final class DimensionHelper
{
    public static boolean isSafe(ResourceKey<Level> dimension, RegistryAccess access)
    {
        var extendedInfo = NWRegistries.getExtendedDataOf(dimension, access);
        return extendedInfo.map(Holder::value)
            .map(ExtendedDimensionData::isSafe)
            .orElseGet(NWCommonConfig::isDimensionsAreSafe);
    }

    public static boolean hasLocalTotemFor(ResourceKey<Level> dimension, HolderLookup.Provider provider)
    {
        return provider.lookupOrThrow(NWRegistries.TOTEM_KEY).listElements().anyMatch((element) ->
            element.value().canUseInDimension(dimension)
        );
    }
}
