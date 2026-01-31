# FoodValuesConfig.json Documentation

This file configures how much hunger is restored by different food items.

## Global Settings

| Field | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| **IgnoreInteractionValues** | `Boolean` | `false` | If true, ignores values defined in code interactions (Java) by other mods. |
| **IgnoreCustomAssetValues** | `Boolean` | `false` | If true, ignores values defined in Hytale asset files (JSONs). |

## Tier Configuration

Defines default restoration values for items based on their rarity tier if no specific override is found.

| Tier | Hunger Restoration (Default) | Max Hunger Saturation (Default) |
| :--- | :--- | :--- |
| **Common** | 3.75 | 0.0 |
| **Uncommon** | 6.25 | 3.75 |
| **Rare** | 11.25 | 7.5 |
| **Epic** | 17.5 | 11.25 |
| **Legendary** | 25.0 | 16.25 |
| **Mythic** | 35.0 | 20.0 |
| **Unique** | 47.5 | 25.0 |

*Note: Saturation acts as an "overfill" buffer preventing hunger loss for a duration.*

## Item Overrides

You can define specific values for individual items by their ID in `ItemHungerRestoration` and `ItemMaxHungerSaturation` maps.

**Example:**

```json
{
  "ItemHungerRestoration": {
    "food_apple": 10.0
  }
}
```
