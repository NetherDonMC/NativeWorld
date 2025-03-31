package ru.netherdon.nativeworld.items.totems;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import ru.netherdon.nativeworld.misc.NWExtraCodecs;

import java.util.Collections;
import java.util.List;

public class TotemParticleColor
{
    public static final Codec<TotemParticleColor> CODEC = Codec.withAlternative(
        Value.CODEC.listOf(1, Integer.MAX_VALUE),
        Value.CODEC,
        Collections::singletonList
    ).xmap(
        TotemParticleColor::new,
        (color) -> color.values
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, TotemParticleColor> STREAM_CODEC = Value.STREAM_CODEC.apply(ByteBufCodecs.list())
        .map(TotemParticleColor::new, (color) -> color.values);

    private final List<Value> values;
    private final int totalWeight;

    private List<Integer> colors;

    private TotemParticleColor(List<Value> values)
    {
        this.values = values;
        this.totalWeight = values.stream().mapToInt(Value::weight).sum();
    }

    public int getRandomColor(RandomSource random)
    {
        return this.getRandomColor(random.nextFloat());
    }

    public int getRandomColor(float randomValue)
    {
        float weight = (float)this.totalWeight * randomValue;
        int w = 0;
        for (Value value : this.values)
        {
            w += value.weight;
            if (weight < w)
            {
                return value.value;
            }
        }

        throw new IllegalArgumentException("No color found for random value " + randomValue);
    }

    public List<Integer> getColors()
    {
        if (this.colors == null)
        {
            this.colors = this.values.stream().map(Value::value).toList();
        }

        return this.colors;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static TotemParticleColor of(int color)
    {
        return builder().add(color).build();
    }

    private record Value(int value, int weight)
    {
        public static final int DEFAULT_WEIGHT = 100;

        public static final Codec<Value> FULL_CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                NWExtraCodecs.RGB_COLOR_CODEC.fieldOf("value").forGetter(Value::value),
                Codec.INT.fieldOf("weight").orElse(DEFAULT_WEIGHT).forGetter(Value::weight)
            ).apply(instance, Value::new)
        );

        public static final Codec<Value> CODEC = Codec.withAlternative(
            FULL_CODEC,
            NWExtraCodecs.RGB_COLOR_CODEC,
            (color) -> new Value(color, DEFAULT_WEIGHT)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, Value> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, Value::value,
            ByteBufCodecs.VAR_INT, Value::weight,
            Value::new
        );
    }

    public static class Builder
    {
        private final ImmutableList.Builder<Value> values = ImmutableList.builder();

        private Builder() {}

        public Builder add(int color, int weight)
        {
            values.add(new Value(color, weight));
            return this;
        }

        public Builder add(int color)
        {
            return this.add(color, Value.DEFAULT_WEIGHT);
        }

        public TotemParticleColor build()
        {
            return new TotemParticleColor(this.values.build());
        }
    }
}
