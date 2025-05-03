package ru.netherdon.nativeworld.neoforge.entity;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.nativeworld.entity.SpatialDecay;

public final class SpatialDecaySerializer implements IAttachmentSerializer<CompoundTag, SpatialDecay>
{
    public static final SpatialDecaySerializer INSTANCE = new SpatialDecaySerializer();

    private SpatialDecaySerializer() {}

    @Override
    public SpatialDecay read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider registries)
    {
        return SpatialDecay.read((Player)holder, tag, registries);
    }

    @Override
    public @Nullable CompoundTag write(SpatialDecay spatialDecay, HolderLookup.Provider registries)
    {
        return spatialDecay.save(registries);
    }
}
