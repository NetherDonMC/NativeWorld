package ru.netherdon.nativeworld.config;

import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import ru.netherdon.nativeworld.NativeWorld;

import static ru.netherdon.nativeworld.config.ConfigHelper.*;

public class NWCommonConfig
{
    public static final String FILE_NAME = NativeWorld.ID + "/common.toml";
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final Pair<NWCommonConfig, ModConfigSpec> PAIR = BUILDER.configure(NWCommonConfig::new);

    private final ModConfigSpec.BooleanValue dimensionsAreSafeByDefault;
    private final SpatialDecayEffectConfigData spatialDecayData;

    private NWCommonConfig(ModConfigSpec.Builder builder)
    {
        builder.push("SpatialDecay");

        this.dimensionsAreSafeByDefault = builder
            .comment(
                "If true, all dimensions that does not have the tags nativeworld:safe or nativeworld:unsafe will be safe. Unsafe otherwise.",
                defaultValueInfo(ConfigConstants.DIMENSION_SAFE_BY_DEFAULT)
            )
            .define("dimensionsAreSafe", ConfigConstants.DIMENSION_SAFE_BY_DEFAULT);

        this.spatialDecayData = new SpatialDecayEffectConfigData(builder);
        builder.pop();
    }

    public static boolean isDimensionsAreSafe()
    { return get().dimensionsAreSafeByDefault.get(); }

    public static SpatialDecayEffectConfigData spatialDecay()
    { return get().spatialDecayData; }

    public static NWCommonConfig get()
    { return PAIR.getLeft(); }

    public static void register(ModContainer modContainer)
    { modContainer.registerConfig(ModConfig.Type.COMMON, PAIR.getRight(), FILE_NAME); }

    public static class SpatialDecayEffectConfigData
    {
        private final ModConfigSpec.IntValue startDegree;
        private final ModConfigSpec.IntValue maxAmplifier;
        private final ModConfigSpec.IntValue amplifierInterval;
        private final ModConfigSpec.IntValue accumulationRate;
        private final ModConfigSpec.IntValue recoveryRate;
        private Integer maxDegree = null;

        public SpatialDecayEffectConfigData(ModConfigSpec.Builder builder)
        {
            this.accumulationRate = builder
                .comment(
                    "Accumulation rate of spatial decay in unsafe dimensions per tick",
                    defaultValueInfo(ConfigConstants.ACCUMULATION_RATE)
                )
                .defineInRange("accumulationRate", ConfigConstants.ACCUMULATION_RATE, 1, Integer.MAX_VALUE);

            this.recoveryRate = builder
                .comment(
                    "Reduction rate of spatial decay in safe dimensions per tick",
                    defaultValueInfo(ConfigConstants.RECOVERY_RATE)
                )
                .defineInRange("recoveryRate", ConfigConstants.RECOVERY_RATE, 1, Integer.MAX_VALUE);

            builder.push("Effect");

            this.startDegree = builder
                .comment(
                    "Degree of spatial decay with which the effect begins to apply",
                    defaultValueInfo(ConfigConstants.START_DEGREE)
                )
                .defineInRange("startDegree", ConfigConstants.START_DEGREE, 1, Integer.MAX_VALUE);

            this.maxAmplifier = builder
                .comment(defaultValueInfo(ConfigConstants.MAX_AMPLIFIER))
                .defineInRange(
                    "maxAmplifier",
                    ConfigConstants.MAX_AMPLIFIER,
                    MobEffectInstance.MIN_AMPLIFIER,
                    MobEffectInstance.MAX_AMPLIFIER
                );

            this.amplifierInterval = builder
                .comment(
                    "Value of the degree of spatial decay for which the effect is amplified",
                    defaultValueInfo(ConfigConstants.AMPLIFIER_INTERVAL)
                )
                .defineInRange("amplifierInterval", ConfigConstants.AMPLIFIER_INTERVAL, 1, Integer.MAX_VALUE);

            builder.pop();
        }

        public int startDegree()
        { return this.startDegree.get(); }

        public int amplifierInterval()
        { return this.amplifierInterval.get(); }

        public int maxAmplifier()
        { return this.maxAmplifier.get(); }

        public int maxDegree()
        {
            if (this.maxDegree == null)
            {
                this.maxDegree = this.startDegree() + this.amplifierInterval() * this.maxAmplifier();
            }

            return this.maxDegree;
        }

        public int accumulationRate()
        { return this.accumulationRate.get(); }

        public int recoveryRate()
        { return this.recoveryRate.get(); }
    }
}
