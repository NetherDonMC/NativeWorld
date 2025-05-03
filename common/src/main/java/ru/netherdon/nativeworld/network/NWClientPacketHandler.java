package ru.netherdon.nativeworld.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.NativeWorldClient;
import ru.netherdon.nativeworld.client.particles.TotemParticleOption;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.registries.NWParticleTypes;
import ru.netherdon.nativeworld.services.SpatialDecayService;

@Environment(EnvType.CLIENT)
public final class NWClientPacketHandler implements INWClientPacketHandler
{
    @Override
    public void handleSpatialDecayDegree(ClientboundSpatialDecayDegreePayload payload)
    {
        Level level = Minecraft.getInstance().level;
        Entity entity = level.getEntity(payload.entityId());
        if (entity instanceof Player player)
        {
            SpatialDecay spatialDecay = SpatialDecayService.getSpatialDecay(player);
            spatialDecay.setDegree(payload.level());
        }
    }

    @Override
    public void handleTotemEffect(ClientboundTotemEffectPayload payload, Player player)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = payload.getEntity(minecraft.level);

        TotemParticleOption option = new TotemParticleOption(NWParticleTypes.TOTEM_OF_BIRTH.value(), payload.color());
        minecraft.particleEngine.createTrackingEmitter(entity, option, 30);

        minecraft.level.playLocalSound(
            entity.getX(), entity.getY(), entity.getZ(),
            SoundEvents.TOTEM_USE, entity.getSoundSource(),
            1.0F, 1.0F, false
        );

        if (player == entity)
        {
            minecraft.gameRenderer.displayItemActivation(payload.stack());
        }
    }

    @Override
    public void handleSpatialDecayStretching(ClientboundSpatialDecayStretchingPayload payload)
    {
        NativeWorldClient.SPATIAL_DECAY_OUTLINE.startStretching();
    }
}
