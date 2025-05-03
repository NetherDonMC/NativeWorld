package ru.netherdon.nativeworld.neoforge.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.registries.NWCommands;

@EventBusSubscriber(modid = NativeWorld.ID)
public final class CommandEventHandler
{
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event)
    {
        event.getDispatcher().register(NWCommands.createMainCommand());
    }
}
