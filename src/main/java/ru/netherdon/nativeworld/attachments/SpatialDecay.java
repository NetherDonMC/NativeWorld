package ru.netherdon.nativeworld.attachments;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.UnknownNullability;
import ru.netherdon.nativeworld.config.NWCommonConfig;
import ru.netherdon.nativeworld.misc.DimensionHelper;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayDegreePayload;
import ru.netherdon.nativeworld.registries.NWMobEffects;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class SpatialDecay implements INBTSerializable<CompoundTag>
{
    public static final String DEGREE_KEY = "Degree";
    public static final String SAFE_DIMENSIONS_KEY = "SafeDimensions";

    public static final int EFFECT_DURATION = 100;

    private int degree = 0;
    private final Set<ResourceKey<Level>> localSafeDimensions = new HashSet<>();

    private final Player player;

    public SpatialDecay(Player player)
    {
        this.player = player;
    }

    public void setDegree(int value)
    {
        int oldLevel = this.degree;
        this.degree = Math.clamp(value, 0, getMaxDegree());
        if (this.degree != oldLevel && !this.player.level().isClientSide)
        {
            PacketDistributor.sendToAllPlayers(
                new ClientboundSpatialDecayDegreePayload(this.player, this.degree)
            );
        }
    }

    public void increaseDegree(int value)
    {
        this.setDegree(this.getDegree() + value);
    }

    public int getDegree()
    {
        return this.degree;
    }

    public Set<ResourceKey<Level>> getLocalSafeDimensions()
    {
        return this.localSafeDimensions;
    }

    public void copyDataFromClone(SpatialDecay cloneSpatialDecay)
    {
        this.localSafeDimensions.clear();
        this.localSafeDimensions.addAll(
            cloneSpatialDecay.localSafeDimensions
        );
    }

    public boolean isSafeDimension(Level level)
    {
        return DimensionHelper.isSafe(level) || localSafeDimensions.contains(level.dimension());
    }

    public boolean mayApplyEffect()
    {
        return this.degree >= getStartDegree();
    }

    public Holder<MobEffect> getEffectType()
    {
        return NWMobEffects.SPATIAL_DECAY;
    }

    public Optional<MobEffectInstance> tryCreateEffect()
    {
        if (!this.mayApplyEffect())
        {
            return Optional.empty();
        }

        int i = this.degree - getStartDegree();
        int amplifier = i / getAmplifierInterval();
        MobEffectInstance effect = new MobEffectInstance(
            this.getEffectType(),
            EFFECT_DURATION,
            amplifier
        );
        return Optional.of(effect);
    }

    public void tick()
    {
        if (this.player.level().isClientSide)
        {
            return;
        }

        int increment = this.getDegreeIncrement();
        this.increaseDegree(increment);
        this.updateEffect();
    }

    public void updateEffect()
    {
        this.tryCreateEffect().ifPresent(
            (effectIn) ->
            {
                MobEffectInstance activeEffect = this.player.getEffect(this.getEffectType());
                if (activeEffect != null && activeEffect.getDuration() > 20)
                {
                    return;
                }

                this.player.addEffect(effectIn);
            }
        );
    }

    public int getDegreeIncrement()
    {
        Level level = this.player.level();
        if (
            this.isSafeDimension(level)
            || this.isPlayerHasImmunity()
        )
        {
            return -NWCommonConfig.spatialDecay().recoveryRate();
        }

        return this.player.hasEffect(NWMobEffects.SPATIAL_RESISTANCE) ?
            0 : NWCommonConfig.spatialDecay().accumulationRate();
    }

    public boolean isPlayerHasImmunity()
    {
        return this.player.isCreative() || this.player.isSpectator();
    }

    public boolean isPlayerShaking()
    {
        return this.degree > 0 && !this.player.hasEffect(NWMobEffects.SPATIAL_RESISTANCE);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider)
    {
        CompoundTag tag = new CompoundTag();
        tag.putInt(DEGREE_KEY, this.degree);
        ListTag safeDimensionsTag = new ListTag();
        for (ResourceKey<Level> dimension : this.localSafeDimensions)
        {
            safeDimensionsTag.add(StringTag.valueOf(dimension.location().toString()));
        }
        tag.put(SAFE_DIMENSIONS_KEY, safeDimensionsTag);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag)
    {
        this.setDegree(compoundTag.getInt(DEGREE_KEY));
        ListTag safeDimensionsTag = compoundTag.getList(SAFE_DIMENSIONS_KEY, StringTag.TAG_STRING);
        for (Tag tag : safeDimensionsTag)
        {
            StringTag dimensionKeyTag = (StringTag) tag;
            ResourceKey<Level> dimensionKey = ResourceKey.create(
                Registries.DIMENSION,
                ResourceLocation.parse(dimensionKeyTag.getAsString())
            );
            this.localSafeDimensions.add(dimensionKey);
        }
    }

    public static int getMaxDegree()
    {
        return NWCommonConfig.spatialDecay().maxDegree();
    }

    public static int getStartDegree()
    {
        return NWCommonConfig.spatialDecay().startDegree();
    }

    public static int getMaxAplifier()
    {
        return NWCommonConfig.spatialDecay().maxAmplifier();
    }

    public static int getAmplifierInterval()
    {
        return NWCommonConfig.spatialDecay().amplifierInterval();
    }
}
