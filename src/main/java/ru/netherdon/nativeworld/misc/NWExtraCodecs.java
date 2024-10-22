package ru.netherdon.nativeworld.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.FastColor;

public final class NWExtraCodecs
{
    public static final Codec<Float> NON_NEGATIVE_FLOAT = Codec.FLOAT.validate((value) ->
        value >= 0 ? DataResult.success(value) : DataResult.error(
            () -> "Value must be non-negative: " + value
        )
    );

    public static final Codec<Integer> RGB_COLOR_CODEC = Codec.withAlternative(Codec.INT, ExtraCodecs.VECTOR3F, (vec3f) ->
        FastColor.ARGB32.colorFromFloat(1f, vec3f.x(), vec3f.y(), vec3f.z())
    );
}
