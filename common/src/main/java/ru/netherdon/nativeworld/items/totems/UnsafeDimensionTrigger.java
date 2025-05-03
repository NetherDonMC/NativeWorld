package ru.netherdon.nativeworld.items.totems;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.services.SpatialDecayService;

import java.util.Optional;

public class UnsafeDimensionTrigger extends SimpleCriterionTrigger<UnsafeDimensionTrigger.TriggerInstance>
{
    @Override
    public Codec<TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player)
    {
        this.trigger(player, (instance) ->
            instance.test(player)
        );
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
            ).apply(instance, TriggerInstance::new)
        );

        public static final TriggerInstance EMPTY = new TriggerInstance(Optional.empty());

        public boolean test(ServerPlayer player)
        {
            Level level = player.level();
            SpatialDecay spatialDecay = SpatialDecayService.getSpatialDecay(player);
            return !spatialDecay.isSafeDimension(level);
        }

        public static TriggerInstance of(ContextAwarePredicate player)
        {
            return new TriggerInstance(Optional.of(player));
        }
    }
}
