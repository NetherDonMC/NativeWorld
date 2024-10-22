package ru.netherdon.nativeworld.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.util.RandomSource;

public record TotemParticleProvider(SpriteSet sprites) implements ParticleProvider<TotemParticleOption>
{
    public Particle createParticle(
        TotemParticleOption type, ClientLevel level,
        double x, double y, double z,
        double xSpeed, double ySpeed, double zSpeed
    )
    {
        TotemParticle particle = new TotemParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites) {};
        RandomSource random = RandomSource.create();
        particle.setColor(
            shiftColor(type.getRed(), random),
            shiftColor(type.getGreen(), random),
            shiftColor(type.getBlue(), random)
        );

        return particle;
    }

    private static float shiftColor(float channel, RandomSource random)
    {
        return Math.clamp(channel + (random.nextFloat() * 0.2f - 0.1f), 0f, 1f);
    }
}
