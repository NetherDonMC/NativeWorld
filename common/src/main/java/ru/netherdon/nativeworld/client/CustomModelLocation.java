package ru.netherdon.nativeworld.client;

import net.minecraft.resources.ResourceLocation;
import ru.netherdon.nativeworld.NativeWorld;

public record CustomModelLocation(String modId, ResourceLocation id)
{
    public CustomModelLocation(ResourceLocation model)
    {
        this(NativeWorld.ID, model);
    }
}
