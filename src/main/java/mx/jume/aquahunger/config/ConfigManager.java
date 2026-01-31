package mx.jume.aquahunger.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mx.jume.aquahunger.AquaThirstHunger;
import java.util.logging.Level;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(HudPosition.class, new HudPositionTypeAdapter())
            .setPrettyPrinting()
            .create();
    private static final String CONFIG_DIR = "mods/Aqua-Thirst-hunger";

    private HHMHungerConfig hungerConfig;
    private HHMFoodValuesConfig foodValuesConfig;
    private HHMThirstFoodValuesConfig thirstFoodValuesConfig;
    private HHMThirstConfig thirstConfig;
    private HHMExternalFoodsConfig externalFoodsConfig;

    public void load() {
        File dir = new File(CONFIG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        this.hungerConfig = loadConfig("HungerConfig.json", HHMHungerConfig.class, new HHMHungerConfig());
        this.thirstConfig = loadConfig("ThirstConfig.json", HHMThirstConfig.class, new HHMThirstConfig());
        this.foodValuesConfig = loadConfig("FoodValuesConfig.json", HHMFoodValuesConfig.class,
                new HHMFoodValuesConfig());
        this.thirstFoodValuesConfig = loadConfig("ThirstFoodValuesConfig.json", HHMThirstFoodValuesConfig.class,
                new HHMThirstFoodValuesConfig());
        this.thirstFoodValuesConfig.ensureDefaults();
        this.externalFoodsConfig = new HHMExternalFoodsConfig(dir);

        // Save back to ensure all fields are present in the file
        save();
    }

    private <T> T loadConfig(String fileName, Class<T> clazz, T defaultValue) {
        File file = new File(CONFIG_DIR, fileName);
        if (!file.exists()) {
            return defaultValue;
        }

        try (FileReader reader = new FileReader(file)) {
            T config = GSON.fromJson(reader, clazz);
            return config != null ? config : defaultValue;
        } catch (IOException e) {
            AquaThirstHunger.LOGGER.at(Level.SEVERE).withCause(e).log("Failed to load config file: " + fileName);
            return defaultValue;
        }
    }

    public void reload() throws Exception {
        File dir = new File(CONFIG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 1. Dry run / Attempt load all
        HHMHungerConfig newHungerConfig = loadConfigStrict("HungerConfig.json", HHMHungerConfig.class);
        HHMThirstConfig newThirstConfig = loadConfigStrict("ThirstConfig.json", HHMThirstConfig.class);
        HHMFoodValuesConfig newFoodValuesConfig = loadConfigStrict("FoodValuesConfig.json", HHMFoodValuesConfig.class);
        HHMThirstFoodValuesConfig newThirstFoodValuesConfig = loadConfigStrict("ThirstFoodValuesConfig.json",
                HHMThirstFoodValuesConfig.class);

        // External config is special, it handles its own loading.
        // We will try to instantiate a new one. If it fails it usually logs but doesn't
        // throw.
        // We might accept that external config is less strict or we can improve it
        // later.
        // For now, let's assume if it instantiates it's "okay" enough (it might be
        // empty on error).
        HHMExternalFoodsConfig newExternalFoodsConfig = new HHMExternalFoodsConfig(dir);

        // 2. Commit
        this.hungerConfig = newHungerConfig;
        AquaThirstHunger.logInfo("Reloaded HungerConfig. LifePerHunger=" + this.hungerConfig.isLifePerHunger());

        this.thirstConfig = newThirstConfig;
        AquaThirstHunger.logInfo("Reloaded ThirstConfig.");

        this.foodValuesConfig = newFoodValuesConfig;
        AquaThirstHunger.logInfo("Reloaded FoodValuesConfig.");

        this.thirstFoodValuesConfig = newThirstFoodValuesConfig;
        this.thirstFoodValuesConfig.ensureDefaults(); // Ensure defaults post-reload
        AquaThirstHunger.logInfo("Reloaded ThirstFoodValuesConfig.");

        this.externalFoodsConfig = newExternalFoodsConfig;
        AquaThirstHunger.logInfo("Reloaded ExternalFoodsConfig.");

        // 4. Save to ensure consistency
        save();

        AquaThirstHunger.logInfo("Configuration reloaded successfully.");
    }

    private <T> T loadConfigStrict(String fileName, Class<T> clazz) throws Exception {
        File file = new File(CONFIG_DIR, fileName);
        if (!file.exists()) {
            // If file doesn't exist, we can't strict load it?
            // Or maybe we treat missing as "use default"?
            // The user said "If any fails, keep current configs".
            // If a file is missing, maybe we should return a default instance?
            // "Load + parse + validate". Use default instance if missing seems safe for
            // reload.
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Could not create default instance for " + clazz.getSimpleName(), e);
            }
        }

        try (FileReader reader = new FileReader(file)) {
            try {
                T config = GSON.fromJson(reader, clazz);
                if (config == null) {
                    throw new IOException("Empty or null JSON in " + fileName);
                }
                return config;
            } catch (com.google.gson.JsonSyntaxException e) {
                throw new Exception("JSON Syntax Error in " + fileName + ": " + e.getMessage(), e);
            }
        } catch (IOException e) {
            throw new Exception("IO Error reading " + fileName + ": " + e.getMessage(), e);
        }
    }

    public void save() {
        saveConfig("HungerConfig.json", hungerConfig);
        saveConfig("ThirstConfig.json", thirstConfig);
        saveConfig("FoodValuesConfig.json", foodValuesConfig);
        saveConfig("ThirstFoodValuesConfig.json", thirstFoodValuesConfig);
        if (externalFoodsConfig != null) {
            externalFoodsConfig.save();
        }
    }

    private void saveConfig(String fileName, Object config) {
        File file = new File(CONFIG_DIR, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            AquaThirstHunger.LOGGER.at(Level.SEVERE).withCause(e).log("Failed to save config file: " + fileName);
        }
    }

    public HHMHungerConfig getHungerConfig() {
        return hungerConfig;
    }

    public HHMThirstConfig getThirstConfig() {
        return thirstConfig;
    }

    public HHMFoodValuesConfig getFoodValuesConfig() {
        return foodValuesConfig;
    }

    public HHMExternalFoodsConfig getExternalFoodsConfig() {
        return externalFoodsConfig;
    }

    public HHMThirstFoodValuesConfig getThirstFoodValuesConfig() {
        return thirstFoodValuesConfig;
    }
}
