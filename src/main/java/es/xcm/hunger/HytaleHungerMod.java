package es.xcm.hunger;

import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.plugin.PluginBase;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import es.xcm.hunger.assets.FoodValue;
import es.xcm.hunger.assets.HungryAssetRegistryLoader;
import es.xcm.hunger.commands.*;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.config.HHMHungerConfig;
import es.xcm.hunger.config.HHMFoodValuesConfig;
import es.xcm.hunger.events.GameModePacketWatcher;
import es.xcm.hunger.events.HHMPlayerReady;
import es.xcm.hunger.interactions.FeedInteractionT1;
import es.xcm.hunger.interactions.FeedInteractionT2;
import es.xcm.hunger.interactions.FeedInteractionT3;
import es.xcm.hunger.systems.OnDeathSystem;
import es.xcm.hunger.systems.StarveSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Set;
import java.util.logging.Level;

public class HytaleHungerMod extends JavaPlugin {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static HytaleHungerMod instance;

    private final Config<HHMHungerConfig> hungerConfig;
    private final Config<HHMFoodValuesConfig> foodValuesConfig;
    private ComponentType<EntityStore, HungerComponent> hungerComponentType;

    public HytaleHungerMod(@NonNullDecl JavaPluginInit init) {
        super(init);
        instance = this;
        this.hungerConfig = this.withConfig("HungerConfig", HHMHungerConfig.CODEC);
        this.foodValuesConfig = this.withConfig("FoodValuesConfig", HHMFoodValuesConfig.CODEC);
    }

    @Override
    protected void setup () {
        super.setup();

        this.hungerConfig.save();
        this.foodValuesConfig.save();

        // register hunger component
        this.hungerComponentType = this.getEntityStoreRegistry()
                .registerComponent(HungerComponent.class, "HungerComponent", HungerComponent.CODEC);

        // register starve system
        final var entityStoreRegistry = this.getEntityStoreRegistry();
        entityStoreRegistry.registerSystem(StarveSystem.create());
        entityStoreRegistry.registerSystem(new OnDeathSystem());

        // register feed interaction
        final var interactionRegistry = this.getCodecRegistry(Interaction.CODEC);
        interactionRegistry.register("Hunger_Feed_T1", FeedInteractionT1.class, FeedInteractionT1.CODEC);
        interactionRegistry.register("Hunger_Feed_T2", FeedInteractionT2.class, FeedInteractionT2.CODEC);
        interactionRegistry.register("Hunger_Feed_T3", FeedInteractionT3.class, FeedInteractionT3.CODEC);

        // setup hunger component and hud on player join
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, HHMPlayerReady::handle);
        this.getEventRegistry().register(LoadedAssetsEvent.class, FoodValue.class, FoodValue::onItemAssetLoad);

        // listen to gamemode changes
        PacketAdapters.registerOutbound(new GameModePacketWatcher());

        // register admin commands
        this.getCommandRegistry().registerCommand(new HungryCommand());
    }

    @Override
    protected void start () {
        super.start();

        // single player worlds get extra permissions to manage config
        PluginBase singleplayerModule = PluginManager.get().getPlugin(PluginIdentifier.fromString("Hytale:SingleplayerModule"));
        if (singleplayerModule != null && singleplayerModule.isEnabled()) {
            final Set<String> singleplayerPermissions = Set.of(
                    HungryCommand.requiredPermission,
                    HungryHideCommand.requiredPermission,
                    HungryShowCommand.requiredPermission,
                    HungryPositionCommand.requiredPermission
            );
            PermissionsModule.get().addGroupPermission("Adventure", singleplayerPermissions);
            PermissionsModule.get().addGroupPermission("Creative", singleplayerPermissions);
            logInfo("Singleplayer module detected, added permissions to Adventure and Creative groups.");
        }
    }

    public ComponentType<EntityStore, HungerComponent> getHungerComponentType() {
        return this.hungerComponentType;
    }

    public HHMHungerConfig getHungerConfig() {
        return this.hungerConfig.get();
    }
    public void saveHungerConfig() {
        this.hungerConfig.save();
    }
    public HHMFoodValuesConfig getFoodValuesConfig() {
        return this.foodValuesConfig.get();
    }

    public static HytaleHungerMod get() {
        return instance;
    }

    public static void logInfo(String message) {
        LOGGER.at(Level.INFO).log(message);
    }

    static {
        HungryAssetRegistryLoader.registerAssets();
    }
}
