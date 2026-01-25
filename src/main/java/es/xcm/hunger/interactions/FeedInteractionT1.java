package es.xcm.hunger.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import es.xcm.hunger.HytaleHungerMod;
import es.xcm.hunger.config.HHMFoodValuesConfig;

public class FeedInteractionT1 extends FeedInteractionBase {
    public static final BuilderCodec<FeedInteractionT1> CODEC = BuilderCodec.builder(FeedInteractionT1.class, FeedInteractionT1::new, SimpleInstantInteraction.CODEC).build();

    public FeedInteractionT1() {
        super();
        HHMFoodValuesConfig config = HytaleHungerMod.get().getFoodValuesConfig();
        this.fallbackHungerRestoration = config.getT1HungerRestoration();
        this.fallbackMaxHungerSaturation = config.getT1MaxHungerSaturation();
    }
}
