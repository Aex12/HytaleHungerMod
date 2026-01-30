package es.xcm.hunger.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import es.xcm.hunger.HytaleHungerMod;

import javax.annotation.Nullable;

public class HungerComponent implements Component<EntityStore> {
    public static final BuilderCodec<HungerComponent> CODEC = BuilderCodec.builder(HungerComponent.class, HungerComponent::new)
            .append(new KeyedCodec<>("HungerLevel", Codec.FLOAT),
                    ((data, value) -> data.hungerLevel = value),
                    HungerComponent::getHungerLevel).add()
            .build();

    public static final float maxHungerLevel = 200.0f;
    // public static final float initialHungerLevel = 100.0f; // Removed in favor of config
    private float elapsedTime = 0.0f;
    private float lowestStaminaSeen = 10.0f;
    private int blockHits = 0;
    private float hungerLevel;

    public HungerComponent() {
        this.hungerLevel = HytaleHungerMod.get().getHungerConfig().getInitialHungerLevel();
    }

    public HungerComponent (float hungerLevel) {
        assert hungerLevel >= 0.0f && hungerLevel <= maxHungerLevel;
        this.hungerLevel = hungerLevel;
    }

    public HungerComponent (HungerComponent other) {
        this.hungerLevel = other.hungerLevel;
        this.elapsedTime = other.elapsedTime;
        this.lowestStaminaSeen = other.lowestStaminaSeen;
        this.blockHits = other.blockHits;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new HungerComponent(this);
    }

    public float getElapsedTime () {
        return this.elapsedTime;
    }
    public void addElapsedTime (float deltaTime) {
        this.elapsedTime += deltaTime;
    }
    public void resetElapsedTime () {
        this.elapsedTime = 0.0f;
    }

    public float getAndResetLowestStaminaSeen() {
        float lowestStaminaSeen = this.lowestStaminaSeen;
        this.lowestStaminaSeen = 10.0f;
        return lowestStaminaSeen;
    }
    public void setStaminaSeen(float stamina) {
        if (stamina > this.lowestStaminaSeen) return;
        this.lowestStaminaSeen = stamina;
    }
    public int getAndResetBlockHits() {
        int blockHits = this.blockHits;
        this.blockHits = 0;
        return blockHits;
    }
    public void incrementBlockHits() {
        this.blockHits += 1;
    }

    public float getHungerLevel () {
        return this.hungerLevel;
    }
    public void setHungerLevel (float hungerLevel) {
        this.hungerLevel = Math.max(0.0f, Math.min(hungerLevel, maxHungerLevel));
    }
    public void feed (float amount) {
        this.hungerLevel = Math.min(this.hungerLevel + amount, maxHungerLevel);
    }
    public void starve(float amount) {
        this.hungerLevel = Math.max(this.hungerLevel - amount, 0.0f);
    }

    public static ComponentType<EntityStore, HungerComponent> getComponentType() {
        return HytaleHungerMod.get().getHungerComponentType();
    }
}
