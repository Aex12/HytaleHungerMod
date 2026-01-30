package mx.jume.aquahunger;

import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.assets.FoodValue;
import mx.jume.aquahunger.assets.HungryAssetRegistryLoader;
import mx.jume.aquahunger.commands.*;
import mx.jume.aquahunger.components.HungerComponent;
import mx.jume.aquahunger.config.HHMHungerConfig;
import mx.jume.aquahunger.config.HHMFoodValuesConfig;
import mx.jume.aquahunger.events.GameModePacketWatcher;
import mx.jume.aquahunger.events.HHMPlayerReady;
import mx.jume.aquahunger.interactions.FailedFeedingInteraction;
import mx.jume.aquahunger.interactions.FeedInteraction;
import mx.jume.aquahunger.interactions.StartFeedingInteraction;
import mx.jume.aquahunger.systems.OnBlockHitSystem;
import mx.jume.aquahunger.systems.OnDeathSystem;
import mx.jume.aquahunger.systems.StarveSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Set;
import java.util.logging.Level;

public class AquaThirstHunger extends JavaPlugin {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static AquaThirstHunger instance;

    private mx.jume.aquahunger.config.ConfigManager configManager;
    private ComponentType<EntityStore, HungerComponent> hungerComponentType;

    public AquaThirstHunger(@NonNullDecl JavaPluginInit init) {
        super(init);
        instance = this;
        // Config Loading moved to setup() to ensure proper folder structure
    }

    @Override
    protected void setup() {
        super.setup();

        // Load configurations manually
        this.configManager = new mx.jume.aquahunger.config.ConfigManager();
        this.configManager.load();
        logInfo("Hunger and FoodValues configurations loaded successfully from manual path.");

        // register hunger component
        this.hungerComponentType = this.getEntityStoreRegistry()
                .registerComponent(HungerComponent.class, "HungerComponent", HungerComponent.CODEC);

        // register starve system
        final var entityStoreRegistry = this.getEntityStoreRegistry();
        entityStoreRegistry.registerSystem(StarveSystem.create());
        entityStoreRegistry.registerSystem(new OnBlockHitSystem());
        entityStoreRegistry.registerSystem(new OnDeathSystem());

        // register feed interaction
        final var interactionRegistry = this.getCodecRegistry(Interaction.CODEC);
        interactionRegistry.register("Hungry_Feed", FeedInteraction.class, FeedInteraction.CODEC);
        interactionRegistry.register("Hungry_Start_Feeding", StartFeedingInteraction.class,
                StartFeedingInteraction.CODEC);
        interactionRegistry.register("Hungry_Failed_Feeding", FailedFeedingInteraction.class,
                FailedFeedingInteraction.CODEC);

        // setup hunger component and hud on player join
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, HHMPlayerReady::handle);
        this.getEventRegistry().register(LoadedAssetsEvent.class, FoodValue.class, FoodValue::onItemAssetLoad);

        // listen to gamemode changes
        PacketAdapters.registerOutbound(new GameModePacketWatcher());

        // register admin commands
        this.getCommandRegistry().registerCommand(new HungryCommand());
    }

    @Override
    protected void start() {
        super.start();

        // single player worlds get extra permissions to manage config
        if (this.getHungerConfig().isSinglePlayer()) {
            final Set<String> singleplayerPermissions = Set.of(
                    HungryCommand.requiredPermission,
                    HungryHideCommand.requiredPermission,
                    HungryShowCommand.requiredPermission,
                    HungryPositionCommand.requiredPermission);
            PermissionsModule.get().addGroupPermission("Adventure", singleplayerPermissions);
            PermissionsModule.get().addGroupPermission("Creative", singleplayerPermissions);
            logInfo("Singleplayer module detected, added permissions to Adventure and Creative groups.");
        }
    }

    public ComponentType<EntityStore, HungerComponent> getHungerComponentType() {
        return this.hungerComponentType;
    }

    public HHMHungerConfig getHungerConfig() {
        return this.configManager.getHungerConfig();
    }

    public void saveHungerConfig() {
        this.configManager.save();
    }

    public HHMFoodValuesConfig getFoodValuesConfig() {
        return this.configManager.getFoodValuesConfig();
    }

    public mx.jume.aquahunger.config.HHMExternalFoodsConfig getExternalFoodsConfig() {
        return this.configManager.getExternalFoodsConfig();
    }

    public static AquaThirstHunger get() {
        return instance;
    }

    public static void logInfo(String message) {
        LOGGER.at(Level.INFO).log(message);
    }

    static {
        HungryAssetRegistryLoader.registerAssets();
    }
}
