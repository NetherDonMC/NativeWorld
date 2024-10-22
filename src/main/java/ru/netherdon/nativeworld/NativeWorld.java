package ru.netherdon.nativeworld;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import ru.netherdon.nativeworld.config.NWCommonConfig;
import ru.netherdon.nativeworld.registries.*;

@Mod(NativeWorld.ID)
public class NativeWorld
{
    public static final String ID = "nativeworld";

    public NativeWorld(IEventBus modEventBus, ModContainer modContainer)
    {
        NWItems.REGISTER.register(modEventBus);
        NWMobEffects.REGISTER.register(modEventBus);
        NWAttachmentTypes.REGISTER.register(modEventBus);
        NWPotions.REGISTER.register(modEventBus);
        NWDataComponentTypes.REGISTER.register(modEventBus);
        NWCreativeTabs.REGISTER.register(modEventBus);
        NWPaticleTypes.REGISTER.register(modEventBus);
        NWCriterionTriggers.REGISTER.register(modEventBus);

        NWCommonConfig.register(modContainer);
    }
}
