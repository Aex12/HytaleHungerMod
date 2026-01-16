package es.xcm.hunger;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.events.HHMPlayerReadyEvent;
import es.xcm.hunger.systems.StarveSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HytaleHungerMod extends JavaPlugin {
    public HytaleHungerMod(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup () {
        super.setup();

        ComponentType<EntityStore, HungerComponent> hungerComponentType = this.getEntityStoreRegistry()
                .registerComponent(HungerComponent.class, HungerComponent::new);

        this.getEntityStoreRegistry().registerSystem(new StarveSystem(hungerComponentType));

        HHMPlayerReadyEvent playerReadyEvent = new HHMPlayerReadyEvent(hungerComponentType);

        EventRegistry eventRegistry = this.getEventRegistry();
        eventRegistry.registerGlobal(PlayerReadyEvent.class, playerReadyEvent::handle);
    }
}
