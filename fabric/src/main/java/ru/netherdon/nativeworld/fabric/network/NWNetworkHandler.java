package ru.netherdon.nativeworld.fabric.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayDegreePayload;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayStretchingPayload;
import ru.netherdon.nativeworld.network.ClientboundTotemEffectPayload;

public final class NWNetworkHandler
{
    public static void register()
    {
        PayloadTypeRegistry.playS2C().register(
            ClientboundSpatialDecayDegreePayload.TYPE,
            ClientboundSpatialDecayDegreePayload.STREAM_CODEC
        );

        PayloadTypeRegistry.playS2C().register(
            ClientboundTotemEffectPayload.TYPE,
            ClientboundTotemEffectPayload.STREAM_CODEC
        );

        PayloadTypeRegistry.playS2C().register(
            ClientboundSpatialDecayStretchingPayload.TYPE,
            ClientboundSpatialDecayStretchingPayload.STREAM_CODEC
        );
    }
}
