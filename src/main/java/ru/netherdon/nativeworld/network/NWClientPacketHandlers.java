package ru.netherdon.nativeworld.network;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.client.events.SpatialDecayOutlineEventHandler;
import ru.netherdon.nativeworld.client.particles.TotemParticleOption;
import ru.netherdon.nativeworld.registries.NWAttachmentTypes;
import ru.netherdon.nativeworld.registries.NWPaticleTypes;

public final class NWClientPacketHandlers
{
    public static void handleTotemEffect(ClientboundTotemEffectPayload payload, IPayloadContext context)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = payload.getEntity(minecraft.level);
        TotemParticleOption option = new TotemParticleOption(NWPaticleTypes.TOTEM_OF_BIRTH.get(), payload.color());
        minecraft.particleEngine.createTrackingEmitter(entity, option, 30);
        minecraft.level.playLocalSound(
            entity.getX(), entity.getY(), entity.getZ(),
            SoundEvents.TOTEM_USE, entity.getSoundSource(),
            1.0F, 1.0F, false
        );

        if (context.player() == entity)
        {
            minecraft.gameRenderer.displayItemActivation(payload.stack());
        }
    }

    public static void handleSpatialDecayLevel(ClientboundSpatialDecayDegreePayload payload, IPayloadContext context)
    {
        Entity entity = Minecraft.getInstance().level.getEntity(payload.entityId());
        if (entity instanceof Player player)
        {
            SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);
            spatialDecay.setDegree(payload.level());
        }
    }

    public static void handleSpatialDecayStretching(ClientboundSpatialDecayStretchingPayload payload, IPayloadContext context)
    {
        SpatialDecayOutlineEventHandler.playerDamaged();
    }
}
