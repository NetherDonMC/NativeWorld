package ru.netherdon.nativeworld;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import ru.netherdon.nativeworld.network.INWClientPacketHandler;
import ru.netherdon.nativeworld.registries.*;

import java.util.Objects;

public final class NativeWorld
{
    public static final String ID = "nativeworld";

    private static NativeWorld INSTANCE;

    private static INWClientPacketHandler CLIENT_PACKET_HANDLER;

    public static void init()
    {
        NWItems.initialize();
        NWDataComponentTypes.initialize();
        NWCreativeTabs.initialize();
        NWMobEffects.initialize();
        NWPotions.initialize();
        NWParticleTypes.initialize();
        NWCriterionTriggers.initialize();
    }

    public static void setClientPacketHandler(@NotNull INWClientPacketHandler clientPacketHandler)
    {
        CLIENT_PACKET_HANDLER = define(CLIENT_PACKET_HANDLER, clientPacketHandler, "Client Packet Handler");
    }

    public static INWClientPacketHandler clientPacketHandler()
    {
        return CLIENT_PACKET_HANDLER;
    }

    public static String translationKey(String prefix, String postfix)
    {
        return "%s.%s.%s".formatted(prefix, ID, postfix);
    }

    public static <T> T define(T currentState, T newState, String name)
    {
        requireUndefined(currentState, name);
        return Objects.requireNonNull(newState);
    }

    public static void requireUndefined(Object obj, String name)
    {
        if (obj != null)
        {
            throw new IllegalStateException("Cannot redefine object: " + name);
        }
    }
}
