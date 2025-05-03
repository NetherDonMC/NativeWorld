package ru.netherdon.nativeworld.services.fabric;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class NetworkServiceImpl
{
    public static void sendToPlayersIn(ServerLevel serverLevel, CustomPacketPayload payload)
    {
        for (ServerPlayer player : PlayerLookup.world(serverLevel))
        {
            ServerPlayNetworking.send(player, payload);
        }
    }

    public static void sendToAllPlayers(MinecraftServer server, CustomPacketPayload payload)
    {
        for (ServerPlayer player : PlayerLookup.all(server))
        {
            ServerPlayNetworking.send(player, payload);
        }
    }

    public static void send(ServerPlayer player, CustomPacketPayload payload)
    {
        ServerPlayNetworking.send(player, payload);
    }
}
