package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.crates.animation.CrateAnimation;
import eu.skypotion.crates.model.Crate;
import eu.skypotion.inventory.CratePreviewInventory;
import eu.skypotion.mongo.codec.ItemStackCodec;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.mongo.player.model.settings.Settings;
import eu.skypotion.ui.accept.AcceptUI;
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
        PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());
        if (event.getAction().name().contains("RIGHT_CLICK_")) {
            if (!crate.isEnabled()) {
                player.sendMessage(ProjectConstants.PREFIX + "§cDiese Crate ist deaktiviert.");
                return;
            }
            if (potionPlayer.getSetting(Settings.CRATE_CONFIRMATION) == 0) {
                AcceptUI acceptUI = new AcceptUI(plugin, "Crate öffnen", "§7Crate §e" + crate.getDisplayName() + " §7öffnen", "§7Möchtest du diese Crate wirklich öffnen?") {
                    @Override
                    public void onAccept(Player player, boolean accepted) {
                        ItemStack itemStack = player.getItemInHand();
                        if (itemStack == null) return;
                        if (itemStack.getType() == Material.AIR) return;
                        if (!itemStack.hasItemMeta()) return;
                        if (!itemStack.getItemMeta().hasDisplayName()) return;
                        String displayName = itemStack.getItemMeta().getDisplayName();
                        Crate crate = plugin.getDatabaseManager().getCrateManager().getByDisplayName(displayName);
                        if (crate == null) return;
                        if (accepted) {
                            if (itemStack.getAmount() > 1) {
                                itemStack = itemStack.clone();
                                itemStack.setAmount(itemStack.getAmount() - 1);
                                player.setItemInHand(itemStack);
                            } else {
                                player.setItemInHand(ItemBuilder.AIR);
                            }
                            player.closeInventory();
                            if (potionPlayer.getSetting(Settings.CRATE_ANIMATION) == 0) {
                                CrateAnimation animation = new CrateAnimation(plugin, crate);
                                plugin.getDatabaseManager().getCrateManager().startAnimation(player.getUniqueId(), animation);
                                animation.start(player);
                                return;
                            }
                            CrateAnimation animation = new CrateAnimation(plugin, crate);
                            animation.finish(player);
                            return;
                        }
                        player.sendMessage(ProjectConstants.PREFIX + "§cDu hast die Aktion abgebrochen.");
                        player.closeInventory();
                    }

                };
                plugin.getUiManager().open(player, acceptUI);
                return;
            }
            CrateAnimation animation = new CrateAnimation(plugin, crate);
            if (itemStack.getAmount() > 1) {
                itemStack = itemStack.clone();
                itemStack.setAmount(itemStack.getAmount() - 1);
                player.setItemInHand(itemStack);
            } else {
                player.setItemInHand(ItemBuilder.AIR);
            }
            if (potionPlayer.getSetting(Settings.CRATE_ANIMATION) == 0) {
                plugin.getDatabaseManager().getCrateManager().startAnimation(player.getUniqueId(), animation);
                animation.start(player);
                return;
            }
            animation.finish(player);
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
