package ru.netherdon.nativeworld.services.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;

@Environment(EnvType.CLIENT)
public class ModelServiceImpl
{
    public static BakedModel getCustomItemModel(ModelManager modelManager, ResourceLocation id)
    {
        return modelManager.getModel(id);
    }
}
