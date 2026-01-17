package es.xcm.hunger.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import es.xcm.hunger.HytaleHungerMod;

public class FeedInteractionT3 extends FeedInteractionBase {
    public static final BuilderCodec<FeedInteractionT3> CODEC = BuilderCodec.builder(FeedInteractionT3.class, FeedInteractionT3::new, SimpleInstantInteraction.CODEC).build();

    public FeedInteractionT3() {
        super();
        this.feedAmount = HytaleHungerMod.get().getConfig().getInteractionFeedT3Amount();
    }
}
