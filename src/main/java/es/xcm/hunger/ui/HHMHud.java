package es.xcm.hunger.ui;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.Anchor;
import com.hypixel.hytale.server.core.ui.Value;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.WeakHashMap;

// https://www.youtube.com/watch?v=cha7YFULwxY&t=1330s

public class HHMHud extends CustomUIHud {
    static private final WeakHashMap<PlayerRef, HHMHud> hudMap = new WeakHashMap<>();
    static public final String hudIdentifier = "es.xcm.hunger.hud.hunger";

    public HHMHud(@NonNullDecl PlayerRef playerRef) {
        super(playerRef);
        hudMap.put(playerRef, this);
    }

    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append("HUD/Hunger.ui");
    }

    public void updateHungerLevel(float hungerLevel) {
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();

        Anchor anchor = new Anchor();
        int fillWidth = (int) hungerLevel * 3; // hunger bar is 300 pixels wide, while max hunger is 100
        anchor.setWidth(Value.of(fillWidth));
        anchor.setHeight(Value.of(12));
        uiCommandBuilder.setObject("#Fill.Anchor", anchor);

        update(false, uiCommandBuilder);
    }

    static public void updatePlayerHungerLevel(@NonNullDecl PlayerRef playerRef, float hungerLevel) {
        HHMHud hud = hudMap.get(playerRef);
        if (hud == null) return;
        hud.updateHungerLevel(hungerLevel);
    }
}
