package ru.netherdon.nativeworld.client.events;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.SpatialDecayOutline;
import ru.netherdon.nativeworld.config.NWClientConfig;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.GAME)
public class SpatialDecayOutlineEventHandler
{
    private static final SpatialDecayOutline SPATIAL_DECAY_OUTLINE = new SpatialDecayOutline();

    @SubscribeEvent
    public static void render(RenderGuiEvent.Pre event)
    {
        if (!NWClientConfig.spatialDecayGui().enabled() || Minecraft.getInstance().options.hideGui)
        {
            return;
        }

        GuiGraphics graphics = event.getGuiGraphics();
        Window window = Minecraft.getInstance().getWindow();

        SPATIAL_DECAY_OUTLINE.render(graphics, window.getX(), window.getY(), event.getPartialTick().getGameTimeDeltaTicks());
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event)
    {
        if (!NWClientConfig.spatialDecayGui().enabled() || Minecraft.getInstance().isPaused())
        {
            return;
        }

        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            SPATIAL_DECAY_OUTLINE.tick(player);
        }
    }
}
