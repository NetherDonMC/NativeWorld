package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.ExtendedDimensionData;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

import java.util.Optional;

@EventBusSubscriber(modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.MOD)
public final class NWRegistries
{
    public static final ResourceKey<Registry<TotemOfBirthType>> TOTEM_KEY =
        ResourceKey.createRegistryKey(ResourceLocationHelper.mod("totem"));

    public static final ResourceKey<Registry<ExtendedDimensionData>> EXTENDED_DIMENSION_DATA =
        ResourceKey.createRegistryKey(ResourceLocationHelper.mod("extended_dimension_data"));

    @SubscribeEvent
    public static void registerForDatapacks(DataPackRegistryEvent.NewRegistry event)
    {
        event.dataPackRegistry(TOTEM_KEY, TotemOfBirthType.CODEC, TotemOfBirthType.CODEC);
        event.dataPackRegistry(EXTENDED_DIMENSION_DATA, ExtendedDimensionData.CODEC, ExtendedDimensionData.CODEC);
    }

    public static Optional<Holder.Reference<ExtendedDimensionData>> getExtendedDataOf(ResourceKey<Level> dimension, HolderLookup.Provider provider)
    {
        HolderLookup.RegistryLookup<ExtendedDimensionData> registryLookup = provider.lookupOrThrow(EXTENDED_DIMENSION_DATA);
        ResourceKey<ExtendedDimensionData> key = ResourceKey.create(EXTENDED_DIMENSION_DATA, dimension.location());
        return registryLookup.get(key);
    }
}
