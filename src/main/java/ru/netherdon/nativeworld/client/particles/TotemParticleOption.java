package ru.netherdon.nativeworld.client.particles;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FastColor;
import ru.netherdon.nativeworld.misc.NWExtraCodecs;

public record TotemParticleOption(ParticleType<TotemParticleOption> type, int color) implements ParticleOptions
{
    public static MapCodec<TotemParticleOption> codec(ParticleType<TotemParticleOption> type)
    {
        return NWExtraCodecs.RGB_COLOR_CODEC.xmap(
            (color) -> new TotemParticleOption(type, color),
            TotemParticleOption::color
        ).fieldOf("color");
    }

    public static StreamCodec<ByteBuf, TotemParticleOption> streamCodec(ParticleType<TotemParticleOption> type)
    {
        return ByteBufCodecs.VAR_INT.map((color) -> new TotemParticleOption(type, color), TotemParticleOption::color);
    }

    public float getRed()
    { return FastColor.ARGB32.red(this.color) / 255f; }

    public float getGreen()
    { return FastColor.ARGB32.green(this.color) / 255f; }

    public float getBlue()
    { return FastColor.ARGB32.blue(this.color) / 255f; }

    @Override
    public ParticleType<?> getType()
    { return this.type; }
}
