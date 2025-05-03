package ru.netherdon.nativeworld.network;

import net.minecraft.world.entity.player.Player;

public interface INWClientPacketHandler
{
    public default void handleSpatialDecayDegree(ClientboundSpatialDecayDegreePayload payload) {}
    public default void handleTotemEffect(ClientboundTotemEffectPayload payload, Player player) {}
    public default void handleSpatialDecayStretching(ClientboundSpatialDecayStretchingPayload payload) {}
}
