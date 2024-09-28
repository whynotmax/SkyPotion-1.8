package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.util.ActionBar;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public record PlayerPickupListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {

        Player player = event.getPlayer();
        Item item = event.getItem();
        PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());


        if(item != null) {
            ItemStack itemStack = item.getItemStack();
            String itemName = itemStack.getItemMeta().getDisplayName();
            boolean isCorrectShardItem = (itemName.contains("amount:") && itemStack.getType().equals(Material.PRISMARINE_SHARD));

            if(isCorrectShardItem) {
                long amount = Long.parseLong(itemName.replaceAll("amount:", ""));
                potionPlayer.getGeneralStats().setShards(potionPlayer.getGeneralStats().getShards() + amount);
                event.setCancelled(true);
                item.remove();
                ActionBar.sendActionBar(player, "§a+" + amount + " Shards");
            }
        }

    }
}
