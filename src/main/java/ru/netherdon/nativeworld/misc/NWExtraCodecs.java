package ru.netherdon.nativeworld.misc;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.FastColor;

public final class NWExtraCodecs
{
    public static final Codec<Float> NON_NEGATIVE_FLOAT = Codec.FLOAT.validate((value) ->
        value >= 0 ? DataResult.success(value) : DataResult.error(
            () -> "Value must be non-negative: " + value
        )
    );

    public static final Codec<Integer> RGB_COLOR_CODEC = Codec.either(
        Codec.INT,
        Codec.STRING
    ).comapFlatMap(
        (either) -> either.map(NWExtraCodecs::validateRGBColor, NWExtraCodecs::parseHexString),
        Either::left
    ).orElse(0xFFFFFF);

    private static DataResult<Integer> parseHexString(String value)
    {
        try
        {
            int color = Integer.parseInt(value, 16);
            return validateRGBColor(color);
        }
        catch (NumberFormatException var2)
        {
            return DataResult.error(() -> "Invalid value value: " + value);
        }
    }

    private static DataResult<Integer> validateRGBColor(int color)
    {
        return isValueInRGBRange(color)
            ? DataResult.success(color, Lifecycle.stable())
            : DataResult.error(() -> "Color value out of range: " + color);
    }

    private static boolean isValueInRGBRange(int value)
    {
        return value >= 0 && value <= 0xFFFFFF;
    }
}
