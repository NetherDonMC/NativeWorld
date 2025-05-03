package ru.netherdon.nativeworld.neoforge.client.events;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.NativeWorldClient;
import ru.netherdon.nativeworld.client.SpatialDecayOutlineHooks;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.GAME)
public class SpatialDecayOutlineEventHandler
{
    @SubscribeEvent
    public static void render(RenderGuiEvent.Pre event)
    {
        SpatialDecayOutlineHooks.renderOutline(
            NativeWorldClient.SPATIAL_DECAY_OUTLINE,
            Minecraft.getInstance(),
            event.getGuiGraphics(),
            event.getPartialTick()
        );
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event)
    {
        SpatialDecayOutlineHooks.clientTick(
            NativeWorldClient.SPATIAL_DECAY_OUTLINE,
            Minecraft.getInstance()
        );
    }
}
