package ru.netherdon.nativeworld.services.fabric;

import net.minecraft.core.Registry;
import ru.netherdon.nativeworld.fabric.registries.RegistryProvider;
import ru.netherdon.nativeworld.registries.IRegistryProvider;

public class RegistryManagerImpl
{
    public static <T> IRegistryProvider<T> createProvider(Registry<T> registry)
    {
        return new RegistryProvider<>(registry);
    }
}
