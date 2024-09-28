package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public record PlayerDeathListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        Location location = event.getEntity().getLocation();

        if(!killer.equals(player)) {
            killer.getLocation().getWorld().dropItem(location, ProjectConstants.SHARD_ITEM(1));
            location.getWorld().playSound(location, Sound.SUCCESSFUL_HIT, 100,1);
        }

    }
}
