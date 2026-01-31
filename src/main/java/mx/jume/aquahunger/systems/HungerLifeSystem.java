package mx.jume.aquahunger.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.AquaThirstHunger;
import mx.jume.aquahunger.components.HungerComponent;
import mx.jume.aquahunger.config.HHMHungerConfig;
import mx.jume.aquahunger.ui.HHMHud;

import javax.annotation.Nonnull;

public class HungerLifeSystem extends EntityTickingSystem<EntityStore> {
    private static final float PULSE_INTERVAL = 2.0f;
    private static final float HEALTH_PER_PULSE = 1.0f;
    private static final float HUNGER_COST = 2.0f;
    private static final float THIRST_COST = 0.5f;

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(
                HungerComponent.getComponentType(),
                mx.jume.aquahunger.components.ThirstComponent.getComponentType(),
                EntityStatMap.getComponentType(),
                PlayerRef.getComponentType(),
                Query.not(DeathComponent.getComponentType()),
                Query.not(Invulnerable.getComponentType()));
    }

    @Override
    public void tick(
            float dt,
            int index,
            @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer) {

        HHMHungerConfig config = AquaThirstHunger.get().getHungerConfig();
        if (!config.isLifePerHunger() || !config.isEnableHunger()) {
            return;
        }

        HungerComponent hunger = archetypeChunk.getComponent(index, HungerComponent.getComponentType());
        mx.jume.aquahunger.components.ThirstComponent thirst = archetypeChunk.getComponent(index,
                mx.jume.aquahunger.components.ThirstComponent.getComponentType());
        EntityStatMap entityStatMap = archetypeChunk.getComponent(index, EntityStatMap.getComponentType());

        if (hunger == null || thirst == null || entityStatMap == null)
            return;

        hunger.setHealTimer(hunger.getHealTimer() + dt);

        if (hunger.getHealTimer() >= PULSE_INTERVAL) {
            hunger.setHealTimer(0.0f);

            // Check resources (Hunger + Saturation must be >= 4.0, Thirst >= 1.0)
            if (hunger.getHungerLevel() < HUNGER_COST || thirst.getThirstLevel() < THIRST_COST) {
                return;
            }

            // Check health
            int healthRef = DefaultEntityStatTypes.getHealth();
            EntityStatValue healthVal = entityStatMap.get(healthRef);

            if (healthVal != null) {
                float currentHealth = healthVal.get();
                float maxHealth = healthVal.getMax();

                if (currentHealth < maxHealth) {
                    // Logic: Increase health utilizing EntityStatMap public API
                    // Using setStatValue as requested option (b)
                    float newHealth = Math.min(currentHealth + HEALTH_PER_PULSE, maxHealth);
                    entityStatMap.setStatValue(healthRef, newHealth);

                    // Drain hunger (effectively drains saturation first as it's the top of the bar)
                    float newHunger = hunger.getHungerLevel() - HUNGER_COST;
                    hunger.setHungerLevel(newHunger);

                    // Drain thirst
                    thirst.dehydrate(THIRST_COST);

                    // Update HUD immediately
                    PlayerRef playerRef = archetypeChunk.getComponent(index, PlayerRef.getComponentType());
                    if (playerRef != null) {
                        HHMHud.updatePlayerHungerLevel(playerRef, newHunger);
                    }
                }
            }
        }
    }
}
