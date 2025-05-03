package ru.netherdon.nativeworld.neoforge.client.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.CustomModelLocation;
import ru.netherdon.nativeworld.client.NativeWorldClient;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.MOD)
public final class ModelEventHandler
{
    @SubscribeEvent
    public static void registerAdditional(ModelEvent.RegisterAdditional event)
    {
        for (CustomModelLocation model : NativeWorldClient.CUSTOM_MODELS)
        {
            if (ModList.get().isLoaded(model.modId()))
            {
                event.register(
                    ResourceLocationHelper.standaloneModel(model.id())
                );
            }
        }
    }
}
