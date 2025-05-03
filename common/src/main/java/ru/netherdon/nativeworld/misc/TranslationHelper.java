package ru.netherdon.nativeworld.misc;

import ru.netherdon.nativeworld.NativeWorld;

public final class TranslationHelper
{
    public static String key(String namespace, String... path)
    {
        return "%s.%s.%s".formatted(namespace, NativeWorld.ID, String.join(".", path));
    }
}
