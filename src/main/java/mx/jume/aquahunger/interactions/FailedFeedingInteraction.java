package mx.jume.aquahunger.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class FailedFeedingInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<FailedFeedingInteraction> CODEC = BuilderCodec.builder(FailedFeedingInteraction.class, FailedFeedingInteraction::new, SimpleInstantInteraction.CODEC).build();

    @Override
    protected void firstRun(
            @NonNullDecl InteractionType interactionType,
            @NonNullDecl InteractionContext context,
            @NonNullDecl CooldownHandler cooldownHandler
    ) {
        final Ref<EntityStore> ref = context.getEntity();
        final Store<EntityStore> store = ref.getStore();
        final PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (playerRef == null) return;

        HHMHud.updatePlayerHungerRestorationPreview(playerRef, 0.0f, 0.0f);
    }
}

