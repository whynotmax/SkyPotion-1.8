package eu.skypotion.util.combat;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.util.ActionBar;
import eu.skypotion.util.TimeUtil;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class CombatLog {

    private static PotionPlugin plugin;

    public static void init(PotionPlugin plugin) {
        CombatLog.plugin = plugin;
    }

    private final Map<UUID, Long> combatLogTime = new ConcurrentHashMap<>();
    private final Map<UUID, UUID> combatLog = new ConcurrentHashMap<>();
    private final Map<UUID, BukkitTask> tasks = new ConcurrentHashMap<>();

    public void addToCombatLog(UUID attacker, UUID victim) {
        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15);
        combatLogTime.put(attacker, endTime);
        combatLogTime.put(victim, endTime);

        combatLog.put(attacker, victim);
        combatLog.put(victim, attacker);


        tasks.put(attacker, Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Player player = Bukkit.getPlayer(attacker);
            if (player != null) {
                Player attackerPlayer = Bukkit.getPlayer(combatLog.get(attacker));
                if (attackerPlayer == null) return;
                ActionBar.sendActionBar(player, "§7Gegner§8: §c" + attackerPlayer.getName() + " §8┃ §7Verbleibend§8: §c" + TimeUtil.beautifyTime(getTimeRemaining(attacker), TimeUnit.MILLISECONDS));
            }
            if (getTimeRemaining(attacker) == 0) {
                removeFromCombatLog(attacker);
                if (player != null) player.sendMessage(ProjectConstants.PREFIX + "§cDu bist nun nicht mehr im Kampf.");
                tasks.get(attacker).cancel();
            }
        }, 0L, 10L));

        tasks.put(victim, Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Player player = Bukkit.getPlayer(victim);
            if (player != null) {
                Player attackerPlayer = Bukkit.getPlayer(combatLog.get(victim));
                if (attackerPlayer == null) return;
                ActionBar.sendActionBar(player, "§7Gegner§8: §c" + attackerPlayer.getName() + " §8┃ §7Verbleibend§8: §c" + TimeUtil.beautifyTime(getTimeRemaining(victim), TimeUnit.MILLISECONDS));
            }
            if (getTimeRemaining(victim) == 0) {
                removeFromCombatLog(victim);
                if (player != null) player.sendMessage(ProjectConstants.PREFIX + "§cDu bist nun nicht mehr im Kampf.");
            }
        }, 0L, 10L));
    }

    public long getTimeRemaining(UUID player) {
        Long endTime = combatLogTime.get(player);
        if (endTime == null) {
            return 0;
        }
        long timeRemaining = endTime - System.currentTimeMillis();
        return timeRemaining > 0 ? timeRemaining : 0;
    }

    public boolean isInCombat(UUID player) {
        return combatLogTime.containsKey(player);
    }

    public UUID getOpponent(UUID player) {
        return combatLog.get(player);
    }

    public void removeFromCombatLog(UUID player) {
        combatLogTime.remove(player);
        combatLog.remove(player);

        plugin.getScoreboardManager().updateScoreboard(Bukkit.getPlayer(player));

        BukkitTask task = tasks.getOrDefault(player, null);
        if (task != null) {
            task.cancel();
            tasks.remove(player);
        }
    }

}
