package ru.netherdon.nativeworld.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.config.NWClientConfig;
import ru.netherdon.nativeworld.config.NWServerConfig;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;
import ru.netherdon.nativeworld.registries.NWAttachmentTypes;

public class SpatialDecayOutline implements Renderable
{
    private static final ResourceLocation THORNS0_TEXTURE = ResourceLocationHelper.mod("textures/misc/spatial_decay_thorns0.png");
    private static final ResourceLocation THORNS1_TEXTURE = ResourceLocationHelper.mod("textures/misc/spatial_decay_thorns1.png");

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
        SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);

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
        guiGraphics.setColor(1f, 1f, 1f, NWClientConfig.spatialDecayGui().layer0Opacity() * alphaMul);
        guiGraphics.blit(THORNS0_TEXTURE, 0, 0, 0, 0, scaledWidth, height, scaledWidth, height);
        guiGraphics.blit(THORNS0_TEXTURE, -widthDiff, 0, 0, 0, scaledWidth, height, -scaledWidth, height);
        guiGraphics.setColor(1f, 1f, 1f, NWClientConfig.spatialDecayGui().layer1Opacity() * alphaMul);
        guiGraphics.blit(THORNS1_TEXTURE, 0, 0, 0, 0, scaledWidth, height, scaledWidth, height);
        guiGraphics.blit(THORNS1_TEXTURE, -widthDiff, 0, 0, 0, scaledWidth, height, -scaledWidth, height);
        guiGraphics.setColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }

    public void startStretching()
    {
        if (NWClientConfig.spatialDecayGui().animationEnabled())
        {
            this.stretchingStarted = true;
        }
    }
}
