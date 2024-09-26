package eu.skypotion.ui.accept;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ui.simple.SimpleUI;
import eu.skypotion.util.builder.ItemBuilder;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public abstract class AcceptUI extends SimpleUI {

    public AcceptUI(PotionPlugin plugin, @NonNull String title, @NonNull String whatToAccept, @Nullable String description) {
        super(plugin, title, 27);

        fill(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));

        fillColumn(0, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 5).withName("§a§lAkzeptieren").withLore("§7Klicke um zu akzeptieren"), event -> {
            this.onAccept((Player) event.getWhoClicked(), true);
        });
        fillColumn(1, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 5).withName("§a§lAkzeptieren").withLore("§7Klicke um zu akzeptieren"), event -> {
            this.onAccept((Player) event.getWhoClicked(), true);
        });
        fillColumn(2, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 5).withName("§a§lAkzeptieren").withLore("§7Klicke um zu akzeptieren"), event -> {
            this.onAccept((Player) event.getWhoClicked(), true);
        });

        setItem(13, ItemBuilder.of(Material.PAPER).withName("§7" + whatToAccept).withLore(description, "", "§7Klicke auf die §agrünen Gläser§7 um zu akzeptieren", "§7Klicke auf die §croten Gläser§7 um abzulehnen"));

        fillColumn(6, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 14).withName("§c§lAblehnen").withLore("§7Klicke um abzulehnen"), event -> {
            this.onAccept((Player) event.getWhoClicked(), false);
        });
        fillColumn(7, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 14).withName("§c§lAblehnen").withLore("§7Klicke um abzulehnen"), event -> {
            this.onAccept((Player) event.getWhoClicked(), false);
        });
        fillColumn(8, ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 14).withName("§c§lAblehnen").withLore("§7Klicke um abzulehnen"), event -> {
            this.onAccept((Player) event.getWhoClicked(), false);
        });

    }

    public abstract void onAccept(Player player, boolean accepted);
}
