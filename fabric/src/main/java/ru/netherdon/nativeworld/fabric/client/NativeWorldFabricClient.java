package ru.netherdon.nativeworld.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import ru.netherdon.nativeworld.NativeWorld;
import ru.netherdon.nativeworld.client.NativeWorldClient;
import ru.netherdon.nativeworld.client.SpatialDecayOutline;
import ru.netherdon.nativeworld.client.SpatialDecayOutlineHooks;
import ru.netherdon.nativeworld.client.particles.TotemParticleProvider;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayDegreePayload;
import ru.netherdon.nativeworld.network.ClientboundSpatialDecayStretchingPayload;
import ru.netherdon.nativeworld.network.ClientboundTotemEffectPayload;
import ru.netherdon.nativeworld.registries.NWParticleTypes;

import static ru.netherdon.nativeworld.client.NativeWorldClient.*;

@Environment(EnvType.CLIENT)
public final class NativeWorldFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        NativeWorldClient.init();
        ModelLoadingPlugin.register(new NativeWorldModelLoader());

        this.registerPacketHandlers();
        this.registerEvents();

        NativeWorldClient.registerItemProperties(ItemProperties::register);
        ParticleFactoryRegistry.getInstance().register(NWParticleTypes.TOTEM_OF_BIRTH.value(), TotemParticleProvider::new);
    }

    private void registerPacketHandlers()
    {
        ClientPlayNetworking.registerGlobalReceiver(
            ClientboundSpatialDecayDegreePayload.TYPE,
            (payload, context) -> NativeWorld.clientPacketHandler().handleSpatialDecayDegree(payload)
        );

        ClientPlayNetworking.registerGlobalReceiver(
            ClientboundTotemEffectPayload.TYPE,
            (payload, context) -> NativeWorld.clientPacketHandler().handleTotemEffect(payload, context.player())
        );

        ClientPlayNetworking.registerGlobalReceiver(
            ClientboundSpatialDecayStretchingPayload.TYPE,
            (payload, context) -> NativeWorld.clientPacketHandler().handleSpatialDecayStretching(payload)
        );
    }

    private void registerEvents()
    {
        HudRenderCallback.EVENT.register((guiGraphics, deltaTracker) ->
            SpatialDecayOutlineHooks.renderOutline(SPATIAL_DECAY_OUTLINE, Minecraft.getInstance(), guiGraphics, deltaTracker)
        );

        ClientTickEvents.END_CLIENT_TICK.register((minecraft) ->
            SpatialDecayOutlineHooks.clientTick(SPATIAL_DECAY_OUTLINE, minecraft)
        );
    }
}
