# HungerConfig.json Documentation

This file contains the core configuration for the Hunger system.

| Field | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| **InitialHungerLevel** | `Float` | `70.0` | The amount of hunger a player starts with when joining for the first time. |
| **RespawnHungerLevel** | `Float` | `50.0` | The amount of hunger a player has after respawning. |
| **StarvationStaminaModifier** | `Float` | `0.175` | *Internal use*. Modifies stamina regen/consumption based on starvation? (Not fully exposed/documented in interaction logic yet). |
| **HungryThreshold** | `Float` | `20.0` | The hunger level at or below which the player is considered "Hungry" (triggers HUD shake/warning). |
| **StarvationDamage** | `Float` | `5.0` | The amount of damage applied per interval when hunger reaches 0. |
| **HudPosition** | `String` | `"BelowHotbarLeft"` | The position of the Hunger HUD. <br>Values: `AboveHotbarCentered`, `BelowHotbarCentered`, `AboveHotbarLeft`, `AboveHotbarRight`, `BelowHotbarLeft`, `BelowHotbarRight`, `BottomLeft`, `BottomRight` or `Custom:x:y`. |
| **SinglePlayer** | `Boolean` | `true` | If true, optimized for single-player specific logic (e.g. pausing hunger in menus, if implemented). |
| **EnableHunger** | `Boolean` | `true` | If false, the hunger system (drain, HUD, validation) is completely disabled. |
