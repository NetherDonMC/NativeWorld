package ru.netherdon.nativeworld.client.particles;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import ru.netherdon.nativeworld.items.totems.TotemParticleColor;

public record TotemParticleOption(ParticleType<TotemParticleOption> type, TotemParticleColor color) implements ParticleOptions
{
    public static MapCodec<TotemParticleOption> codec(ParticleType<TotemParticleOption> type)
    {
        return TotemParticleColor.CODEC.xmap(
            (color) -> new TotemParticleOption(type, color),
            TotemParticleOption::color
        ).fieldOf("color");
    }

    public static StreamCodec<RegistryFriendlyByteBuf, TotemParticleOption> streamCodec(ParticleType<TotemParticleOption> type)
    {
        return TotemParticleColor.STREAM_CODEC.map((color) -> new TotemParticleOption(type, color), TotemParticleOption::color);
    }

    @Override
    public ParticleType<?> getType()
    {
        return this.type;
    }
}
