package ru.netherdon.nativeworld.items.totems;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.Optional;
import java.util.function.Consumer;

public record TotemContent(Optional<Holder<TotemOfBirthType>> totem, boolean showInTooltip) implements TooltipProvider
{
    public static final TotemContent EMPTY = new TotemContent(Optional.empty(), false);

    public static final Codec<TotemContent> FULL_CODEC = RecordCodecBuilder.create((instance) ->
        instance.group(
            TotemOfBirthType.HOLDER_CODEC.optionalFieldOf("id").forGetter(TotemContent::totem),
            Codec.BOOL.fieldOf("showInTooltip").forGetter(TotemContent::showInTooltip)
        ).apply(instance, TotemContent::new)
    );

    public static final Codec<TotemContent> CODEC = Codec.withAlternative(
        FULL_CODEC,
        TotemOfBirthType.HOLDER_CODEC,
        (totem) -> new TotemContent(totem, true)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, TotemContent> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.optional(TotemOfBirthType.STREAM_CODEC),
        TotemContent::totem,
        ByteBufCodecs.BOOL,
        TotemContent::showInTooltip,
        TotemContent::new
    );

    public TotemContent(Holder<TotemOfBirthType> totem, boolean showInTooltip)
    {
        this(Optional.ofNullable(totem), showInTooltip);
    }

    public TotemContent(Holder<TotemOfBirthType> totem)
    {
        this(totem, true);
    }

    public int getParticleColor()
    {
        return this.totem.map((totemIn) -> totemIn.value().particleColor())
            .orElse(TotemOfBirthType.DEFAULT_PARTICLE_COLOR);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        if (this.showInTooltip && this.totem.isPresent())
        {
            consumer.accept(
                Component.empty()
                    .withStyle(ChatFormatting.GRAY)
                    .append(this.totem.get().value().name())
            );
        }
    }
}
