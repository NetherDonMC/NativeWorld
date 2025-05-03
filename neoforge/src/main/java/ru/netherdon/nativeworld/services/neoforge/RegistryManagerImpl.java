package ru.netherdon.nativeworld.services.neoforge;

import net.minecraft.core.Registry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.neoforge.registries.RegistryProvider;
import ru.netherdon.nativeworld.registries.IRegistryProvider;

import java.util.ArrayList;
import java.util.List;

public class RegistryManagerImpl
{
    private static final List<DeferredRegister<?>> DEFERRED_REGISTERS = new ArrayList<>();

    public static <T> IRegistryProvider<T> createProvider(Registry<T> registry)
    {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registry, NativeWorld.ID);
        DEFERRED_REGISTERS.add(deferredRegister);
        return new RegistryProvider<>(deferredRegister);
    }

    public static void registerDeferredRegistries(IEventBus modEventBus)
    {
        DEFERRED_REGISTERS.forEach((register) -> register.register(modEventBus));
    }
}
