package ru.netherdon.nativeworld.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;
import ru.netherdon.nativeworld.registries.NWItems;

@JeiPlugin
public class NTTJeiPlugin implements IModPlugin
{
    public static final ResourceLocation ID = ResourceLocationHelper.mod("jei_plugin");

    @Override
    public @NotNull ResourceLocation getPluginUid()
    {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        registration.registerSubtypeInterpreter(NWItems.TOTEM_OF_BIRTH.get(), TotemSubtypeInterpreter.INSTANCE);
    }
}
