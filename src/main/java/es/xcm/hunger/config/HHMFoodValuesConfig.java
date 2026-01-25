package es.xcm.hunger.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.map.MapCodec;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.HashMap;
import java.util.Map;

public class HHMFoodValuesConfig {
    public static final BuilderCodec<HHMFoodValuesConfig> CODEC = BuilderCodec.builder(HHMFoodValuesConfig.class, HHMFoodValuesConfig::new)
            .append(new KeyedCodec<>("T1HungerRestoration", Codec.FLOAT),
                    ((config, value) -> config.T1HungerRestoration = value),
                    HHMFoodValuesConfig::getT1HungerRestoration).add()
            .append(new KeyedCodec<>("T2HungerRestoration", Codec.FLOAT),
                    ((config, value) -> config.T2HungerRestoration = value),
                    HHMFoodValuesConfig::getT2HungerRestoration).add()
            .append(new KeyedCodec<>("T3HungerRestoration", Codec.FLOAT),
                    ((config, value) -> config.T3HungerRestoration = value),
                    HHMFoodValuesConfig::getT3HungerRestoration).add()
            .append(new KeyedCodec<>("T1MaxHungerSaturation", Codec.FLOAT),
                    ((config, value) -> config.T1MaxHungerSaturation = value),
                    HHMFoodValuesConfig::getT1MaxHungerSaturation).add()
            .append(new KeyedCodec<>("T2MaxHungerSaturation", Codec.FLOAT),
                    ((config, value) -> config.T2MaxHungerSaturation = value),
                    HHMFoodValuesConfig::getT2MaxHungerSaturation).add()
            .append(new KeyedCodec<>("T3MaxHungerSaturation", Codec.FLOAT),
                    ((config, value) -> config.T3MaxHungerSaturation = value),
                    HHMFoodValuesConfig::getT3MaxHungerSaturation).add()
            .append(new KeyedCodec<>("HungerRestoration", new MapCodec<>(Codec.FLOAT, HashMap::new)),
                    ((config, value) -> config.hungerRestoration = value),
                    (c) -> c.hungerRestoration).add()
            .append(new KeyedCodec<>("MaxHungerSaturation", new MapCodec<>(Codec.FLOAT, HashMap::new)),
                    ((config, value) -> config.maxHungerSaturation = value),
                    (c) -> c.maxHungerSaturation).add()
            .build();

    private float T1HungerRestoration = 15.0f;
    private float T2HungerRestoration = 25.0f;
    private float T3HungerRestoration = 45.0f;
    private float T1MaxHungerSaturation = 0.0f;
    private float T2MaxHungerSaturation = 15.0f;
    private float T3MaxHungerSaturation = 30.0f;
    private Map<String, Float> hungerRestoration = new HashMap<>();
    private Map<String, Float> maxHungerSaturation = new HashMap<>();

    public Float getFoodHungerRestoration(@NonNullDecl String itemId) {
        return hungerRestoration.get(itemId);
    }
    public Float getFoodMaxHungerSaturation(@NonNullDecl String itemId) {
        return maxHungerSaturation.get(itemId);
    }
    public float getT1HungerRestoration() {
        return T1HungerRestoration;
    }
    public float getT2HungerRestoration() {
        return T2HungerRestoration;
    }
    public float getT3HungerRestoration() {
        return T3HungerRestoration;
    }
    public float getT1MaxHungerSaturation() {
        return T1MaxHungerSaturation;
    }
    public float getT2MaxHungerSaturation() {
        return T2MaxHungerSaturation;
    }
    public float getT3MaxHungerSaturation() {
        return T3MaxHungerSaturation;
    }
}
