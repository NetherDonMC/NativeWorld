package ru.netherdon.nativeworld.compat.jei;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.registries.NWDataComponentTypes;

public final class TotemSubtypeInterpreter implements ISubtypeInterpreter<ItemStack>
{
    public static final TotemSubtypeInterpreter INSTANCE = new TotemSubtypeInterpreter();

    private TotemSubtypeInterpreter() {}

    @Override
    public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context)
    {
        TotemContent content = ingredient.getOrDefault(
            NWDataComponentTypes.TOTEM,
            TotemContent.EMPTY
        );
        return content.totem()
            .orElse(null);
    }

    @Override
    public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context)
    {
        if (ingredient.getComponentsPatch().isEmpty())
        {
            return "";
        }
        TotemContent totem = ingredient.getOrDefault(NWDataComponentTypes.TOTEM, TotemContent.EMPTY);
        String itemDescriptionId = ingredient.getItem().getDescriptionId();
        String totemId = totem.totem().map(Holder::getRegisteredName).orElse("none");
        return "%s.totem_id.%s".formatted(itemDescriptionId, totemId);
    }
}
