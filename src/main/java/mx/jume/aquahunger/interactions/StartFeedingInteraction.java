package mx.jume.aquahunger.interactions;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.ui.HHMHud;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class StartFeedingInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<StartFeedingInteraction> CODEC = BuilderCodec.builder(StartFeedingInteraction.class, StartFeedingInteraction::new, SimpleInstantInteraction.CODEC)
            .appendInherited(new KeyedCodec<>("HungerRestoration", Codec.FLOAT),
                    ((foodValue, value) -> foodValue.hungerRestoration = value),
                    (foodValue) -> foodValue.hungerRestoration,
                    (foodValue, parent) -> foodValue.hungerRestoration = parent.hungerRestoration).add()
            .appendInherited(new KeyedCodec<>("MaxHungerSaturation", Codec.FLOAT),
                    ((foodValue, value) -> foodValue.maxHungerSaturation = value),
                    (foodValue) -> foodValue.maxHungerSaturation,
                    (foodValue, parent) -> foodValue.maxHungerSaturation = parent.maxHungerSaturation).add()
            .build();

    private Float hungerRestoration;
    private Float maxHungerSaturation;


    public float getHungerRestoration(Item item) {
        return FeedInteraction.getHungerRestoration(item, this.hungerRestoration);
    }
    public float getMaxHungerSaturation(Item item) {
        return FeedInteraction.getMaxHungerSaturation(item, this.maxHungerSaturation);
    }

    @Override
    protected void firstRun(
            @NonNullDecl InteractionType interactionType,
            @NonNullDecl InteractionContext context,
            @NonNullDecl CooldownHandler cooldownHandler
    ) {
        final Ref<EntityStore> ref = context.getEntity();
        final Store<EntityStore> store = ref.getStore();
        final Item item = context.getOriginalItemType();
        final PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (item == null || playerRef == null) return;

        float hungerRestoration = getHungerRestoration(item);
        float maxHungerSaturation = getMaxHungerSaturation(item);
        HHMHud.updatePlayerHungerRestorationPreview(playerRef, hungerRestoration, maxHungerSaturation);
    }
}

