package mx.jume.aquahunger.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import mx.jume.aquahunger.AquaThirstHunger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HHMExternalFoodsConfig {
    private static final Logger LOGGER = Logger.getLogger("AquaThirstHunger");
    private final File configFile;
    private final Gson gson;

    // Map of ItemID -> Values
    private Map<String, ExternalFoodEntry> entries = new HashMap<>();

    public static class ExternalFoodEntry {
        public float hungerRestoration;
        public float maxHungerSaturation;
        public float thirstRestoration; // New field

        public ExternalFoodEntry() {
        } // Gson

        public ExternalFoodEntry(float hungerRestoration, float maxHungerSaturation, float thirstRestoration) {
            this.hungerRestoration = hungerRestoration;
            this.maxHungerSaturation = maxHungerSaturation;
            this.thirstRestoration = thirstRestoration;
        }
    }

    public HHMExternalFoodsConfig(File configDir) {
        this.configFile = new File(configDir, "ExternalFoodsConfig.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        load();
    }

    private void load() {
        if (!configFile.exists()) {
            createDefault();
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            Type type = new TypeToken<Map<String, ExternalFoodEntry>>() {
            }.getType();
            Map<String, ExternalFoodEntry> loaded = gson.fromJson(reader, type);
            if (loaded != null) {
                this.entries = loaded;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load ExternalFoodsConfig.json", e);
        }
    }

    private void createDefault() {
        entries.clear();
        // Add defaults requested by user with Thirst values
        // Soya Sauce context implies salty or liquid? Assume liquid-ish but minimal
        // thirst interaction for food.
        // Sake is a drink -> Higher thirst restoration
        addDefault("Andiechef_Food_Item_Soya", 5f, 100f, -2f);
        addDefault("Andiechef_Food_Nigiri", 25f, 165f, 0f); // CORRECTED from user list (previous was 8)
        addDefault("Andiechef_Food_Onigiri", 15f, 155f, 0f); // CORRECTED from user list (previous was 10)
        addDefault("Andiechef_Food_Rollo", 13f, 125f, 0f); // CORRECTED saturation (previous was 175)
        addDefault("Andiechef_Food_Sake", 2f, 105f, 25f);
        addDefault("Andiechef_Ingredient_SalsaSoya", 8f, 110f, 10f); // CORRECTED: User said thirst 10, hunger 8
        addDefault("Andiechef_Ingredient_Wasabi", 5f, 100f, -15f); // CORRECTED: User said thirst -15, hunger 5
        addDefault("Andiechef_Item_Soya_Fermentada", 11f, 165f, 2f); // CORRECTED: Thirst 2

        // ADDED MISSING ITEMS from User's ExternalFoodsConfig.json content
        addDefault("Andiechef_YakimeshiBeef", 10f, 150f, 10f);
        addDefault("Andiechef_YakimeshiChicken", 11f, 150f, 10f);
        addDefault("Andiechef_YakimeshiFish", 12f, 150f, 10f);
        addDefault("Andiechef_YakimeshiPork", 9f, 150f, 10f);

        save();
    }

    private void addDefault(String id, float restoration, float saturation, float thirst) {
        entries.put(id, new ExternalFoodEntry(restoration, saturation, thirst));
    }

    public void save() {
        try {
            // Ensure directory exists
            if (configFile.getParentFile() != null) {
                configFile.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(configFile)) {
                gson.toJson(entries, writer);
            }
            LOGGER.info("Saved ExternalFoodsConfig.json to " + configFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save ExternalFoodsConfig.json", e);
        }
    }

    /**
     * Resolves food values for a given item ID.
     * Priority: Exact match -> Glob match (*)
     * 
     * @return Entry if found, null otherwise.
     */
    public ExternalFoodEntry resolve(String itemId) {
        if (itemId == null)
            return null;

        // 1. Exact Match
        if (entries.containsKey(itemId)) {
            return entries.get(itemId);
        }

        // 2. Glob Match (Simple '*' suffix support as per request)
        // Optimization: Iterating map is linear, but config shouldn't be huge.
        for (Map.Entry<String, ExternalFoodEntry> entry : entries.entrySet()) {
            String key = entry.getKey();
            if (key.endsWith("*")) {
                String prefix = key.substring(0, key.length() - 1);
                if (itemId.startsWith(prefix)) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }
}
