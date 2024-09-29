package eu.skypotion;

import eu.skypotion.casino.CasinoManager;
import eu.skypotion.config.loader.JSONConfigManager;
import eu.skypotion.discord.DiscordBot;
import eu.skypotion.manager.perk.PerkManager;
import eu.skypotion.manager.scoreboard.ScoreboardManager;
import eu.skypotion.manager.tablist.TablistManager;
import eu.skypotion.manager.teleports.TeleportRequestManager;
import eu.skypotion.mongo.DatabaseManager;
import eu.skypotion.runnables.AutoBroadcastRunnable;
import eu.skypotion.runnables.TebexGoalRunnable;
import eu.skypotion.ui.UIManager;
import eu.skypotion.util.combat.CombatLog;
import lombok.Getter;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.internal.HolographicDisplaysAPIProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Field;

@Getter
public final class PotionPlugin extends JavaPlugin {

    CommandMap commandMap;

    HolographicDisplaysAPI hologramApi;

    DiscordBot discordBot;

    TeleportRequestManager teleportRequestManager;
    DatabaseManager databaseManager;
    ScoreboardManager scoreboardManager;
    PerkManager perkManager;
    TablistManager tablistManager;
    UIManager uiManager;
    CasinoManager casinoManager;
    JSONConfigManager configManager;

    TebexGoalRunnable tebexGoalRunnable;

    @Override
    public void onEnable() {

        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            this.commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        this.databaseManager = new DatabaseManager();
        this.scoreboardManager = new ScoreboardManager(this);
        this.perkManager = new PerkManager();
        this.tablistManager = new TablistManager(this);
        this.uiManager = new UIManager(this);
        this.teleportRequestManager = new TeleportRequestManager(this);
        this.casinoManager = new CasinoManager(databaseManager);
        this.configManager = new JSONConfigManager();

        CombatLog.init(this);

        this.hologramApi = HolographicDisplaysAPI.get(this);

        Reflections listenerReflections = new Reflections("eu.skypotion.listener");
        listenerReflections.getSubTypesOf(org.bukkit.event.Listener.class).forEach(listener -> {
            try {
                getServer().getPluginManager().registerEvents(listener.getDeclaredConstructor(PotionPlugin.class)
                        .newInstance(this), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Reflections commandReflections = new Reflections("eu.skypotion.command");
        commandReflections.getSubTypesOf(Command.class).forEach(command -> {
            try {
                commandMap.register("skypotion", command.getDeclaredConstructor(PotionPlugin.class)
                        .newInstance(this));
            } catch (Exception e) {
                try {
                    commandMap.register("skypotion", command.getDeclaredConstructor().newInstance());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach(tablistManager::setRank),
                20*5L, 20*5L);

        Bukkit.getScheduler().runTaskTimer(this, new AutoBroadcastRunnable(this), 20*60*2L, 20*60*2L);

        Bukkit.getScheduler().runTaskTimer(this, (tebexGoalRunnable = new TebexGoalRunnable(this)), 20*5L, 20*30L);

        this.discordBot = new DiscordBot(this.databaseManager, this);

    }

    @Override
    public void onDisable() {
        this.getDatabaseManager().getPotionPlayerManager().saveAll();
        this.configManager.saveAll();
        this.tebexGoalRunnable.getHologram().delete();

        getLogger().log(java.util.logging.Level.INFO, "Plugin disabled.");
    }
}
