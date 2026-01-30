package mx.jume.aquahunger.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
import com.hypixel.hytale.server.core.modules.entity.damage.*;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.config.HHMHungerConfig;
import mx.jume.aquahunger.HHMUtils;
import mx.jume.aquahunger.AquaThirstHunger;
import mx.jume.aquahunger.ui.HHMHud;
import mx.jume.aquahunger.components.HungerComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StarveSystem extends EntityTickingSystem<EntityStore> {
    @Nonnull
    private final HHMHungerConfig config;

    private StarveSystem(@NonNullDecl HHMHungerConfig config) {
        this.config = config;
    }

    public static StarveSystem create () {
        HHMHungerConfig config = AquaThirstHunger.get().getHungerConfig();
        return new StarveSystem(config);
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
            Query.not(DeathComponent.getComponentType()),
            Query.not(Invulnerable.getComponentType())
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
        if (hunger == null || entityStatMap == null) return;

        hunger.setStaminaSeen(getStaminaValue(entityStatMap));
        hunger.addElapsedTime(dt);
        if (hunger.getElapsedTime() < this.config.getStarvationTickRate()) return;
        hunger.resetElapsedTime();

        float lowestStaminaSeen = hunger.getAndResetLowestStaminaSeen();
        float staminaModifier = ((10.0f - lowestStaminaSeen) / 10.0f) * this.config.getStarvationStaminaModifier();

        float damagedBlocksModifier = hunger.getAndResetBlockHits() * this.config.getStarvationPerBlockHit();

        hunger.starve(this.config.getStarvationPerTick() + staminaModifier + damagedBlocksModifier);

        float hungerLevel = hunger.getHungerLevel();
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

        // Apply hungry effect when hunger level is below 20
        if (hungerLevel != 0 && hungerLevel < this.config.getHungryThreshold()) {
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
            HHMUtils.removeActiveEffects(ref, commandBuffer, effectController, StarveSystem::shouldRemoveEffectOnStarvation);
            // apply starving effect
            EntityEffect starvingEffect = HHMUtils.getStarvingEntityEffect();
            effectController.addEffect(ref, starvingEffect, commandBuffer);
            // apply starvation damage
            Damage damage = new Damage(Damage.NULL_SOURCE, HHMUtils.getStarvationDamageCause(), this.config.getStarvationDamage());
            DamageSystems.executeDamage(ref, commandBuffer, damage);
        }

        PlayerRef playerRef = archetypeChunk.getComponent(index, PlayerRef.getComponentType());
        if (playerRef == null) return;
        HHMHud.updatePlayerHungerLevel(playerRef, hungerLevel);
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
        return statValue.get();
    }
}

