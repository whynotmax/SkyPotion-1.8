package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.util.combat.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public record PlayerDeathListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        Location location = event.getEntity().getLocation();
        PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());



        if(!killer.equals(player)) {
            killer.getLocation().getWorld().dropItem(location, ProjectConstants.SHARD_ITEM(1));
            location.getWorld().playSound(location, Sound.SUCCESSFUL_HIT, 100,1);
        }

    }
}
