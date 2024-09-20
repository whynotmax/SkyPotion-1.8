package eu.skypotion.manager.scoreboard;

import eu.skypotion.PotionPlugin;
import eu.skypotion.mongo.player.model.PotionPlayer;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    Map<UUID, FastBoard> scoreboards;
    Map<UUID, BukkitTask> tasks;
    PotionPlugin plugin;

    public ScoreboardManager(PotionPlugin plugin) {
        this.plugin = plugin;
        this.scoreboards = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    public void createScoreboard(Player player) {
        FastBoard board = new FastBoard(player);
        scoreboards.put(player.getUniqueId(), board);

        PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());

        board.updateTitle("§5§lSkyPotion§8 ┃ §a" + Bukkit.getOnlinePlayers().size() + "§8/§a" + Bukkit.getMaxPlayers());
        board.updateLines(
                "§1",
                "§8» §7Name§8:",
                "§d✎ §8┃ §5" + player.getName(),
                "§2",
                "§8» §7Brieftasche§8:",
                "§e$ §8┃ §6" + potionPlayer.getGeneralStats().getTokens(),
                "§b⚛ §8┃ §3" + potionPlayer.getGeneralStats().getShards(),
                "§3",
                "§8» §7Rang§8:",
                "§a❤ §8┃ §2" + (potionPlayer.getRank() != null ? potionPlayer.getRank().getName() : "§2Spieler"),
                "§4",
                "§8» §7Online§8:",
                "§4✭ §8┃ §c" + Bukkit.getOnlinePlayers().size() + "§8/§c" + Bukkit.getMaxPlayers(),
                "§r"
        );

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (potionPlayer == null) {
                return;
            }
            board.updateTitle("§5§lSkyPotion§8 ┃ §a" + Bukkit.getOnlinePlayers().size() + "§8/§a" + Bukkit.getMaxPlayers());
            board.updateLines(
                    "§1",
                    "§8» §7Name§8:",
                    "§d✎ §8┃ §5" + player.getName(),
                    "§2",
                    "§8» §7Brieftasche§8:",
                    "§e$ §8┃ §6" + potionPlayer.getGeneralStats().getTokens(),
                    "§b⚛ §8┃ §3" + potionPlayer.getGeneralStats().getShards(),
                    "§3",
                    "§8» §7Rang§8:",
                    "§a❤ §8┃ §2" + (potionPlayer.getRank() != null ? potionPlayer.getRank().getName() : "§2Spieler"),
                    "§4",
                    "§8» §7Online§8:",
                    "§4✭ §8┃ §c" + Bukkit.getOnlinePlayers().size() + "§8/§c" + Bukkit.getMaxPlayers(),
                    "§r"
            );
        }, 0, 20);
        tasks.put(player.getUniqueId(), task);
    }

    public void removeScoreboard(Player player) {
        FastBoard board = scoreboards.get(player.getUniqueId());
        if (board != null) {
            board.delete();
            scoreboards.remove(player.getUniqueId());
        }
        BukkitTask task = tasks.get(player.getUniqueId());
        if (task != null) {
            task.cancel();
            tasks.remove(player.getUniqueId());
        }
    }

}
