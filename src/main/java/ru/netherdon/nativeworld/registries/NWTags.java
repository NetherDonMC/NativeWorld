package ru.netherdon.nativeworld.registries;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public final class NWTags
{
    public static final class Totems
    {
        public static final TagKey<TotemOfBirthType> TOTEM = ntt("creative_tab_order");

        private static TagKey<TotemOfBirthType> ntt(String id)
        {
            return TagKey.create(NWRegistries.TOTEM_KEY, ResourceLocationHelper.mod(id));
        }
    }

    public static <T> boolean contains(RegistryAccess access, TagKey<T> tag, ResourceKey<T> key)
    {
        return access.registryOrThrow(tag.registry()).getHolderOrThrow(key).is(tag);
    }
}
