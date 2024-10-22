package ru.netherdon.nativeworld.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import ru.netherdon.nativeworld.NativeWorld;

@EventBusSubscriber(modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.MOD)
public final class NWNetworkHandler
{
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar(NativeWorld.ID);

        registrar.playToClient(
            ClientboundTotemEffectPayload.TYPE,
            ClientboundTotemEffectPayload.STREAM_CODEC,
            ClientboundTotemEffectPayload::handle
        );

        registrar.playToClient(
            ClientboundSpatialDecayDegreePayload.TYPE,
            ClientboundSpatialDecayDegreePayload.STREAM_CODEC,
            ClientboundSpatialDecayDegreePayload::handle
        );
    }
}
