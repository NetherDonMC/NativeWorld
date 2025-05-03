package ru.netherdon.nativeworld.fabric.mixin.client;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.fabric.entity.ISpatialDecayHolder;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;

@Mixin(Player.class)
public abstract class PlayerMixin implements ISpatialDecayHolder
{
    @Unique
    private static final ResourceLocation DATA_KEY = ResourceLocationHelper.mod("spatial_decay");

    @Unique
    private SpatialDecay spatialDecay;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci)
    {
        this.getSpatialDecay().tick();
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void save(CompoundTag compoundTag, CallbackInfo ci)
    {
        Player player = this.asPlayer();
        RegistryAccess registryAccess = player.registryAccess();
        compoundTag.put(DATA_KEY.toString(), this.getSpatialDecay().save(registryAccess));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void load(CompoundTag compoundTag, CallbackInfo ci)
    {
        Player player = this.asPlayer();
        RegistryAccess registryAccess = player.registryAccess();
        CompoundTag tag = compoundTag.getCompound(DATA_KEY.toString());
        this.spatialDecay = SpatialDecay.read(player, tag, registryAccess);
    }

    @Override
    public SpatialDecay getSpatialDecay()
    {
        if (spatialDecay == null)
        {
            this.spatialDecay = new SpatialDecay(this.asPlayer());
        }

        return this.spatialDecay;
    }

    @Unique
    private Player asPlayer()
    {
        return (Player)(Object)this;
    }
}
