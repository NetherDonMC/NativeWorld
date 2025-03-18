package ru.netherdon.nativeworld.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import ru.netherdon.nativeworld.NativeWorld;

import static ru.netherdon.nativeworld.config.ConfigHelper.defaultValueInfo;

public class NWClientConfig
{
    public static final String FILE_NAME = NativeWorld.ID + "/client.toml";
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final Pair<NWClientConfig, ModConfigSpec> PAIR = BUILDER.configure(NWClientConfig::new);

    private final SpatialDecayGUIData spatialDecayGuiData;

    private NWClientConfig(ModConfigSpec.Builder builder)
    {
        builder.push("GUI");
        this.spatialDecayGuiData = new SpatialDecayGUIData(builder);
        builder.pop();
    }

    public static NWClientConfig get()
    {
        return PAIR.getLeft();
    }

    public static SpatialDecayGUIData spatialDecayGui()
    {
        return get().spatialDecayGuiData;
    }

    public static void register(ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.CLIENT, PAIR.getRight(), FILE_NAME);
    }

    public static class SpatialDecayGUIData
    {
        private final ModConfigSpec.BooleanValue enabled;
        private final ModConfigSpec.BooleanValue animationEnabled;
        private final ModConfigSpec.DoubleValue layer0Opacity;
        private final ModConfigSpec.DoubleValue layer1Opacity;

        private SpatialDecayGUIData(ModConfigSpec.Builder builder)
        {
            builder.push("SpatialDecayOutline");

            this.enabled = builder
                .comment(defaultValueInfo(ConfigConstants.SPATIAL_DECAY_GUI_ENABLED))
                .translation(NativeWorld.ID + ".configuration.gui.spatial_decay.enabled")
                .define("enabled", ConfigConstants.SPATIAL_DECAY_GUI_ENABLED);

            this.animationEnabled = builder
                .comment(defaultValueInfo(ConfigConstants.SPATIAL_DECAY_GUI_ANIMATION_ENABLED))
                .translation(NativeWorld.ID + ".configuration.gui.spatial_decay.animation_enabled")
                .define("animationEnabled", ConfigConstants.SPATIAL_DECAY_GUI_ANIMATION_ENABLED);

            this.layer0Opacity = builder
                .comment(defaultValueInfo(ConfigConstants.SPATIAL_DECAY_GUI_LAYER0_OPACITY))
                .translation(NativeWorld.ID + ".configuration.gui.spatial_decay.layer0_opacity")
                .defineInRange("layer0Opacity", ConfigConstants.SPATIAL_DECAY_GUI_LAYER0_OPACITY, 0d, 1d);

            this.layer1Opacity = builder
                .comment(defaultValueInfo(ConfigConstants.SPATIAL_DECAY_GUI_LAYER1_OPACITY))
                .translation(NativeWorld.ID + ".configuration.gui.spatial_decay.layer1_opacity")
                .defineInRange("layer1Opacity", ConfigConstants.SPATIAL_DECAY_GUI_LAYER1_OPACITY, 0d, 1d);

            builder.pop();
        }

        public boolean enabled()
        {
            return this.enabled.get();
        }

        public boolean animationEnabled()
        {
            return this.animationEnabled.get();
        }

        public float layer0Opacity()
        {
            return this.layer0Opacity.get().floatValue();
        }

        public float layer1Opacity()
        {
            return this.layer1Opacity.get().floatValue();
        }
    }

}
