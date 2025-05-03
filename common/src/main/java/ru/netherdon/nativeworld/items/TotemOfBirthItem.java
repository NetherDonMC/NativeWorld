package ru.netherdon.nativeworld.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.entity.SpatialDecay;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.misc.DimensionHelper;
import ru.netherdon.nativeworld.network.ClientboundTotemEffectPayload;
import ru.netherdon.nativeworld.registries.NWCriterionTriggers;
import ru.netherdon.nativeworld.registries.NWDataComponentTypes;
import ru.netherdon.nativeworld.services.NetworkService;
import ru.netherdon.nativeworld.services.SpatialDecayService;

import java.util.List;

public class TotemOfBirthItem extends Item implements INeoForgeItemExtension
{
    public static final int COOLDOWN = 40;
    private static final Component UNEXPLORED = Component.translatable("item." + NativeWorld.ID + ".totem_of_birth.unexplored")
        .withStyle(ChatFormatting.GRAY);

    public TotemOfBirthItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        TotemContent totemContent = stack.getOrDefault(NWDataComponentTypes.TOTEM.value(), TotemContent.EMPTY);
        if (totemContent.totem().isPresent())
        {
            totemContent.addToTooltip(context, tooltipComponents::add, tooltipFlag);
        }
        else
        {
            tooltipComponents.add(UNEXPLORED);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        TotemContent totemContent = stack.getOrDefault(
            NWDataComponentTypes.TOTEM.value(),
            TotemContent.EMPTY
        );

        if (!this.canUseInDimension(totemContent, level.dimension(), level.registryAccess()))
        {
            return super.use(level, player, hand);
        }

        SpatialDecay spatialDecay = SpatialDecayService.getSpatialDecay(player);
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
            NWCriterionTriggers.USED_TOTEM_OF_BIRTH.value().trigger(serverPlayer, stack);
            NetworkService.sendToPlayersIn(
                serverPlayer.serverLevel(),
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
    @Nullable
    public String getCreatorModId(ItemStack itemStack)
    {
        TotemContent totemContent = itemStack.get(NWDataComponentTypes.TOTEM.value());
        if (totemContent != null && totemContent.totem().isPresent())
        {
            var key = totemContent.totem().get().unwrapKey();
            if (key.isPresent())
            {
                return key.get().location().getNamespace();
            }
        }

        return BuiltInRegistries.ITEM.getKey(this).getNamespace();
    }

    public ItemStack withContent(TotemContent content)
    {
        ItemStack stack = this.getDefaultInstance();
        stack.set(NWDataComponentTypes.TOTEM.value(), content);
        return stack;
    }
}
