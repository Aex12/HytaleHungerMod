package es.xcm.hunger.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import es.xcm.hunger.HytaleHungerMod;
import es.xcm.hunger.config.HudPosition;
import es.xcm.hunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HungryPositionCommand extends CommandBase {
    public static final String requiredPermission = "hungry.config.position";

    public HungryPositionCommand() {
        super("position", "Set the HUD position");
        this.addUsageVariant(new HungrySetPositionCommand());
        this.requirePermission(requiredPermission);
    }

    static Message positionsMessage = Message.raw("""
        BottomLeft
        AboveHotbarCentered
        AboveHotbarLeft
        BelowHotbarCentered
        BelowHotbarLeft
        Custom:<left>:<bottom> (e.g., Custom:100:50)
        """);
    static Message helpMessage = Message.join(
            Message.raw("Valid HUD positions are:\n"),
            positionsMessage
    );

    @Override
    protected void executeSync(@NonNullDecl CommandContext context) {
        context.sendMessage(helpMessage);
    }

    private static class HungrySetPositionCommand extends CommandBase {
        private final RequiredArg<String> position = this.withRequiredArg("position", "The new HUD position", ArgTypes.STRING);

        public HungrySetPositionCommand() {
            super("Set HUD position");
            this.requirePermission(requiredPermission);
        }

        static Message invalidPositionMessage = Message.join(
                Message.raw("Invalid HUD position. Valid positions are:\n"),
                positionsMessage
        );

        @Override
        protected void executeSync(@NonNullDecl CommandContext context) {
            HudPosition hudPosition = HudPosition.valueOf(this.position.get(context));
            if (hudPosition == null) {
                context.sendMessage(invalidPositionMessage);
                return;
            }
            HytaleHungerMod mod = HytaleHungerMod.get();
            mod.getConfig().setHudPosition(hudPosition);
            mod.saveConfig();
            for (World world : Universe.get().getWorlds().values()) {
                world.execute(() -> {
                    world.getPlayerRefs().forEach((playerRef) -> {
                        HHMHud.updatePlayerHudPosition(playerRef, hudPosition);
                    });
                });
            }
            context.sendMessage(Message.raw("HUD position set to " + hudPosition.name() + " to all players in the server. The new configuration value has been saved in the config file."));
        }
    }
}