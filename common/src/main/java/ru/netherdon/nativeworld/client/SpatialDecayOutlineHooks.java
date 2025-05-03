package ru.netherdon.nativeworld.client;

import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class SpatialDecayOutlineHooks
{
    public static void renderOutline(SpatialDecayOutline outline, Minecraft minecraft, GuiGraphics graphics, DeltaTracker deltaTracker)
    {
        if (!SpatialDecayOutline.isEnabled() || minecraft.options.hideGui)
        {
            return;
        }

        Window window = minecraft.getWindow();

        outline.render(graphics, window.getX(), window.getY(), deltaTracker.getGameTimeDeltaTicks());
    }

    public static void clientTick(SpatialDecayOutline outline, Minecraft minecraft)
    {
        if (!SpatialDecayOutline.isEnabled() || minecraft.isPaused())
        {
            return;
        }

        Player player = minecraft.player;
        if (player != null)
        {
            outline.tick(player);
        }
    }
}
