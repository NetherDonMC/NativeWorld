package ru.netherdon.nativeworld.services;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import org.jetbrains.annotations.Contract;
import ru.netherdon.nativeworld.registries.IRegistryProvider;

import java.util.HashMap;
import java.util.Map;

public class RegistryManager
{
    private static final Map<Registry<?>, IRegistryProvider<?>> PROVIDERS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> IRegistryProvider<T> getOrCreate(Registry<T> registry)
    {
        if (!PROVIDERS.containsKey(registry))
        {
            IRegistryProvider<T> provider = createProvider(registry);
            PROVIDERS.put(registry, provider);
            return provider;
        }

        return (IRegistryProvider<T>) PROVIDERS.get(registry);
    }

    @ExpectPlatform
    @Contract(pure = true)
    private static <T> IRegistryProvider<T> createProvider(Registry<T> registry)
    {
        throw new UnsupportedOperationException();
    }
}
