package eu.skypotion.config.impl;

import eu.skypotion.config.JSONConfig;
import eu.skypotion.config.path.JSONConfigPath;

import java.util.Map;

@JSONConfigPath("plugins/SkyPotion/playerRecord.json")
public class PlayerRecordConfig extends JSONConfig {

    double recordAllTime;

    public PlayerRecordConfig(Map<String, Object> values) {
        super(values);
    }

    @Override
    public void onLoad() {
        this.recordAllTime = (double) get("recordAllTime");
    }

    @Override
    public void onSave() {
        set("recordAllTime", recordAllTime);
    }

    @Override
    public void defaults() {
        set("recordAllTime", 0);
        this.recordAllTime = 0;
    }
}
