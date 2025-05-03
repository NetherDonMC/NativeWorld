package ru.netherdon.nativeworld.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.misc.NWExtraCodecs;
import ru.netherdon.nativeworld.services.RegistryManager;

public final class NWDataComponentTypes
{
    private static final IRegistryProvider<DataComponentType<?>> PROVIDER =
        RegistryManager.getOrCreate(BuiltInRegistries.DATA_COMPONENT_TYPE);

    public static final Holder<DataComponentType<TotemContent>> TOTEM =
        PROVIDER.register("totem", () ->
            DataComponentType.<TotemContent>builder()
                .persistent(TotemContent.CODEC)
                .networkSynchronized(TotemContent.STREAM_CODEC)
                .cacheEncoding()
                .build()
        );

    public static final Holder<DataComponentType<Float>> METER_BAR =
        PROVIDER.register("meter_bar", () ->
            DataComponentType.<Float>builder()
                .persistent(NWExtraCodecs.NON_NEGATIVE_FLOAT)
                .networkSynchronized(ByteBufCodecs.FLOAT)
                .cacheEncoding()
                .build()
        );

    public static void initialize() {}
}
