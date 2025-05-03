package ru.netherdon.nativeworld.client;

import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public final class ConfigScreenLoader
{
    private static final String CLASS_NAME = "ru.netherdon.nativeworld.compat.ClothConfigBuilder";
    private static Function<Screen, Screen> CONFIG_SCREEN_LOADER;
    private static boolean INITIALIZED = false;

    @Nullable
    public static Function<Screen, Screen> get()
    {
        if (!INITIALIZED)
        {
            try
            {
                Class<?> builderClass = Class.forName(CLASS_NAME);
                final Method buildMethod = builderClass.getMethod("build", Screen.class);
                CONFIG_SCREEN_LOADER = (screen) ->
                {
                    try
                    {
                        return (Screen) buildMethod.invoke(null, screen);
                    }
                    catch (InvocationTargetException | IllegalAccessException | ClassCastException exception)
                    {
                        return null;
                    }
                };
            }
            catch (ClassNotFoundException | NoSuchMethodException | NoClassDefFoundError exception)
            {
                CONFIG_SCREEN_LOADER = null;
            }
            INITIALIZED = true;
        }

        return CONFIG_SCREEN_LOADER;
    }
}
