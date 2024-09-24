package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.crates.animation.CrateAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public record InventoryClickListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        if (!event.getClickedInventory().getTitle().startsWith("Crate: ")) return;
        if (!plugin.getDatabaseManager().getCrateManager().hasAnimation(player.getUniqueId())) return;
        event.setCancelled(plugin.getDatabaseManager().getCrateManager().hasAnimation(player.getUniqueId()));
    }

}
