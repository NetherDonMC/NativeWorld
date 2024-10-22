package ru.netherdon.nativeworld.registries;

import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.commands.SafeDimensionsCommand;
import ru.netherdon.nativeworld.commands.SpatialDecayCommand;


@EventBusSubscriber(modid = NativeWorld.ID)
public class NWCommands
{
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event)
    {
        event.getDispatcher().register(
            Commands.literal(NativeWorld.ID)
                .requires(source -> source.hasPermission(2))
                .then(
                    SafeDimensionsCommand.create()
                ).then(
                    SpatialDecayCommand.create()
                )
        );
    }
}
