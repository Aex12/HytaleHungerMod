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

            .build();

    private float starvationTickRate = 2f;
    private float starvationPerTick = 0.125f;
    private float starvationDamage = 5.0f;

    public float getStarvationTickRate() {
        return starvationTickRate;
    }
    public float getStarvationPerTick() {
        return starvationPerTick;
    }
    public float getStarvationDamage() {
        return starvationDamage;
    }
}
