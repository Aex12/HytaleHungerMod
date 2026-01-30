package mx.jume.aquahunger.systems;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.HHMUtils;
import mx.jume.aquahunger.components.HungerComponent;
import mx.jume.aquahunger.config.HHMHungerConfig;
import mx.jume.aquahunger.AquaThirstHunger;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class OnDeathSystem extends DeathSystems.OnDeathSystem {
    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(
                HungerComponent.getComponentType(),
                PlayerRef.getComponentType());
    }

    @Override
    public void onComponentAdded(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl DeathComponent deathComponent,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        // No action needed on death, however method must be overridden
    }

    @Override
    public void onComponentRemoved(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl DeathComponent component,
            @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        HHMHungerConfig config = AquaThirstHunger.get().getHungerConfig();

        if (config.isResetHungerOnDeath()) {
            HHMUtils.setPlayerHungerLevel(ref, store, config.getRespawnHungerLevel());
        } else {
            HungerComponent hunger = store.getComponent(ref, HungerComponent.getComponentType());
            if (hunger != null && hunger.getHungerLevel() < config.getRespawnHungerLevel()) {
                HHMUtils.setPlayerHungerLevel(ref, store, config.getRespawnHungerLevel());
            }
        }
    }
}

