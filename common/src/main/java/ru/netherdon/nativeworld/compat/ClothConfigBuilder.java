package ru.netherdon.nativeworld.compat;

import me.shedaniel.clothconfig2.api.*;
import me.shedaniel.clothconfig2.impl.builders.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.neoforged.neoforge.common.ModConfigSpec;
import ru.netherdon.nativeworld.config.NWClientConfig;
import ru.netherdon.nativeworld.config.NWServerConfig;

import java.util.List;
import java.util.Optional;

import static ru.netherdon.nativeworld.misc.TranslationHelper.*;

@Environment(EnvType.CLIENT)
public class ClothConfigBuilder
{
    private static final Component INTERFACE_CATEGORY = Component.translatable(key("config","interface"));
    private static final Component SPATIAL_DECAY_CATEGORY = Component.translatable(key("config","spatial_decay"));
    private static final Component INWORLD_CATEGORY_DESCRIPTION = Component.translatable(key("config","inworld_category_description")).withStyle(ChatFormatting.RED);
    private static final Component ONLINE_MODE_CATEGORY_DESCRIPTION = Component.translatable(key("config","online_mode_category_description")).withStyle(ChatFormatting.RED);
    private static final Component TITLE = Component.literal("Native World");
    private static final Component CHANGE_ALERT = Component.translatable(key("config", "server_properties_change_alert")).withStyle(ChatFormatting.YELLOW);

    public static Screen build(Screen lastScreen)
    {
        NWClientConfig client = NWClientConfig.get();
        NWServerConfig server = NWServerConfig.get();
        Minecraft minecraft = Minecraft.getInstance();

        ConfigBuilder builder = ConfigBuilder.create();
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        builder.setParentScreen(lastScreen);
        builder.setTitle(TITLE);

        buildInterfaceCategory(client, builder, entryBuilder, Permission.client());
        buildSpatialDecayCategory(server, builder, entryBuilder, Permission.server(minecraft));

        return builder.build();
    }

    private static ConfigCategory buildInterfaceCategory(NWClientConfig client, ConfigBuilder builder, ConfigEntryBuilder entryBuilder, Permission permission)
    {
        ConfigCategory category = builder.getOrCreateCategory(INTERFACE_CATEGORY);

        category.addEntry(
            entryBuilder.startSubCategory(
                Component.translatable(key("config","interface", "group", "spatial_decay_outline")),
                List.of(
                    booleanToggle(client.spatialDecayOutline().enabled(), entryBuilder, permission).build(),
                    booleanToggle(client.spatialDecayOutline().animationEnabled(), entryBuilder, permission).build(),
                    percentageSlider(client.spatialDecayOutline().frontLayerOpacity(), entryBuilder, permission).build(),
                    percentageSlider(client.spatialDecayOutline().backLayerOpacity(), entryBuilder, permission).build()
                )
            ).setExpanded(true).build()
        );

        return category;
    }

    private static ConfigCategory buildSpatialDecayCategory(
        NWServerConfig server,
        ConfigBuilder builder,
        ConfigEntryBuilder entryBuilder,
        Permission permission
    )
    {
        ConfigCategory category = builder.getOrCreateCategory(SPATIAL_DECAY_CATEGORY);
        category.setDescription(() ->
        {
            FormattedText text = CHANGE_ALERT;
            if (!permission.levelLoaded())
            {
                text = INWORLD_CATEGORY_DESCRIPTION;
            }
            else if (!permission.isSingleplayer())
            {
                text = ONLINE_MODE_CATEGORY_DESCRIPTION;
            }

            return Optional.of(new FormattedText[] { text });
        });

        category.addEntry(
            booleanToggle(server.spatialDecay().unexploredDimensionAreSafe(), entryBuilder, permission).build()
        );

        category.addEntry(
            intField(server.spatialDecay().accumulationRate(), entryBuilder, permission).build()
        );

        category.addEntry(
            intField(server.spatialDecay().recoveryRate(), entryBuilder, permission).build()
        );

        category.addEntry(
            entryBuilder.startSubCategory(
                Component.translatable(key("config","spatial_decay", "group", "effect")),
                List.of(
                    intField(server.spatialDecay().startDegree(), entryBuilder, permission).build(),
                    intField(server.spatialDecay().maxAmplifier(), entryBuilder, permission).build(),
                    intField(server.spatialDecay().amplifierInterval(), entryBuilder, permission).build()
                )
            ).setExpanded(true).build()
        );

        return category;
    }

    private static BooleanToggleBuilder booleanToggle(ModConfigSpec.BooleanValue configValue, ConfigEntryBuilder builder, Permission permission)
    {
        return init(
            builder.startBooleanToggle(name(configValue), value(configValue, permission)),
            configValue, permission
        );
    }

    private static IntFieldBuilder intField(ModConfigSpec.ConfigValue<Integer> configValue, ConfigEntryBuilder builder, Permission permission)
    {
        var result = init(
            builder.startIntField(name(configValue), value(configValue, permission)),
            configValue, permission
        );

        ModConfigSpec.Range<Integer> range = configValue.getSpec().getRange();
        if (range != null)
        {
            result.setMin(range.getMin());
            result.setMax(range.getMax());
        }

        return result;
    }

    private static IntSliderBuilder percentageSlider(ModConfigSpec.ConfigValue<Integer> configValue, ConfigEntryBuilder builder, Permission permission)
    {
        ModConfigSpec.Range<Integer> range = configValue.getSpec().getRange();
        return init(
            builder.startIntSlider(name(configValue), value(configValue, permission), range.getMin(), range.getMax()),
            configValue, permission
        ).setTextGetter((value) -> Component.literal(value + "%"));
    }

    private static <V, T extends AbstractFieldBuilder<V, ?, ?>> T init(T t, ModConfigSpec.ConfigValue<V> configValue, Permission permission)
    {
        t.setDefaultValue(configValue.getDefault());
        t.setSaveConsumer(permission.levelLoaded() ? (value) ->
        {
            configValue.set(value);
            configValue.save();
        } : null);
        t.setRequirement(permission::entryAvailable);
        t.setTooltip(tooltip(configValue));
        t.requireRestart(configValue.getSpec().restartType() == ModConfigSpec.RestartType.GAME);
        return t;
    }

    private static <T> T value(ModConfigSpec.ConfigValue<T> configValue, Permission permission)
    {
        return permission.levelLoaded() ? configValue.getRaw() : configValue.getDefault();
    }

    private static Component name(ModConfigSpec.ConfigValue<?> value)
    {
        String key = value.getSpec().getTranslationKey();
        if (key == null)
        {
            key = String.join(".", value.getPath());
        }
        return Component.translatable(key);
    }

    private static Optional<Component[]> tooltip(ModConfigSpec.ConfigValue<?> value)
    {
        String key = value.getSpec().getTranslationKey();
        if (key == null)
        {
            return Optional.empty();
        }

        Component text = Component.translatableWithFallback(key + ".tooltip", "");
        if (text.getString().isEmpty())
        {
            return Optional.empty();
        }

        return Optional.of(new Component[] { text });
    }

    private interface Permission
    {
        public boolean levelLoaded();
        public boolean isSingleplayer();
        public default boolean entryAvailable()
        {
            return this.levelLoaded() && this.isSingleplayer();
        }

        public static Permission client()
        {
            return new Permission()
            {
                @Override
                public boolean levelLoaded() { return true; }

                @Override
                public boolean isSingleplayer() { return true; }
            };
        }

        public static Permission server(Minecraft minecraft)
        {
            return new Permission()
            {
                @Override
                public boolean levelLoaded() { return minecraft.level != null; }

                @Override
                public boolean isSingleplayer() { return minecraft.isSingleplayer(); }
            };
        }
    }
}
