package eu.skypotion.crates.animation;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.crates.model.Crate;
import eu.skypotion.crates.model.item.CrateItem;
import eu.skypotion.util.NumberUtil;
import eu.skypotion.util.RandomUtil;
import eu.skypotion.util.builder.ItemBuilder;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CrateAnimation {

    final PotionPlugin plugin;

    final Crate crate;
    int currentTick;
    int spinSpeed;
    BukkitTask task;

    final Inventory inventory;


    public CrateAnimation(PotionPlugin plugin, Crate crate) {
        this.plugin = plugin;
        this.crate = crate;
        this.currentTick = 0;
        this.spinSpeed = 1;

        this.inventory = Bukkit.createInventory(null, 27, replaceAllColorCodes(this.crate.getDisplayName()));
    }

    private void setupInventory() {
        this.inventory.clear();
        for (int i = 0; i < 27; i++) {
            this.inventory.setItem(i, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));
        }

        this.inventory.setItem(2, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 14).withName("§r "));
        this.inventory.setItem(3, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 4).withName("§r "));
        this.inventory.setItem(4, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 5).withName("§a⬇ Dein Gewinn ⬇"));
        this.inventory.setItem(5, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 4).withName("§r "));
        this.inventory.setItem(6, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 14).withName("§r "));

        for (int i = 9; i < 18; i++) {
            this.inventory.setItem(i, ItemBuilder.AIR);
        }

        this.inventory.setItem(20, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 14).withName("§r "));
        this.inventory.setItem(21, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 4).withName("§r "));
        this.inventory.setItem(22, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 5).withName("§a⬆ Dein Gewinn ⬆"));
        this.inventory.setItem(23, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 4).withName("§r "));
        this.inventory.setItem(24, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 14).withName("§r "));

    }

    public void start(Player player) {
        this.setupInventory();
        player.openInventory(this.inventory);
        this.task = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> this.run(player), 1, 1);
    }

    int i = 0;

    public void run(Player player) {

        if (currentTick < spinSpeed) {
            currentTick++;
            return;
        }

        this.inventory.setItem(9, this.inventory.getItem(10) != null ? this.inventory.getItem(10) : ItemBuilder.AIR);
        this.inventory.setItem(10, this.inventory.getItem(11) != null ? this.inventory.getItem(11) : ItemBuilder.AIR);
        this.inventory.setItem(11, this.inventory.getItem(12) != null ? this.inventory.getItem(12) : ItemBuilder.AIR);
        this.inventory.setItem(12, this.inventory.getItem(13) != null ? this.inventory.getItem(13) : ItemBuilder.AIR);
        this.inventory.setItem(13, this.inventory.getItem(14) != null ? this.inventory.getItem(14) : ItemBuilder.AIR);
        this.inventory.setItem(14, this.inventory.getItem(15) != null ? this.inventory.getItem(15) : ItemBuilder.AIR);
        this.inventory.setItem(15, this.inventory.getItem(16) != null ? this.inventory.getItem(16) : ItemBuilder.AIR);
        this.inventory.setItem(16, this.inventory.getItem(17) != null ? this.inventory.getItem(17) : getNextSpinItemWithChance().getItemStack());

        if (i >= 30) {
            i = 0;
            this.spinSpeed += 2;
        }

        if (this.spinSpeed >= 20) {
            this.task.cancel();

            CrateItem item = getByItemStack(this.inventory.getItem(13));

            if (item.getBroadcastMessage() != null) {
                String[] message = item.getBroadcastMessage().split("\n");
                for (String s : message) {
                    Bukkit.broadcastMessage(ProjectConstants.PREFIX + s.replaceAll("%player%", player.getName())
                            .replaceAll("%item%", item.getItemStack().getItemMeta().hasDisplayName() ?
                                    item.getItemStack().getItemMeta().getDisplayName() :
                                    item.getItemStack().getType().toString())
                            .replaceAll("%crate%", this.crate.getDisplayName())
                            .replaceAll("%amount%", String.valueOf(item.getItemStack().getAmount()))
                            .replaceAll("%chance%", String.valueOf(item.getChance()))
                    );
                }
            }
            boolean giveItem = true;
            if (item.getTokensToGive() > 0) {
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast §6" + NumberUtil.formatWithDecimalPlaces(item.getTokensToGive(), 0) + " §6Token§8(§6s§8) §7erhalten§8!");
                giveItem = false;
            }
            if (item.getCommand() != null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), item.getCommand().replaceAll("%player%", player.getName()));
                giveItem = false;
            }
            if (giveItem) {
                player.getInventory().addItem(item.getItemStack());
            }
            if (item.getChance() <= 5) {
                Bukkit.broadcastMessage(ProjectConstants.PREFIX + "§7Der Spieler §6" + player.getName() + " §7hat §6" + (item.getItemStack().getItemMeta() != null && item.getItemStack().getItemMeta().hasDisplayName() ? item.getItemStack().getItemMeta().getDisplayName() : item.getItemStack().getType().toString()) + " §7aus einer §r" + this.crate.getDisplayName() + " §7gezogen§8! (§7Chance§8: §e" + NumberUtil.formatWithDecimalPlaces(item.getChance(), 2) + "§6%§8)");
            }
        }
    }

    private String replaceAllColorCodes(String s) {
        return s.replaceAll("§+[a-z0-9]", "");
    }

    private CrateItem getNextSpinItemWithChance() {
        return this.crate.getItems().stream().filter(item -> RandomUtil.chance(item.getChance())).findFirst().orElse(null);
    }

    private CrateItem getByItemStack(ItemStack itemStack) {
        return this.crate.getItems().stream().filter(item -> item.getItemStack().isSimilar(itemStack)).findFirst().orElse(null);
    }

}
