package ru.netherdon.nativeworld.registries;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.particles.TotemParticleOption;

public final class NWPaticleTypes
{
    public static final DeferredRegister<ParticleType<?>> REGISTER = DeferredRegister.create(Registries.PARTICLE_TYPE, NativeWorld.ID);

    public static final DeferredHolder<ParticleType<?>, ParticleType<TotemParticleOption>> TOTEM_OF_BIRTH =
        REGISTER.register("totem_of_birth", () -> new ParticleType<TotemParticleOption>(false) {
            @Override
            public MapCodec<TotemParticleOption> codec()
            { return TotemParticleOption.codec(this); }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, TotemParticleOption> streamCodec()
            { return TotemParticleOption.streamCodec(this); }
        });
}
