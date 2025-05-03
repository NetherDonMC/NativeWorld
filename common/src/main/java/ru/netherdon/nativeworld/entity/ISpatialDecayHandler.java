package ru.netherdon.nativeworld.entity;

import net.minecraft.world.entity.player.Player;

public interface ISpatialDecayHandler
{
    public SpatialDecay getSpatialDecay(Player player);
}
