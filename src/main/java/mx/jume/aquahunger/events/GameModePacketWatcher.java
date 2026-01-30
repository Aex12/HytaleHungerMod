package mx.jume.aquahunger.events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.protocol.packets.player.SetGameMode;
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketWatcher;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.HHMUtils;
import mx.jume.aquahunger.components.HungerComponent;
import mx.jume.aquahunger.AquaThirstHunger;
import mx.jume.aquahunger.ui.HHMHud;

public class GameModePacketWatcher implements PlayerPacketWatcher {
    @Override
    public void accept(PlayerRef playerRef, Packet packet) {
        if (!(packet instanceof SetGameMode setGameMode)) return;

        Ref<EntityStore> ref = playerRef.getReference();
        if (ref == null) return;
        Store<EntityStore> store = ref.getStore();
        World world = store.getExternalData().getWorld();

        world.execute(() -> {
            GameMode gameMode = setGameMode.gameMode;
            HHMHud.updatePlayerGameMode(playerRef, gameMode);
            HungerComponent hunger = store.getComponent(ref, HungerComponent.getComponentType());
            if (hunger == null) return;
            float initialHunger = AquaThirstHunger.get().getHungerConfig().getInitialHungerLevel();
            if (gameMode == GameMode.Creative && hunger.getHungerLevel() < initialHunger) {
                HHMUtils.setPlayerHungerLevel(ref, store, initialHunger);
            }
        });
    }
}

