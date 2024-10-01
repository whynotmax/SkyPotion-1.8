package eu.skypotion.inventory.kits.preview;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.inventory.kits.KitsSelectInventory;
import eu.skypotion.inventory.settings.SettingsMainInventory;
import eu.skypotion.kits.model.Kit;
import eu.skypotion.mongo.codec.ItemStackCodec;
import eu.skypotion.ui.paged.PagedUI;
import eu.skypotion.util.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitPreviewInventory extends PagedUI {

    Kit kit;

    public KitPreviewInventory(PotionPlugin plugin, Kit kit) {
        super(plugin, ProjectConstants.PREFIX + "§cKit-Vorschau", 54, 28);
        this.kit = kit;

        for (ItemStack itemStack : kit.getItems()) {
            getPagination().addItem(itemStack);
        }

        update(0);

    }

    @Override
    public void update(int newPage) {

        List<Integer> slots = List.of(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);

        fill(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 7).withName(" "));
        fillBorders(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));

        for (ItemStack itemStack : getPagination().getItems(newPage)) {
            setItem(slots.get(getPagination().getItems(newPage).indexOf(itemStack)), itemStack);
        }

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
            getPlugin().getUiManager().open(player, new KitsSelectInventory(getPlugin()));
        }));

        this.setItem(46, this.getPreviousPageItem(), (event -> {
            if (getPagination().hasPreviousPage()) {
                previousPage();
                update(getPagination().getCurrentPage() - 1);
                return;
            }
            event.getWhoClicked().sendMessage(ProjectConstants.PREFIX + "§cDu bist bereits auf der ersten Seite.");
        }));

    }

    @Override
    public void open(Player player) {

    }

    @Override
    public void close(Player player) {

    }
}
