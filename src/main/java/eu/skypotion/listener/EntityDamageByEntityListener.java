package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.util.combat.CombatLog;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public record EntityDamageByEntityListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player damaged) {
            if (damager.equals(damaged)) {
                event.setCancelled(true);
                damager.sendMessage(ProjectConstants.PREFIX + "§cDu kannst dir selbst keinen Schaden zufügen.");
                return;
            }
            CombatLog.addToCombatLog(damager.getUniqueId(), damaged.getUniqueId());
            return;
        }
        if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player damager && event.getEntity() instanceof Player damaged) {
            if (damager.equals(damaged)) {
                event.setCancelled(true);
                damager.sendMessage(ProjectConstants.PREFIX + "§cDu kannst dir selbst keinen Schaden zufügen.");
                return;
            }
            CombatLog.addToCombatLog(damager.getUniqueId(), damaged.getUniqueId());
        }
    }

}
