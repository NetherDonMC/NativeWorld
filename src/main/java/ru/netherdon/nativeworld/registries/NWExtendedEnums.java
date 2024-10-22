package ru.netherdon.nativeworld.registries;

import net.minecraft.client.gui.Gui;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import static ru.netherdon.nativeworld.misc.ResourceLocationHelper.heartSprite;

public final class NWExtendedEnums
{
    public static final EnumProxy<Gui.HeartType> SPATIAL_DECAY_HEART_TYPE = new EnumProxy<>(
        Gui.HeartType.class,
        heartSprite("full"),
        heartSprite("full_blinking"),
        heartSprite("half"),
        heartSprite("half_blinking"),
        heartSprite("hardcore_full"),
        heartSprite("hardcore_full_blinking"),
        heartSprite("hardcore_half"),
        heartSprite("hardcore_half_blinking")
    );

    public static Gui.HeartType spatialDecayHeartType()
    {
        return SPATIAL_DECAY_HEART_TYPE.getValue();
    }
}
