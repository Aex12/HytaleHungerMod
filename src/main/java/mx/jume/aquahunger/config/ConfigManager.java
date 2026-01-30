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
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_DIR = "mods/Aqua-Thirst-hunger";

    private HHMHungerConfig hungerConfig;
    private HHMFoodValuesConfig foodValuesConfig;
    private HHMExternalFoodsConfig externalFoodsConfig;

    public void load() {
        File dir = new File(CONFIG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        this.hungerConfig = loadConfig("HungerConfig.json", HHMHungerConfig.class, new HHMHungerConfig());
        this.foodValuesConfig = loadConfig("FoodValuesConfig.json", HHMFoodValuesConfig.class,
                new HHMFoodValuesConfig());
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

    public void save() {
        saveConfig("HungerConfig.json", hungerConfig);
        saveConfig("FoodValuesConfig.json", foodValuesConfig);
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

    public HHMFoodValuesConfig getFoodValuesConfig() {
        return foodValuesConfig;
    }

    public HHMExternalFoodsConfig getExternalFoodsConfig() {
        return externalFoodsConfig;
    }
}
