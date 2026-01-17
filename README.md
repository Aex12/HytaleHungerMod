# Hytale Hunger Mod
Introduces a Hunger system to Hytale, requiring players to manage their food intake to survive and thrive in the game world.

## Features
- **Hunger Bar**: A visual representation of the player's hunger level displayed on the HUD 
- **Hunger Depletion**: Hunger decreases over time, with the rate influenced by player stamina usage.
- **Food Consumption**: Players can consume various food items to restore their hunger levels.
- **Status effects**: When hunger drops below a certain threshold, players experience reduced movement speed and disabled sprint.
- **Starvation Damage**: Players take damage over time when their hunger reaches zero.
- **SFX and VFX**: Includes screen effects and sound effects when starving.
- **Configurable Settings**: Customize hunger depletion rates, starvation interval, starvation damage, hungry threshold, and food restoration values via a configuration file.

## Balance
Currently, the default configuration is pretty forgiving, if you want a more challenging experience, you may want to tweak the configuration file to increase the starvation rate and damage.

With the default configuration, you'll go from max hunger (100) to starvation (0) in about 27 minutes of real-life time. If you're running all the time, it may take around 15 minutes.

## Compatibility
- If you use other HUD mods, make sure to use the [MultipleHUD](https://www.curseforge.com/hytale/mods/multiplehud) mod.
  The other HUD mods that you use must support it too.
- This mod is compatible with both single-player and multiplayer servers.
- This mod is compatible with most custom food items, as long as they use the standard food consume interaction.
- This mod modifies the `Server.Item.Interactions.Consumables.Consume_Charge_Food_T*.json` assets, so it won't be compatible with other mods that modify that asset.

## Configuration
This mod will create a configuration file under `mods/es.xcm_HytaleHungerMod/HungerConfig.json` after the first run. You can customize the following settings:
- `StarvationTickRate`: How often (in seconds) the hunger depletes. Also affect how often you take damage when starving.
- `StarvationPerTick`: How much hunger is lost every tick (as defined by StarvationTickRate).
- `StarvationStaminaModifier`: How much stamina affects starvation. The modifier is added to the StarvationPerTick when stamina is below its max value. Currently it interpolates the value depending in how much stamina you've used.
- `StarvationDamage`: How much damage is taken every tick (as defined by StarvationTickRate) when starving.
- `HungryThreshold`: When hunger drops below this value, the player will receive the hungry status effects (slower movement speed and disabled sprint).
- `InteractionFeedT1Amount`: How much hunger is restored when consuming a Tier 1 food item.
- `InteractionFeedT2Amount`: How much hunger is restored when consuming a Tier 2 food item.
- `InteractionFeedT3Amount`: How much hunger is restored when consuming a Tier 3 food item.

The max hunger is non-configurable and is set to 100. If you want to check the default values, you can find them in the [HHMConfig](src/main/java/es/xcm/hunger/HHMConfig.java) class. 

You can use the following formula to calculate how long it takes to starve from full hunger to zero:
```
(100/(StarvationPerTick/StarvationTickRate)/60
```
The result is in minutes.
## Special thanks
- [trouble-dev](https://github.com/trouble-dev): For his [UI guides](https://www.youtube.com/watch?v=cha7YFULwxY) and [noels-whitelist-manager](https://github.com/trouble-dev/noels-whitelist-manager) plugin, which I used as a reference for updating UI elements.
- [Darkhax](https://github.com/Darkhax): For his [Spellbook](https://github.com/Hytale-Mods/Spellbook) mod that I used as reference for creating new interactions
- [oskarscot](https://github.com/oskarscot): For his [Hytale ECS Basics guide](https://hytalemodding.dev/en/docs/guides/ecs/hytale-ecs)
- [Buuz135](https://github.com/Buuz135): For his [MultipleHUD](https://github.com/Buuz135/MHUD) mod, which I rely on for making this mod compatible with other HUD mods.
- [ItsNeil17](https://github.com/ItsNeil17): For his numerous contributions to the [HytaleModding](https://hytalemodding.dev/) website, such as the [creating events](https://hytalemodding.dev/en/docs/guides/plugin/creating-events), [creating commands](https://hytalemodding.dev/en/docs/guides/plugin/creating-commands) and [Store persistent data to a Player](https://discord.com/channels/1440173445039132724/1461088566229864449) guides.
- [underscore95](https://github.com/underscore95): For his contributions to the HytaleModding website, such as the [Custom UI guide](https://hytalemodding.dev/en/docs/guides/plugin/ui)
- [Elliesaur](https://github.com/Elliesaur): For her contributions to the HytaleModding guides, such as the [creating events](https://hytalemodding.dev/en/docs/guides/plugin/creating-events) guide.
- [OwnerAli](https://github.com/OwnerAli): For his [CODECs and Custom Config](https://www.youtube.com/watch?v=WZCjmIFYLU8&t=65s) guide.
- bird3681: For his [How to use death event](https://discord.com/channels/1440173445039132724/1460714920361332789) guide on the HytaleModding discord.
- [HytaleModding](https://hytalemodding.dev/): For providing valuable resources for Hytale modding.
- [Santoniche](https://opengameart.org/users/santoniche): For the [chicken leg icon](https://opengameart.org/content/chicken-0) used in the HUD.
- To all the helpful people in both Hytale and HytaleModding discords

This project could not have been possible without the shared effort of the Hytale modding community!