package es.xcm.hunger.assets;

import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;

public class HungryAssetRegistryLoader {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void registerAssets () {
        AssetRegistry.register(
                ((HytaleAssetStore.Builder) ((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(
                                FoodValue.class, new DefaultAssetMap()
                        )
                        .setPath("Item/Hungry/FoodValues"))
                        .setCodec(FoodValue.CODEC))
                        .setKeyFunction((item) -> ((FoodValue) item).getId()))
                        .build()
        );
    }
}
