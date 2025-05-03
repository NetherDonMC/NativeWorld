package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public final class NWRegistryKeys
{
    public static final ResourceKey<Registry<TotemOfBirthType>> TOTEM_KEY =
        ResourceKey.createRegistryKey(ResourceLocationHelper.mod("totem"));
}
