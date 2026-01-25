package es.xcm.hunger.interactions;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.HHMUtils;
import es.xcm.hunger.assets.FoodValue;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

class FeedInteractionBase extends SimpleInstantInteraction {
    protected float fallbackHungerRestoration;

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
        final ItemStack heldItem = context.getHeldItem();
        if (playerRef == null || hungerComponent == null || heldItem == null) return;

        float hungerRestoration = FoodValue.getHungerRestoration(heldItem.getItemId(), this.fallbackHungerRestoration);

        hungerComponent.feed(hungerRestoration);
        HHMHud.updatePlayerHungerLevel(playerRef, hungerComponent.getHungerLevel());
        HHMUtils.removeActiveEffects(ref, commandBuffer, HHMUtils::activeEntityEffectIsHungerRelated);
    }
}
