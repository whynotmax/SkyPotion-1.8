package eu.skypotion.config.loader;

import eu.skypotion.config.JSONConfig;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class JSONConfigManager {

    Map<String, JSONConfig> configs;

    public JSONConfigManager() {
        this.configs = new HashMap<>();

        Reflections reflections = new Reflections("eu.skypotion.config.impl");
        reflections.getSubTypesOf(JSONConfig.class).forEach(config -> {
            try {
                JSONConfig instance = config.getDeclaredConstructor().newInstance();
                configs.put(config.getSimpleName(), instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public <T> T getConfig(Class<T> config) {
        return (T) configs.get(config.getSimpleName());
    }

    public void saveAll() {
        configs.values().forEach(JSONConfig::save);
    }

}
