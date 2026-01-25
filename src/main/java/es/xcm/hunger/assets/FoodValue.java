package es.xcm.hunger.assets;

import com.hypixel.hytale.assetstore.AssetExtraInfo;
import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
import com.hypixel.hytale.assetstore.codec.AssetCodec;
import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import es.xcm.hunger.HytaleHungerMod;
import es.xcm.hunger.config.HHMFoodValuesConfig;

import java.util.HashMap;
import java.util.Map;

public class FoodValue implements JsonAssetWithMap<String, DefaultAssetMap<String, FoodValue>>  {
    private static final AssetBuilderCodec.Builder<String, FoodValue> CODEC_BUILDER;
    public static final AssetCodec<String, FoodValue> CODEC;
    public static Map<String, FoodValue> ASSET_MAP = new HashMap<>();

    protected AssetExtraInfo.Data data;
    private Float hungerRestoration;
    private Float maxHungerSaturation;
    private String id;

    @Override
    public String getId() {
        return this.id;
    }
    public Float getHungerRestoration () {
        return this.hungerRestoration;
    }
    public Float getMaxHungerSaturation () {
        return this.maxHungerSaturation;
    }

    public static Float getHungerRestoration (String itemId) {
        FoodValue assetValues = ASSET_MAP.get(itemId);
        if (assetValues == null) return null;
        return assetValues.hungerRestoration;
    }
    public static Float getMaxHungerSaturation (String itemId) {
        FoodValue assetValues = ASSET_MAP.get(itemId);
        if (assetValues == null) return null;
        return assetValues.maxHungerSaturation;
    }

    public static void onItemAssetLoad(LoadedAssetsEvent<String, FoodValue, DefaultAssetMap<String, FoodValue>> event) {
        ASSET_MAP = event.getAssetMap().getAssetMap();
    }

    static {
        CODEC_BUILDER = AssetBuilderCodec.builder(
                FoodValue.class,
                FoodValue::new,
                Codec.STRING,
                (foodValue, s) -> foodValue.id = s,
                foodValue -> foodValue.id,
                (asset, data) -> asset.data = data,
                asset -> asset.data
            )
            .appendInherited(new KeyedCodec<>("HungerRestoration", Codec.FLOAT),
                ((foodValue, value) -> foodValue.hungerRestoration = value),
                (foodValue) -> foodValue.hungerRestoration,
                (foodValue, parent) -> foodValue.hungerRestoration = parent.hungerRestoration).add()
            .appendInherited(new KeyedCodec<>("MaxHungerSaturation", Codec.FLOAT),
                ((foodValue, value) -> foodValue.maxHungerSaturation = value),
                (foodValue) -> foodValue.maxHungerSaturation,
                (foodValue, parent) -> foodValue.maxHungerSaturation = parent.maxHungerSaturation).add();

        CODEC = CODEC_BUILDER.build();
    }
}
