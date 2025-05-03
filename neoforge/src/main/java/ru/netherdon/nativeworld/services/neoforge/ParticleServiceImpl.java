package ru.netherdon.nativeworld.services.neoforge;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleServiceImpl
{
    public static TotemParticle createTotemParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet)
    {
        return new TotemParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet) {};
    }
}
