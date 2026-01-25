package es.xcm.hunger.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.function.Predicate;

public class HungryCommand extends AbstractPlayerCommand {
    public static final String requiredPermission = "hungry.commmand.base";

    public HungryCommand() {
        super("hunger", "Hungry Command", false);
        this.addSubCommand(new SetHungerCommand());
        this.addSubCommand(new HungryHideCommand());
        this.addSubCommand(new HungryShowCommand());
        this.addSubCommand(new HungryPositionCommand());
        this.requirePermission(requiredPermission);
    }

    static String helpMessageBase = """
        Hungry commands
                    
        /hunger - Show this help message.""";
    static String hideMessage = "/hunger hide - Hide the Hunger bar.";
    static String showMessage = "/hunger show - Show the Hunger bar.";
    static String positionMessage = "/hunger position <position> - Sets the position of the Hunger bar.";
    static String helpMessageSetSelf = "/hunger set <hungerLevel> - Set your own hunger level (0-100).";
    static String helpMessageSetOther = "/hunger set <player> <hungerLevel> - Set another player's hunger level (0-100).";

    @Override
    protected void execute(
            @NonNullDecl CommandContext context,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl PlayerRef playerRef,
            @NonNullDecl World world
    ) {
        Message message = Message.empty();
        message.insert(helpMessageBase);
        if (context.sender().hasPermission(HungryHideCommand.requiredPermission)) {
            message.insert("\n").insert(hideMessage);
        }
        if (context.sender().hasPermission(HungryShowCommand.requiredPermission)) {
            message.insert("\n").insert(showMessage);
        }
        if (context.sender().hasPermission(HungryPositionCommand.requiredPermission)) {
            message.insert("\n").insert(positionMessage);
        }
        if (context.sender().hasPermission(SetHungerCommand.requiredPermission)) {
            message.insert("\n").insert(helpMessageSetSelf);
        }
        if (context.sender().hasPermission(SetHungerCommand.SetHungerOtherCommand.requiredPermission)) {
            message.insert("\n").insert(helpMessageSetOther);
        }
        playerRef.sendMessage(message);
    }
}
