package es.xcm.hunger.events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.compat.hud.CompatHUD;
import es.xcm.hunger.ui.HHMHud;
import es.xcm.hunger.components.HungerComponent;

public class HHMPlayerReady {
    public static void handle(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        Ref<EntityStore> ref = event.getPlayerRef();
        Store<EntityStore> store = ref.getStore();
        World world = store.getExternalData().getWorld();

        world.execute(() -> {
            PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
            if (playerRef == null) return;

            HungerComponent hungerComponent = store.ensureAndGetComponent(ref, HungerComponent.getComponentType());
            float hungerLevel = hungerComponent.getHungerLevel();

            HHMHud hud = new HHMHud(playerRef);
            CompatHUD.get().setCustomHud(player, playerRef, HHMHud.hudIdentifier, hud);
            hud.updateHungerLevel(hungerLevel);
        });
    }
}