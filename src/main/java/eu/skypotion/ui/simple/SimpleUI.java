package eu.skypotion.ui.simple;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ui.size.UISize;
import eu.skypotion.util.builder.ItemBuilder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class SimpleUI {

    final PotionPlugin plugin;
    final String title;
    final int size;
    final InventoryType type;
    final Inventory inventory;
    final Map<Integer, Consumer<InventoryClickEvent>> clickActions;

    public SimpleUI(PotionPlugin plugin, String title, int size) {
        this.plugin = plugin;
        this.title = title;
        this.size = size;
        this.type = InventoryType.CHEST;
        this.clickActions = new HashMap<>();
        this.inventory = plugin.getServer().createInventory(null, size, title);
    }

    public SimpleUI(PotionPlugin plugin, String title, InventoryType type) {
        this.plugin = plugin;
        this.title = title;
        this.size = type.getDefaultSize();
        this.type = type;
        this.clickActions = new HashMap<>();
        this.inventory = plugin.getServer().createInventory(null, type, title);
    }

    public SimpleUI(PotionPlugin plugin, String title, UISize size) {
        this.plugin = plugin;
        this.title = title;
        this.size = size.getSize();
        this.type = InventoryType.CHEST;
        this.clickActions = new HashMap<>();
        this.inventory = plugin.getServer().createInventory(null, size.getSize(), title);
    }

    public abstract void open(Player player);

    public abstract void close(Player player);

    public void openTo(Player... players) {
        for (Player player : players) {
            open(player);
            player.openInventory(this.inventory);
        }
    }

    public void fill(ItemStack item) {
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, item);
        }
    }

    public void fillBorders(ItemStack itemStack) {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, itemStack);
            inventory.setItem(size - 1 - i, itemStack);
        }
        for (int i = 0; i < size; i += 9) {
            inventory.setItem(i, itemStack);
            inventory.setItem(i + 8, itemStack);
        }
    }

    public void fillRow(int row, ItemStack item) {
        try {
            for (int i = row * 9; i < (row + 1) * 9; i++) {
                inventory.setItem(i, item);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    public void fillRow(int row, ItemStack item, Consumer<InventoryClickEvent> clickEventConsumer) {
        try {
            for (int i = row * 9; i < (row + 1) * 9; i++) {
                inventory.setItem(i, item);
                clickActions.put(i, clickEventConsumer);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    public void fillColumn(int column, ItemStack item) {
        for (int i = column; i < size; i += 9) {
            inventory.setItem(i, item);
        }
    }

    public void fillColumn(int column, ItemStack item, Consumer<InventoryClickEvent> clickEventConsumer) {
        for (int i = column; i < size; i += 9) {
            inventory.setItem(i, item);
            clickActions.put(i, clickEventConsumer);
        }
    }

    public void fillArea(int start, int end, ItemStack item) {
        for (int i = start; i < end; i++) {
            inventory.setItem(i, item);
        }
    }

    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    public void setItem(int row, int column, ItemStack item) {
        inventory.setItem(row * 9 + column, item);
    }

    public void setItem(int row, int column, ItemStack item, Consumer<InventoryClickEvent> action) {
        inventory.setItem(row * 9 + column, item);
        clickActions.put(row * 9 + column, action);
    }

    public void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> action) {
        inventory.setItem(slot, item);
        clickActions.put(slot, action);
    }

    public ItemStack getCloseItem() {
        return ItemBuilder.of(Material.BARRIER).withAmount(1).withName("§8» §cInventar schließen");
    }

}
