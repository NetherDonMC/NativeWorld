package ru.netherdon.nativeworld.neoforge.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.registries.IRegistryProvider;

import java.util.function.Supplier;

public record RegistryProvider<T>(DeferredRegister<T> deferredRegister) implements IRegistryProvider<T>
{
    @SuppressWarnings("unchecked")
    @Override
    public <V extends T> Holder<V> register(String name, Supplier<V> registryObject)
    {
        return (Holder<V>)this.deferredRegister.register(name, registryObject);
    }

    @Override
    public ResourceKey<? extends Registry<T>> getKey()
    {
        return this.deferredRegister.getRegistryKey();
    }
}
