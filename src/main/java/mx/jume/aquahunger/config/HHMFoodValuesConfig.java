package mx.jume.aquahunger.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
import com.hypixel.hytale.codec.codecs.map.MapCodec;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class HHMFoodValuesConfig {
        public static final BuilderCodec<HHMFoodValuesConfig> CODEC = BuilderCodec
                        .builder(HHMFoodValuesConfig.class, HHMFoodValuesConfig::new)
                        // migrated values
                        .append(new KeyedCodec<>("T1HungerRestoration", Codec.FLOAT),
                                        ((config, value) -> config.tierHungerRestoration.put(ItemTier.Common, value)),
                                        (c) -> null)
                        .add()
                        .append(new KeyedCodec<>("T2HungerRestoration", Codec.FLOAT),
                                        ((config, value) -> config.tierHungerRestoration.put(ItemTier.Uncommon, value)),
                                        (c) -> null)
                        .add()
                        .append(new KeyedCodec<>("T3HungerRestoration", Codec.FLOAT),
                                        ((config, value) -> config.tierHungerRestoration.put(ItemTier.Rare, value)),
                                        (c) -> null)
                        .add()
                        .append(new KeyedCodec<>("T1MaxHungerSaturation", Codec.FLOAT),
                                        ((config, value) -> config.tierMaxHungerSaturation.put(ItemTier.Common, value)),
                                        (c) -> null)
                        .add()
                        .append(new KeyedCodec<>("T2MaxHungerSaturation", Codec.FLOAT),
                                        ((config, value) -> config.tierMaxHungerSaturation.put(ItemTier.Uncommon,
                                                        value)),
                                        (c) -> null)
                        .add()
                        .append(new KeyedCodec<>("T3MaxHungerSaturation", Codec.FLOAT),
                                        ((config, value) -> config.tierMaxHungerSaturation.put(ItemTier.Rare, value)),
                                        (c) -> null)
                        .add()
                        .append(new KeyedCodec<>("HungerRestoration", new MapCodec<>(Codec.FLOAT, HashMap::new)),
                                        ((config, value) -> config.itemHungerRestoration = value),
                                        (c) -> null)
                        .add()
                        .append(new KeyedCodec<>("MaxHungerSaturation", new MapCodec<>(Codec.FLOAT, HashMap::new)),
                                        ((config, value) -> config.itemMaxHungerSaturation = value),
                                        (c) -> null)
                        .add()

                        // current values
                        .append(new KeyedCodec<>("IgnoreInteractionValues", Codec.BOOLEAN),
                                        ((config, value) -> config.ignoreInteractionValues = value),
                                        (c) -> c.ignoreInteractionValues)
                        .add()
                        .append(new KeyedCodec<>("IgnoreCustomAssetValues", Codec.BOOLEAN),
                                        ((config, value) -> config.ignoreCustomAssetValues = value),
                                        (c) -> c.ignoreCustomAssetValues)
                        .add()
                        .append(new KeyedCodec<>("TierHungerRestoration",
                                        new EnumMapCodec<>(ItemTier.class, Codec.FLOAT)),
                                        ((config, value) -> config.tierHungerRestoration.putAll(value)),
                                        (c) -> c.tierHungerRestoration)
                        .add()
                        .append(new KeyedCodec<>("TierMaxHungerSaturation",
                                        new EnumMapCodec<>(ItemTier.class, Codec.FLOAT)),
                                        ((config, value) -> config.tierMaxHungerSaturation.putAll(value)),
                                        (c) -> c.tierMaxHungerSaturation)
                        .add()
                        .append(new KeyedCodec<>("ItemHungerRestoration", new MapCodec<>(Codec.FLOAT, HashMap::new)),
                                        ((config, value) -> config.itemHungerRestoration = value),
                                        (c) -> c.itemHungerRestoration)
                        .add()
                        .append(new KeyedCodec<>("ItemMaxHungerSaturation", new MapCodec<>(Codec.FLOAT, HashMap::new)),
                                        ((config, value) -> config.itemMaxHungerSaturation = value),
                                        (c) -> c.itemMaxHungerSaturation)
                        .add()
                        .build();

        private boolean ignoreInteractionValues = false;
        private boolean ignoreCustomAssetValues = false;
        private final Map<ItemTier, Float> tierHungerRestoration = new EnumMap<>(ItemTier.class);
        private final Map<ItemTier, Float> tierMaxHungerSaturation = new EnumMap<>(ItemTier.class);
        private Map<String, Float> itemHungerRestoration = new HashMap<>();
        private Map<String, Float> itemMaxHungerSaturation = new HashMap<>();

        public HHMFoodValuesConfig() {
                tierHungerRestoration.put(ItemTier.Common, 3.75f);
                tierHungerRestoration.put(ItemTier.Uncommon, 6.25f);
                tierHungerRestoration.put(ItemTier.Rare, 11.25f);
                tierHungerRestoration.put(ItemTier.Epic, 17.5f);
                tierHungerRestoration.put(ItemTier.Legendary, 25.0f);
                tierHungerRestoration.put(ItemTier.Mythic, 35.0f);
                tierHungerRestoration.put(ItemTier.Unique, 47.5f);

                tierMaxHungerSaturation.put(ItemTier.Common, 0.0f);
                tierMaxHungerSaturation.put(ItemTier.Uncommon, 3.75f);
                tierMaxHungerSaturation.put(ItemTier.Rare, 7.5f);
                tierMaxHungerSaturation.put(ItemTier.Epic, 11.25f);
                tierMaxHungerSaturation.put(ItemTier.Legendary, 16.25f);
                tierMaxHungerSaturation.put(ItemTier.Mythic, 20.0f);
                tierMaxHungerSaturation.put(ItemTier.Unique, 25.0f);
        }

        public Float getItemHungerRestoration(@NonNullDecl String itemId) {
                return itemHungerRestoration.get(itemId);
        }

        public Float getItemMaxHungerSaturation(@NonNullDecl String itemId) {
                return itemMaxHungerSaturation.get(itemId);
        }

        public float getTierHungerRestoration(@NonNullDecl ItemTier tier) {
                return tierHungerRestoration.getOrDefault(tier, 0.0f);
        }

        public float getTierMaxHungerSaturation(@NonNullDecl ItemTier tier) {
                return tierMaxHungerSaturation.getOrDefault(tier, 0.0f);
        }

        public boolean isIgnoreInteractionValues() {
                return ignoreInteractionValues;
        }

        public boolean isIgnoreCustomAssetValues() {
                return ignoreCustomAssetValues;
        }
}

