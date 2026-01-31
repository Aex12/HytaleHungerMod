package mx.jume.aquahunger.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class ThirstyCommand extends AbstractPlayerCommand {
    public static final String requiredPermission = "hungry.thirst.base";

    public ThirstyCommand() {
        super("aquathirst", "Thirst Command", false);
        this.addSubCommand(new SetThirstCommand());
        this.requirePermission(requiredPermission);
    }

    static String helpMessageBase = """
            Thirst commands

            /aquathirst - Show this help message.""";
    static String helpMessageSetSelf = "/aquathirst set <thirstLevel> - Set your own thirst level (0-100).";
    static String helpMessageSetOther = "/aquathirst set <player> <thirstLevel> - Set another player's thirst level (0-100).";

    @Override
    protected void execute(
            @NonNullDecl CommandContext context,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl PlayerRef playerRef,
            @NonNullDecl World world) {
        Message message = Message.empty();
        message.insert(helpMessageBase);

        if (context.sender().hasPermission(SetThirstCommand.requiredPermission)) {
            message.insert("\n").insert(helpMessageSetSelf);
        }
        if (context.sender().hasPermission(SetThirstCommand.SetThirstOtherCommand.requiredPermission)) {
            message.insert("\n").insert(helpMessageSetOther);
        }
        playerRef.sendMessage(message);
    }
}
