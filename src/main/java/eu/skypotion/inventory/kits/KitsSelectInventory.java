package eu.skypotion.inventory.kits;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.inventory.kits.preview.KitPreviewInventory;
import eu.skypotion.kits.model.Kit;
import eu.skypotion.ui.simple.SimpleUI;
import eu.skypotion.util.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitsSelectInventory extends SimpleUI {

    public KitsSelectInventory(PotionPlugin plugin) {
        super(plugin, ProjectConstants.PREFIX + "§cKits", 54);

        fill(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 7).withName(" "));
        fillBorders(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));

        setItem(39, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §b§lG§3§lUARDIAN§8-§7Kit").withLore("§7Erhalte das §b§lG§3§lUARDIAN§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.GUARDIAN;

            System.out.println(event.getAction().name());
            if (event.getAction().name().contains("RIGHT") || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(40, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §e§lVORT§6§lΣ§e§lX§8-§7Kit").withLore("§7Erhalte das §e§lVORT§6§lΣ§e§lX§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.VORTEX;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(41, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §5§lU§d§lL§5§lT§d§lR§5§lA§8-§7Kit").withLore("§7Erhalte das §5§lU§d§lL§5§lT§d§lR§5§lA§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.ULTRA;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(28, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §d§lE§4§lX§b§lE§a§lC§6§lU§9§lT§2§lE§8-§7Kit").withLore("§7Erhalte das §d§lE§4§lX§b§lE§a§lC§6§lU§9§lT§2§lE§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.EXECUTE;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(29, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §a§lMASTER§8-§7Kit").withLore("§7Erhalte das §a§lMASTER§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.MASTER;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(31, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §2§lΣ§a§lX§2§lT§a§lR§2§lE§a§lM§2§lE§8-§7Kit").withLore("§7Erhalte das §2§lΣ§a§lX§2§lT§a§lR§2§lE§a§lM§2§lE§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.EXECUTE;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(33, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §5§lV§dΣ§5§lNUS§8-§7Kit").withLore("§7Erhalte das §5§lV§dΣ§5§lNUS§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.VENUS;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(34, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §b§lC§3§lH§b§lA§3§lM§b§lP§3§lI§b§lO§3§lN§8-§7Kit").withLore("§7Erhalte das §b§lC§3§lH§b§lA§3§lM§b§lP§3§lI§b§lO§3§lN§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.CHAMPION;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(11, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §c§lH§4§lΣ§c§lRO§8-§7Kit").withLore("§7Erhalte das §c§lH§4§lΣ§c§lRO§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.HERO;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(13, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §e⚔ §6§lK§e§lI§6§lN§e§lG §6⚔§8-§7Kit").withLore("§7Erhalte das §e⚔ §6§lK§e§lI§6§lN§e§lG §6⚔§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.KING;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(15, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §5§lPO§d§lTI§5§lON§8-§7Kit").withLore("§7Erhalte das §5§lPO§d§lTI§5§lON§8-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.POTION;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });

        setItem(49, ItemBuilder.of(Material.SKULL_ITEM).withAmount(1).withDurability((short) 3).withSkullOwner("MHF_Chest").withName("§8» §7Spieler§8-§7Kit").withLore("§7Erhalte das §7Spieler-§7Kit§8.", "§r", "§aLinksklick§8: §7Auswählen", "§aRechtsklick§8: §7Vorschau"), event -> {
            Player player = (Player) event.getWhoClicked();
            Kit kit = Kit.SPIELER;

            if (event.getAction().name().contains("RIGHT")  || event.getAction().name().contains("PICKUP_HALF")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return;
            }
            for (ItemStack itemStack : kit.getItems()) {
                player.getInventory().addItem(itemStack);
                player.closeInventory();
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das " + kit.getDisplayName() + " §7erhalten.");
        });
    }

    @Override
    public void open(Player player) {

    }

    @Override
    public void close(Player player) {

    }
}
