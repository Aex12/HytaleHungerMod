# Hytale Hunger Mod
Introduces a Hunger system to Hytale, requiring players to manage their food intake to survive and thrive in the game world.

Designed with maximum compatibility and performance in mind.

## Features
- **Hunger Bar**: A visual representation of the player's hunger level displayed on the HUD 
- **Hunger Depletion**: Hunger decreases over time, with the rate influenced by player actions such as stamina usage, or mining blocks.
- **Hunger Saturation**: Some foods will saturate your hunger bar over the 100% value, providing a buffer before hunger starts depleting again.
- **Food Consumption**: Players can consume various food items to restore their hunger levels.
- **Status effects**: When hunger drops below a certain threshold, players experience reduced movement speed and disabled sprint.
- **Starvation Damage**: Players take damage over time when their hunger reaches zero.
- **SFX and VFX**: Includes screen effects and sound effects when starving.
- **Safe areas**: Hunger depletion is paused when players are in designated safe zones (provided by other plugins).
- **Configurable Settings**: Customize hunger depletion rates, starvation interval, starvation damage, hungry threshold, how much stamina usage and mining affects hunger, and food restoration values via a configuration file.

## Actions affecting hunger
- **Basal Metabolic Rate**: Hunger slowly depletes over time, even when the player is idle.
- **Stamina usage**: The more stamina the player uses, the faster their hunger depletes. This includes sprinting, blocking, bashing, and charging attacks.
- **Hitting blocks**: Each time the player hits a block, a small amount of hunger is depleted. Using better tools requires fewer hits to break a block, resulting in less hunger lost per block broken.

## Balance
Currently, the default configuration is pretty forgiving, if you want a more challenging experience, you may want to tweak the configuration file to increase the starvation rate and damage.

With the default configuration, you'll go from max hunger (100) to starvation (0) in about 27 minutes of real-life time. If you're running all the time, it may take around 15 minutes.

## Compatibility
This mod have been designed with compatibility in mind, ensuring it works well alongside other mods, including HUD, food and safe zone mods.
- If you use other HUD mods, make sure to use the [MultipleHUD](https://www.curseforge.com/hytale/mods/multiplehud) mod.
  The other HUD mods that you use must support it too.
- This mod is works in both single-player worlds and multiplayer servers.
- This mod is compatible with most custom food items, as long as they use the Template_Food and don't override the default consume food interaction, which most food mod doesnt.
- Safe Areas defined by other plugins will pause hunger depletion as long as the mod that defines it uses the `Invulnerable` component, which most mods use.
- This mod modifies the `Server.Item.Interactions.Consumables.Consume_Charge_Food_T*.json` assets, so it won't be compatible with other mods that modify that asset.

## Hunger Configuration
This mod will create a configuration file under `mods/es.xcm_HytaleHungerMod/HungerConfig.json` in your world folder after the first run. You can customize the following settings:

| Key                         | Valid value                                                                                                                                         | Default Value         | Description                                                                                                                                                                                             |
|-----------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `StarvationTickRate`        | Number (seconds)                                                                                                                                    | 2.0                   | How often (in seconds) the hunger depletes. Also affect how often you take damage when starving.                                                                                                        |
| `StarvationPerTick`         | Number                                                                                                                                              | 0.125                 | How much hunger is lost every tick (as defined by StarvationTickRate).                                                                                                                                  |
| `StarvationPerBlockHit`     | Number                                                                                                                                              | 0.02                  | How much hunger is lost per block hit. Note: the game allows at most 3 block hits per second                                                                                                            |
| `StarvationStaminaModifier` | Number                                                                                                                                              | 0.175                 | How much stamina affects starvation. The modifier is added to the StarvationPerTick when stamina is below its max value. Currently it interpolates the value depending in how much stamina you've used. |
| `StarvationDamage`          | Number                                                                                                                                              | 5                     | How much damage is taken every tick (as defined by StarvationTickRate) when starving.                                                                                                                   |
| `HungryThreshold`           | Number (0-100)                                                                                                                                      | 20                    | When hunger drops below this value, the player will receive the hungry status effects (slower movement speed and disabled sprint).                                                                      |
| `HudPosition`               | `BottomLeft` <br/> `AboveHotbarCentered` <br/> `AboveHotbarLeft` <br/> `BelowHotbarCentered` <br/> `BelowHotbarLeft` <br/> `Custom:<left>:<bottom>` | `AboveHotbarCentered` | The position where the HUD will be rendered. `Custom:12:12` is equivalent to `BottomLeft`.                                                                                                              |

The max hunger is non-configurable and is set to 100. If you want to check the default values, you can find them in the [HHMConfig](src/main/java/es/xcm/hunger/HHMConfig.java) class. 

You can use the following formula to calculate how long it takes to starve from full hunger to zero:
```
(100/(StarvationPerTick/StarvationTickRate)/60
```
The result is in minutes.

## Food Configuration
You can customize how much hunger is restored by particular food items by modifying the JSON file under `mods/es.xcm_HytaleHungerMod/FoodValuesConfig.json` in your world folder.

| Key                   | Valid value          | Default Value | Description                                                    |
|-----------------------|----------------------|---------------|----------------------------------------------------------------|
| `T1HungerRestoration` | Number (0-100)       | 15            | How much hunger is restored when consuming a Tier 1 food item. |
| `T2HungerRestoration` | Number (0-100)       | 25            | How much hunger is restored when consuming a Tier 2 food item. |
| `T3HungerRestoration` | Number (0-100)       | 40            | How much hunger is restored when consuming a Tier 3 food item. |
| `HungerRestoration`   | Map<String, Number>  | Empty         | Per item hunger restoration values.                            |

Here is an example on how the `HungerRestoration` value should look like:
```json
{
  "HungerRestoration": {
    "Food_Pie_Meat": 60.0,
    "Food_Pie_Apple": 50.0
  }
}
```

## Commands
This mod adds the following commands:

| Command                         | Permission                | Description                                                                                                                                                                                          |
|---------------------------------|---------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/hunger`                       | `hungry.command.base`     | Shows a list of available commands based on user permissions.                                                                                                                                        |
| `/hunger hide`                  | `hungry.hunger.hide`      | Hides the hunger HUD for the player executing the command.                                                                                                                                           |
| `/hunger show`                  | `hungry.hunger.show`      | Shows the hunger HUD for the player executing the command.                                                                                                                                           |
| `/hunger position <position>`   | `hungry.config.position`  | Updates the hunger bar position (server-side). The updated value is saved to the config file. For a list of available positions read the configuration section or run the `/hunger position` command |
| `/hunger set <amount>`          | `hungry.hunger.set.self`  | Sets the hunger of the player executing the command to the specified amount (0-100).                                                                                                                 |
| `/hunger set <player> <amount>` | `hungry.hunger.set.other` | Sets the hunger of the specified player to the specified amount (0-100).                                                                                                                             |

When this mods runs in a single player world, the user get access to the commands `/hunger`, `/hunger hide`, `/hunger show`, `/hunger position <position>`.

## Performance
This mod is optimized for performance in high pop servers, ensuring minimal impact on TPS.
It achieves so by:
- Ensure that hunger updates are distributed evenly across multiple ticks
- Hunger ticks only apply to elegible players (Not dead, not in safe zone, not in creative, spawned in a world)
- Use events whenever possible instead of polling
- Use partial UI updates (Please note some other popular HUD mods such as EyeSpy and RPGLeveling forces full UI updates for all HUD mods)

In high pop servers its recommended that the `StarvationTickRate` is set to at least 2 seconds (default). Setting it to a lower value may result in performance issues.

## Recommended mods
- [Poisonous Raw Meat](https://www.curseforge.com/hytale/mods/poisonous-raw-meat)
- [MultipleHUD](https://www.curseforge.com/hytale/mods/multiplehud)

## Custom Assets (For mod authors)
If you're developing your own food mod and want to define custom hunger restoration values for your food items, you can do so by defining a new asset this mod will process.

Please note this is optional, and if you don't define a custom `FoodValue` asset for your food items, the mod will fallback to the default configuration value based on the food tier interaction used.

This asset was specifically designed to remain compatible with users who don’t use this mod. It can be safely included in your asset pack without affecting those who don’t have this mod installed.

First you should create a new JSON asset under `Server/Item/Hungry/FoodValues`. The name of the JSON file must be the same as the name of your food item asset.

Then, you should define the following structure:
```json
{
  "HungerRestoration": <number>
}
```
Where `<number>` is the amount of hunger that will be restored when consuming that food item.

This new asset support inheritance, so you can create a template asset for each one of your food tiers, and then have your food items inherit from one of them.

To do so, simply create a new template asset, like `Server/Item/Hungry/FoodValues/Template_MyMod_T1.json`, define the `HungerRestoration` value, and then have your food items inherit from that template by specifying the parent:
```json
{
  "Parent": "Template_MyMod_T1"
}
```

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
- @jezebelhunter at discord: For helping me debug issues with the multiplayer functionality.
- To all the helpful people in both Hytale and HytaleModding discords

This project could not have been possible without the shared effort of the Hytale modding community!

## Open source
Licensed under the [GNU Affero General Public License v3.0](https://www.gnu.org/licenses/agpl-3.0.txt)