package eu.skypotion.config.impl;

import eu.skypotion.config.JSONConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class MaintenanceConfig extends JSONConfig {

    boolean enabled;
    List<UUID> whitelist;

    public MaintenanceConfig() {
        File configFile = new File("plugins/SkyPotion/maintenance.yml");
        if (!configFile.exists()) {
            this.enabled = false;
            this.whitelist = new ArrayList<>();
            save();
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/SkyPotion/maintenance.yml"));
        this.enabled = config.getBoolean("enabled");
        this.whitelist = config.getStringList("whitelist").stream().map(UUID::fromString).toList();
    }

    @Override
    public void save() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("enabled", enabled);
        List<String> whitelistString = new ArrayList<>();
        for (UUID uuid : whitelist) {
            whitelistString.add(uuid.toString());
        }
        config.set("whitelist", whitelistString);
        try {
            config.save(new File("plugins/SkyPotion/maintenance.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
