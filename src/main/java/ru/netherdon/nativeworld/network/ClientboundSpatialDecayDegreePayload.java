package ru.netherdon.nativeworld.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public final class ClientboundSpatialDecayDegreePayload implements CustomPacketPayload
{
    public static final ResourceLocation ID = ResourceLocationHelper.mod("spatial_decay_degree");
    public static final CustomPacketPayload.Type<ClientboundSpatialDecayDegreePayload> TYPE = new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSpatialDecayDegreePayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT,
        ClientboundSpatialDecayDegreePayload::entityId,
        ByteBufCodecs.VAR_INT,
        ClientboundSpatialDecayDegreePayload::level,
        ClientboundSpatialDecayDegreePayload::new
    );

    private final int entityId;
    private final int degree;

    private ClientboundSpatialDecayDegreePayload(int entityId, int degree)
    {
        this.entityId = entityId;
        this.degree = degree;
    }

    public ClientboundSpatialDecayDegreePayload(Player player, int degree)
    { this(player.getId(), degree); }

    public int entityId()
    { return this.entityId; }

    public int level()
    { return this.degree; }

    public void handle(IPayloadContext context)
    {
        NWClientPacketHandlers.handleSpatialDecayLevel(this, context);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type()
    { return TYPE; }
}
