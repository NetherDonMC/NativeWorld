package ru.netherdon.nativeworld.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayDegreePayload;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayStretchingPayload;
import ru.netherdon.nativeworld.registries.*;

@EventBusSubscriber(modid = NativeWorld.ID)
public final class PlayerEventHandler
{
    @SubscribeEvent
    private static void loggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        Player player = event.getEntity();
        Level level = player.level();
        if (!level.isClientSide)
        {
            SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY.get());
            PacketDistributor.sendToAllPlayers(
                new ClientboundSpatialDecayDegreePayload(player, spatialDecay.getDegree())
            );
        }
    }

    @SubscribeEvent
    private static void tick(PlayerTickEvent.Pre event)
    {
        Player player = event.getEntity();
        SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY.get());
        spatialDecay.tick();
    }

    @SubscribeEvent
    private static void clone(PlayerEvent.Clone event)
    {
        if (event.isWasDeath() && event.getOriginal().hasData(NWAttachmentTypes.SPATIAL_DECAY))
        {
            SpatialDecay spatialDecay = event.getEntity()
                .getData(NWAttachmentTypes.SPATIAL_DECAY);
            SpatialDecay originalSpatialDecay = event.getOriginal()
                .getData(NWAttachmentTypes.SPATIAL_DECAY);
            spatialDecay.copyDataFromClone(originalSpatialDecay);
        }
    }

    @SubscribeEvent
    private static void getHeartType(PlayerHeartTypeEvent event)
    {
        Player player = event.getEntity();
        if (player.hasEffect(NWMobEffects.SPATIAL_DECAY))
        {
            event.setType(NWExtendedEnums.spatialDecayHeartType());
        }
    }

    @SubscribeEvent
    private static void changeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer serverPlayer)
        {
            NWCriterionTriggers.UNSAFE_DIMENSION.get().trigger(serverPlayer);
        }
    }

    @SubscribeEvent
    private static void playerDamaged(LivingDamageEvent.Post event)
    {
        if (event.getEntity() instanceof ServerPlayer player && event.getSource().is(NWDamageTypes.SPATIAL_DECAY))
        {
            SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);
            if (spatialDecay.mayApplyEffect())
            {
                PacketDistributor.sendToPlayer(player, ClientboundSpatialDecayStretchingPayload.INSTANCE);
            }
        }
    }
}
