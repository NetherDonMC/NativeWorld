package ru.netherdon.nativeworld.registries;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.commands.SafeDimensionsCommand;
import ru.netherdon.nativeworld.commands.SpatialDecayCommand;

public final class NWCommands
{
    public static LiteralArgumentBuilder<CommandSourceStack> createMainCommand()
    {
        return Commands.literal(NativeWorld.ID)
            .requires(source -> source.hasPermission(2))
            .then(SafeDimensionsCommand.create())
            .then(SpatialDecayCommand.create());
    }
}
