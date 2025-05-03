package ru.netherdon.nativeworld.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiStack;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.registries.NWDataComponentTypes;
import ru.netherdon.nativeworld.registries.NWItems;

@EmiEntrypoint
public class NWEmiPlugin implements EmiPlugin
{
    @Override
    public void register(EmiRegistry registry)
    {
        registry.setDefaultComparison(
            EmiStack.of(NWItems.TOTEM_OF_BIRTH.value()),
            Comparison.compareData((stack) ->
                stack.getOrDefault(
                    NWDataComponentTypes.TOTEM.value(),
                    TotemContent.EMPTY
                )
                .totem()
                .orElse(null)
            )
        );
    }
}
