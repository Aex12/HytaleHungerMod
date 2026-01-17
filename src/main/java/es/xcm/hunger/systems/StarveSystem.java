package es.xcm.hunger.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.*;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import es.xcm.hunger.HHMConfig;
import es.xcm.hunger.HytaleHungerMod;
import es.xcm.hunger.ui.HHMHud;
import es.xcm.hunger.components.HungerComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StarveSystem extends DelayedEntitySystem<EntityStore> {
    private final float starvationPerTick;
    private final float starvationDamage;
    private final float starvationStaminaModifier;

    private StarveSystem(
        float starvationTickRate,
        float starvationPerTick,
        float starvationDamage,
        float starvationStaminaModifier
    ) {
        super(starvationTickRate);
        this.starvationPerTick = starvationPerTick;
        this.starvationDamage = starvationDamage;
        this.starvationStaminaModifier = starvationStaminaModifier;
    }

    public static StarveSystem create () {
        HHMConfig conf = HytaleHungerMod.get().getConfig();
        return new StarveSystem(
            conf.getStarvationTickRate(),
            conf.getStarvationPerTick(),
            conf.getStarvationDamage(),
            conf.getStarvationStaminaModifier()
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
            Player.getComponentType(),
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
        // interpolate staminaModifier based on current stamina (from 0.0 to 10.0)
        // the lower the stamina, the higher the modifier
        // when stamina is at max, the modifier is 1.0f (no change)
        float staminaModifier = Math.max(1.0f, 1.0f + ((10.0f - stamina) / 10.0f) * this.starvationStaminaModifier - 1.0f);
        hunger.starve(this.starvationPerTick * staminaModifier);

        // Apply damage to the player due to starvation
        if (hunger.getHungerLevel() == 0) {
            Damage damage = new Damage(Damage.NULL_SOURCE, DamageCause.OUT_OF_WORLD, this.starvationDamage);
            DamageSystems.executeDamage(ref, commandBuffer, damage);
        }

        HHMHud.updatePlayerHud(playerRef, hunger.getHungerLevel());
    }

    public static float getStaminaValue(@NonNullDecl EntityStatMap entityStatMap) {
        final int staminaRef = DefaultEntityStatTypes.getStamina();
        final EntityStatValue statValue = entityStatMap.get(staminaRef);
        if (statValue == null) return 10.0f; // Default stamina (max) value if not found
        return Math.max(statValue.get(), 0.0f); // for the purpose of the mod, we don't want negative stamina.
    }
}
