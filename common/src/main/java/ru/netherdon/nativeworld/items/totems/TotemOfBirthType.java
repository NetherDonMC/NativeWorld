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
import net.minecraft.world.level.Level;
import ru.netherdon.nativeworld.registries.NWRegistryKeys;

import java.util.List;
import java.util.Optional;

public record TotemOfBirthType(
    List<DimensionMatcher> matchers,
    Optional<ResourceLocation> model,
    TotemParticleColor particleColor,
    Component name
)
{
    public static final TotemParticleColor DEFAULT_PARTICLE_COLOR = TotemParticleColor.builder()
        .add(0xFEC3FD)
        .add(0xCDC3FE)
        .add(0xC3FCFE)
        .add(0xC9FEC3)
        .add(0xFDFEC3)
        .add(0xFEE4C3)
        .add(0xFEC3C3)
        .build();

    public static final Codec<TotemOfBirthType> CODEC = RecordCodecBuilder.create((instance) ->
        instance.group(
            DimensionMatcher.CODEC.listOf().fieldOf("dimensions").forGetter(TotemOfBirthType::matchers),
            ResourceLocation.CODEC.optionalFieldOf("model").forGetter(TotemOfBirthType::model),
            TotemParticleColor.CODEC.fieldOf("particle_color").orElse(DEFAULT_PARTICLE_COLOR)
                .forGetter(TotemOfBirthType::particleColor),
            ComponentSerialization.CODEC.fieldOf("name").forGetter(TotemOfBirthType::name)
        ).apply(instance, TotemOfBirthType::new)
    );

    public static final Codec<Holder<TotemOfBirthType>> HOLDER_CODEC =
        RegistryFixedCodec.create(NWRegistryKeys.TOTEM_KEY);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<TotemOfBirthType>> STREAM_CODEC =
        ByteBufCodecs.holderRegistry(NWRegistryKeys.TOTEM_KEY);

    public boolean canUseInDimension(ResourceKey<Level> dimension)
    {
        return this.matchers.stream().anyMatch((matcher) -> matcher.match(dimension));
    }
}
