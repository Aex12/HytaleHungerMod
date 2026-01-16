package es.xcm.hunger.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

public class HungerComponent implements Component<EntityStore> {
    private static int maxHungerLevel = 100;
    private int hungerLevel;

    public HungerComponent() {
        this.hungerLevel = 100; // Max hunger level
    }

    public HungerComponent (int hungerLevel) {
        assert hungerLevel >= 0 && hungerLevel <= maxHungerLevel;
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

    public int getHungerLevel () {
        return this.hungerLevel;
    }

    public void feed (int amount) {
        this.hungerLevel = Math.min(this.hungerLevel + amount, maxHungerLevel);
    }

    public void starve(int amount) {
        this.hungerLevel = Math.max(this.hungerLevel - amount, 0);
    }
}
