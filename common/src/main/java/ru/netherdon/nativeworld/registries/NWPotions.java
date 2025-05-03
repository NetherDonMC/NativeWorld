package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.services.RegistryManager;

public final class NWPotions
{
    private static final IRegistryProvider<Potion> PROVIDER = RegistryManager.getOrCreate(BuiltInRegistries.POTION);

    public static final Holder<Potion> SPATIAL_RESISTANCE =
        PROVIDER.register("spatial_resistance",
            () -> new Potion(new MobEffectInstance(NWMobEffects.SPATIAL_RESISTANCE, 3000))
        );

    public static final Holder<Potion> MEDIUM_SPATIAL_RESISTANCE =
        PROVIDER.register("medium_spatial_resistance",
            () -> new Potion("spatial_resistance", new MobEffectInstance(NWMobEffects.SPATIAL_RESISTANCE, 6000))
        );

    public static final Holder<Potion> LONG_SPATIAL_RESISTANCE =
        PROVIDER.register("long_spatial_resistance",
            () -> new Potion("spatial_resistance", new MobEffectInstance(NWMobEffects.SPATIAL_RESISTANCE, 9600))
        );



    public static void registerRecipes(IRecipeRegister register)
    {
        register.addStartMix(NWItems.SPATIAL_MIX.value(), SPATIAL_RESISTANCE);
        register.addMix(SPATIAL_RESISTANCE, Items.REDSTONE, MEDIUM_SPATIAL_RESISTANCE);
        register.addMix(MEDIUM_SPATIAL_RESISTANCE, Items.REDSTONE, LONG_SPATIAL_RESISTANCE);
    }

    public static void initialize() {}

    public interface IRecipeRegister
    {
        public void addStartMix(Item ingredient, Holder<Potion> result);
        public void addMix(Holder<Potion> input, Item ingredient, Holder<Potion> result);
    }
}
