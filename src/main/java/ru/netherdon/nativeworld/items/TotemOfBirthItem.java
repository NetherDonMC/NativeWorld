package ru.netherdon.nativeworld.items;

import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.nativeworld.attachments.SpatialDecay;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.misc.DimensionHelper;
import ru.netherdon.nativeworld.network.ClientboundTotemEffectPayload;
import ru.netherdon.nativeworld.registries.NWAttachmentTypes;
import ru.netherdon.nativeworld.registries.NWCriterionTriggers;
import ru.netherdon.nativeworld.registries.NWDataComponentTypes;

import java.util.List;

public class TotemOfBirthItem extends Item
{
    public static final int COOLDOWN = 40;

    public TotemOfBirthItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        TotemContent totemContent = stack.get(NWDataComponentTypes.TOTEM);
        if (totemContent != null)
        {
            totemContent.addToTooltip(context, tooltipComponents::add, tooltipFlag);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        TotemContent totemContent = stack.getOrDefault(
            NWDataComponentTypes.TOTEM,
            TotemContent.EMPTY
        );

        if (!this.canUseInDimension(totemContent, level.dimension(), level.registryAccess()))
        {
            return super.use(level, player, hand);
        }

        SpatialDecay spatialDecay = player.getData(NWAttachmentTypes.SPATIAL_DECAY);
        spatialDecay.getLocalSafeDimensions().add(level.dimension());
        ItemStack newStack = stack.copy();
        if (!player.hasInfiniteMaterials())
        {
            newStack.shrink(1);
        }
        player.setItemInHand(hand, newStack);
        player.getCooldowns().addCooldown(this, COOLDOWN);
        player.awardStat(Stats.ITEM_USED.get(this));
        if (player instanceof ServerPlayer serverPlayer)
        {
            NWCriterionTriggers.USED_TOTEM_OF_BIRTH.get().trigger(serverPlayer, stack);
            PacketDistributor.sendToAllPlayers(
                new ClientboundTotemEffectPayload(
                    stack,
                    totemContent.getParticleColor(),
                    serverPlayer.getId()
                )
            );
        }
        return InteractionResultHolder.sidedSuccess(newStack, level.isClientSide);
    }

    protected boolean canUseInDimension(TotemContent totemContent, ResourceKey<Level> dimension, HolderLookup.Provider provider)
    {
        return totemContent.totem()
            .map(
                (totem) -> totem.value().canUseInDimension(dimension)
            )
            .orElseGet(
                () -> !DimensionHelper.hasLocalTotemFor(dimension, provider)
            );
    }

    @Override
    public @Nullable String getCreatorModId(ItemStack itemStack)
    {
        TotemContent totemContent = itemStack.get(NWDataComponentTypes.TOTEM);
        if (totemContent != null && totemContent.totem().isPresent())
        {
            return totemContent.totem().get().getKey().location().getNamespace();
        }

        return super.getCreatorModId(itemStack);
    }

    public ItemStack withContent(TotemContent content)
    {
        ItemStack stack = this.getDefaultInstance();
        stack.set(NWDataComponentTypes.TOTEM, content);
        return stack;
    }
}
