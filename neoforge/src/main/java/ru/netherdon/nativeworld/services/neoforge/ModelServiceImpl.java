package ru.netherdon.nativeworld.services.neoforge;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

@OnlyIn(Dist.CLIENT)
public class ModelServiceImpl
{
    public static BakedModel getCustomItemModel(ModelManager modelManager, ResourceLocation id)
    {
        return modelManager.getModel(ResourceLocationHelper.standaloneModel(id));
    }
}
