package ru.netherdon.nativeworld.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.registries.NWAttachmentTypes;
import ru.netherdon.nativeworld.registries.NWCriterionTriggers;
import ru.netherdon.nativeworld.registries.NWExtendedEnums;
import ru.netherdon.nativeworld.registries.NWMobEffects;

@EventBusSubscriber(modid = NativeWorld.ID)
public final class PlayerEventHandler
{
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
}
