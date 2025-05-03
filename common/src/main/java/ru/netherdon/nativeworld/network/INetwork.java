package ru.netherdon.nativeworld.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;

public interface INetwork
{
    public void sendToPlayersIn(ServerLevel serverLevel, CustomPacketPayload payload);
}
