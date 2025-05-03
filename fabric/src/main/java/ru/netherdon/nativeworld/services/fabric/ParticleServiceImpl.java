package ru.netherdon.nativeworld.services.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;

@Environment(EnvType.CLIENT)
public class ParticleServiceImpl
{
    public static TotemParticle createTotemParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet)
    {
        return new TotemParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
    }
}
