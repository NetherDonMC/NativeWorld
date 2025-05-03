package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.TotemOfBirthItem;
import ru.netherdon.nativeworld.services.RegistryManager;

public final class NWItems
{
    private static final IRegistryProvider<Item> PROVIDER = RegistryManager.getOrCreate(BuiltInRegistries.ITEM);

    public static final Holder<Item> TOTEM_OF_BIRTH_FRAGMENT = simpleItem("totem_of_birth_fragment");

    public static final Holder<Item> SPATIAL_MIX = simpleItem("spatial_mix");

    public static final Holder<Item> SPATIAL_DECAY_METER =
        PROVIDER.register("spatial_decay_meter", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final Holder<TotemOfBirthItem> TOTEM_OF_BIRTH =
        PROVIDER.register(
            "totem_of_birth",
            () -> new TotemOfBirthItem(
                    new Item.Properties()
                        .stacksTo(1)
                        .rarity(Rarity.UNCOMMON)
                )
        );



    public static void initialize() {}

    private static Holder<Item> simpleItem(String name)
    {
        return PROVIDER.register(name, () -> new Item(new Item.Properties()));
    }
}
