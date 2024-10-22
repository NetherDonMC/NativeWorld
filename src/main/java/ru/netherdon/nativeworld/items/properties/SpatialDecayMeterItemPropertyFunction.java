package ru.netherdon.nativeworld.items.properties;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.registries.NWAttachmentTypes;
import ru.netherdon.nativeworld.registries.NWDataComponentTypes;

public class SpatialDecayMeterItemPropertyFunction implements ClampedItemPropertyFunction
{
    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed)
    {
        Float stage = stack.get(NWDataComponentTypes.METER_BAR);
        if (stage != null)
        {
            return Math.max(stage, 0f);
        }

        if (entity instanceof Player player)
        {
            SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);
            int start = SpatialDecay.getStartDegree();
            int interval = SpatialDecay.getAmplifierInterval();
            int degree = spatialDecay.getDegree();
            if (degree > start)
            {
                int d = degree - start;
                return 1f + (float)d / interval;
            }

            return (float)degree / start;
        }

        return 0f;
    }
}
