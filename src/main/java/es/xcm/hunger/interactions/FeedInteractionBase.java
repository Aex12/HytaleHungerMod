package es.xcm.hunger.interactions;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

class FeedInteractionBase extends SimpleInstantInteraction {
    protected float feedAmount;

    @Override
    protected void firstRun(
            @NonNullDecl InteractionType interactionType,
            @NonNullDecl InteractionContext context,
            @NonNullDecl CooldownHandler cooldownHandler
    ) {
        final Ref<EntityStore> ref = context.getEntity();
        final Store<EntityStore> store = ref.getStore();
        final PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        final HungerComponent hungerComponent = store.getComponent(ref, HungerComponent.getComponentType());
        if (playerRef == null || hungerComponent == null) return;
        hungerComponent.feed(this.feedAmount);
        float hunger = hungerComponent.getHungerLevel();
        HHMHud.updatePlayerHud(playerRef, hunger);
    }
}
