package ru.netherdon.nativeworld.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.netherdon.nativeworld.items.totems.TotemContent;
import ru.netherdon.nativeworld.items.totems.TotemOfBirthType;
import ru.netherdon.nativeworld.registries.NWDataComponentTypes;
import ru.netherdon.nativeworld.registries.NWItems;

import java.util.Optional;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin
{
    @Inject(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BakedModel;getOverrides()Lnet/minecraft/client/renderer/block/model/ItemOverrides;"))
    public void getModelInject(ItemStack stack, Level level, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir, @Local LocalRef<BakedModel> bakedmodel)
    {
        if (stack.is(NWItems.TOTEM_OF_BIRTH.get()))
        {
            TotemContent totemContent = stack.getOrDefault(
                NWDataComponentTypes.TOTEM,
                TotemContent.EMPTY
            );
            if (totemContent.totem().isPresent())
            {
                TotemOfBirthType totem = totemContent.totem().get().value();
                if (totem.model().isPresent())
                {
                    ResourceLocation modelId = totem.model().get();
                    this.nativeworld$getModel(modelId).ifPresent(bakedmodel::set);
                }
            }
        }
    }

    @Unique
    private Optional<BakedModel> nativeworld$getModel(ResourceLocation id)
    {
        ModelManager modelManager = this.itemModelShaper().getModelManager();
        BakedModel missingModel = modelManager.getMissingModel();
        BakedModel model = modelManager.getModel(
            ModelResourceLocation.inventory(id)
        );
        if (model != missingModel)
        {
            return Optional.of(model);
        }

        model = modelManager.getModel(
            ModelResourceLocation.standalone(id)
        );

        if (model != missingModel)
        {
            return Optional.of(model);
        }

        return Optional.empty();
    }

    @Accessor("itemModelShaper")
    protected abstract ItemModelShaper itemModelShaper();
}
