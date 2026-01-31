package mx.jume.aquahunger;

import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemQuality;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.components.HungerComponent;
import mx.jume.aquahunger.config.HHMHungerConfig;
import mx.jume.aquahunger.config.ItemTier;
import mx.jume.aquahunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Predicate;

public class HHMUtils {
    private static EntityEffect starvingEntityEffect;
    private static EntityEffect hungryEntityEffect;
    private static DamageCause starvationDamageCause;

    public static final String starvingEntityEffectId = "Starving";
    public static final String hungryEntityEffectId = "Hungry";
    public static final String poisonEntityEffectId = "Poison_T1";

    @NonNullDecl
    public static EntityEffect getStarvingEntityEffect() {
        if (starvingEntityEffect == null) {
            starvingEntityEffect = EntityEffect.getAssetMap().getAsset(starvingEntityEffectId);
            assert starvingEntityEffect != null;
            HHMHungerConfig conf = AquaThirstHunger.get().getHungerConfig();
            try {
                // patch damageCalculator cooldown so that audio syncs properly with starvation
                // tick rate (user configured)
                Field f = EntityEffect.class.getDeclaredField("damageCalculatorCooldown");
                f.setAccessible(true);
                // this only affect sfx. Users with low hunger tick rates may notice bugged sfx
                // timings otherwise.
                float cooldown = Math.max(conf.getStarvationTickRate(), 1.0f);
                f.setFloat(starvingEntityEffect, cooldown);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return starvingEntityEffect;
    }

    @NonNullDecl
    public static EntityEffect getHungryEntityEffect() {
        if (hungryEntityEffect == null) {
            hungryEntityEffect = EntityEffect.getAssetMap().getAsset(hungryEntityEffectId);
            assert hungryEntityEffect != null;
        }
        return hungryEntityEffect;
    }

    private static EntityEffect poisonEntityEffect;
    private static boolean poisonEffectChecked = false;

    public static EntityEffect getPoisonT1EntityEffect() {
        if (!poisonEffectChecked) {
            poisonEntityEffect = EntityEffect.getAssetMap().getAsset(poisonEntityEffectId);
            if (poisonEntityEffect == null) {
                AquaThirstHunger.logInfo("Poison_T1 effect asset not found.");
            }
            poisonEffectChecked = true;
        }
        return poisonEntityEffect;
    }

    private static EntityEffect dehydrationEntityEffect;
    public static final String dehydrationEntityEffectId = "Dehydration";

    public static EntityEffect getDehydrationEntityEffect() {
        if (dehydrationEntityEffect == null) {
            dehydrationEntityEffect = EntityEffect.getAssetMap().getAsset(dehydrationEntityEffectId);
            if (dehydrationEntityEffect == null) {
                // Warning logged only once if needed, or rely on null check in caller
                AquaThirstHunger.logInfo("Dehydration effect asset not found.");
            }
        }
        return dehydrationEntityEffect;
    }

    public static boolean activeEntityEffectIsDehydration(ActiveEntityEffect effect) {
        return activeEntityEffectIs(effect, dehydrationEntityEffectId);
    }

    public static boolean activeEntityEffectIs(ActiveEntityEffect effect, String entityEffectId) {
        try {
            Field f = ActiveEntityEffect.class.getDeclaredField("entityEffectId");
            f.setAccessible(true);
            String id = (String) f.get(effect);
            return id.equals(entityEffectId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean activeEntityEffectIsStarving(ActiveEntityEffect effect) {
        return activeEntityEffectIs(effect, starvingEntityEffectId);
    }

    public static boolean activeEntityEffectIsHungry(ActiveEntityEffect effect) {
        return activeEntityEffectIs(effect, hungryEntityEffectId);
    }

    public static boolean activeEntityEffectIsHungerRelated(ActiveEntityEffect effect) {
        return activeEntityEffectIsStarving(effect) || activeEntityEffectIsHungry(effect);
    }

    public static void removeActiveEffects(
            Ref<EntityStore> ref,
            ComponentAccessor<EntityStore> componentAccessor,
            @NonNullDecl EffectControllerComponent effectController,
            Predicate<ActiveEntityEffect> shouldRemoveEffect) {
        final ActiveEntityEffect[] activeEffects = effectController.getAllActiveEntityEffects();
        if (activeEffects != null && activeEffects.length > 0) {
            Arrays.stream(activeEffects).filter(shouldRemoveEffect).forEach(effect -> {
                effectController.removeEffect(ref, effect.getEntityEffectIndex(), componentAccessor);
            });
        }
    }

    public static void removeActiveEffects(
            Ref<EntityStore> ref,
            ComponentAccessor<EntityStore> componentAccessor,
            Predicate<ActiveEntityEffect> shouldRemoveEffect) {
        final EffectControllerComponent effectController = componentAccessor.getComponent(ref,
                EffectControllerComponent.getComponentType());
        if (effectController == null)
            return;
        removeActiveEffects(ref, componentAccessor, effectController, shouldRemoveEffect);
    }

    public static void setPlayerHungerLevel(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl ComponentAccessor<EntityStore> componentAccessor,
            float hungerLevel) {
        HungerComponent hungerComponent = componentAccessor.getComponent(ref, HungerComponent.getComponentType());
        PlayerRef playerRef = componentAccessor.getComponent(ref, PlayerRef.getComponentType());
        if (hungerComponent == null || playerRef == null)
            return;

        hungerComponent.setHungerLevel(hungerLevel);
        HHMHud.updatePlayerHungerLevel(playerRef, hungerLevel);

        removeActiveEffects(ref, componentAccessor, HHMUtils::activeEntityEffectIsHungerRelated);
    }

    public static void setPlayerThirstLevel(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl ComponentAccessor<EntityStore> componentAccessor,
            float thirstLevel) {
        mx.jume.aquahunger.components.ThirstComponent thirstComponent = componentAccessor.getComponent(ref,
                mx.jume.aquahunger.components.ThirstComponent.getComponentType());
        PlayerRef playerRef = componentAccessor.getComponent(ref, PlayerRef.getComponentType());
        if (thirstComponent == null || playerRef == null)
            return;

        thirstComponent.setThirstLevel(thirstLevel);
        mx.jume.aquahunger.ui.HHMThirstHud.updatePlayerThirstLevel(playerRef, thirstLevel);
    }

    @NonNullDecl
    public static DamageCause getStarvationDamageCause() {
        if (starvationDamageCause != null)
            return starvationDamageCause;
        starvationDamageCause = new DamageCause("Starvation", "Starvation", false, true, true);
        return starvationDamageCause;
    }

    @NonNullDecl
    private static final java.util.Set<String> LOGGED_UNKNOWN_TIERS = new java.util.HashSet<>();

    @NonNullDecl
    public static ItemTier getItemTier(Item item) {
        ItemQuality itemQuality = ItemQuality.getAssetMap().getAsset(item.getQualityIndex());
        if (itemQuality == null) {
            return ItemTier.Common;
        }
        String id = itemQuality.getId();
        if ("Default".equals(id) || id == null) {
            return ItemTier.Common;
        }
        try {
            return ItemTier.valueOf(id);
        } catch (IllegalArgumentException e) {
            if (LOGGED_UNKNOWN_TIERS.add(id)) {
                AquaThirstHunger.logInfo("Unknown ItemQuality/Tier: '" + id + "'. Defaulting to Common.");
            }
            return ItemTier.Common;
        }
    }
}
