package ru.netherdon.nativeworld.registries;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.attachments.SpatialDecay;

public final class NWAttachmentTypes
{
    public static final DeferredRegister<AttachmentType<?>> REGISTER =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NativeWorld.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SpatialDecay>> SPATIAL_DECAY =
        REGISTER.register("spatial_decay", () -> AttachmentType.serializable((holder) ->
        {
            if (holder instanceof Player player)
            {
                return new SpatialDecay(player);
            }

            throw new IllegalStateException("Spatial decay can't be applied to " + holder.getClass().getName());
        }).build());
}
