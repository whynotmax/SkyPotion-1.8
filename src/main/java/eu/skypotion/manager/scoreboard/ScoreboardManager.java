package eu.skypotion.manager.scoreboard;

import eu.skypotion.PotionPlugin;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.util.combat.CombatLog;
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
                "§e⛃ §8┃ §6" + potionPlayer.getGeneralStats().getTokens(),
                "§b⚛ §8┃ §3" + potionPlayer.getGeneralStats().getShards(),
                "§3",
                "§8» §7Rang§8:",
                "§a❤ §8┃ §2" + (plugin.getTablistManager().getRankPrefixScoreboard(player) != null ? plugin.getTablistManager().getRankPrefixScoreboard(player) : "§2Spieler"),
                "§4",
                "§8» §7Online§8:",
                "§4✭ §8┃ §c" + Bukkit.getOnlinePlayers().size() + "§8/§c" + Bukkit.getMaxPlayers(),
                "§r"
        );

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            updateScoreboard(player);
        }, 0, 10);
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

    public void updateScoreboard(Player player) {
        FastBoard board = scoreboards.get(player.getUniqueId());
        PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());

        if (potionPlayer == null) {
            return;
        }
        if (CombatLog.isInCombat(player.getUniqueId())) {
            Player opponentPlayer = Bukkit.getPlayer(CombatLog.getOpponent(player.getUniqueId()));
            board.updateTitle("§c" + opponentPlayer.getName());
            board.updateLines(
                    "§1",
                    "§8» §7Leben§8:",
                    "§4❤ §8┃ §c" + (opponentPlayer.getHealth() / 2),
                    "§2",
                    "§8» §7Deine Rüstung§8:",
                    "§r §r §7K§8: " + getPercentage(player.getInventory().getHelmet().getDurability(), player.getInventory().getHelmet().getType().getMaxDurability()),
                    "§r §r §7B§8: " + getPercentage(player.getInventory().getChestplate().getDurability(), player.getInventory().getChestplate().getType().getMaxDurability()),
                    "§r §r §7H§8: " + getPercentage(player.getInventory().getLeggings().getDurability(), player.getInventory().getLeggings().getType().getMaxDurability()),
                    "§r §r §7S§8: " + getPercentage(player.getInventory().getBoots().getDurability(), player.getInventory().getBoots().getType().getMaxDurability()),
                    "§3",
                    "§8» §7Im Kampf§8:",
                    "§c✭ §8┃ §4" + CombatLog.getTimeRemaining(player.getUniqueId()) + "s",
                    ""
            );
            return;
        }
        board.updateTitle("§5§lSkyPotion§8 ┃ §a" + Bukkit.getOnlinePlayers().size() + "§8/§a" + Bukkit.getMaxPlayers());
        board.updateLines(
                "§1",
                "§8» §7Name§8:",
                "§d✎ §8┃ §5" + player.getName(),
                "§2",
                "§8» §7Brieftasche§8:",
                "§e⛃ §8┃ §6" + potionPlayer.getGeneralStats().getTokens(),
                "§b⚛ §8┃ §3" + potionPlayer.getGeneralStats().getShards(),
                "§3",
                "§8» §7Rang§8:",
                "§a❤ §8┃ §2" + (plugin.getTablistManager().getRankPrefixScoreboard(player) != null ? plugin.getTablistManager().getRankPrefixScoreboard(player) : "§7Spieler"),
                "§4",
                "§8» §7Online§8:",
                "§4✭ §8┃ §c" + Bukkit.getOnlinePlayers().size() + "§8/§c" + Bukkit.getMaxPlayers(),
                "§r"
        );
    }

    private String getPercentage(short durability, short maxDurability) {
        final String GOOD = "§a";
        final String ALRIGHT = "§2";
        final String OKAY = "§e";
        final String PROBLEM = "§6";
        final String BAD = "§c";

        double percentage = (double) durability / maxDurability;

        if (percentage >= 0.8) {
            return GOOD + "█████§7 " + String.format("%.2f", percentage * 100) + "%";
        }
        if (percentage >= 0.6) {
            return ALRIGHT + "████§8▒§7 " + String.format("%.2f", percentage * 100) + "%";
        }
        if (percentage >= 0.4) {
            return OKAY + "███§8▒▒§7 " + String.format("%.2f", percentage * 100) + "%";
        }
        if (percentage >= 0.2) {
            return PROBLEM + "██§8▒▒▒§7 " + String.format("%.2f", percentage * 100) + "%";
        }
        return BAD + "█§8▒▒▒▒§7 " + String.format("%.2f", percentage * 100) + "%";
    }

}
