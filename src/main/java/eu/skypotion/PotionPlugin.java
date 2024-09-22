package eu.skypotion;

import eu.skypotion.manager.perk.PerkManager;
import eu.skypotion.manager.scoreboard.ScoreboardManager;
import eu.skypotion.mongo.DatabaseManager;
import eu.skypotion.util.combat.CombatLog;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Field;

@Getter
public final class PotionPlugin extends JavaPlugin {

    CommandMap commandMap;

    DatabaseManager databaseManager;
    ScoreboardManager scoreboardManager;
    PerkManager perkManager;


    @Override
    public void onEnable() {

        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        databaseManager = new DatabaseManager();
        scoreboardManager = new ScoreboardManager(this);

        CombatLog.init(this);

        Reflections listenerReflections = new Reflections("eu.skypotion.listener");
        listenerReflections.getSubTypesOf(org.bukkit.event.Listener.class).forEach(listener -> {
            try {
                getServer().getPluginManager().registerEvents(listener.getDeclaredConstructor(PotionPlugin.class).newInstance(this), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Reflections commandReflections = new Reflections("eu.skypotion.command");
        commandReflections.getSubTypesOf(Command.class).forEach(command -> {
            try {
                commandMap.register("skypotion", command.getDeclaredConstructor(PotionPlugin.class).newInstance(this));
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
