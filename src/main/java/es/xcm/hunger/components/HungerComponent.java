package es.xcm.hunger.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

public class HungerComponent implements Component<EntityStore> {
    private static float maxHungerLevel = 100.0f;
    private float hungerLevel;

    public HungerComponent() {
        this.hungerLevel = 100.0f; // Max hunger level
    }

    public HungerComponent (float hungerLevel) {
        assert hungerLevel >= 0.0f && hungerLevel <= maxHungerLevel;
        this.hungerLevel = hungerLevel;
    }

    public HungerComponent (HungerComponent other) {
        this.hungerLevel = other.hungerLevel;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new HungerComponent(this);
    }

    public float getHungerLevel () {
        return this.hungerLevel;
    }

    public void feed (float amount) {
        this.hungerLevel = Math.min(this.hungerLevel + amount, maxHungerLevel);
    }

    public void starve(float amount) {
        this.hungerLevel = Math.max(this.hungerLevel - amount, 0.0f);
    }
}
