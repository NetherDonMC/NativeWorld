package ru.netherdon.nativeworld.services;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;
import org.jetbrains.annotations.Contract;

@Environment(EnvType.CLIENT)
public class ParticleService
{
    @ExpectPlatform
    @Contract(pure = true)
    public static TotemParticle createTotemParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet)
    {
        throw new UnsupportedOperationException();
    }
}
