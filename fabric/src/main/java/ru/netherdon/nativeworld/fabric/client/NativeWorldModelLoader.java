package ru.netherdon.nativeworld.fabric.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.loader.api.FabricLoader;
import ru.netherdon.nativeworld.client.CustomModelLocation;
import ru.netherdon.nativeworld.client.NativeWorldClient;

@Environment(EnvType.CLIENT)
public class NativeWorldModelLoader implements ModelLoadingPlugin
{
    @Override
    public void onInitializeModelLoader(Context context)
    {
        for (CustomModelLocation model : NativeWorldClient.CUSTOM_MODELS)
        {
            if (FabricLoader.getInstance().isModLoaded(model.modId()))
            {
                context.addModels(model.id());
            }
        }
    }
}
