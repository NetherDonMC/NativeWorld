package ru.netherdon.nativeworld.misc;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import ru.netherdon.nativeworld.NativeWorld;

public final class ResourceLocationHelper
{
    public static final String SPATIAL_DECAY_HEART_PREFIX = "hud/heart/spatial_decay_";
    public static final String TOTEM_MODEL_PREFIX = "item/totem_of_birth/";

    public static ResourceLocation mod(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(NativeWorld.ID, name);
    }

    public static ResourceLocation heartSprite(ResourceLocation sprite)
    {
        return sprite.withPrefix(SPATIAL_DECAY_HEART_PREFIX);
    }

    public static ResourceLocation heartSprite(String name)
    {
        return heartSprite(mod(name));
    }

    public static ModelResourceLocation standaloneModel(ResourceLocation path)
    {
        return new ModelResourceLocation(path, "standalone");
    }

    public static ResourceLocation totemModel(ResourceLocation path)
    {
        return path.withPrefix(TOTEM_MODEL_PREFIX);
    }

    public static ResourceLocation totemModel(String path)
    {
        return totemModel(mod(path));
    }
}
