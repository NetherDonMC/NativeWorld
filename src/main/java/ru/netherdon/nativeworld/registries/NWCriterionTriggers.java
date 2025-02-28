package ru.netherdon.nativeworld.registries;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.UnsafeDimensionTrigger;
import ru.netherdon.nativeworld.items.totems.UsedTotemOfBirthTrigger;

public final class NWCriterionTriggers
{
    public static final DeferredRegister<CriterionTrigger<?>> REGISTER =
        DeferredRegister.create(Registries.TRIGGER_TYPE, NativeWorld.ID);

    public static final DeferredHolder<CriterionTrigger<?>, UnsafeDimensionTrigger> UNSAFE_DIMENSION =
        REGISTER.register("unsafe_dimension", UnsafeDimensionTrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, UsedTotemOfBirthTrigger> USED_TOTEM_OF_BIRTH =
        REGISTER.register("used_totem_of_birth", UsedTotemOfBirthTrigger::new);
}
