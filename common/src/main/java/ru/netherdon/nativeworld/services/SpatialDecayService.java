package ru.netherdon.nativeworld.services;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Contract;
import ru.netherdon.nativeworld.entity.SpatialDecay;

public class SpatialDecayService
{
    @ExpectPlatform
    @Contract(pure = true)
    public static SpatialDecay getSpatialDecay(Player player)
    {
        throw new UnsupportedOperationException();
    }
}
