package ru.netherdon.nativeworld.registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.misc.NWExtraCodecs;

public final class NWDataComponentTypes
{
    public static final DeferredRegister.DataComponents REGISTER =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NativeWorld.ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<TotemContent>> TOTEM =
        REGISTER.register("totem", () ->
            DataComponentType.<TotemContent>builder()
                .persistent(TotemContent.CODEC)
                .networkSynchronized(TotemContent.STREAM_CODEC)
                .cacheEncoding()
                .build()
        );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> METER_BAR =
        REGISTER.register("meter_bar", () ->
            DataComponentType.<Float>builder()
                .persistent(NWExtraCodecs.NON_NEGATIVE_FLOAT)
                .networkSynchronized(ByteBufCodecs.FLOAT)
                .cacheEncoding()
                .build()
        );
}
