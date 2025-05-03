package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public interface IRegistryProvider<T>
{
    public <V extends T> Holder<V> register(String name, Supplier<V> registryObject);
    public ResourceKey<? extends Registry<T>> getKey();
}
