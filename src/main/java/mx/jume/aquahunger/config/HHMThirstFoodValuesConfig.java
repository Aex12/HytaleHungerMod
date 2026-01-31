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

public class HHMThirstFoodValuesConfig {
    public static final BuilderCodec<HHMThirstFoodValuesConfig> CODEC = BuilderCodec
            .builder(HHMThirstFoodValuesConfig.class, HHMThirstFoodValuesConfig::new)
            .append(new KeyedCodec<>("ResultResourceTypeId", Codec.STRING),
                    ((config, value) -> config.fruitResourceTypeId = value),
                    HHMThirstFoodValuesConfig::getFruitResourceTypeId)
            .add()
            .append(new KeyedCodec<>("FruitMultiplier", Codec.FLOAT),
                    ((config, value) -> config.fruitMultiplier = value),
                    HHMThirstFoodValuesConfig::getFruitMultiplier)
            .add()
            .append(new KeyedCodec<>("TierThirstRestoration",
                    new EnumMapCodec<>(ItemTier.class, Codec.FLOAT)),
                    ((config, value) -> config.tierThirstRestoration.putAll(value)),
                    (c) -> c.tierThirstRestoration)
            .add()
            .append(new KeyedCodec<>("ItemThirstRestoration", new MapCodec<>(Codec.FLOAT, HashMap::new)),
                    ((config, value) -> config.itemThirstRestoration = value),
                    (c) -> c.itemThirstRestoration)
            .add()
            .build();

    private String fruitResourceTypeId = "Fruit";
    private float fruitMultiplier = 5.0f;
    private final Map<ItemTier, Float> tierThirstRestoration = new EnumMap<>(ItemTier.class);
    private Map<String, Float> itemThirstRestoration = new HashMap<>();

    public HHMThirstFoodValuesConfig() {
        tierThirstRestoration.put(ItemTier.Common, 5.5f);
        tierThirstRestoration.put(ItemTier.Uncommon, 6.5f);
        tierThirstRestoration.put(ItemTier.Rare, 7.0f);
        tierThirstRestoration.put(ItemTier.Epic, 7.5f);
        tierThirstRestoration.put(ItemTier.Legendary, 8.0f);
        tierThirstRestoration.put(ItemTier.Mythic, 9.5f);
        tierThirstRestoration.put(ItemTier.Unique, 10.0f);
        itemThirstRestoration.put("AquaThirstHunger_Canteen", 10.0f);
    }

    public String getFruitResourceTypeId() {
        return fruitResourceTypeId;
    }

    public float getFruitMultiplier() {
        return fruitMultiplier;
    }

    public Float getItemThirstRestoration(@NonNullDecl String itemId) {
        return itemThirstRestoration.get(itemId);
    }

    public float getTierThirstRestoration(@NonNullDecl ItemTier tier) {
        return tierThirstRestoration.getOrDefault(tier, 0.0f);
    }

    public void ensureDefaults() {
        itemThirstRestoration.putIfAbsent("AquaThirstHunger_Canteen", 10.0f);
    }
}
