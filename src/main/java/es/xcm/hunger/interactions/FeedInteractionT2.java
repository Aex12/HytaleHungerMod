package es.xcm.hunger.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import es.xcm.hunger.HytaleHungerMod;

public class FeedInteractionT2 extends FeedInteractionBase {
    public static final BuilderCodec<FeedInteractionT2> CODEC = BuilderCodec.builder(FeedInteractionT2.class, FeedInteractionT2::new, SimpleInstantInteraction.CODEC).build();

    public FeedInteractionT2() {
        super();
        this.fallbackHungerRestoration = HytaleHungerMod.get().getHungerConfig().getInteractionFeedT2Amount();
    }
}
