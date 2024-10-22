package ru.netherdon.nativeworld.registries;

import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.compress.utils.Lists;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;

import java.util.List;
import java.util.Map;

public final class NWCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> REGISTER =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NativeWorld.ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COMMON =
        REGISTER.register("common", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.nativeworld"))
            .icon(NWItems.TOTEM_OF_BIRTH_FRAGMENT::toStack)
            .displayItems((parameters, output) ->
            {
                output.accept(NWItems.TOTEM_OF_BIRTH_FRAGMENT.get());
                output.accept(NWItems.SPATIAL_MIX.get());
                output.accept(NWItems.SPATIAL_DECAY_METER.get());
                output.accept(NWItems.TOTEM_OF_BIRTH.get());

                acceptTotems(parameters, output);

                acceptPotionStacks(
                    output,
                    NWPotions.SPATIAL_RESISTANCE,
                    NWPotions.LONG_SPATIAL_RESISTANCE
                );
            }).build()
        );

    private static void acceptTotems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output)
    {
        HolderLookup.RegistryLookup<TotemOfBirthType> totemRegistry = parameters.holders().lookupOrThrow(NWRegistries.TOTEM_KEY);
        HolderSet<TotemOfBirthType> ordered = totemRegistry.get(NWTags.Totems.TOTEM)
            .map((a) -> (HolderSet<TotemOfBirthType>)a)
            .orElseGet(HolderSet::direct);

        for (Holder<TotemOfBirthType> totem : ordered)
        {
            output.accept(createTotemItem(totem));
        }

        totemRegistry.listElements().forEach((totem) ->
        {
            output.accept(createTotemItem(totem));
        });
    }

    private static ItemStack createTotemItem(Holder<TotemOfBirthType> totem)
    {
        TotemContent totemContent = new TotemContent(totem);
        return NWItems.TOTEM_OF_BIRTH.get().withContent(totemContent);
    }

    @SafeVarargs
    private static void acceptPotionStacks(CreativeModeTab.Output output, Holder<Potion>... potions)
    {
        Map<Item, List<ItemStack>> potionStacks = Maps.newLinkedHashMap();
        potionStacks.put(Items.POTION, Lists.newArrayList());
        potionStacks.put(Items.SPLASH_POTION, Lists.newArrayList());
        potionStacks.put(Items.LINGERING_POTION, Lists.newArrayList());
        potionStacks.put(Items.TIPPED_ARROW, Lists.newArrayList());

        for (Holder<Potion> potion : potions)
        {
            for (var entry : potionStacks.entrySet())
            {
                entry.getValue().add(
                    PotionContents.createItemStack(entry.getKey(), potion)
                );
            }
        }

        potionStacks.values().forEach(output::acceptAll);
    }
}
