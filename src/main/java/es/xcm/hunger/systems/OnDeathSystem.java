package es.xcm.hunger.systems;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.HHMUtils;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.config.HHMHungerConfig;
import es.xcm.hunger.HytaleHungerMod;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class OnDeathSystem extends DeathSystems.OnDeathSystem {
    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(
            HungerComponent.getComponentType(),
            PlayerRef.getComponentType()
        );
    }

    @Override
    public void onComponentAdded(
        @NonNullDecl Ref<EntityStore> ref,
        @NonNullDecl DeathComponent deathComponent,
        @NonNullDecl Store<EntityStore> store,
        @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {
        // No action needed on death, however method must be overridden
    }

    @Override
    public void onComponentRemoved(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl DeathComponent component, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        HHMHungerConfig config = HytaleHungerMod.get().getHungerConfig();

        if (config.isResetHungerOnDeath()) {
            HHMUtils.setPlayerHungerLevel(ref, store, config.getInitialHungerLevel());
        } else {
            HungerComponent hunger = store.getComponent(ref, HungerComponent.getComponentType());
            if (hunger != null && hunger.getHungerLevel() < 20.0f) {
                HHMUtils.setPlayerHungerLevel(ref, store, 20.0f);
            }
        }
    }
}
