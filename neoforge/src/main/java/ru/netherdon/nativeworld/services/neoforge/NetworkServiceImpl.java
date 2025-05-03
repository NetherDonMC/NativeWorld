package ru.netherdon.nativeworld.services.neoforge;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Contract;

public class NetworkServiceImpl
{
    public static void sendToPlayersIn(ServerLevel serverLevel, CustomPacketPayload payload)
    {
        PacketDistributor.sendToPlayersInDimension(serverLevel, payload);
    }

    public static void sendToAllPlayers(MinecraftServer server, CustomPacketPayload payload)
    {
        PacketDistributor.sendToAllPlayers(payload);
    }

    public static void send(ServerPlayer player, CustomPacketPayload payload)
    {
        PacketDistributor.sendToPlayer(player, payload);
    }
}
