package ru.netherdon.nativeworld.services;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Contract;

public class NetworkService
{
    @ExpectPlatform
    @Contract(pure = true)
    public static void sendToPlayersIn(ServerLevel serverLevel, CustomPacketPayload payload)
    {
        throw new UnsupportedOperationException();
    }

    @ExpectPlatform
    @Contract(pure = true)
    public static void sendToAllPlayers(MinecraftServer server, CustomPacketPayload payload)
    {
        throw new UnsupportedOperationException();
    }

    @ExpectPlatform
    @Contract(pure = true)
    public static void send(ServerPlayer player, CustomPacketPayload payload)
    {
        throw new UnsupportedOperationException();
    }
}
