package eu.skypotion.config.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.skypotion.config.JSONConfig;
import eu.skypotion.config.path.JSONConfigPath;
import org.reflections.Reflections;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JSONConfigManager {

    Map<String, JSONConfig> configs;
    Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public JSONConfigManager() {
        this.configs = new HashMap<>();

        Reflections reflections = new Reflections("eu.skypotion.config.impl");
        for (Class<? extends JSONConfig> clazz : reflections.getSubTypesOf(JSONConfig.class)) {
            try {
                JSONConfig config = clazz.getConstructor(Map.class).newInstance(new HashMap<>());
                registerConfig(clazz.getSimpleName(), config);
            } catch (Exception e) {
                throw new RuntimeException("Failed to register config: " + clazz.getSimpleName(), e);
            }
        }
    }

    public JSONConfig getConfig(String name) {
        return configs.get(name);
    }

    public void registerConfig(String name, JSONConfig config) {
        if (!config.getClass().isAnnotationPresent(JSONConfigPath.class)) {
            throw new IllegalArgumentException("JSONConfig must be annotated with @JSONConfigPath");
        }
        JSONConfigPath pathAnnotation = config.getClass().getAnnotation(JSONConfigPath.class);
        if (pathAnnotation != null) {
            String path = pathAnnotation.value();
            try (Reader reader = new FileReader(path)) {
                Map<String, Object> values = gson.fromJson(reader, Map.class);
                config.setValues(values);
                config.onLoad();
            } catch (IOException e) {
                config.defaults();
                saveConfig(name);
            } finally {
                configs.put(name, config);
            }
        } else {
            throw new IllegalArgumentException("JSONConfig must be annotated with @JSONConfigPath");
        }
    }

    public void saveConfig(String name) {
        JSONConfig config = configs.get(name);
        JSONConfigPath pathAnnotation = config.getClass().getAnnotation(JSONConfigPath.class);
        if (pathAnnotation != null) {
            String path = pathAnnotation.value();
            try {
                config.onSave();
                File file = new File(path);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                try (Writer writer = new FileWriter(file)) {
                    gson.toJson(config.getValues(), writer);
                } catch (IOException e) {
                    throw new RuntimeException("1 - Failed to save config to path: " + path, e);
                }
            } catch (IOException e) {
                throw new RuntimeException("2 - Failed to save config to path: " + path, e);
            }
        } else {
            throw new IllegalArgumentException("JSONConfig must be annotated with @JSONConfigPath");
        }
    }

}
