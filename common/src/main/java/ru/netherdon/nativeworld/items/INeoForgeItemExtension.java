package ru.netherdon.nativeworld.items;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface INeoForgeItemExtension
{
    @Nullable
    public default String getCreatorModId(ItemStack itemStack)
    {
        return null;
    }
}
