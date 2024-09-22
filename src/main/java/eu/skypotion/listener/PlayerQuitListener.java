package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.util.combat.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public record PlayerQuitListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void handlePlayerQuitEvent(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());
        plugin.getScoreboardManager().removeScoreboard(player);

        if (CombatLog.isInCombat(player.getUniqueId())) {
            CombatLog.removeFromCombatLog(player.getUniqueId());
            Bukkit.broadcastMessage(ProjectConstants.PREFIX + "§c" + player.getName() + " hat sich im Kampf ausgeloggt.");
            player.setHealth(0.0D);

            UUID opponent = CombatLog.getOpponent(player.getUniqueId());

            potionPlayer.addDeath();
            potionPlayer.getSeasonStats().addDeath();
            potionPlayer.getSeasonStats().removeElo(15);

            Player opponentPlayer = Bukkit.getPlayer(opponent);
            PotionPlayer opponentPotionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(opponent);

            opponentPotionPlayer.addKill();
            opponentPotionPlayer.getSeasonStats().addKill();
            opponentPotionPlayer.getSeasonStats().addElo(15);

            if (opponentPlayer != null) {
                opponentPlayer.sendMessage(ProjectConstants.PREFIX + "§c" + player.getName() + " hat sich im Kampf ausgeloggt.");
                opponentPlayer.sendMessage(ProjectConstants.PREFIX + "§a+1 §7Kill §8┃ §a+15 Season-Elo");
                opponentPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 1, false, false));
                opponentPlayer.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*5, 1, false, false));
                opponentPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20*3, 1, false, false));
            }
        }
        potionPlayer.updatePlayTime();
        plugin.getDatabaseManager().getPotionPlayerManager().save(potionPlayer);
    }

}
