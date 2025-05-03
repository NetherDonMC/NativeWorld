package ru.netherdon.nativeworld.neoforge.registries;

import net.minecraft.client.gui.Gui;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import ru.netherdon.nativeworld.registries.HeartSprites;

public final class NWHeartTypes
{
    public static final EnumProxy<Gui.HeartType> SPATIAL_DECAY = new EnumProxy<>(
        Gui.HeartType.class,
        HeartSprites.SPATIAL_DECAY.full(),
        HeartSprites.SPATIAL_DECAY.fullBlinking(),
        HeartSprites.SPATIAL_DECAY.half(),
        HeartSprites.SPATIAL_DECAY.halfBlinking(),
        HeartSprites.SPATIAL_DECAY.hardcoreFull(),
        HeartSprites.SPATIAL_DECAY.hardcoreFullBlinking(),
        HeartSprites.SPATIAL_DECAY.hardcoreHalf(),
        HeartSprites.SPATIAL_DECAY.hardcoreHalfBlinking()
    );

    public static Gui.HeartType spatialDecay()
    {
        return SPATIAL_DECAY.getValue();
    }
}
