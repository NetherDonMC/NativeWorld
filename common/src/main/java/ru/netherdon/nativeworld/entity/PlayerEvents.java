package ru.netherdon.nativeworld.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayDegreePayload;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayStretchingPayload;
import ru.netherdon.nativeworld.registries.NWCriterionTriggers;
import ru.netherdon.nativeworld.registries.NWDamageTypes;
import ru.netherdon.nativeworld.services.NetworkService;
import ru.netherdon.nativeworld.services.SpatialDecayService;

public final class PlayerEvents
{
    public static void onChangeDimension(ServerPlayer player)
    {
        NWCriterionTriggers.UNSAFE_DIMENSION.value().trigger(player);
    }

    public static void onLogIn(ServerPlayer player)
    {
        SpatialDecay spatialDecay = SpatialDecayService.getSpatialDecay(player);
        var payload = new ClientboundSpatialDecayDegreePayload(player, spatialDecay.getDegree());
        NetworkService.sendToAllPlayers(player.getServer(), payload);
        NetworkService.send(player, payload);
    }

    public static void onClone(ServerPlayer oldPlayer, ServerPlayer player, boolean alive)
    {
        if (!alive)
        {
            SpatialDecay oldSpatialDecay = SpatialDecayService.getSpatialDecay(oldPlayer);
            SpatialDecay spatialDecay = SpatialDecayService.getSpatialDecay(player);
            spatialDecay.copyDataFromClone(oldSpatialDecay);
        }
    }

    public static void onDamage(ServerPlayer player, DamageSource source)
    {
        if (source.is(NWDamageTypes.SPATIAL_DECAY))
        {
            SpatialDecay spatialDecay = SpatialDecayService.getSpatialDecay(player);
            if (spatialDecay.mayApplyEffect())
            {
                NetworkService.send(player, ClientboundSpatialDecayStretchingPayload.INSTANCE);
            }
        }
    }
}
