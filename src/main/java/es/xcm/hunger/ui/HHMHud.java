package es.xcm.hunger.ui;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.Anchor;
import com.hypixel.hytale.server.core.ui.Value;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.HytaleHungerMod;
import es.xcm.hunger.compat.hud.CompatHUD;
import es.xcm.hunger.components.HungerComponent;
import es.xcm.hunger.config.HHMConfig;
import es.xcm.hunger.config.HudPosition;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.WeakHashMap;

// https://www.youtube.com/watch?v=cha7YFULwxY&t=1330s

public class HHMHud extends CustomUIHud {
    static private final WeakHashMap<PlayerRef, HHMHud> hudMap = new WeakHashMap<>();
    static public final String hudIdentifier = "es.xcm.hunger.hud.hunger";
    private GameMode gameMode;
    private float hungerLevel;
    private boolean visible = true;

    public HHMHud(@NonNullDecl PlayerRef playerRef, GameMode gameMode, float hungerLevel) {
        super(playerRef);
        this.gameMode = gameMode;
        this.hungerLevel = hungerLevel;
        hudMap.put(playerRef, this);
    }

    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        HHMConfig config = HytaleHungerMod.get().getConfig();
        HudPosition hudPosition = config.getHudPosition();
        uiCommandBuilder.append("Hungry/HUD/Hunger.ui");
        updateHudPosition(uiCommandBuilder, hudPosition);
        updateGameMode(uiCommandBuilder, this.gameMode);
        updateHungerLevel(uiCommandBuilder, this.hungerLevel);
        updateVisibility(uiCommandBuilder, this.visible);
    }

    protected void updateHudPosition(UICommandBuilder uiCommandBuilder, HudPosition hudPosition) {
        Anchor anchor = new Anchor();
        anchor.setWidth(Value.of(332));
        anchor.setHeight(Value.of(20));

        if (hudPosition.left() != 0) {
            anchor.setLeft(Value.of(hudPosition.left()));
        }
        if (hudPosition.bottom() != 0) {
            anchor.setBottom(Value.of(hudPosition.bottom()));
        }

        uiCommandBuilder.setObject("#HHMContainer.Anchor", anchor);
    }

    protected void updateHungerLevel(UICommandBuilder uiCommandBuilder, float hungerLevel) {
        this.hungerLevel = hungerLevel;
        float barValue = hungerLevel / HungerComponent.maxHungerLevel;
        uiCommandBuilder.set("#HHMHungerBar.Value", barValue);
        uiCommandBuilder.set("#HHMCreativeHungerBar.Value", barValue);
    }

    protected void updateGameMode(UICommandBuilder uiCommandBuilder, GameMode gameMode) {
        this.gameMode = gameMode;
        String iconBackground = gameMode == GameMode.Adventure
            ? "Hungry/HUD/HungerIcon.png"
            : "Hungry/HUD/CreativeHungerIcon.png";
        uiCommandBuilder.set("#HHMIcon.Background", iconBackground);
        uiCommandBuilder.set("#HHMHungerBar.Visible", gameMode == GameMode.Adventure);
        uiCommandBuilder.set("#HHMCreativeHungerBar.Visible", gameMode == GameMode.Creative);
    }

    protected void updateVisibility (UICommandBuilder uiCommandBuilder, boolean visible) {
        this.visible = visible;
        uiCommandBuilder.set("#HHMContainer.Visible", visible);
    }

    static public void updatePlayerHungerLevel(@NonNullDecl PlayerRef playerRef, float hungerLevel) {
        HHMHud hud = hudMap.get(playerRef);
        if (hud == null) return;
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        hud.updateHungerLevel(uiCommandBuilder, hungerLevel);
        hud.update(false, uiCommandBuilder);
    }
    static public void updatePlayerGameMode(@NonNullDecl PlayerRef playerRef, GameMode gameMode) {
        HHMHud hud = hudMap.get(playerRef);
        if (hud == null) return;
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        hud.updateGameMode(uiCommandBuilder, gameMode);
        hud.update(false, uiCommandBuilder);
    }
    static public void updatePlayerHudVisibility(@NonNullDecl PlayerRef playerRef, boolean visible) {
        HHMHud hud = hudMap.get(playerRef);
        if (hud == null) return;
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        hud.updateVisibility(uiCommandBuilder, visible);
        hud.update(false, uiCommandBuilder);
    }

    static public void createPlayerHud(
        @NonNullDecl Store<EntityStore> store,
        @NonNullDecl Ref<EntityStore> ref,
        @NonNullDecl PlayerRef playerRef,
        @NonNullDecl Player player
    ) {
        HungerComponent hunger = store.ensureAndGetComponent(ref, HungerComponent.getComponentType());
        HHMHud hud = new HHMHud(playerRef, player.getGameMode(), hunger.getHungerLevel());
        CompatHUD.get().setCustomHud(player, playerRef, hudIdentifier, hud);
    }
}
