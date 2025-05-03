package ru.netherdon.nativeworld.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.fml.config.ModConfig;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.config.NWClientConfig;
import ru.netherdon.nativeworld.config.NWServerConfig;
import ru.netherdon.nativeworld.entity.PlayerEvents;
import ru.netherdon.nativeworld.fabric.network.NWNetworkHandler;
import ru.netherdon.nativeworld.registries.NWCommands;
import ru.netherdon.nativeworld.registries.NWPotions;
import ru.netherdon.nativeworld.registries.NWRegistries;

public final class NativeWorldFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        NativeWorld.init();
        NWNetworkHandler.register();
        NWRegistries.registerDataPackRegistry(DynamicRegistries::registerSynced);

        NeoForgeConfigRegistry.INSTANCE.register(NativeWorld.ID, ModConfig.Type.CLIENT, NWClientConfig.getSpec(), NWClientConfig.FILE_NAME);
        NeoForgeConfigRegistry.INSTANCE.register(NativeWorld.ID, ModConfig.Type.SERVER, NWServerConfig.getSpec(), NWServerConfig.FILE_NAME);

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
            NWPotions.registerRecipes(new NWPotions.IRecipeRegister()
            {
                @Override
                public void addStartMix(Item ingredient, Holder<Potion> result)
                {
                    builder.addStartMix(ingredient, result);
                }

                @Override
                public void addMix(Holder<Potion> input, Item ingredient, Holder<Potion> result)
                {
                    builder.addMix(input, ingredient, result);
                }
            })
        );

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(NWCommands.createMainCommand())
        );

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, from, to) -> {
            System.out.println(to.dimension());
            PlayerEvents.onChangeDimension(player);
        });

        ServerPlayConnectionEvents.JOIN.register((listener, sender, server) ->
            PlayerEvents.onLogIn(listener.player)
        );

        ServerPlayerEvents.COPY_FROM.register(PlayerEvents::onClone);

        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, f1, f2, f3) ->
        {
            if (entity instanceof ServerPlayer player)
            {
                PlayerEvents.onDamage(player, source);
            }
        });
    }
}
