package ru.netherdon.nativeworld.neoforge.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.ConfigScreenLoader;
import ru.netherdon.nativeworld.client.NativeWorldClient;
import ru.netherdon.nativeworld.client.particles.TotemParticleProvider;
import ru.netherdon.nativeworld.registries.NWParticleTypes;

@OnlyIn(Dist.CLIENT)
@Mod(value = NativeWorld.ID, dist = Dist.CLIENT)
public class NativeWorldNeoForgeClient
{
    public NativeWorldNeoForgeClient(IEventBus modEventBus, ModContainer modContainer)
    {
        NativeWorldClient.init();
        modEventBus.register(this);

        final var configScreenLoader = ConfigScreenLoader.get();
        if (configScreenLoader != null)
        {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> configScreenLoader.apply(parent));
        }
    }

    @SubscribeEvent
    public void setup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> NativeWorldClient.registerItemProperties(ItemProperties::register));
    }

    @SubscribeEvent
    public void registerParticleProvider(RegisterParticleProvidersEvent event)
    {
        event.registerSpriteSet(NWParticleTypes.TOTEM_OF_BIRTH.value(), TotemParticleProvider::new);
    }
}
