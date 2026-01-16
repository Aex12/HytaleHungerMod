package es.xcm.hunger.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.*;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import es.xcm.hunger.HHMConfig;
import es.xcm.hunger.ui.HHMHud;
import es.xcm.hunger.components.HungerComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// https://hytalemodding.dev/en/docs/guides/ecs/hytale-ecs
public class StarveSystem extends DelayedEntitySystem<EntityStore> {
    private final ComponentType<EntityStore, HungerComponent> hungerComponentType;
    private final float starvationRate;
    private final float starvationDamage;

    private StarveSystem(
            ComponentType<EntityStore, HungerComponent> hungerComponentType,
            float starvationInterval,
            float starvationRate,
            float starvationDamage
    ) {
        super(starvationInterval); // Tick every 5 seconds
        this.hungerComponentType = hungerComponentType;
        this.starvationRate = starvationRate;
        this.starvationDamage = starvationDamage;
    }

    public static StarveSystem create (
            ComponentType<EntityStore, HungerComponent> hungerComponentType,
            Config<HHMConfig> config
    ) {
        HHMConfig hhmConfig = config.get();
        float starvationInterval = hhmConfig.getStarvationInterval();
        float starvationRate = hhmConfig.getStarvationRate();
        float starvationDamage = hhmConfig.getStarvationDamage();
        return new StarveSystem(hungerComponentType, starvationInterval, starvationRate, starvationDamage);
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
                this.hungerComponentType,
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
        HungerComponent hunger = archetypeChunk.getComponent(index, hungerComponentType);
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

        if (hunger == null) {
            return;
        }

        hunger.starve(this.starvationRate);

        // Apply damage to the player due to starvation
        if (hunger.getHungerLevel() == 0) {
            Damage damage = new Damage(Damage.NULL_SOURCE, DamageCause.OUT_OF_WORLD, this.starvationDamage);
            DamageSystems.executeDamage(ref, commandBuffer, damage);
        }

        PlayerRef playerRef = archetypeChunk.getComponent(index, PlayerRef.getComponentType());
        if (playerRef != null) {
            HHMHud.updatePlayerHud(playerRef, hunger.getHungerLevel());
        }
    }
}
