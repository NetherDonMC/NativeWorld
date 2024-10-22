package ru.netherdon.nativeworld.items.totems;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.WithConditions;
import ru.netherdon.nativeworld.misc.NWExtraCodecs;
import ru.netherdon.nativeworld.registries.NWRegistries;

import java.util.List;
import java.util.Optional;

public record TotemOfBirthType(
    List<DimensionMatcher> matchers,
    Optional<ResourceLocation> model,
    int particleColor,
    Component name
)
{
    public static final int DEFAULT_PARTICLE_COLOR = FastColor.ARGB32.colorFromFloat(1f, 0.9f, 0.9f, 0.9f);

    public static final Codec<TotemOfBirthType> CODEC = RecordCodecBuilder.create((instance) ->
        instance.group(
            DimensionMatcher.CODEC.listOf().fieldOf("dimensions").forGetter(TotemOfBirthType::matchers),
            ResourceLocation.CODEC.optionalFieldOf("model").forGetter(TotemOfBirthType::model),
            NWExtraCodecs.RGB_COLOR_CODEC.fieldOf("particle_color").orElse(DEFAULT_PARTICLE_COLOR)
                .forGetter(TotemOfBirthType::particleColor),
            ComponentSerialization.CODEC.fieldOf("name").forGetter(TotemOfBirthType::name)
        ).apply(instance, TotemOfBirthType::new)
    );

    public static final Codec<Holder<TotemOfBirthType>> HOLDER_CODEC =
        RegistryFixedCodec.create(NWRegistries.TOTEM_KEY);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<TotemOfBirthType>> STREAM_CODEC =
        ByteBufCodecs.holderRegistry(NWRegistries.TOTEM_KEY);

    public static final Codec<Optional<WithConditions<TotemOfBirthType>>> CONDITIONAL_CODEC =
        ConditionalOps.createConditionalCodecWithConditions(CODEC);

    public boolean canUseInDimension(ResourceKey<Level> dimension)
    {
        return this.matchers.stream().anyMatch((matcher) -> matcher.match(dimension));
    }
}
