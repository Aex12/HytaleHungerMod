package es.xcm.hunger.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import es.xcm.hunger.HytaleHungerMod;
import es.xcm.hunger.config.HHMFoodValuesConfig;

public class FeedInteractionT3 extends FeedInteractionBase {
    public static final BuilderCodec<FeedInteractionT3> CODEC = BuilderCodec.builder(FeedInteractionT3.class, FeedInteractionT3::new, SimpleInstantInteraction.CODEC).build();

    public FeedInteractionT3() {
        super();
        HHMFoodValuesConfig config = HytaleHungerMod.get().getFoodValuesConfig();
        this.fallbackHungerRestoration = config.getT3HungerRestoration();
        this.fallbackMaxHungerSaturation = config.getT3MaxHungerSaturation();
    }
}
