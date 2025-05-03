package ru.netherdon.nativeworld.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import ru.netherdon.nativeworld.config.NWClientConfig;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;
import ru.netherdon.nativeworld.services.SpatialDecayService;

@Environment(EnvType.CLIENT)
public class SpatialDecayOutline implements Renderable
{
    private static final ResourceLocation SPIKES_TEXTURE0 = ResourceLocationHelper.mod("textures/misc/spatial_decay_spikes0.png");
    private static final ResourceLocation SPIKES_TEXTURE1 = ResourceLocationHelper.mod("textures/misc/spatial_decay_spikes1.png");

    private static final int MAX_FADE = 10;
    private static final int STRETCH_ANIMATION_TIME = 5;

    private int lastFadeTick = 0;
    private int fadeTick = 0;
    private int lastStretchTick = 0;
    private int stretchTick = 0;
    private boolean stretchingStarted = false;

    public void tick(@NotNull Player player)
    {
        this.lastFadeTick = this.fadeTick;
        this.lastStretchTick = this.stretchTick;
        SpatialDecay spatialDecay = SpatialDecayService.getSpatialDecay(player);

        if (spatialDecay.isPlayerShaking() && !spatialDecay.isPlayerHasImmunity() && !player.isDeadOrDying())
        {
            this.fadeTick = Math.min(this.fadeTick + 1, MAX_FADE);
        }
        else
        {
            this.fadeTick = Math.max(this.fadeTick - 1, 0);
        }

        if (this.stretchingStarted)
        {
            this.stretchTick++;
            if (this.stretchTick >= STRETCH_ANIMATION_TIME)
            {
                this.stretchTick = STRETCH_ANIMATION_TIME;
                this.stretchingStarted = false;
            }
        }
        else if (this.stretchTick > 0)
        {
            this.stretchTick--;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks)
    {
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        if (this.fadeTick == 0 && this.lastFadeTick == 0)
        {
            return;
        }

        float alphaMul = Mth.lerp(partialTicks, this.fadeTick, this.lastFadeTick) / MAX_FADE;
        float scale = Mth.lerp(partialTicks, this.stretchTick, this.lastStretchTick) / STRETCH_ANIMATION_TIME;
        float easedScale = 1f - (float)Math.pow(1f - scale, 1.5f);
        int scaledWidth = (int)((float)width * (1f + easedScale * 1.3f));
        int widthDiff = scaledWidth - width;

        RenderSystem.enableBlend();
        float opacity0 = NWClientConfig.get().spatialDecayOutline().backLayerOpacityValue();
        guiGraphics.setColor(1f, 1f, 1f, opacity0 * alphaMul);
        guiGraphics.blit(SPIKES_TEXTURE0, 0, 0, 0, 0, scaledWidth, height, scaledWidth, height);
        guiGraphics.blit(SPIKES_TEXTURE0, -widthDiff, 0, 0, 0, scaledWidth, height, -scaledWidth, height);
        float opacity1 = NWClientConfig.get().spatialDecayOutline().frontLayerOpacityValue();
        guiGraphics.setColor(1f, 1f, 1f, opacity1 * alphaMul);
        guiGraphics.blit(SPIKES_TEXTURE1, 0, 0, 0, 0, scaledWidth, height, scaledWidth, height);
        guiGraphics.blit(SPIKES_TEXTURE1, -widthDiff, 0, 0, 0, scaledWidth, height, -scaledWidth, height);
        guiGraphics.setColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }

    public void startStretching()
    {
        if (isAnimationEnabled())
        {
            this.stretchingStarted = true;
        }
    }

    public static boolean isEnabled()
    {
        return NWClientConfig.get().spatialDecayOutline().enabledValue();
    }

    public static boolean isAnimationEnabled()
    {
        return NWClientConfig.get().spatialDecayOutline().animationEnabledValue();
    }
}
