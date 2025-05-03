package ru.netherdon.nativeworld.services.fabric;

import net.minecraft.world.entity.player.Player;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.fabric.entity.ISpatialDecayHolder;

public class SpatialDecayServiceImpl
{
    public static SpatialDecay getSpatialDecay(Player player)
    {
        return ((ISpatialDecayHolder)player).getSpatialDecay();
    }
}
