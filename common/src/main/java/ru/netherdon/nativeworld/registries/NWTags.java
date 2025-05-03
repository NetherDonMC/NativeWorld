package ru.netherdon.nativeworld.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.dimension.DimensionType;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public final class NWTags
{
    public static final class Totems
    {
        public static final TagKey<TotemOfBirthType> CREATIVE_TAB_ORDER = mod("creative_tab_order");

        private static TagKey<TotemOfBirthType> mod(String id)
        {
            return TagKey.create(NWRegistryKeys.TOTEM_KEY, ResourceLocationHelper.mod(id));
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
}
