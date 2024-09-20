package eu.skypotion;

import eu.skypotion.manager.perk.PerkManager;
import eu.skypotion.manager.scoreboard.ScoreboardManager;
import eu.skypotion.mongo.DatabaseManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

@Getter
public final class PotionPlugin extends JavaPlugin {

    DatabaseManager databaseManager;
    ScoreboardManager scoreboardManager;
    PerkManager perkManager;


    @Override
    public void onEnable() {
        databaseManager = new DatabaseManager();
        scoreboardManager = new ScoreboardManager(this);

        Reflections listenerReflections = new Reflections("eu.skypotion.listener");
        listenerReflections.getSubTypesOf(org.bukkit.event.Listener.class).forEach(listener -> {
            try {
                getServer().getPluginManager().registerEvents(listener.getDeclaredConstructor(PotionPlugin.class).newInstance(this), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Reflections commandReflections = new Reflections("eu.skypotion.command");
        commandReflections.getSubTypesOf(org.bukkit.command.CommandExecutor.class).forEach(command -> {
            try {
                //TODO: Get command map and register command
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
