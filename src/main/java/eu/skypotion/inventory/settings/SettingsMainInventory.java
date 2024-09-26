package eu.skypotion.inventory.settings;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.player.model.settings.category.SettingsCategory;
import eu.skypotion.ui.paged.PagedUI;
import eu.skypotion.ui.simple.SimpleUI;
import eu.skypotion.ui.size.UISize;
import eu.skypotion.util.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class SettingsMainInventory extends PagedUI {

    public SettingsMainInventory(PotionPlugin plugin) {
        super(plugin, ProjectConstants.PREFIX + "§cEinstellungen", UISize.SIX_ROWS, 28);

        fill(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));
        fillBorders(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 7).withName(" "));

        for (SettingsCategory category : SettingsCategory.VALUES) {
            ItemBuilder categoryItem = ItemBuilder.of(category.getDisplayMaterial());
            categoryItem.withName("§c" + category.getName());
            categoryItem.withLore(
                    category.getDescription(),
                    "",
                    "§7Klicke§8,§7 um die Katgeorie zu öffnen§8.",
                    "§8» §7Es sind §c" + category.howManySettingsInCategory() + " §7Einstellungen verfügbar§8."
            );
            getPagination().addItem(categoryItem);
        }

    }

    @Override
    public void open(Player player) {

    }

    @Override
    public void close(Player player) {

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

        this.setItem(49, this.getCloseItem(), (event -> event.getWhoClicked().closeInventory()));

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
                SettingsCategory category = SettingsCategory.getByName(event.getCurrentItem().getItemMeta().getDisplayName().replace("§c", ""));
                if (category == null) return;
                //TODO: Open settings category inventory
            });
        }
    }
}
