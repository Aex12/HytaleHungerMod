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
            .append(new KeyedCodec<>("HungerRestoration", new MapCodec<>(Codec.FLOAT, HashMap::new)),
                    ((config, value) -> config.hungerRestoration = value),
                    (c) -> c.hungerRestoration).add()
            .build();

    private Map<String, Float> hungerRestoration = new HashMap<>();

    public Float getFoodHungerRestoration(@NonNullDecl String itemId) {
        return hungerRestoration.get(itemId);
    }
}
