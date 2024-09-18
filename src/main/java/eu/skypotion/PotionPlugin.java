package eu.skypotion;

import eu.skypotion.mongo.DatabaseManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PotionPlugin extends JavaPlugin {

    DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        databaseManager = new DatabaseManager();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
