package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

@EventBusSubscriber(modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.MOD)
public final class NWRegistries
{
    public static final ResourceKey<Registry<TotemOfBirthType>> TOTEM_KEY =
        ResourceKey.createRegistryKey(ResourceLocationHelper.mod("totem"));

    @SubscribeEvent
    public static void registerForDatapacks(DataPackRegistryEvent.NewRegistry event)
    {
        event.dataPackRegistry(TOTEM_KEY, TotemOfBirthType.CODEC, TotemOfBirthType.CODEC);
    }
}
