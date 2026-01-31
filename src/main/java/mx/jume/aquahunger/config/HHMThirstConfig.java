package mx.jume.aquahunger.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import java.util.Optional;

public class HHMThirstConfig {
    public static final BuilderCodec<HHMThirstConfig> CODEC = BuilderCodec
            .builder(HHMThirstConfig.class, HHMThirstConfig::new)
            .append(new KeyedCodec<>("MaxThirst", Codec.FLOAT),
                    ((config, value) -> config.maxThirst = value),
                    HHMThirstConfig::getMaxThirst)
            .add()
            .append(new KeyedCodec<>("DepletionTickRate", Codec.FLOAT), // Seconds between updates
                    ((config, value) -> config.depletionTickRate = value),
                    HHMThirstConfig::getDepletionTickRate)
            .add()
            .append(new KeyedCodec<>("DepletionPerTick", Codec.FLOAT),
                    ((config, value) -> config.depletionPerTick = value),
                    HHMThirstConfig::getDepletionPerTick)
            .add()
            .append(new KeyedCodec<>("DepletionPerBlockHit", Codec.FLOAT),
                    ((config, value) -> config.depletionPerBlockHit = value),
                    HHMThirstConfig::getDepletionPerBlockHit)
            .add()
            .append(new KeyedCodec<>("SprintDepletionModifier", Codec.FLOAT),
                    ((config, value) -> config.sprintDepletionModifier = value),
                    HHMThirstConfig::getSprintDepletionModifier)
            .add()
            .append(new KeyedCodec<>("DehydrationDamage", Codec.FLOAT),
                    ((config, value) -> config.dehydrationDamage = value),
                    HHMThirstConfig::getDehydrationDamage)
            .add()
            .append(new KeyedCodec<>("DamageIntervalSeconds", Codec.FLOAT),
                    ((config, value) -> config.damageIntervalSeconds = value),
                    HHMThirstConfig::getDamageIntervalSeconds)
            .add()
            .append(new KeyedCodec<>("HudPosition", Codec.STRING),
                    ((config, value) -> {
                        config.hudPosition = Optional.ofNullable(HudPosition.valueOf(value))
                                .orElse(HudPosition.pluginDefault());
                    }),
                    ((config) -> config.getHudPosition().name()))
            .add()
            .append(new KeyedCodec<>("ResetThirstOnDeath", Codec.BOOLEAN),
                    ((config, value) -> config.resetThirstOnDeath = value),
                    HHMThirstConfig::isResetThirstOnDeath)
            .add()
            .append(new KeyedCodec<>("RespawnThirstLevel", Codec.FLOAT),
                    ((config, value) -> config.respawnThirstLevel = value),
                    HHMThirstConfig::getRespawnThirstLevel)
            .add()
            .append(new KeyedCodec<>("EnableThirst", Codec.BOOLEAN),
                    ((config, value) -> config.enableThirst = value),
                    HHMThirstConfig::isEnableThirst)
            .add()
            .build();

    private float maxThirst = 100.0f;
    private float depletionTickRate = 2.0f; // Check every 2 seconds
    private float depletionPerTick = 0.2f; // Lose 0.2 thirst every 2s
    private float depletionPerBlockHit = 0.05f;
    private float sprintDepletionModifier = 0.1f;
    private float dehydrationDamage = 2.0f;
    private float damageIntervalSeconds = 4.0f;
    private HudPosition hudPosition = HudPosition.Preset.BelowHotbarRight;
    private boolean resetThirstOnDeath = true;
    private float respawnThirstLevel = 50.0f;
    private boolean enableThirst = true;

    public float getMaxThirst() {
        return maxThirst;
    }

    public float getDepletionTickRate() {
        return depletionTickRate;
    }

    public float getDepletionPerTick() {
        return depletionPerTick;
    }

    public float getDepletionPerBlockHit() {
        return depletionPerBlockHit;
    }

    public float getSprintDepletionModifier() {
        return sprintDepletionModifier;
    }

    public float getDehydrationDamage() {
        return dehydrationDamage;
    }

    public float getDamageIntervalSeconds() {
        return damageIntervalSeconds;
    }

    public HudPosition getHudPosition() {
        return hudPosition;
    }

    public void setHudPosition(HudPosition pos) {
        this.hudPosition = pos;
    }

    public boolean isResetThirstOnDeath() {
        return resetThirstOnDeath;
    }

    public float getRespawnThirstLevel() {
        return respawnThirstLevel;
    }

    public boolean isEnableThirst() {
        return enableThirst;
    }
}
