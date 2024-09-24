package eu.skypotion.inventory;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.crates.model.Crate;
import eu.skypotion.crates.model.item.CrateItem;
import eu.skypotion.ui.paged.PagedUI;
import eu.skypotion.util.NumberUtil;
import eu.skypotion.util.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CratePreviewInventory extends PagedUI {

    Crate crate;

    public CratePreviewInventory(PotionPlugin plugin, Crate crate) {
        super(plugin, "Crate-Preview: " + crate.getDisplayName().replaceAll("§+[a-z0-9]", ""), 45, 21);
        this.crate = crate;

        for (CrateItem item : crate.getItems()) {

            ItemBuilder pageItem = ItemBuilder.of(item.getItemStack().getType());

            pageItem.withName("§e" + (item.getItemStack().getItemMeta().hasDisplayName() ? item.getItemStack().getItemMeta().getDisplayName() : item.getItemStack().getType().name().replace("_", " ")));
            List<String> newLore = new ArrayList<>();
            if (item.getItemStack().getItemMeta().hasLore()) {
                newLore.addAll(item.getItemStack().getItemMeta().getLore());
            }

            newLore.add("§r");
            newLore.add("§7Chance§8: §c" + NumberUtil.formatWithDecimalPlaces(item.getChance(), 2) + "% §8(§r" + getRarity(item) + "§8)");

            pageItem.withLore(newLore);

            getPagination().addItem(pageItem);
        }
    }

    @Override
    public void update(int newPage) {

        List<Integer> slots = List.of(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

        this.fill(ItemBuilder.AIR);
        this.fillBorders(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));

        this.setItem(43, this.getNextPageItem(), (event -> {
            if (getPagination().hasNextPage()) {
                nextPage();
                update(getPagination().getCurrentPage() + 1);
                return;
            }
            event.getWhoClicked().sendMessage(ProjectConstants.PREFIX + "§cDu bist bereits auf der letzten Seite.");
        }));

        this.setItem(40, this.getCloseItem(), (event -> event.getWhoClicked().closeInventory()));

        this.setItem(37, this.getPreviousPageItem(), (event -> {
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
            this.setItem(slot, getPagination().getItems(newPage).get(i));
        }
    }

    @Override
    public void open(Player player) {

    }

    @Override
    public void close(Player player) {

    }

    private String getRarity(CrateItem item) {
        final String SPECIAL = "§d§lSPECIAL";
        final String LEGENDARY = "§6§lLEGENDARY";
        final String EPIC = "§5§lEPIC";
        final String RARE = "§9§lRARE";
        final String UNCOMMON = "§a§lUNCOMMON";
        final String COMMON = "§f§lCOMMON";

        double chance = item.getChance();

        if (chance >= 0.01 && chance <= 0.1) {
            return SPECIAL;
        }
        if (chance >= 0.11 && chance <= 0.5) {
            return LEGENDARY;
        }
        if (chance >= 0.51 && chance <= 1.0) {
            return EPIC;
        }
        if (chance >= 1.01 && chance <= 5.0) {
            return RARE;
        }
        if (chance >= 5.01 && chance <= 30.0) {
            return UNCOMMON;
        }
        return COMMON;
    }
}
