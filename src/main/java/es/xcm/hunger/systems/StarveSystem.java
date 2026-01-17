package es.xcm.hunger.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.*;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.config.HHMConfig;
import es.xcm.hunger.HHMUtils;
import es.xcm.hunger.HytaleHungerMod;
import es.xcm.hunger.ui.HHMHud;
import es.xcm.hunger.components.HungerComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public class StarveSystem extends DelayedEntitySystem<EntityStore> {
    private final float starvationPerTick;
    private final float starvationDamage;
    private final float starvationStaminaModifier;
    private final float hungryThreshold;

    private StarveSystem(
        float starvationTickRate,
        float starvationPerTick,
        float starvationDamage,
        float starvationStaminaModifier,
        float hungryThreshold
    ) {
        super(starvationTickRate);
        this.starvationPerTick = starvationPerTick;
        this.starvationDamage = starvationDamage;
        this.starvationStaminaModifier = starvationStaminaModifier;
        this.hungryThreshold = hungryThreshold;
    }

    public static StarveSystem create () {
        HHMConfig conf = HytaleHungerMod.get().getConfig();
        return new StarveSystem(
            conf.getStarvationTickRate(),
            conf.getStarvationPerTick(),
            conf.getStarvationDamage(),
            conf.getStarvationStaminaModifier(),
            conf.getHungryThreshold()
        );
    }

    @Nullable
    @Override
    public SystemGroup<EntityStore> getGroup() {
        return DamageModule.get().getGatherDamageGroup();
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(
            HungerComponent.getComponentType(),
            EntityStatMap.getComponentType(),
            PlayerRef.getComponentType(),
            Query.not(DeathComponent.getComponentType())
        );
    }

    @Override
    public void tick(
        float dt,
        int index,
        @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
        @NonNullDecl Store<EntityStore> store,
        @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {
        HungerComponent hunger = archetypeChunk.getComponent(index, HungerComponent.getComponentType());
        EntityStatMap entityStatMap = archetypeChunk.getComponent(index, EntityStatMap.getComponentType());
        PlayerRef playerRef = archetypeChunk.getComponent(index, PlayerRef.getComponentType());
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

        if (hunger == null || entityStatMap == null || playerRef == null) {
            return;
        }

        float stamina = getStaminaValue(entityStatMap);
        float staminaModifier = ((10.0f - stamina) / 10.0f) * this.starvationStaminaModifier;
        hunger.starve(this.starvationPerTick + staminaModifier);

        float hungerLevel = hunger.getHungerLevel();

        // Apply hungry effect when hunger level is below 20
        if (hungerLevel != 0 && hungerLevel < this.hungryThreshold) {
            EffectControllerComponent effectController = commandBuffer.getComponent(ref, EffectControllerComponent.getComponentType());
            if (effectController == null) return;
            // apply hungry effect
            EntityEffect hungryEffect = HHMUtils.getHungryEntityEffect();
            effectController.addEffect(ref, hungryEffect, commandBuffer);
        }

        // Apply starvation effect when hunger level reaches 0
        if (hungerLevel == 0) {
            EffectControllerComponent effectController = commandBuffer.getComponent(ref, EffectControllerComponent.getComponentType());
            if (effectController == null) return;
            // remove all buffs when starving
            final ActiveEntityEffect[] activeEffects = effectController.getAllActiveEntityEffects();
            if (activeEffects != null && activeEffects.length > 0) {
                Arrays.stream(activeEffects).filter(StarveSystem::shouldRemoveEffectOnStarvation).forEach(effect -> {
                    effectController.removeEffect(ref, effect.getEntityEffectIndex(), commandBuffer);
                });
            }
            // apply starving effect
            EntityEffect starvingEffect = HHMUtils.getStarvingEntityEffect();
            effectController.addEffect(ref, starvingEffect, commandBuffer);
            // apply starvation damage
             Damage damage = new Damage(Damage.NULL_SOURCE, HHMUtils.getStarvationDamageCause(), this.starvationDamage);
             DamageSystems.executeDamage(ref, commandBuffer, damage);
        }

        HHMHud.updatePlayerHungerLevel(playerRef, hunger.getHungerLevel());
    }

    public static boolean shouldRemoveEffectOnStarvation (ActiveEntityEffect effect) {
        if (HHMUtils.activeEntityEffectIsHungry(effect)) return true;
        if (effect.isInfinite()) return false;
        return !effect.isDebuff();
    }

    public static float getStaminaValue(@NonNullDecl EntityStatMap entityStatMap) {
        final int staminaRef = DefaultEntityStatTypes.getStamina();
        final EntityStatValue statValue = entityStatMap.get(staminaRef);
        if (statValue == null) return 10.0f; // Default stamina (max) value if not found
        return Math.max(statValue.get(), 0.0f); // for the purpose of the mod, we don't want negative stamina.
    }
}
