package ru.netherdon.nativeworld.registries;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.TotemOfBirthItem;

public final class NWItems
{
    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(NativeWorld.ID);

    public static final DeferredItem<Item> TOTEM_OF_BIRTH_FRAGMENT =
        REGISTER.registerSimpleItem("totem_of_birth_fragment");

    public static final DeferredItem<Item> SPATIAL_MIX =
        REGISTER.registerSimpleItem("spatial_mix");

    public static final DeferredItem<TotemOfBirthItem> TOTEM_OF_BIRTH =
        REGISTER.registerItem(
            "totem_of_birth",
            TotemOfBirthItem::new,
            new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON)
        );

    public static final DeferredItem<Item> SPATIAL_DECAY_METER =
        REGISTER.registerSimpleItem("spatial_decay_meter", new Item.Properties().stacksTo(1));
}
