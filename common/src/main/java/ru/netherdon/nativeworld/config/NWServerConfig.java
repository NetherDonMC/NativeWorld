package ru.netherdon.nativeworld.config;

import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import ru.netherdon.nativeworld.NativeWorld;

import static ru.netherdon.nativeworld.config.ConfigHelper.defaultValueInfo;
import static ru.netherdon.nativeworld.misc.TranslationHelper.*;

public class NWServerConfig
{
    public static final String FILE_NAME = NativeWorld.ID + "/server.toml";
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final Pair<NWServerConfig, ModConfigSpec> PAIR = BUILDER.configure(NWServerConfig::new);

    private static final String CONFIG = "config";
    private static final String SPATIAL_DECAY = "spatial_decay";

    private final SpatialDecayData spatialDecayData;

    private NWServerConfig(ModConfigSpec.Builder builder)
    {
        builder.push("SpatialDecay");

        this.spatialDecayData = new SpatialDecayData(
            builder
                .worldRestart()
                .comment(
                    "If true, all dimensions that does not have the tags nativeworld:safe or nativeworld:unsafe will be safe. Unsafe otherwise.",
                    defaultValueInfo(ConfigConstants.DIMENSION_SAFE_BY_DEFAULT)
                )
                .translation(key(CONFIG, SPATIAL_DECAY, "dimensions_are_safe"))
                .define("dimensionsAreSafe", ConfigConstants.DIMENSION_SAFE_BY_DEFAULT),

            builder
                .worldRestart()
                .comment(
                    "Accumulation rate of spatial decay in unsafe dimensions per tick",
                    defaultValueInfo(ConfigConstants.ACCUMULATION_RATE)
                )
                .translation(key(CONFIG, SPATIAL_DECAY, "accumulation_rate"))
                .defineInRange("accumulationRate", ConfigConstants.ACCUMULATION_RATE, 1, Integer.MAX_VALUE),

            builder
                .worldRestart()
                .comment(
                    "Reduction rate of spatial decay in safe dimensions per tick",
                    defaultValueInfo(ConfigConstants.RECOVERY_RATE)
                )
                .translation(key(CONFIG, SPATIAL_DECAY, "recovery_rate"))
                .defineInRange("recoveryRate", ConfigConstants.RECOVERY_RATE, 1, Integer.MAX_VALUE),

            builder
                .push("Effect")
                .worldRestart()
                .comment(
                    "Degree of spatial decay with which the effect begins to apply",
                    defaultValueInfo(ConfigConstants.START_DEGREE)
                )
                .translation(key(CONFIG, SPATIAL_DECAY, "effect", "start_degree"))
                .defineInRange("startDegree", ConfigConstants.START_DEGREE, ConfigConstants.START_DEGREE_MIN, Integer.MAX_VALUE),

            builder
                .worldRestart()
                .comment(defaultValueInfo(ConfigConstants.MAX_AMPLIFIER))
                .translation(key(CONFIG, SPATIAL_DECAY, "effect", "max_amplifier"))
                .defineInRange(
                    "maxAmplifier",
                    ConfigConstants.MAX_AMPLIFIER,
                    MobEffectInstance.MIN_AMPLIFIER,
                    MobEffectInstance.MAX_AMPLIFIER
                ),

            builder
                .worldRestart()
                .comment(
                    "Value of the degree of spatial decay for which the effect is amplified",
                    defaultValueInfo(ConfigConstants.AMPLIFIER_INTERVAL)
                )
                .translation(key(CONFIG, SPATIAL_DECAY, "effect", "amplification_period"))
                .defineInRange("amplificationPeriod", ConfigConstants.AMPLIFIER_INTERVAL, 1, Integer.MAX_VALUE)
        );

        builder.pop(2);
    }

    public SpatialDecayData spatialDecay()
    {
        return this.spatialDecayData;
    }

    public static NWServerConfig get()
    {
        return PAIR.getLeft();
    }

    public static IConfigSpec getSpec()
    {
        return PAIR.getRight();
    }

    public record SpatialDecayData(
        ModConfigSpec.BooleanValue unexploredDimensionAreSafe,
        ModConfigSpec.IntValue accumulationRate,
        ModConfigSpec.IntValue recoveryRate,
        ModConfigSpec.IntValue startDegree,
        ModConfigSpec.IntValue maxAmplifier,
        ModConfigSpec.IntValue amplifierInterval
    ) {
        public boolean isUnexploredDimensionAreSafe() { return this.unexploredDimensionAreSafe.getAsBoolean(); }
        public int accumulationRateValue() { return this.accumulationRate.get(); }
        public int recoveryRateValue() { return this.recoveryRate.get(); }
        public int startDegreeValue() { return this.startDegree.get(); }
        public int maxAmplifierValue() { return this.maxAmplifier.get(); }
        public int amplifierIntervalValue() { return this.amplifierInterval.get(); }

        public int maxDegreeValue()
        {
            return this.startDegreeValue() + this.amplifierIntervalValue() * this.maxAmplifierValue();
        }
    }
}
