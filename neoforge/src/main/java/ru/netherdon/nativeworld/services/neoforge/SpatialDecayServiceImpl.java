package ru.netherdon.nativeworld.services.neoforge;

import net.minecraft.world.entity.player.Player;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.neoforge.registries.NWAttachmentTypes;

public class SpatialDecayServiceImpl
{
    public static SpatialDecay getSpatialDecay(Player player)
    {
        return player.getData(NWAttachmentTypes.SPATIAL_DECAY);
    }
}
