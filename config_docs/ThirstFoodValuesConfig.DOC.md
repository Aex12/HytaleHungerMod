# ThirstFoodValuesConfig.json Documentation

This file configures how much thirst is restored by different items.

## Global Settings

| Field | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| **ResultResourceTypeId** | `String` | `"Fruit"` | The resource tag used to identify "Fruit" items generally. |
| **FruitMultiplier** | `Float` | `5.0` | Multiplier applied to fruit items if they don't have a specific override. |

## Tier Configuration

Defines default thirst restoration values for items based on their rarity tier.

| Tier | Thirst Restoration (Default) |
| :--- | :--- |
| **Common** | 5.5 |
| **Uncommon** | 6.5 |
| **Rare** | 7.0 |
| **Epic** | 7.5 |
| **Legendary** | 8.0 |
| **Mythic** | 9.5 |
| **Unique** | 10.0 |

## Item Overrides

You can define specific values for individual items by their ID in `ItemThirstRestoration` map.

**Example:**

```json
{
  "ItemThirstRestoration": {
    "AquaThirstHunger_Canteen": 10.0
  }
}
```
