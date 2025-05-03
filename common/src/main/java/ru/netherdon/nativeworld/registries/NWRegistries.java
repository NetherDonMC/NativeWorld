package ru.netherdon.nativeworld.registries;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;

public final class NWRegistries
{
    public static void registerDataPackRegistry(DataPackRegistryRegister register)
    {
        register.register(NWRegistryKeys.TOTEM_KEY, TotemOfBirthType.CODEC);
    }

    public interface DataPackRegistryRegister
    {
        public <T> void register(ResourceKey<Registry<T>> key, Codec<T> codec);
    }
}
