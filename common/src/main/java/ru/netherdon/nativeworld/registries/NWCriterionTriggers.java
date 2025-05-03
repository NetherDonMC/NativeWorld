package ru.netherdon.nativeworld.registries;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.UnsafeDimensionTrigger;
import ru.netherdon.nativeworld.items.totems.UsedTotemOfBirthTrigger;
import ru.netherdon.nativeworld.services.RegistryManager;

public final class NWCriterionTriggers
{
    private static final IRegistryProvider<CriterionTrigger<?>> PROVIDER =
        RegistryManager.getOrCreate(BuiltInRegistries.TRIGGER_TYPES);

    public static final Holder<UnsafeDimensionTrigger> UNSAFE_DIMENSION =
        PROVIDER.register("unsafe_dimension", UnsafeDimensionTrigger::new);

    public static final Holder<UsedTotemOfBirthTrigger> USED_TOTEM_OF_BIRTH =
        PROVIDER.register("used_totem_of_birth", UsedTotemOfBirthTrigger::new);

    public static void initialize() {}
}
