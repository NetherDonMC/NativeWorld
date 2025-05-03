package ru.netherdon.nativeworld.fabric.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;
import ru.netherdon.nativeworld.registries.IRegistryProvider;

import java.util.function.Supplier;

public record RegistryProvider<T>(Registry<T> registry) implements IRegistryProvider<T>
{
    @SuppressWarnings("unchecked")
    @Override
    public <V extends T> Holder<V> register(String name, Supplier<V> registryObject)
    {
        return (Holder<V>) Registry.registerForHolder(this.registry, ResourceLocationHelper.mod(name), registryObject.get());
    }

    @Override
    public ResourceKey<? extends Registry<T>> getKey()
    {
        return this.registry.key();
    }
}
