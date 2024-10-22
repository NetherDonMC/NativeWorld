package ru.netherdon.nativeworld.items.totems;

import com.mojang.serialization.Codec;
import ru.netherdon.nativeworld.config.NWCommonConfig;

public record ExtendedDimensionData(boolean isSafe)
{
    public static final Codec<ExtendedDimensionData> CODEC = Codec.BOOL.fieldOf("is_safe")
        .xmap(ExtendedDimensionData::new, ExtendedDimensionData::isSafe).codec();

    public static ExtendedDimensionData getDefault()
    {
        return new ExtendedDimensionData(NWCommonConfig.isDimensionsAreSafe());
    }
}
