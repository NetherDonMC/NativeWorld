package ru.netherdon.nativeworld.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public final class ClientboundSpatialDecayStretchingPayload implements CustomPacketPayload
{
    public static final ResourceLocation ID = ResourceLocationHelper.mod("spatial_decay_stretching");
    public static final CustomPacketPayload.Type<ClientboundSpatialDecayStretchingPayload> TYPE = new CustomPacketPayload.Type<>(ID);

    public static final ClientboundSpatialDecayStretchingPayload INSTANCE = new ClientboundSpatialDecayStretchingPayload();

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSpatialDecayStretchingPayload> STREAM_CODEC =
        StreamCodec.unit(INSTANCE);

    private ClientboundSpatialDecayStretchingPayload() {}

    public void handle(IPayloadContext context)
    {
        NWClientPacketHandlers.handleSpatialDecayStretching(this, context);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
