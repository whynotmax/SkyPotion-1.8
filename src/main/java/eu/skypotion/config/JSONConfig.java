package eu.skypotion.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public abstract class JSONConfig {

    Map<String, Object> values;

    public JSONConfig(Map<String, Object> values) {
        this.values = values;
    }

    public abstract void onLoad();

    public abstract void onSave();

    public abstract void defaults();

    public void set(String path, Object value) {
        values.put(path, value);
    }

    public Object get(String path) {
        return values.get(path);
    }

    public boolean contains(String path) {
        return values.containsKey(path);
    }

    public void remove(String path) {
        values.remove(path);
    }

}
