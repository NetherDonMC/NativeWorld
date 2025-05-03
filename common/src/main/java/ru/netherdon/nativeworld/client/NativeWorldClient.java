package ru.netherdon.nativeworld.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.util.TriConsumer;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.compat.OtherModNames;
import ru.netherdon.nativeworld.items.properties.SpatialDecayMeterItemPropertyFunction;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;
import ru.netherdon.nativeworld.network.NWClientPacketHandler;
import ru.netherdon.nativeworld.registries.NWItems;

import java.util.List;

import static ru.netherdon.nativeworld.misc.ResourceLocationHelper.totemModel;

@Environment(EnvType.CLIENT)
public class NativeWorldClient
{
    public static final List<CustomModelLocation> CUSTOM_MODELS = List.of(
        new CustomModelLocation(totemModel("overworld")),
        new CustomModelLocation(totemModel("nether")),
        new CustomModelLocation(totemModel("end")),
        new CustomModelLocation(OtherModNames.DEEPER_AND_DARKER, totemModel("otherside")),
        new CustomModelLocation(OtherModNames.BUMBLEZONE, totemModel("bumblezone")),
        new CustomModelLocation(OtherModNames.TWILIGHT_FOREST, totemModel("twilight_forest")),
        new CustomModelLocation(OtherModNames.AETHER, totemModel("aether"))
    );

    public static final SpatialDecayOutline SPATIAL_DECAY_OUTLINE = new SpatialDecayOutline();

    public static void init()
    {
        NativeWorld.setClientPacketHandler(new NWClientPacketHandler());
    }

    public static void registerItemProperties(TriConsumer<Item, ResourceLocation, ClampedItemPropertyFunction> register)
    {
        register.accept(
            NWItems.SPATIAL_DECAY_METER.value(),
            ResourceLocationHelper.mod("degree"),
            new SpatialDecayMeterItemPropertyFunction()
        );
    }
}
