package ru.netherdon.nativeworld.registries;

import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.apache.commons.compress.utils.Lists;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.services.RegistryManager;

import java.util.List;
import java.util.Map;

public final class NWCreativeTabs
{
    private static final IRegistryProvider<CreativeModeTab> PROVIDER =
        RegistryManager.getOrCreate(BuiltInRegistries.CREATIVE_MODE_TAB);

    public static final Holder<CreativeModeTab> COMMON = PROVIDER.register("common",
        () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup.nativeworld"))
            .icon(() -> new ItemStack(NWItems.TOTEM_OF_BIRTH.value()))
            .displayItems((parameters, output) ->
            {
                output.accept(NWItems.TOTEM_OF_BIRTH_FRAGMENT.value());
                output.accept(NWItems.SPATIAL_MIX.value());
                output.accept(NWItems.SPATIAL_DECAY_METER.value());
                output.accept(NWItems.TOTEM_OF_BIRTH.value());

                acceptTotems(parameters, output);

                acceptPotionStacks(
                    output,
                    NWPotions.SPATIAL_RESISTANCE,
                    NWPotions.MEDIUM_SPATIAL_RESISTANCE,
                    NWPotions.LONG_SPATIAL_RESISTANCE
                );
            }).build()
        );

    private static void acceptTotems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output)
    {
        HolderLookup.RegistryLookup<TotemOfBirthType> totemRegistry = parameters.holders().lookupOrThrow(NWRegistryKeys.TOTEM_KEY);
        HolderSet<TotemOfBirthType> ordered = totemRegistry.get(NWTags.Totems.CREATIVE_TAB_ORDER)
            .map((a) -> (HolderSet<TotemOfBirthType>)a)
            .orElseGet(HolderSet::direct);

        for (Holder<TotemOfBirthType> totem : ordered)
        {
            output.accept(createTotemItem(totem));
        }

        totemRegistry.listElements().forEach((totem) ->
        {
            if (ordered.contains(totem))
            {
                return;
            }

            output.accept(createTotemItem(totem));
        });
    }

    private static ItemStack createTotemItem(Holder<TotemOfBirthType> totem)
    {
        TotemContent totemContent = new TotemContent(totem);
        return NWItems.TOTEM_OF_BIRTH.value().withContent(totemContent);
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

    public static void initialize() {}
}
