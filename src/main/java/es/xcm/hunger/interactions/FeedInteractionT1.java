package es.xcm.hunger.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import es.xcm.hunger.HytaleHungerMod;

public class FeedInteractionT1 extends FeedInteractionBase {
    public static final BuilderCodec<FeedInteractionT1> CODEC = BuilderCodec.builder(FeedInteractionT1.class, FeedInteractionT1::new, SimpleInstantInteraction.CODEC).build();

    public FeedInteractionT1() {
        super();
        this.fallbackHungerRestoration = HytaleHungerMod.get().getHungerConfig().getInteractionFeedT1Amount();
    }
}
