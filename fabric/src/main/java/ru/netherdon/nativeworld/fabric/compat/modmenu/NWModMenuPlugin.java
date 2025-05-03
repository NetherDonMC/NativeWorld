package ru.netherdon.nativeworld.fabric.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import ru.netherdon.nativeworld.client.ConfigScreenLoader;
import ru.netherdon.nativeworld.compat.ClothConfigBuilder;

public class NWModMenuPlugin implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        final var configScreenLoader = ConfigScreenLoader.get();
        if (configScreenLoader == null)
        {
            return null;
        }
        return configScreenLoader::apply;
    }
}
