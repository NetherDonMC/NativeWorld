package ru.netherdon.nativeworld.registries;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.dimension.DimensionType;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public final class NWTags
{
    public static final class Totems
    {
        public static final TagKey<TotemOfBirthType> TOTEM = mod("creative_tab_order");

        private static TagKey<TotemOfBirthType> mod(String id)
        {
            return TagKey.create(NWRegistries.TOTEM_KEY, ResourceLocationHelper.mod(id));
        }
    }

    public static final class DimensionTypes
    {
        public static final TagKey<DimensionType> SAFE = mod("safe");
        public static final TagKey<DimensionType> UNSAFE = mod("unsafe");

        private static TagKey<DimensionType> mod(String id)
        {
            return TagKey.create(Registries.DIMENSION_TYPE, ResourceLocationHelper.mod(id));
        }
    }

    public static <T> boolean contains(RegistryAccess access, TagKey<T> tag, ResourceKey<T> key)
    {
        return access.registryOrThrow(tag.registry()).getHolderOrThrow(key).is(tag);
    }
}
