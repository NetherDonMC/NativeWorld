package ru.netherdon.nativeworld.neoforge.registries;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.neoforge.entity.SpatialDecaySerializer;

public final class NWAttachmentTypes
{
    public static final DeferredRegister<AttachmentType<?>> REGISTER =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NativeWorld.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SpatialDecay>> SPATIAL_DECAY =
        REGISTER.register("spatial_decay", () -> AttachmentType.builder(SpatialDecay::of)
        .serialize(SpatialDecaySerializer.INSTANCE)
        .build());
}
