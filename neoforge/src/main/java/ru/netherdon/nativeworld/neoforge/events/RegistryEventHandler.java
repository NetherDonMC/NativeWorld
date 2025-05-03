package ru.netherdon.nativeworld.neoforge.events;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.registries.NWRegistries;

@EventBusSubscriber(modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.MOD)
public final class RegistryEventHandler
{
    @SubscribeEvent
    public static void registerForDatapacks(DataPackRegistryEvent.NewRegistry event)
    {
        NWRegistries.registerDataPackRegistry(new NWRegistries.DataPackRegistryRegister()
        {
            @Override
            public <T> void register(ResourceKey<Registry<T>> key, Codec<T> codec)
            {
                event.dataPackRegistry(key, codec, codec);
            }
        });
    }
}
