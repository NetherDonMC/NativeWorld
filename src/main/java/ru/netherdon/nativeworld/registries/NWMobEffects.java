package ru.netherdon.nativeworld.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.effects.SpatialDecayMobEffect;

public final class NWMobEffects
{
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, NativeWorld.ID);

    public static final DeferredHolder<MobEffect, SpatialDecayMobEffect> SPATIAL_DECAY =
        REGISTER.register("spatial_decay", () -> new SpatialDecayMobEffect(MobEffectCategory.HARMFUL, 0xA406FF));

    public static final DeferredHolder<MobEffect, MobEffect> SPATIAL_RESISTANCE =
        REGISTER.register("spatial_resistance", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xE53ED5) {});
}
