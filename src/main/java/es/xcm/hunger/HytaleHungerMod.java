package es.xcm.hunger;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import es.xcm.hunger.commands.SetHungerCommand;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.events.HHMPlayerReady;
import es.xcm.hunger.interactions.FeedInteractionT1;
import es.xcm.hunger.interactions.FeedInteractionT2;
import es.xcm.hunger.interactions.FeedInteractionT3;
import es.xcm.hunger.systems.StarveSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HytaleHungerMod extends JavaPlugin {
    private static HytaleHungerMod instance;
    private final Config<HHMConfig> config;
    private ComponentType<EntityStore, HungerComponent> hungerComponentType;

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public HytaleHungerMod(@NonNullDecl JavaPluginInit init) {
        super(init);
        instance = this;
        this.config = this.withConfig("HungerConfig", HHMConfig.CODEC);
    }

    @Override
    protected void setup () {
        super.setup();

        this.config.save();

        // register hunger component
        this.hungerComponentType = this.getEntityStoreRegistry()
                .registerComponent(HungerComponent.class, HungerComponent::new);

        // register starve system
        final var entityStoreRegistry = this.getEntityStoreRegistry();
        entityStoreRegistry.registerSystem(StarveSystem.create());

        // register feed interaction
        final var interactionRegistry = this.getCodecRegistry(Interaction.CODEC);
        interactionRegistry.register("Hunger_Feed_T1", FeedInteractionT1.class, FeedInteractionT1.CODEC);
        interactionRegistry.register("Hunger_Feed_T2", FeedInteractionT2.class, FeedInteractionT2.CODEC);
        interactionRegistry.register("Hunger_Feed_T3", FeedInteractionT3.class, FeedInteractionT3.CODEC);

        // setup hunger component and hud on player join
        HHMPlayerReady playerReadyEvent = new HHMPlayerReady(this.hungerComponentType);
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, playerReadyEvent::handle);

        // register admin commands
        this.getCommandRegistry().registerCommand(new SetHungerCommand());
    }

    public ComponentType<EntityStore, HungerComponent> getHungerComponentType() {
        return this.hungerComponentType;
    }

    public HHMConfig getConfig() {
        return this.config.get();
    }

    public static HytaleHungerMod get() {
        return instance;
    }
}
