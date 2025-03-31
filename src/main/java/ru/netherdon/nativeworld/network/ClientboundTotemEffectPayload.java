package ru.netherdon.nativeworld.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.netherdon.nativeworld.items.totems.TotemParticleColor;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

public record ClientboundTotemEffectPayload(ItemStack stack, TotemParticleColor color, int entityId) implements CustomPacketPayload
{
    public static final ResourceLocation ID = ResourceLocationHelper.mod("totem_effect");
    public static final CustomPacketPayload.Type<ClientboundTotemEffectPayload> TYPE = new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundTotemEffectPayload> STREAM_CODEC = StreamCodec.composite(
        ItemStack.STREAM_CODEC,
        ClientboundTotemEffectPayload::stack,
        TotemParticleColor.STREAM_CODEC,
        ClientboundTotemEffectPayload::color,
        ByteBufCodecs.VAR_INT,
        ClientboundTotemEffectPayload::entityId,
        ClientboundTotemEffectPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type()
    { return TYPE; }

    public Entity getEntity(Level level)
    {
        return level.getEntity(this.entityId);
    }

    public void handle(IPayloadContext context)
    {
        NWClientPacketHandlers.handleTotemEffect(this, context);
    }
}
