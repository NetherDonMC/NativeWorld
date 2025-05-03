package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.effects.SpatialDecayMobEffect;
import ru.netherdon.nativeworld.services.RegistryManager;

public final class NWMobEffects
{
    private static final IRegistryProvider<MobEffect> PROVIDER = RegistryManager.getOrCreate(BuiltInRegistries.MOB_EFFECT);

    public static final Holder<MobEffect> SPATIAL_DECAY =
        PROVIDER.register("spatial_decay", () -> new SpatialDecayMobEffect(MobEffectCategory.HARMFUL, 0xA406FF));

    public static final Holder<MobEffect> SPATIAL_RESISTANCE =
        PROVIDER.register("spatial_resistance", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xE53ED5) {});

    public static void initialize() {}
}
