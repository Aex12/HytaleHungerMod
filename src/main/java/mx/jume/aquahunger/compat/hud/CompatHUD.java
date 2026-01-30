package mx.jume.aquahunger.compat.hud;

import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.plugin.PluginBase;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import mx.jume.aquahunger.AquaThirstHunger;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class CompatHUD implements HUDAdapter {
    private final HUDAdapter hudAdapter;
    private static final CompatHUD instance = new CompatHUD();

    public CompatHUD() {
        PluginBase multiplehud = PluginManager.get().getPlugin(PluginIdentifier.fromString("Buuz135:MultipleHUD"));
        if (multiplehud == null || !multiplehud.isEnabled()) {
            hudAdapter = new VanillaHUDAdapter();
            AquaThirstHunger.logInfo("MultipleHUD plugin not found or not enabled. Mod won't be compatible with other HUD mods.");
        } else {
            hudAdapter = new MultipleHUDAdapter();
        }
    }

    public void setCustomHud(
        @NonNullDecl Player player,
        @NonNullDecl PlayerRef playerRef,
        @NonNullDecl String hudIdentifier,
        @NonNullDecl CustomUIHud hud
    ) {
        hudAdapter.setCustomHud(player, playerRef, hudIdentifier, hud);
    }

    public static CompatHUD get() {
        return instance;
    }
}

