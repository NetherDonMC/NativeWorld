package ru.netherdon.nativeworld.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.particles.TotemParticleProvider;
import ru.netherdon.nativeworld.items.properties.SpatialDecayMeterItemPropertyFunction;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;
import ru.netherdon.nativeworld.registries.NWItems;
import ru.netherdon.nativeworld.registries.NWPaticleTypes;

@EventBusSubscriber(modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
@Mod(value = NativeWorld.ID, dist = Dist.CLIENT)
public class NativeWorldClient
{
    public NativeWorldClient(ModContainer modContainer)
    {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event)
    {
        event.enqueueWork(NativeWorldClient::registerItemProperties);
    }

    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event)
    {
        event.registerSpriteSet(NWPaticleTypes.TOTEM_OF_BIRTH.get(), TotemParticleProvider::new);
    }

    private static void registerItemProperties()
    {
        ItemProperties.register(
            NWItems.SPATIAL_DECAY_METER.get(),
            ResourceLocationHelper.mod("degree"),
            new SpatialDecayMeterItemPropertyFunction()
        );
    }
}
