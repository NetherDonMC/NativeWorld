package ru.netherdon.nativeworld.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public final class ClientboundSpatialDecayDegreePayload implements CustomPacketPayload
{
    public static final ResourceLocation ID = ResourceLocationHelper.mod("spatial_decay_degree");
    public static final Type<ClientboundSpatialDecayDegreePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<? super FriendlyByteBuf, ClientboundSpatialDecayDegreePayload> STREAM_CODEC = StreamCodec.composite(
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
    {
        this(player.getId(), degree);
    }

    public int entityId()
    {
        return this.entityId;
    }

    public int level()
    {
        return this.degree;
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
