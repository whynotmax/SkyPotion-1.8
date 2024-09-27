package eu.skypotion.inventory.settings;

import com.avaje.ebean.Page;
import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.mongo.player.model.settings.Settings;
import eu.skypotion.mongo.player.model.settings.category.SettingsCategory;
import eu.skypotion.ui.paged.PagedUI;
import eu.skypotion.ui.size.UISize;
import eu.skypotion.util.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SettingsCategoryInventory extends PagedUI {

    PotionPlayer potionPlayer;
    SettingsCategory category;

    public SettingsCategoryInventory(PotionPlugin plugin, Player player, SettingsCategory category) {
        super(plugin, ProjectConstants.PREFIX + "§cEinstellungen", UISize.SIX_ROWS, 28);
        this.potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());
        this.category = category;

        fill(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 7).withName(" "));
        fillBorders(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));

        for (Settings settings : Settings.VALUES) {
            if (settings.getCategory() != category) continue;
            ItemBuilder categoryItem = ItemBuilder.of(settings.getCategory().getDisplayMaterial());
            categoryItem.withName("§c" + settings.getDisplayName());
            List<String> lore = new ArrayList<>(settings.getDescription());
            lore.add("");
            lore.add("§7Klicke§8,§7 um diese Einstellung zu ändern§8.");
            lore.add("§8» §7Aktueller Wert: §c" + settings.getValueNames().get(potionPlayer.getSetting(settings)));
            categoryItem.withLore(lore);
            getPagination().addItem(categoryItem);
        }

    }

    @Override
    public void update(int newPage) {
        List<Integer> slots = List.of(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);

        fill(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));
        fillBorders(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 7).withName(" "));

        this.setItem(52, this.getNextPageItem(), (event -> {
            if (getPagination().hasNextPage()) {
                nextPage();
                update(getPagination().getCurrentPage() + 1);
                return;
            }
            event.getWhoClicked().sendMessage(ProjectConstants.PREFIX + "§cDu bist bereits auf der letzten Seite.");
        }));

        this.setItem(49, this.getCloseItem(), (event -> {
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();
            getPlugin().getUiManager().open(player, new SettingsMainInventory(getPlugin()));
        }));

        this.setItem(46, this.getPreviousPageItem(), (event -> {
            if (getPagination().hasPreviousPage()) {
                previousPage();
                update(getPagination().getCurrentPage() - 1);
                return;
            }
            event.getWhoClicked().sendMessage(ProjectConstants.PREFIX + "§cDu bist bereits auf der ersten Seite.");
        }));

        for (int i = 0; i < slots.size(); i++) {
            int slot = slots.get(i);
            if (getPagination().getItems(newPage).size() <= i) {
                this.setItem(slot, ItemBuilder.AIR);
                continue;
            }
            this.setItem(slot, getPagination().getItems(newPage).get(i), event -> {
                Settings settings = Settings.getByName(event.getCurrentItem().getItemMeta().getDisplayName().substring(2));
                int newValue = potionPlayer.getSetting(settings) + 1;
                if (newValue >= settings.getValueNames().size()) {
                    newValue = 0;
                }
                potionPlayer.setSetting(settings, newValue);
                getPlugin().getDatabaseManager().getPotionPlayerManager().save(potionPlayer);
                event.getWhoClicked().sendMessage(ProjectConstants.PREFIX + "§7Du hast die Einstellung §c" + settings.getDisplayName() + " §7auf §c" + settings.getValueNames().get(potionPlayer.getSetting(settings)) + " §7geändert.");
                ItemBuilder builder = ItemBuilder.of(event.getCurrentItem());
                builder.clearLore();
                List<String> lore = new ArrayList<>(settings.getDescription());
                lore.add("");
                lore.add("§7Klicke§8,§7 um diese Einstellung zu ändern§8.");
                lore.add("§8» §7Aktueller Wert: §c" + settings.getValueNames().get(potionPlayer.getSetting(settings)));
                builder.withLore(lore);
                this.setItem(slot, builder);
            });
        }
    }

    @Override
    public void open(Player player) {

    }

    @Override
    public void close(Player player) {

    }
}
