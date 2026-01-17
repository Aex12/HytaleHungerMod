package es.xcm.hunger.interactions;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.HHMUtils;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.lang.reflect.Field;
import java.util.Arrays;

class FeedInteractionBase extends SimpleInstantInteraction {
    protected float feedAmount;

    @Override
    protected void firstRun(
            @NonNullDecl InteractionType interactionType,
            @NonNullDecl InteractionContext context,
            @NonNullDecl CooldownHandler cooldownHandler
    ) {
        final CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
        final Ref<EntityStore> ref = context.getEntity();
        final Store<EntityStore> store = ref.getStore();
        if (commandBuffer == null) return;

        final PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        final HungerComponent hungerComponent = store.getComponent(ref, HungerComponent.getComponentType());
        final EffectControllerComponent effectController = commandBuffer.getComponent(ref, EffectControllerComponent.getComponentType());
        if (playerRef == null || hungerComponent == null || effectController == null) return;

        hungerComponent.feed(this.feedAmount);
        HHMHud.updatePlayerHungerLevel(playerRef, hungerComponent.getHungerLevel());

        final var activeEffects = effectController.getAllActiveEntityEffects();
        if (activeEffects != null) {
            Arrays.stream(activeEffects).filter(HHMUtils::activeEntityEffectIsHungerRelated).forEach(effect -> {
                effectController.removeEffect(ref, effect.getEntityEffectIndex(), commandBuffer);
            });
        }
    }
}
