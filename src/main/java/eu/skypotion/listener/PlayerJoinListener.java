package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.mongo.player.model.PotionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record PlayerJoinListener(PotionPlugin plugin) implements Listener {

   @EventHandler
   public void handlePlayerJoinEvent(final PlayerJoinEvent event) {
       Player player = event.getPlayer();
       PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());
       player.setPlayerListName(potionPlayer.getRank().getPrefix() + player.getName());
   }

}
