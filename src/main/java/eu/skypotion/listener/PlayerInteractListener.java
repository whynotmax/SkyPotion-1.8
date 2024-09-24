package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.crates.animation.CrateAnimation;
import eu.skypotion.crates.model.Crate;
import eu.skypotion.inventory.CratePreviewInventory;
import eu.skypotion.mongo.codec.ItemStackCodec;
import eu.skypotion.util.DateUtil;
import eu.skypotion.util.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public record PlayerInteractListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if (itemStack == null) return;
        if (itemStack.getType() == Material.AIR) return;
        if (!itemStack.hasItemMeta()) return;
        if (!itemStack.getItemMeta().hasDisplayName()) return;
        String displayName = itemStack.getItemMeta().getDisplayName();
        Crate crate = plugin.getDatabaseManager().getCrateManager().getByDisplayName(displayName);
        if (crate == null) return;
        event.setCancelled(true);
        if (event.getAction().name().contains("RIGHT_CLICK_")) {
            if (!crate.isEnabled()) {
                player.sendMessage(ProjectConstants.PREFIX + "§cDiese Crate ist deaktiviert.");
                return;
            }
            if (itemStack.getAmount() > 1) {
                itemStack = itemStack.clone();
                itemStack.setAmount(itemStack.getAmount() - 1);
                player.setItemInHand(itemStack);
            } else {
                player.setItemInHand(ItemBuilder.AIR);
            }
            //TODO: Open confirm menu
            CrateAnimation animation = new CrateAnimation(plugin, crate);
            plugin.getDatabaseManager().getCrateManager().startAnimation(player.getUniqueId(), animation);
            animation.start(player);
            return;
        }
        if (event.getAction().name().contains("LEFT_CLICK_")) {
            if (player.isSneaking()) {
                player.sendMessage("§8§m------------------------------------§r");
                player.sendMessage(ProjectConstants.PREFIX + "§7Crate§8: §r" + crate.getDisplayName());
                player.sendMessage("§r");
                player.sendMessage(ProjectConstants.PREFIX + "§7Erstellt von§8: §c" + crate.getCreatedBy());
                player.sendMessage(ProjectConstants.PREFIX + "§7Erstellt am§8: §c" + DateUtil.formatDateAndTime(crate.getTimeCreated()));
                player.sendMessage(ProjectConstants.PREFIX + "§7Zuletzt bearbeitet§8: §c" + DateUtil.formatDateAndTime(crate.getLastUpdated()));
                player.sendMessage(ProjectConstants.PREFIX + "§7Aktiviert§8: §r" + (crate.isEnabled() ? "§aJa" : "§cNein"));
                player.sendMessage(ProjectConstants.PREFIX + "§7Sammlung§8: §c" + crate.getCollection());
                player.sendMessage(ProjectConstants.PREFIX + "§7Items§8: §c" + crate.getItems().size());
                player.sendMessage("§8§m------------------------------------§r");
                return;
            }
            CratePreviewInventory inventory = new CratePreviewInventory(plugin, crate);
            plugin.getUiManager().open(player, inventory);
        }
    }

}
