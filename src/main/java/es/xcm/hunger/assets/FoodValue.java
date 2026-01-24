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
    private String id;

    @Override
    public String getId() {
        return this.id;
    }
    public Float getHungerRestoration () {
        return this.hungerRestoration;
    }

    public static float getHungerRestoration (String itemId, float fallbackValue) {
        HHMFoodValuesConfig config = HytaleHungerMod.get().getFoodValuesConfig();
        // first prefer user config
        Float configValue = config.getFoodHungerRestoration(itemId);
        if (configValue != null) {
            return configValue;
        }
        // then asset (mod author) values
        FoodValue assetValues = ASSET_MAP.get(itemId);
        if (assetValues != null && assetValues.hungerRestoration != null) {
            return assetValues.hungerRestoration;
        }
        // finally the fallback value
        return fallbackValue;
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
            .appendInherited(
                new KeyedCodec<>("HungerRestoration", Codec.FLOAT),
                    ((foodValue, value) -> foodValue.hungerRestoration = value),
                    (foodValue) -> foodValue.hungerRestoration,
                    (foodValue, parent) -> foodValue.hungerRestoration = parent.hungerRestoration).add();

        CODEC = CODEC_BUILDER.build();
    }
}
