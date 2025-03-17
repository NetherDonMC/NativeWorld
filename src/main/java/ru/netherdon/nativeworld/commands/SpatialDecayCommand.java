package ru.netherdon.nativeworld.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.config.NWServerConfig;
import ru.netherdon.nativeworld.registries.NWAttachmentTypes;

import java.util.function.Function;

public class SpatialDecayCommand
{
    public static LiteralArgumentBuilder<CommandSourceStack> create()
    {
        return Commands.literal("spatialdecay").executes(
            (source) -> get(
                source.getSource(),
                source.getSource().getPlayerOrException()
            )
        ).then(
            Commands.argument("target", EntityArgument.player()).executes(
                (source) -> get(
                    source.getSource(),
                    EntityArgument.getPlayer(source, "target")
                )
            ).then(
                Commands.literal("get").executes(
                    (source) -> get(
                        source.getSource(),
                        EntityArgument.getPlayer(source, "target")
                    )
                )
            ).then(
                Commands.literal("set").then(
                    Commands.argument("value", IntegerArgumentType.integer(0)).executes(
                        (source) -> set(
                            source.getSource(),
                            EntityArgument.getPlayer(source, "target"),
                            IntegerArgumentType.getInteger(source, "value")
                        )
                    )
                )
            ).then(
                Commands.literal("add").then(
                    Commands.argument("value", IntegerArgumentType.integer(1)).executes(
                        (source) -> add(
                            source.getSource(),
                            EntityArgument.getPlayer(source, "target"),
                            IntegerArgumentType.getInteger(source, "value")
                        )
                    )
                )
            ).then(
                Commands.literal("remove").then(
                    Commands.argument("value", IntegerArgumentType.integer(0)).executes(
                        (source) -> remove(
                            source.getSource(),
                            EntityArgument.getPlayer(source, "target"),
                            IntegerArgumentType.getInteger(source, "value")
                        )
                    )
                )
            )
        );
    }

    private static int get(CommandSourceStack source, ServerPlayer player)
    {
        SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);
        int deegre = spatialDecay.getDegree();
        source.sendSuccess(
            () -> Component.translatable(
                "commands." + NativeWorld.ID + ".spatialdecay.get.success",
                player.getDisplayName(),
                deegre
            ),
            false
        );
        return spatialDecay.getDegree();
    }

    private static int set(
        CommandSourceStack source,
        ServerPlayer player,
        Function<Integer, Integer> value
    )
    {
        SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);
        spatialDecay.setDegree(value.apply(spatialDecay.getDegree()));
        int degree = spatialDecay.getDegree();
        source.sendSuccess(() -> Component.translatable(
                "commands." + NativeWorld.ID + ".spatialdecay.set.success",
                player.getDisplayName(),
                degree
            ),
            true
        );
        return degree;
    }

    private static int set(CommandSourceStack source, ServerPlayer player, int value)
    {
        return set(source, player, (degree) -> value);
    }

    private static int add(CommandSourceStack source, ServerPlayer player, int value)
    {
        return set(source, player, (degree) -> degree + value);
    }

    private static int remove(CommandSourceStack source, ServerPlayer player, int value)
    {
        return set(source, player, (degree) -> degree - value);
    }
}
