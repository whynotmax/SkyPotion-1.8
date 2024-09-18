package eu.skypotion;

import eu.skypotion.manager.scoreboard.ScoreboardManager;
import eu.skypotion.mongo.DatabaseManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PotionPlugin extends JavaPlugin {

    DatabaseManager databaseManager;
    ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        databaseManager = new DatabaseManager();
        scoreboardManager = new ScoreboardManager(this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
