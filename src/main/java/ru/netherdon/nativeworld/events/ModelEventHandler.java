package ru.netherdon.nativeworld.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.compat.OtherModIds;

import static ru.netherdon.nativeworld.misc.ResourceLocationHelper.totemModel;

@EventBusSubscriber(value = Dist.CLIENT, modid = NativeWorld.ID, bus = EventBusSubscriber.Bus.MOD)
public final class ModelEventHandler
{
    @SubscribeEvent
    public static void registerAdditional(ModelEvent.RegisterAdditional event)
    {
        event.register(totemModel("overworld"));
        event.register(totemModel("nether"));
        event.register(totemModel("end"));
        registerIfModLoaded(event, OtherModIds.DEEPER_AND_DARKER, "otherside");
        registerIfModLoaded(event, OtherModIds.BUMBLEZONE, "bumblezone");
    }

    private static void registerIfModLoaded(ModelEvent.RegisterAdditional event, String modId, String... totems)
    {
        if (ModList.get().isLoaded(modId))
        {
            for (String totem : totems)
            {
                event.register(totemModel(totem));
            }
        }
    }
}
