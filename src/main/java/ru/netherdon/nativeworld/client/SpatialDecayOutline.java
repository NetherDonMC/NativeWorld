package ru.netherdon.nativeworld.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.config.NWClientConfig;
import ru.netherdon.nativeworld.misc.ResourceLocationHelper;
import ru.netherdon.nativeworld.registries.NWAttachmentTypes;

public class SpatialDecayOutline implements Renderable
{
    private static final ResourceLocation THORNS0_TEXTURE = ResourceLocationHelper.mod("textures/misc/spatial_decay_thorns0.png");
    private static final ResourceLocation THORNS1_TEXTURE = ResourceLocationHelper.mod("textures/misc/spatial_decay_thorns1.png");

    private static final int MAX_FADE = 20;
    private static final int HURT_ANIMATION_TIME = 8;
    private static final int MAX_HURT_ANIMATION_TIME = HURT_ANIMATION_TIME * 2;
    private static final int RARE_HURT_DECAY = 200;
    private static final int FREQUENTLY_HURT_DECAY = MAX_HURT_ANIMATION_TIME + 5;

    private int fadeTick = 0;
    private int hurtAnimationTick = 0;
    private int hurtDecay = 0;

    private int lastDegree = 0;
    private boolean wasHealthy = true;

    public void tick(@NotNull Player player)
    {
        SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);

        if (spatialDecay.isPlayerShaking() && !spatialDecay.isPlayerHasImmunity())
        {
            this.fadeTick = Math.min(this.fadeTick + 1, MAX_FADE);
        }
        else
        {
            this.fadeTick = Math.max(this.fadeTick - 1, 0);
        }

        boolean healthy = !spatialDecay.mayApplyEffect();
        int degree = spatialDecay.getDegree();
        boolean worse = degree != 0 && this.lastDegree <= degree;
        this.lastDegree = degree;

        if (this.hurtDecay > 0)
        {
            this.hurtDecay--;
        }

        if (!healthy && wasHealthy)
        {
            this.hurtDecay = 0;
        }
        this.wasHealthy = healthy;

        if (this.hurtAnimationTick >= MAX_HURT_ANIMATION_TIME)
        {
            if (this.hurtDecay <= 0 && worse && NWClientConfig.spatialDecayGui().animationEnabled())
            {
                this.hurtAnimationTick = 0;
                this.hurtDecay = healthy ? RARE_HURT_DECAY : FREQUENTLY_HURT_DECAY;
            }
        }
        else
        {
            this.hurtAnimationTick++;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks)
    {
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        if (this.fadeTick == 0)
        {
            return;
        }

        float alphaMul = (float)this.fadeTick / MAX_FADE;
        float scale = (float)Math.abs(this.hurtAnimationTick - HURT_ANIMATION_TIME) / HURT_ANIMATION_TIME;
        float easedScale = (float)Math.pow(1f - scale, 2f);
        int scaledWidth = (int)((float)width * (1f + easedScale));
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
}
