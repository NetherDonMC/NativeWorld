package ru.netherdon.nativeworld.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.services.SpatialDecayService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SafeDimensionsCommand
{
    public static final SimpleCommandExceptionType ERROR_ADD_FAILED = new SimpleCommandExceptionType(Component.translatable("commands." + NativeWorld.ID + ".safedimensions.add.failed"));
    public static final SimpleCommandExceptionType ERROR_REMOVE_FAILED = new SimpleCommandExceptionType(Component.translatable("commands." + NativeWorld.ID + ".safedimensions.remove.failed"));
    public static final SimpleCommandExceptionType ERROR_CLEAR_FAILED = new SimpleCommandExceptionType(Component.translatable("commands." + NativeWorld.ID + ".safedimensions.clear.failed"));

    public static LiteralArgumentBuilder<CommandSourceStack> create()
    {
        return Commands.literal("safedimensions").executes(
            (source) -> sendSafeDimensionList(
                source.getSource(),
                source.getSource().getPlayerOrException()
            )
        ).then(
            Commands.argument("target", EntityArgument.player()).executes(
                (source) -> sendSafeDimensionList(
                    source.getSource(),
                    EntityArgument.getPlayer(source, "target")
                )
            ).then(
                Commands.literal("list").executes(
                    (source) -> sendSafeDimensionList(
                        source.getSource(),
                        EntityArgument.getPlayer(source, "target")
                    )
                )
            ).then(
                Commands.literal("add").then(
                    Commands.argument("dimension", DimensionArgument.dimension()).executes(
                        (source) -> addSafeDimension(
                            source.getSource(),
                            EntityArgument.getPlayer(source, "target"),
                            DimensionArgument.getDimension(source, "dimension")
                        )
                    )
                ).then(
                    Commands.literal("current").executes(
                        (source) -> addSafeDimension(
                            source.getSource(),
                            EntityArgument.getPlayer(source, "target"),
                            source.getSource().getLevel()
                        )
                    )
                )
            ).then(
                Commands.literal("remove").then(
                    Commands.argument("dimension", ResourceLocationArgument.id())
                        .suggests(
                            (context, builder) -> SharedSuggestionProvider.suggest(
                                getLocalSafeDimensions(EntityArgument.getPlayer(context, "target")), builder
                            )
                        )
                        .executes(
                            (source) -> removeSafeDimensions(
                                source.getSource(),
                                EntityArgument.getPlayer(source, "target"),
                                ResourceLocationArgument.getId(source, "dimension")
                            )
                        )
                )
            )
            .then(
                Commands.literal("clear").executes(
                    (source) -> clearSafeDimensions(
                        source.getSource(),
                        EntityArgument.getPlayer(source, "target")
                    )
                )
            )
        );
    }

    private static Collection<String> getLocalSafeDimensions(ServerPlayer player)
    {
        return SpatialDecayService.getSpatialDecay(player)
            .getLocalSafeDimensions()
            .stream()
            .map((key) -> key.location().toString())
            .sorted()
            .collect(Collectors.toList());
    }

    private static Component getDimensionName(ResourceLocation dimensionId)
    {
        return Component.translatableWithFallback(
                dimensionId.toLanguageKey("dimension"),
                dimensionId.toString()
            )
            .setStyle(
                Style.EMPTY
                    .withColor(ChatFormatting.GREEN)
                    .withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        Component.literal(dimensionId.toString())
                    )).withClickEvent(new ClickEvent(
                        ClickEvent.Action.COPY_TO_CLIPBOARD,
                        dimensionId.toString()
                    ))
            );
    }

    private static int addSafeDimension(CommandSourceStack source, ServerPlayer player, ServerLevel dimension) throws CommandSyntaxException
    {
        boolean isAdded = SpatialDecayService.getSpatialDecay(player)
            .getLocalSafeDimensions()
            .add(dimension.dimension());

        if (isAdded)
        {
            source.sendSuccess(
                () -> Component.translatable(
                    "commands." + NativeWorld.ID + ".safedimensions.add.success",
                    getDimensionName(dimension.dimension().location()),
                    player.getDisplayName()
                ),
                true
            );
            return 1;
        }

        throw ERROR_ADD_FAILED.create();
    }

    private static int clearSafeDimensions(CommandSourceStack source, ServerPlayer player) throws CommandSyntaxException {
        Set<ResourceKey<Level>> dimensions = SpatialDecayService.getSpatialDecay(player)
            .getLocalSafeDimensions();
        int size = dimensions.size();
        if (size == 0)
        {
            throw ERROR_CLEAR_FAILED.create();
        }
        dimensions.clear();

        source.sendSuccess(
            () -> Component.translatable(
                "commands." + NativeWorld.ID + ".safedimensions.clear.success",
                size,
                player.getDisplayName()
            ),
            true
        );
        return 1;
    }

    private static int removeSafeDimensions(CommandSourceStack source, ServerPlayer player, ResourceLocation dimension) throws CommandSyntaxException
    {
        boolean isRemoved = SpatialDecayService.getSpatialDecay(player)
            .getLocalSafeDimensions()
            .remove(ResourceKey.create(Registries.DIMENSION, dimension));

        if (isRemoved)
        {
            source.sendSuccess(
                () -> Component.translatable(
                    "commands." + NativeWorld.ID + ".safedimensions.remove.success",
                    getDimensionName(dimension),
                    player.getDisplayName()
                ),
                true
            );
            return 1;
        }

        throw ERROR_REMOVE_FAILED.create();
    }

    private static int sendSafeDimensionList(CommandSourceStack source, ServerPlayer player)
    {
        Set<Component> dimensions = SpatialDecayService.getSpatialDecay(player)
            .getLocalSafeDimensions()
            .stream()
            .map((key) -> getDimensionName(key.location()))
            .collect(Collectors.toSet());
        if (dimensions.isEmpty())
        {
            source.sendSuccess(
                () -> Component.translatable(
                    "commands." + NativeWorld.ID + ".safedimensions.list.empty",
                    player.getDisplayName()
                ),
                false
            );
        }
        else
        {
            source.sendSuccess(
                () -> Component.translatable(
                    "commands." + NativeWorld.ID + ".safedimensions.list.success",
                    player.getDisplayName(),
                    dimensions.size(),
                    ComponentUtils.formatList(dimensions, ComponentUtils.DEFAULT_SEPARATOR)
                ),
                false
            );
        }
        return dimensions.size();
    }
}
