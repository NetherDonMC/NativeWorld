package ru.netherdon.nativeworld.neoforge.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.entity.PlayerEvents;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.neoforge.registries.NWAttachmentTypes;
import ru.netherdon.nativeworld.neoforge.registries.NWHeartTypes;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayStretchingPayload;
import ru.netherdon.nativeworld.registries.NWDamageTypes;
import ru.netherdon.nativeworld.registries.NWMobEffects;

@EventBusSubscriber(modid = NativeWorld.ID)
public final class PlayerEventHandler
{
    @SubscribeEvent
    private static void loggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer serverPlayer)
        {
            PlayerEvents.onLogIn(serverPlayer);
        }
    }

    @SubscribeEvent
    private static void tick(PlayerTickEvent.Pre event)
    {
        Player player = event.getEntity();
        SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);
        spatialDecay.tick();
    }

    @SubscribeEvent
    private static void clone(PlayerEvent.Clone event)
    {
        if (
            event.getOriginal() instanceof ServerPlayer originalServerPlayer
            && event.getEntity() instanceof ServerPlayer serverPlayer
        )
        {
            PlayerEvents.onClone(originalServerPlayer, serverPlayer, !event.isWasDeath());
        }
    }

    @SubscribeEvent
    private static void getHeartType(PlayerHeartTypeEvent event)
    {
        Player player = event.getEntity();
        if (player.hasEffect(NWMobEffects.SPATIAL_DECAY))
        {
            event.setType(NWHeartTypes.spatialDecay());
        }
    }

    @SubscribeEvent
    private static void changeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer serverPlayer)
        {
            PlayerEvents.onChangeDimension(serverPlayer);
        }
    }

    @SubscribeEvent
    private static void playerDamaged(LivingDamageEvent.Post event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            PlayerEvents.onDamage(player, event.getSource());
        }
    }
}
