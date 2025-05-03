package ru.netherdon.nativeworld.registries;

import net.minecraft.resources.ResourceLocation;

import static ru.netherdon.nativeworld.misc.ResourceLocationHelper.heartSprite;

public record HeartSprites(
    ResourceLocation full,
    ResourceLocation fullBlinking,
    ResourceLocation half,
    ResourceLocation halfBlinking,
    ResourceLocation hardcoreFull,
    ResourceLocation hardcoreFullBlinking,
    ResourceLocation hardcoreHalf,
    ResourceLocation hardcoreHalfBlinking
)
{
    public static final HeartSprites SPATIAL_DECAY = new HeartSprites(
        heartSprite("full"),
        heartSprite("full_blinking"),
        heartSprite("half"),
        heartSprite("half_blinking"),
        heartSprite("hardcore_full"),
        heartSprite("hardcore_full_blinking"),
        heartSprite("hardcore_half"),
        heartSprite("hardcore_half_blinking")
    );

    public ResourceLocation getSprite(boolean hardcore, boolean half, boolean blinking)
    {
        if (!hardcore)
        {
            if (half)
            {
                return blinking ? this.halfBlinking : this.half;
            }
            else
            {
                return blinking ? this.fullBlinking : this.full;
            }
        }
        else if (half)
        {
            return blinking ? this.hardcoreHalfBlinking : this.hardcoreHalf;
        }
        else
        {
            return blinking ? this.hardcoreFullBlinking : this.hardcoreFull;
        }
    }
}
