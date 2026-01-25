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
import es.xcm.hunger.HytaleHungerMod;
import es.xcm.hunger.assets.FoodValue;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.config.HHMFoodValuesConfig;
import es.xcm.hunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

class FeedInteractionBase extends SimpleInstantInteraction {
    protected float fallbackHungerRestoration;
    protected float fallbackMaxHungerSaturation;

    public static float getHungerRestoration (String itemId, float fallbackValue) {
        HHMFoodValuesConfig config = HytaleHungerMod.get().getFoodValuesConfig();
        // first prefer user config
        Float configValue = config.getFoodHungerRestoration(itemId);
        if (configValue != null) return configValue;

        // then asset (mod author) values
        Float assetValue = FoodValue.getHungerRestoration(itemId);
        if (assetValue != null) return assetValue;

        // finally the fallback value
        return fallbackValue;
    }
    public static float getMaxHungerSaturation (String itemId, float fallbackValue) {
        HHMFoodValuesConfig config = HytaleHungerMod.get().getFoodValuesConfig();
        // first prefer user config
        Float configValue = config.getFoodMaxHungerSaturation(itemId);
        if (configValue != null) return configValue;

        // then asset (mod author) values
        Float assetValue = FoodValue.getMaxHungerSaturation(itemId);
        if (assetValue != null) return assetValue;

        // finally the fallback value
        return fallbackValue;
    }

    public float getHungerRestoration(String itemId) {
        return getHungerRestoration(itemId, this.fallbackHungerRestoration);
    }
    public float getMaxHungerSaturation(String itemId) {
        return getMaxHungerSaturation(itemId, this.fallbackMaxHungerSaturation);
    }

    @Override
    protected void firstRun(
            @NonNullDecl InteractionType interactionType,
            @NonNullDecl InteractionContext context,
            @NonNullDecl CooldownHandler cooldownHandler
    ) {
        final Ref<EntityStore> ref = context.getEntity();
        final Store<EntityStore> store = ref.getStore();

        final HungerComponent hungerComponent = store.getComponent(ref, HungerComponent.getComponentType());
        final ItemStack heldItem = context.getHeldItem();
        if (hungerComponent == null || heldItem == null) return;

        float currentHungerLevel = hungerComponent.getHungerLevel();
        float hungerRestoration = getHungerRestoration(heldItem.getItemId());
        float maxHungerSaturation = getMaxHungerSaturation(heldItem.getItemId());

        float targetHungerLevel = Math.min(
            currentHungerLevel + hungerRestoration,
            100.0f + maxHungerSaturation
        );

        // Avoid unnecessary updates
        // if (currentHungerLevel >= targetHungerLevel) return;
        hungerComponent.setHungerLevel(targetHungerLevel);

        final CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
        final PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (commandBuffer == null || playerRef == null) return;

        HHMHud.updatePlayerHungerLevel(playerRef, targetHungerLevel);
        HHMUtils.removeActiveEffects(ref, commandBuffer, HHMUtils::activeEntityEffectIsHungerRelated);
    }
}
