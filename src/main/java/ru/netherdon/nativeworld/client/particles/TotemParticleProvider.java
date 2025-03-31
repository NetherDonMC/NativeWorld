package ru.netherdon.nativeworld.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.util.FastColor;
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
        int color = type.color().getRandomColor(random);
        float red = (float)FastColor.ARGB32.red(color) / 255;
        float green = (float)FastColor.ARGB32.green(color) / 255;
        float blue = (float)FastColor.ARGB32.blue(color) / 255;
        particle.setColor(red, green, blue);

        return particle;
    }
}
