package ru.netherdon.nativeworld.neoforge.events;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.registries.NWPotions;

@EventBusSubscriber(modid = NativeWorld.ID)
public final class PotionEventHandler
{
    @SubscribeEvent
    public static void registerRecipes(RegisterBrewingRecipesEvent event)
    {
        final PotionBrewing.Builder builder = event.getBuilder();
        NWPotions.registerRecipes(new NWPotions.IRecipeRegister()
        {
            @Override
            public void addStartMix(Item ingredient, Holder<Potion> result)
            {
                builder.addStartMix(ingredient, result);
            }

            @Override
            public void addMix(Holder<Potion> input, Item ingredient, Holder<Potion> result)
            {
                builder.addMix(input, ingredient, result);
            }
        });
    }
}
