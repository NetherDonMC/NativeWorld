package ru.netherdon.nativeworld.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.NativeWorld;

@EventBusSubscriber(modid = NativeWorld.ID)
public final class NWPotions
{
    public static final DeferredRegister<Potion> REGISTER =
        DeferredRegister.create(Registries.POTION, NativeWorld.ID);

    public static final DeferredHolder<Potion, Potion> SPATIAL_RESISTANCE =
        REGISTER.register("spatial_resistance",
            () -> new Potion(new MobEffectInstance(NWMobEffects.SPATIAL_RESISTANCE, 3000))
        );

    public static final DeferredHolder<Potion, Potion> MEDIUM_SPATIAL_RESISTANCE =
        REGISTER.register("medium_spatial_resistance",
            () -> new Potion("spatial_resistance", new MobEffectInstance(NWMobEffects.SPATIAL_RESISTANCE, 6000))
        );

    public static final DeferredHolder<Potion, Potion> LONG_SPATIAL_RESISTANCE =
        REGISTER.register("long_spatial_resistance",
            () -> new Potion("spatial_resistance", new MobEffectInstance(NWMobEffects.SPATIAL_RESISTANCE, 9600))
        );

    @SubscribeEvent
    public static void registerRecipes(RegisterBrewingRecipesEvent event)
    {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addStartMix(NWItems.SPATIAL_MIX.get(), SPATIAL_RESISTANCE);
        builder.addMix(SPATIAL_RESISTANCE, Items.REDSTONE, MEDIUM_SPATIAL_RESISTANCE);
        builder.addMix(MEDIUM_SPATIAL_RESISTANCE, Items.REDSTONE, LONG_SPATIAL_RESISTANCE);
    }
}
