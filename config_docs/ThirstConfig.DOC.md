# ThirstConfig.json Documentation

This file contains the core configuration for the Thirst system.

| Field | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| **MaxThirst** | `Float` | `100.0` | The maximum thirst level a player can have. |
| **BaseThirstDepletion** | `Float` | `0.05` | The base amount of thirst lost per tick/interval. |
| **SprintDepletionModifier** | `Float` | `0.1` | Additional thirst depletion multiplier when sprinting. |
| **DehydrationDamage** | `Float` | `2.0` | The amount of damage taken when thirst reaches 0. |
| **DamageIntervalSeconds** | `Float` | `4.0` | How often (in seconds) dehydration damage is applied. |
| **HudPosition** | `String` | `"BelowHotbarRight"` | The position of the Thirst HUD. <br>Values: `AboveHotbarCentered`, `BelowHotbarCentered`, `AboveHotbarLeft`, `AboveHotbarRight`, `BelowHotbarLeft`, `BelowHotbarRight`, `BottomLeft`, `BottomRight` or `Custom:x:y`. |
| **ResetThirstOnDeath** | `Boolean` | `true` | If true, thirst is reset to `RespawnThirstLevel` on death. If false, it keeps the previous value (or 0). |
| **RespawnThirstLevel** | `Float` | `50.0` | The thirst level the player respawns with (if reset is enabled). |
| **EnableThirst** | `Boolean` | `true` | If false, the thirst system (drain, HUD, validation) is completely disabled. |
