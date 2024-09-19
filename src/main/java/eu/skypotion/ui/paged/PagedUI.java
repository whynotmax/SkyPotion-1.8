package eu.skypotion.ui.paged;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ui.simple.SimpleUI;
import eu.skypotion.ui.size.UISize;
import eu.skypotion.ui.paged.pagination.Pagination;
import eu.skypotion.util.builder.ItemBuilder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class PagedUI extends SimpleUI {

    final int itemsOnPage;
    int currentPage;
    final Pagination<ItemStack> pagination;

    public PagedUI(PotionPlugin plugin, String title, int size, int itemsOnPage) {
        super(plugin, title, size);
        this.itemsOnPage = itemsOnPage;
        this.currentPage = 0;
        this.pagination = new Pagination<>(itemsOnPage);
    }

    public PagedUI(PotionPlugin plugin, String title, InventoryType type, int itemsOnPage) {
        super(plugin, title, type);
        this.itemsOnPage = itemsOnPage;
        this.currentPage = 0;
        this.pagination = new Pagination<>(itemsOnPage);
    }

    public PagedUI(PotionPlugin plugin, String title, UISize size, int itemsOnPage) {
        super(plugin, title, size);
        this.itemsOnPage = itemsOnPage;
        this.currentPage = 0;
        this.pagination = new Pagination<>(itemsOnPage);
    }

    public abstract void update(int newPage);

    public void nextPage() {
        if (pagination.hasNextPage()) {
            currentPage++;
            update(currentPage);
        }
    }

    public void previousPage() {
        if (pagination.hasPreviousPage()) {
            currentPage--;
            update(currentPage);
        }
    }

    public void setPage(int page) {
        if (pagination.isValidPage(page)) {
            currentPage = page;
            update(currentPage);
        }
    }

    public ItemStack getNextPageItem() {
        return ItemBuilder.of(Material.ARROW).withAmount(1).withName("§8» §7Nächste Seite");
    }

    public ItemStack getPreviousPageItem() {
        return ItemBuilder.of(Material.ARROW).withAmount(1).withName("§8» §7Vorherige Seite");
    }

    public ItemStack getInfoItem() {
        return ItemBuilder.of(Material.BOOK).withAmount(1).withName("§8» §7Seite §c" + (currentPage + 1) + "§7/§c" + (pagination.getPages().size()));
    }
}
