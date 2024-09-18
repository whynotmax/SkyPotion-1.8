package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.mongo.player.model.PotionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerQuitListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void handlePlayerQuitEvent(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());
        potionPlayer.updatePlayTime();
        plugin.getDatabaseManager().getPotionPlayerManager().save(potionPlayer);
        plugin.getScoreboardManager().removeScoreboard(player);
    }

}
