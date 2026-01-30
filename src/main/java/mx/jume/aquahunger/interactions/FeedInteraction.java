package mx.jume.aquahunger.interactions;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.HHMUtils;
import mx.jume.aquahunger.AquaThirstHunger;
import mx.jume.aquahunger.assets.FoodValue;
import mx.jume.aquahunger.components.HungerComponent;
import mx.jume.aquahunger.config.HHMFoodValuesConfig;
import mx.jume.aquahunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Set;

public class FeedInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<FeedInteraction> CODEC = BuilderCodec
            .builder(FeedInteraction.class, FeedInteraction::new, SimpleInstantInteraction.CODEC)
            .appendInherited(new KeyedCodec<>("HungerRestoration", Codec.FLOAT),
                    ((foodValue, value) -> foodValue.hungerRestoration = value),
                    (foodValue) -> foodValue.hungerRestoration,
                    (foodValue, parent) -> foodValue.hungerRestoration = parent.hungerRestoration)
            .add()
            .appendInherited(new KeyedCodec<>("MaxHungerSaturation", Codec.FLOAT),
                    ((foodValue, value) -> foodValue.maxHungerSaturation = value),
                    (foodValue) -> foodValue.maxHungerSaturation,
                    (foodValue, parent) -> foodValue.maxHungerSaturation = parent.maxHungerSaturation)
            .add()
            .build();

    private Float hungerRestoration;
    private Float maxHungerSaturation;

    private static final Set<String> RAW_MEATS = Set.of(
            "Food_Beef_Raw",
            "Food_Pork_Raw",
            "Food_Chicken_Raw",
            "Food_Wildmeat_Raw");

    public static float getHungerRestoration(Item item, Float interactionValue) {
        // 0. External Config Override (Highest Priority)
        mx.jume.aquahunger.config.HHMExternalFoodsConfig externalConfig = AquaThirstHunger.get()
                .getExternalFoodsConfig();
        if (externalConfig != null) {
            var entry = externalConfig.resolve(item.getId());
            if (entry != null) {
                return entry.hungerRestoration;
            }
        }

        HHMFoodValuesConfig config = AquaThirstHunger.get().getFoodValuesConfig();
        // first prefer user config
        Float configValue = config.getItemHungerRestoration(item.getId());
        if (configValue != null)
            return configValue;

        // then asset (mod author) values
        if (!config.isIgnoreCustomAssetValues()) {
            Float assetValue = FoodValue.getHungerRestoration(item.getId());
            if (assetValue != null)
                return assetValue;
        }

        // then interaction (mod author) values
        if (!config.isIgnoreInteractionValues()) {
            if (interactionValue != null)
                return interactionValue;
        }

        // finally the tier config value
        return config.getTierHungerRestoration(HHMUtils.getItemTier(item));
    }

    public static float getMaxHungerSaturation(Item item, Float interactionValue) {
        // 0. External Config Override (Highest Priority)
        mx.jume.aquahunger.config.HHMExternalFoodsConfig externalConfig = AquaThirstHunger.get()
                .getExternalFoodsConfig();
        if (externalConfig != null) {
            var entry = externalConfig.resolve(item.getId());
            if (entry != null) {
                return entry.maxHungerSaturation;
            }
        }

        HHMFoodValuesConfig config = AquaThirstHunger.get().getFoodValuesConfig();
        // first prefer user config
        Float configValue = config.getItemMaxHungerSaturation(item.getId());
        if (configValue != null)
            return configValue;

        // then asset (mod author) values
        if (!config.isIgnoreCustomAssetValues()) {
            Float assetValue = FoodValue.getMaxHungerSaturation(item.getId());
            if (assetValue != null)
                return assetValue;
        }

        // then interaction (mod author) values
        if (!config.isIgnoreInteractionValues()) {
            if (interactionValue != null)
                return interactionValue;
        }

        // finally the tier config value
        return config.getTierMaxHungerSaturation(HHMUtils.getItemTier(item));
    }

    public static float getExpectedHungerLevel(float currentHungerLevel, float hungerRestoration,
            float maxHungerSaturation) {
        return Math.min(
                currentHungerLevel + hungerRestoration,
                100.0f + maxHungerSaturation);
    }

    public float getHungerRestoration(Item item) {
        return getHungerRestoration(item, this.hungerRestoration);
    }

    public float getMaxHungerSaturation(Item item) {
        return getMaxHungerSaturation(item, this.maxHungerSaturation);
    }

    @Override
    protected void firstRun(
            @NonNullDecl InteractionType interactionType,
            @NonNullDecl InteractionContext context,
            @NonNullDecl CooldownHandler cooldownHandler) {
        final Ref<EntityStore> ref = context.getEntity();
        final Store<EntityStore> store = ref.getStore();

        final HungerComponent hungerComponent = store.getComponent(ref, HungerComponent.getComponentType());
        final Item item = context.getOriginalItemType();
        if (hungerComponent == null || item == null)
            return;

        float currentHungerLevel = hungerComponent.getHungerLevel();
        float hungerRestoration = getHungerRestoration(item);
        float maxHungerSaturation = getMaxHungerSaturation(item);

        float targetHungerLevel = getExpectedHungerLevel(currentHungerLevel, hungerRestoration, maxHungerSaturation);

        // Avoid unnecessary updates
        if (currentHungerLevel >= targetHungerLevel)
            return;
        hungerComponent.setHungerLevel(targetHungerLevel);

        final CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
        final PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (commandBuffer == null || playerRef == null)
            return;

        HHMHud.updatePlayerHungerRestorationPreview(playerRef, 0.0f, 0.0f);
        HHMHud.updatePlayerHungerLevel(playerRef, targetHungerLevel);
        HHMUtils.removeActiveEffects(ref, commandBuffer, HHMUtils::activeEntityEffectIsHungerRelated);

        // Apply poison if raw meat
        if (RAW_MEATS.contains(item.getId())) {
            final EffectControllerComponent effectController = commandBuffer.getComponent(ref,
                    EffectControllerComponent.getComponentType());
            if (effectController != null) {
                EntityEffect poisonEffect = HHMUtils.getPoisonT1EntityEffect();
                if (poisonEffect != null) {
                    effectController.addEffect(ref, poisonEffect, commandBuffer);
                }
            }
        }
    }
}
