package ru.netherdon.nativeworld.config;

import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import ru.netherdon.nativeworld.NativeWorld;

import static ru.netherdon.nativeworld.config.ConfigHelper.defaultValueInfo;
import static ru.netherdon.nativeworld.misc.TranslationHelper.*;

public class NWClientConfig
{
    public static final String FILE_NAME = NativeWorld.ID + "/client.toml";
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final Pair<NWClientConfig, ModConfigSpec> PAIR = BUILDER.configure(NWClientConfig::new);

    private static final String CONFIG = "config";
    private static final String SPATIAL_DECAY_OUTLINE = "spatial_decay_outline";

    private final SpatialDecayOutline spatialDecayOutline;

    private NWClientConfig(ModConfigSpec.Builder builder)
    {
        builder.push("GUI");
        builder.push("SpatialDecayOutline");

        this.spatialDecayOutline = new SpatialDecayOutline(
            builder.comment(defaultValueInfo(ConfigConstants.SPATIAL_DECAY_GUI_ENABLED))
                .translation(key(CONFIG, SPATIAL_DECAY_OUTLINE, "enabled"))
                .define("enabled", ConfigConstants.SPATIAL_DECAY_GUI_ENABLED),

            builder.comment(defaultValueInfo(ConfigConstants.SPATIAL_DECAY_GUI_ANIMATION_ENABLED))
                .translation(key(CONFIG, SPATIAL_DECAY_OUTLINE, "animation_enabled"))
                .define("animationEnabled", ConfigConstants.SPATIAL_DECAY_GUI_ANIMATION_ENABLED),

            builder.comment(defaultValueInfo(ConfigConstants.SPATIAL_DECAY_GUI_OPACITY0))
                .translation(key(CONFIG, SPATIAL_DECAY_OUTLINE, "back_layer_opacity"))
                .defineInRange("backLayerOpacity", ConfigConstants.SPATIAL_DECAY_GUI_OPACITY0, 0, 100),

            builder.comment(defaultValueInfo(ConfigConstants.SPATIAL_DECAY_GUI_OPACITY1))
                .translation(key(CONFIG, SPATIAL_DECAY_OUTLINE, "front_layer_opacity"))
                .defineInRange("frontLayerOpacity", ConfigConstants.SPATIAL_DECAY_GUI_OPACITY1, 0, 100)
        );

        builder.pop(2);
    }

    public static NWClientConfig get()
    {
        return PAIR.getLeft();
    }

    public static IConfigSpec getSpec()
    {
        return PAIR.getRight();
    }

    public SpatialDecayOutline spatialDecayOutline()
    {
        return this.spatialDecayOutline;
    }

    public record SpatialDecayOutline(
        ModConfigSpec.BooleanValue enabled,
        ModConfigSpec.BooleanValue animationEnabled,
        ModConfigSpec.IntValue backLayerOpacity,
        ModConfigSpec.IntValue frontLayerOpacity
    )
    {
        public boolean enabledValue() { return this.enabled.get(); }
        public boolean animationEnabledValue() { return this.animationEnabled.get(); }
        public float backLayerOpacityValue() { return this.backLayerOpacity.get() / 100f; }
        public float frontLayerOpacityValue() { return this.frontLayerOpacity.get() / 100f; }
    }
}
