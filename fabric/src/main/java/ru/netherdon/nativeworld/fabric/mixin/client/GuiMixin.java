package ru.netherdon.nativeworld.fabric.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.nativeworld.registries.HeartSprites;
import ru.netherdon.nativeworld.registries.NWMobEffects;

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public final class GuiMixin
{
    @Unique
    private boolean hasSpatialDecay = false;

    @Unique
    private ResourceLocation spatialDecaySprite;

    @Inject(method = "renderHearts", at = @At("HEAD"))
    public void checkSpatialDecay(GuiGraphics guiGraphics, Player player, int x, int y, int lines, int regeneration, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci)
    {
        this.hasSpatialDecay = player.hasEffect(NWMobEffects.SPATIAL_DECAY);
    }

    @Inject(method = "renderHearts", at = @At("RETURN"))
    public void resetSpatialDecay(GuiGraphics guiGraphics, Player player, int x, int y, int lines, int regeneration, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci)
    {
        this.hasSpatialDecay = false;
    }

    @Inject(method = "renderHeart", at = @At(value = "HEAD"))
    public void getSpatialDecayHeartSprite(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci)
    {
        this.spatialDecaySprite = this.hasSpatialDecay && heartType != Gui.HeartType.CONTAINER && heartType != Gui.HeartType.ABSORBING
            ? HeartSprites.SPATIAL_DECAY.getSprite(hardcore, half, blinking)
            : null;
    }

    @ModifyArg(method = "renderHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"), index = 0)
    public ResourceLocation renderSpatialDecayHearts(ResourceLocation oldSprite)
    {
        return this.spatialDecaySprite != null ? this.spatialDecaySprite : oldSprite;
    }
}
