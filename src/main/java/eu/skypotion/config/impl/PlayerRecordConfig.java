package eu.skypotion.config.impl;

import eu.skypotion.config.JSONConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

@Getter
@Setter
public class PlayerRecordConfig extends JSONConfig {

    double recordAllTime;

    public PlayerRecordConfig() {
        File configFile = new File("plugins/SkyPotion/playerRecord.yml");
        if (!configFile.exists()) {
            this.recordAllTime = 0;
            save();
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/SkyPotion/playerRecord.yml"));
        this.recordAllTime = config.getDouble("recordAllTime");
    }

    @Override
    public void save() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("recordAllTime", recordAllTime);
        try {
            config.save(new File("plugins/SkyPotion/playerRecord.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
