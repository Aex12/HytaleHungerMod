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
import es.xcm.hunger.config.HHMHungerConfig;
import es.xcm.hunger.config.HudPosition;
import es.xcm.hunger.interactions.FeedInteraction;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.WeakHashMap;

// https://www.youtube.com/watch?v=cha7YFULwxY&t=1330s

public class HHMHud extends CustomUIHud {
    static private final WeakHashMap<PlayerRef, HHMHud> hudMap = new WeakHashMap<>();
    static public final String hudIdentifier = "es.xcm.hunger.hud.hunger";
    private HudPosition hudPosition;
    private GameMode gameMode;
    private float hungerLevel;
    private float previewHungerRestoration = 0.0f;
    private float previewMaxHungerSaturation = 0.0f;
    private boolean visible = true;

    public HHMHud(@NonNullDecl PlayerRef playerRef, GameMode gameMode, float hungerLevel) {
        super(playerRef);
        this.gameMode = gameMode;
        this.hungerLevel = hungerLevel;
        HHMHungerConfig config = HytaleHungerMod.get().getHungerConfig();
        this.hudPosition = config.getHudPosition();
        hudMap.put(playerRef, this);
    }

    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append("Hungry/HUD/Hunger.ui");
        updateHudPosition(uiCommandBuilder, this.hudPosition);
        updateGameMode(uiCommandBuilder, this.gameMode);
        updateHungerLevel(uiCommandBuilder, this.hungerLevel);
        updateVisibility(uiCommandBuilder, this.visible);
    }

    protected void updateHudPosition(UICommandBuilder uiCommandBuilder, HudPosition hudPosition) {
        this.hudPosition = hudPosition;
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
        uiCommandBuilder.set("#HHMHUD.LayoutMode", hudPosition.layoutMode().name());
    }

    protected void updateHungerLevel(UICommandBuilder uiCommandBuilder, float hungerLevel) {
        this.hungerLevel = hungerLevel;
        float hungerBarValue = Math.min(hungerLevel, 100.0f) / 100.0f;
        float saturationBarValue = Math.max(hungerLevel - 100.0f, 0.0f) / 100.0f;

        uiCommandBuilder.set("#HHMHungerBar.Value", hungerBarValue);
        uiCommandBuilder.set("#HHMHungerBarEffect.Value", hungerBarValue);
        uiCommandBuilder.set("#HHMCreativeHungerBar.Value", hungerBarValue);
        uiCommandBuilder.set("#HHMHungerSaturationBar.Value", saturationBarValue);

        if (this.previewHungerRestoration != 0.0f || this.previewMaxHungerSaturation != 0.0f) {
            updateHungerRestorationPreview(uiCommandBuilder, this.previewHungerRestoration, this.previewMaxHungerSaturation);
        }
    }

    protected void updateHungerRestorationPreview (UICommandBuilder uiCommandBuilder, float hungerRestoration, float maxHungerSaturation) {
        this.previewHungerRestoration = hungerRestoration;
        this.previewMaxHungerSaturation = maxHungerSaturation;
        if (hungerRestoration == 0.0f && maxHungerSaturation == 0.0f) {
            uiCommandBuilder.set("#HHMHungerRestorePreviewBar.Value", 0.0f);
            uiCommandBuilder.set("#HHMHungerSaturationRestorePreviewBar.Value", 0.0f);
            return;
        };

        float expectedHungerLevel = FeedInteraction.getExpectedHungerLevel(this.hungerLevel, hungerRestoration, maxHungerSaturation);
        float hungerBarValue = Math.min(expectedHungerLevel, 100.0f) / 100.0f;
        float saturationBarValue = Math.max(expectedHungerLevel - 100.0f, 0.0f) / 100.0f;

        uiCommandBuilder.set("#HHMHungerRestorePreviewBar.Value", hungerBarValue);
        uiCommandBuilder.set("#HHMHungerSaturationRestorePreviewBar.Value", saturationBarValue);
    }

    protected void updateGameMode(UICommandBuilder uiCommandBuilder, GameMode gameMode) {
        this.gameMode = gameMode;
        String iconBackground = gameMode == GameMode.Adventure
            ? "Hungry/HUD/HungerIcon.png"
            : "Hungry/HUD/CreativeHungerIcon.png";
        uiCommandBuilder.set("#HHMIcon.Background", iconBackground);
        uiCommandBuilder.set("#HHMHungerBar.Visible", gameMode == GameMode.Adventure);
        uiCommandBuilder.set("#HHMHungerSaturationBar.Visible", gameMode == GameMode.Adventure);
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
    static public void updatePlayerHungerRestorationPreview(@NonNullDecl PlayerRef playerRef, float hungerRestoration, float maxHungerSaturation) {
        HHMHud hud = hudMap.get(playerRef);
        if (hud == null) return;
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        hud.updateHungerRestorationPreview(uiCommandBuilder, hungerRestoration, maxHungerSaturation);
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
    static public void updatePlayerHudPosition(@NonNullDecl PlayerRef playerRef, HudPosition hudPosition) {
        HHMHud hud = hudMap.get(playerRef);
        if (hud == null) return;
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        hud.updateHudPosition(uiCommandBuilder, hudPosition);
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
