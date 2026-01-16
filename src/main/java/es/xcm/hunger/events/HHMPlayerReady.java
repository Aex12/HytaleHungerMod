package es.xcm.hunger.events;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.ui.HHMHud;
import es.xcm.hunger.components.HungerComponent;

public class HHMPlayerReady {
    private final ComponentType<EntityStore, HungerComponent> hungerComponentType;

    public HHMPlayerReady(ComponentType<EntityStore, HungerComponent> hungerComponentType) {
        this.hungerComponentType = hungerComponentType;
    }

    public void handle(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        Ref<EntityStore> ref = event.getPlayerRef();
        Store<EntityStore> store = ref.getStore();
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());

        if (playerRef == null) return;

        HungerComponent hungerComponent = new HungerComponent();
        HHMHud hud = new HHMHud(playerRef);

        player.getHudManager().setCustomHud(playerRef, hud);
        store.addComponent(ref, this.hungerComponentType, hungerComponent);
    }
}