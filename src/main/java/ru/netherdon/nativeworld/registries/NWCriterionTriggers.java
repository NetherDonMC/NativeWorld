package ru.netherdon.nativeworld.registries;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.UnsafeDimensionTrigger;

public final class NWCriterionTriggers
{
    public static final DeferredRegister<CriterionTrigger<?>> REGISTER =
        DeferredRegister.create(Registries.TRIGGER_TYPE, NativeWorld.ID);

    public static final DeferredHolder<CriterionTrigger<?>, UnsafeDimensionTrigger> UNSAFE_DIMENSION =
        REGISTER.register("unsafe_dimension", UnsafeDimensionTrigger::new);
}
