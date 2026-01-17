package es.xcm.hunger;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class HHMConfig {
    public static final BuilderCodec<HHMConfig> CODEC = BuilderCodec.builder(HHMConfig.class, HHMConfig::new)
            .append(new KeyedCodec<>("StarvationTickRate", Codec.FLOAT),
                    ((config, value) -> config.starvationTickRate = value),
                    HHMConfig::getStarvationTickRate).add()
            .append(new KeyedCodec<>("StarvationPerTick", Codec.FLOAT),
                    ((config, value) -> config.starvationPerTick = value),
                    HHMConfig::getStarvationPerTick).add()
            .append(new KeyedCodec<>("StarvationDamage", Codec.FLOAT),
                    ((config, value) -> config.starvationDamage = value),
                    HHMConfig::getStarvationDamage).add()
            .append(new KeyedCodec<>("StarvationStaminaModifier", Codec.FLOAT),
                    ((config, value) -> config.starvationStaminaModifier = value),
                    HHMConfig::getStarvationStaminaModifier).add()
            .append(new KeyedCodec<>("InteractionFeedT1Amount", Codec.FLOAT),
                    ((config, value) -> config.interactionFeedT1Amount = value),
                    HHMConfig::getInteractionFeedT1Amount).add()
            .append(new KeyedCodec<>("InteractionFeedT2Amount", Codec.FLOAT),
                    ((config, value) -> config.interactionFeedT2Amount = value),
                    HHMConfig::getInteractionFeedT2Amount).add()
            .append(new KeyedCodec<>("InteractionFeedT3Amount", Codec.FLOAT),
                    ((config, value) -> config.interactionFeedT3Amount = value),
                    HHMConfig::getInteractionFeedT3Amount).add()
            .build();

    private float starvationTickRate = 2f;
    private float starvationPerTick = 0.125f;
    private float starvationDamage = 5.0f;
    private float starvationStaminaModifier = 3f;
    private float interactionFeedT1Amount = 15.0f;
    private float interactionFeedT2Amount = 25.0f;
    private float interactionFeedT3Amount = 45.0f;

    public float getStarvationTickRate() {
        return starvationTickRate;
    }
    public float getStarvationPerTick() {
        return starvationPerTick;
    }
    public float getStarvationDamage() {
        return starvationDamage;
    }
    public float getStarvationStaminaModifier() {
        return starvationStaminaModifier;
    }
    public float getInteractionFeedT1Amount() {
        return interactionFeedT1Amount;
    }
    public float getInteractionFeedT2Amount() {
        return interactionFeedT2Amount;
    }
    public float getInteractionFeedT3Amount() {
        return interactionFeedT3Amount;
    }
}
