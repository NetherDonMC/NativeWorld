package ru.netherdon.nativeworld.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.config.NWClientConfig;
import ru.netherdon.nativeworld.config.NWServerConfig;
import ru.netherdon.nativeworld.neoforge.registries.NWAttachmentTypes;
import ru.netherdon.nativeworld.services.neoforge.RegistryManagerImpl;

@Mod(NativeWorld.ID)
public final class NativeWorldNeoForge
{
    public NativeWorldNeoForge(IEventBus modEventBus, ModContainer modContainer)
    {
        NativeWorld.init();
        RegistryManagerImpl.registerDeferredRegistries(modEventBus);

        NWAttachmentTypes.REGISTER.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, NWServerConfig.getSpec(), NWServerConfig.FILE_NAME);
        modContainer.registerConfig(ModConfig.Type.CLIENT, NWClientConfig.getSpec(), NWClientConfig.FILE_NAME);
    }
}
