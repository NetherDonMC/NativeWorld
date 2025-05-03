package ru.netherdon.nativeworld.registries;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.particles.TotemParticleOption;
import ru.netherdon.nativeworld.services.RegistryManager;

public final class NWParticleTypes
{
    private static final IRegistryProvider<ParticleType<?>> PROVIDER = RegistryManager.getOrCreate(BuiltInRegistries.PARTICLE_TYPE);

    public static final Holder<ParticleType<TotemParticleOption>> TOTEM_OF_BIRTH =
        PROVIDER.register("totem_of_birth", () -> new ParticleType<TotemParticleOption>(false) {
            @Override
            public MapCodec<TotemParticleOption> codec()
            {
                return TotemParticleOption.codec(this);
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, TotemParticleOption> streamCodec()
            {
                return TotemParticleOption.streamCodec(this);
            }
        });

    public static void initialize() {}
}
