package ru.netherdon.nativeworld.services;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;

@Environment(EnvType.CLIENT)
public class ModelService
{
    @ExpectPlatform
    @Contract(pure = true)
    public static BakedModel getCustomItemModel(ModelManager modelManager, ResourceLocation id)
    {
        throw new UnsupportedOperationException();
    }
}
