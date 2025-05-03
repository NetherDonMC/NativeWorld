package ru.netherdon.nativeworld.neoforge.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayDegreePayload;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayStretchingPayload;
import ru.netherdon.nativeworld.network.ClientboundTotemEffectPayload;

@EventBusSubscriber(modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.MOD)
public final class NWNetworkHandler
{
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar(NativeWorld.ID);

        registrar.playToClient(
            ClientboundSpatialDecayDegreePayload.TYPE,
            ClientboundSpatialDecayDegreePayload.STREAM_CODEC,
            (payload, context) -> NativeWorld.clientPacketHandler().handleSpatialDecayDegree(payload)
        );

        registrar.playToClient(
            ClientboundTotemEffectPayload.TYPE,
            ClientboundTotemEffectPayload.STREAM_CODEC,
            (payload, context) -> NativeWorld.clientPacketHandler().handleTotemEffect(payload, context.player())
        );

        registrar.playToClient(
            ClientboundSpatialDecayStretchingPayload.TYPE,
            ClientboundSpatialDecayStretchingPayload.STREAM_CODEC,
            (payload, context) -> NativeWorld.clientPacketHandler().handleSpatialDecayStretching(payload)
        );
    }
}
