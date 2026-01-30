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

        public ExternalFoodEntry() {
        } // Gson

        public ExternalFoodEntry(float hungerRestoration, float maxHungerSaturation) {
            this.hungerRestoration = hungerRestoration;
            this.maxHungerSaturation = maxHungerSaturation;
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
        // Add defaults requested by user
        addDefault("Andiechef_Food_Item_Soya", 5f, 120f);
        addDefault("Andiechef_Food_Nigiri", 8f, 145f);
        addDefault("Andiechef_Food_Onigiri", 10f, 155f);
        addDefault("Andiechef_Food_Rollo", 13f, 175f);
        addDefault("Andiechef_Food_Sake", 2f, 105f);
        addDefault("Andiechef_Ingredient_SalsaSoya", 2f, 110f);
        addDefault("Andiechef_Ingredient_Wasabi", 1f, 100f);
        addDefault("Andiechef_Item_Soya_Fermentada", 11f, 165f);

        save();
    }

    private void addDefault(String id, float restoration, float saturation) {
        entries.put(id, new ExternalFoodEntry(restoration, saturation));
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
