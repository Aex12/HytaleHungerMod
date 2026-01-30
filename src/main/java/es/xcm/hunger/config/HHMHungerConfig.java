package es.xcm.hunger.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import java.util.Optional;

public class HHMHungerConfig {
    public static final BuilderCodec<HHMHungerConfig> CODEC = BuilderCodec.builder(HHMHungerConfig.class, HHMHungerConfig::new)
            .append(new KeyedCodec<>("StarvationTickRate", Codec.FLOAT),
                    ((config, value) -> config.starvationTickRate = value),
                    HHMHungerConfig::getStarvationTickRate).add()
            .append(new KeyedCodec<>("InitialHungerLevel", Codec.FLOAT),
                    ((config, value) -> config.initialHungerLevel = value),
                    HHMHungerConfig::getInitialHungerLevel).add()
            .append(new KeyedCodec<>("ResetHungerOnDeath", Codec.BOOLEAN),
                    ((config, value) -> config.resetHungerOnDeath = value),
                    HHMHungerConfig::isResetHungerOnDeath).add()
            .append(new KeyedCodec<>("StarvationPerTick", Codec.FLOAT),
                    ((config, value) -> config.starvationPerTick = value),
                    HHMHungerConfig::getStarvationPerTick).add()
            .append(new KeyedCodec<>("StarvationPerBlockHit", Codec.FLOAT),
                    ((config, value) -> config.starvationPerBlockHit = value),
                    HHMHungerConfig::getStarvationPerBlockHit).add()
            .append(new KeyedCodec<>("StarvationStaminaModifier", Codec.FLOAT),
                    ((config, value) -> config.starvationStaminaModifier = value),
                    HHMHungerConfig::getStarvationStaminaModifier).add()
            .append(new KeyedCodec<>("HungryThreshold", Codec.FLOAT),
                    ((config, value) -> config.hungryThreshold = value),
                    HHMHungerConfig::getHungryThreshold).add()
            .append(new KeyedCodec<>("StarvationDamage", Codec.FLOAT),
                    ((config, value) -> config.starvationDamage = value),
                    HHMHungerConfig::getStarvationDamage).add()
            .append(new KeyedCodec<>("HudPosition", Codec.STRING),
                    ((config, value) -> {
                        config.hudPosition = Optional.ofNullable(HudPosition.valueOf(value))
                                .orElse(HudPosition.pluginDefault());
                    }),
                    ((config) -> config.getHudPosition().name())).add()
            .build();


    private float initialHungerLevel = 100.0f;
    private boolean resetHungerOnDeath = true;
    private float starvationTickRate = 2f;
    private float starvationPerTick = 0.125f;
    private float starvationPerBlockHit = 0.02f;
    private float starvationStaminaModifier = 0.175f;
    private float hungryThreshold = 20.0f;
    private float starvationDamage = 5.0f;
    private HudPosition hudPosition = HudPosition.pluginDefault();

    public float getStarvationTickRate() {
        return starvationTickRate;
    }
    public float getInitialHungerLevel() {
        return initialHungerLevel;
    }
    public boolean isResetHungerOnDeath() {
        return resetHungerOnDeath;
    }
    public float getStarvationPerTick() {
        return starvationPerTick;
    }
    public float getStarvationPerBlockHit() {
        return starvationPerBlockHit;
    }
    public float getStarvationStaminaModifier() {
        return starvationStaminaModifier;
    }
    public float getHungryThreshold() {
        return hungryThreshold;
    }
    public float getStarvationDamage() {
        return starvationDamage;
    }
    public HudPosition getHudPosition() {
        return hudPosition;
    }
    public void setHudPosition(HudPosition hudPosition) {
        this.hudPosition = hudPosition;
    }
}
