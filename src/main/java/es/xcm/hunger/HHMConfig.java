package es.xcm.hunger;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class HHMConfig {
    public static final BuilderCodec<HHMConfig> CODEC = BuilderCodec.builder(HHMConfig.class, HHMConfig::new)
            .append(new KeyedCodec<>("StarvationRate", Codec.FLOAT),
                    ((config, value) -> config.starvationRate = value),
                    HHMConfig::getStarvationRate).add()
            .append(new KeyedCodec<>("StarvationDamage", Codec.FLOAT),
                    ((config, value) -> config.starvationDamage = value),
                    HHMConfig::getStarvationDamage).add()
            .append(new KeyedCodec<>("StarvationInterval", Codec.FLOAT),
                    ((config, value) -> config.starvationInterval = value),
                    HHMConfig::getStarvationInterval).add()
            .build();

    private float starvationRate = 1.0f;
    private float starvationDamage = 2.0f;
    private float starvationInterval = 5.0f;

    public float getStarvationRate() {
        return starvationRate;
    }
    public float getStarvationInterval () {
        return starvationInterval;
    }
    public float getStarvationDamage() {
        return starvationDamage;
    }
}
