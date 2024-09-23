package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.crates.model.Crate;
import eu.skypotion.crates.model.item.CrateItem;
import eu.skypotion.mongo.player.model.PotionPlayer;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CratesCommand extends Command {

    PotionPlugin plugin;

    public CratesCommand(PotionPlugin plugin) {
        super("crates", "", "", List.of("crate", "c", "case", "cases"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (!player.hasPermission("skypotion.command.crates")) {
                player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.crates"));
                return false;
            }
            if (args.length <= 1) {
                player.sendMessage(ProjectConstants.PREFIX + "§7Hilfe§8: /§a%s".formatted(s));
                player.sendMessage("§8» /§7crates create <name>");
                player.sendMessage("§8» /§7crates delete <name>");
                player.sendMessage("§8» /§7crates setdisplayname <name> <displayName>");
                player.sendMessage("§8» /§7crates setdisplayitem <name>");
                player.sendMessage("§8» /§7crates setcollection <name> <collection>");
                player.sendMessage("§8» /§7crates setenabled <name> <true/false>");
                player.sendMessage("§8» /§7crates additem <name> <item> <chance>");
                player.sendMessage("§8» /§7crates additem <name> <item> <chance> tokens <tokens>");
                player.sendMessage("§8» /§7crates additem <name> <item> <chance> command <command>");
                player.sendMessage("§8» /§7crates additem <name> <item> <chance> broadcast <message>");
                player.sendMessage("§8» /§7crates clear");
                player.sendMessage("§8» /§7crates give <player> <crate> [amount]");
                player.sendMessage("§8» /§7crates giveall <crate> [amount]");
                return false;
            }
            if (args[0].equalsIgnoreCase("create")) {
                String name = args[1];
                if (plugin.getDatabaseManager().getCrateManager().get(name) != null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert bereits§8.");
                    return false;
                }
                plugin.getDatabaseManager().getCrateManager().createCrate(name, player.getName());
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast die Crate §c" + name + "§7 erstellt§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("delete")) {
                String name = args[1];
                if (plugin.getDatabaseManager().getCrateManager().get(name) == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                    return false;
                }
                plugin.getDatabaseManager().getCrateManager().delete(name);
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast die Crate §c" + name + "§7 gelöscht§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("setdisplayname")) {
                String name = args[1];
                StringBuilder displayName = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    displayName.append(args[i]).append(" ");
                }
                Crate crate = plugin.getDatabaseManager().getCrateManager().get(name);
                if (crate == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                    return false;
                }
                crate.setDisplayName(displayName.toString().trim());
                plugin.getDatabaseManager().getCrateManager().save(crate);
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast den Namen der Crate §c" + name + "§7 zu §c" + displayName + "§7 geändert§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("setdisplayitem")) {
                String name = args[1];
                Crate crate = plugin.getDatabaseManager().getCrateManager().get(name);
                if (crate == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                    return false;
                }
                ItemStack item = player.getInventory().getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Du musst ein Item in der Hand halten§8.");
                    return false;
                }
                crate.setDisplayItem(item);
                plugin.getDatabaseManager().getCrateManager().save(crate);
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das Display-Item der Crate §c" + name + "§7 geändert§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("setcollection")) {
                String name = args[1];
                int collection = Integer.parseInt(args[2]);
                Crate crate = plugin.getDatabaseManager().getCrateManager().get(name);
                if (crate == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                    return false;
                }
                crate.setCollection(collection);
                plugin.getDatabaseManager().getCrateManager().save(crate);
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast die Collection der Crate §c" + name + "§7 zu §c" + collection + "§7 geändert§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("setenabled")) {
                String name = args[1];
                boolean enabled = Boolean.parseBoolean(args[2]);
                Crate crate = plugin.getDatabaseManager().getCrateManager().get(name);
                if (crate == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                    return false;
                }
                crate.setEnabled(enabled);
                plugin.getDatabaseManager().getCrateManager().save(crate);
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast die Crate §c" + name + "§7 " + (enabled ? "§aaktiviert" : "§cdeaktiviert") + "§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("additem")) {
                String name = args[1];
                ItemStack item = player.getInventory().getItemInHand();
                double chance = Double.parseDouble(args[3]);
                if (args[4].equalsIgnoreCase("tokens")) {
                    double tokens = Double.parseDouble(args[5]);
                    CrateItem crateItem = new CrateItem();
                    crateItem.setItemStack(item);
                    crateItem.setChance(chance);
                    crateItem.setTokensToGive(tokens);

                    Crate crate = plugin.getDatabaseManager().getCrateManager().get(name);
                    if (crate == null) {
                        player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                        return false;
                    }
                    crate.getItems().add(crateItem);
                    plugin.getDatabaseManager().getCrateManager().save(crate);
                    player.sendMessage(ProjectConstants.PREFIX + "§7Du hast ein Item zur Crate §c" + name + "§7 hinzugefügt§8.");
                    return true;
                }
                if (args[4].equalsIgnoreCase("command")) {
                    StringBuilder command = new StringBuilder();
                    for (int i = 5; i < args.length; i++) {
                        command.append(args[i]).append(" ");
                    }
                    CrateItem crateItem = new CrateItem();
                    crateItem.setItemStack(item);
                    crateItem.setChance(chance);
                    crateItem.setCommand(command.toString().trim());

                    Crate crate = plugin.getDatabaseManager().getCrateManager().get(name);
                    if (crate == null) {
                        player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                        return false;
                    }
                    crate.getItems().add(crateItem);
                    plugin.getDatabaseManager().getCrateManager().save(crate);
                    player.sendMessage(ProjectConstants.PREFIX + "§7Du hast ein Item zur Crate §c" + name + "§7 hinzugefügt§8.");
                    return true;
                }
                if (args[4].equalsIgnoreCase("broadcast")) {
                    StringBuilder broadcastMessage = new StringBuilder();
                    for (int i = 5; i < args.length; i++) {
                        broadcastMessage.append(args[i]).append(" ");
                    }
                    CrateItem crateItem = new CrateItem();
                    crateItem.setItemStack(item);
                    crateItem.setChance(chance);
                    crateItem.setBroadcastMessage(broadcastMessage.toString().replace("&", "§").trim());

                    Crate crate = plugin.getDatabaseManager().getCrateManager().get(name);
                    if (crate == null) {
                        player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                        return false;
                    }
                    crate.getItems().add(crateItem);
                    plugin.getDatabaseManager().getCrateManager().save(crate);
                    player.sendMessage(ProjectConstants.PREFIX + "§7Du hast ein Item zur Crate §c" + name + "§7 hinzugefügt§8.");
                    return true;
                }
                Crate crate = plugin.getDatabaseManager().getCrateManager().get(name);
                if (crate == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + name + "§7 existiert nicht§8.");
                    return false;
                }
                CrateItem crateItem = new CrateItem();
                crateItem.setItemStack(item);
                crateItem.setChance(chance);
                crate.getItems().add(crateItem);
                plugin.getDatabaseManager().getCrateManager().save(crate);
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast ein Item zur Crate §c" + name + "§7 hinzugefügt§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("clear")) {
                String crateName = args[1];
                Crate crate = plugin.getDatabaseManager().getCrateManager().get(crateName);
                if (crate == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + crateName + "§7 existiert nicht§8.");
                    return false;
                }
                crate.getItems().clear();
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast alle Items der Crate §c" + crateName + "§7 gelöscht§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                String playerName = args[1];
                String crateName = args[2];
                int amount = args.length == 4 ? Integer.parseInt(args[3]) : 1;

                Player target = Bukkit.getPlayer(playerName);
                if (target == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Der Spieler §c" + playerName + "§7 ist nicht online§8.");
                    return false;
                }
                Crate crate = plugin.getDatabaseManager().getCrateManager().get(crateName);
                if (crate == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + crateName + "§7 existiert nicht§8.");
                    return false;
                }
                target.getInventory().addItem(plugin.getDatabaseManager().getCrateManager().get(crateName, amount));
                target.sendMessage(ProjectConstants.PREFIX + "§7Du hast §c" + amount + "§8x '§r" + crate.getDisplayName() + "§8' §7erhalten§8.");
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast §c" + playerName + "§7 " + amount + "x '§r" + crate.getDisplayName() + "§7' gegeben§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("giveall")) {
                String crateName = args[1];
                int amount = args.length == 3 ? Integer.parseInt(args[2]) : 1;

                Crate crate = plugin.getDatabaseManager().getCrateManager().get(crateName);
                if (crate == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§7Die Crate §c" + crateName + "§7 existiert nicht§8.");
                    return false;
                }
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                    onlinePlayer.getInventory().addItem(plugin.getDatabaseManager().getCrateManager().get(crateName, amount));
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Du hast §c" + amount + "§8x '§r" + crate.getDisplayName() + "§8' §7erhalten§8.");
                });
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast allen Spielern §c" + amount + "§8x '§r" + crate.getDisplayName() + "§8' gegeben§8.");
                return true;
            }
            player.sendMessage(ProjectConstants.PREFIX + "§7Hilfe§8: /§a%s".formatted(s));
            player.sendMessage("§8» /§7crates create <name>");
            player.sendMessage("§8» /§7crates delete <name>");
            player.sendMessage("§8» /§7crates setdisplayname <name> <displayName>");
            player.sendMessage("§8» /§7crates setdisplayitem <name>");
            player.sendMessage("§8» /§7crates setcollection <name> <collection>");
            player.sendMessage("§8» /§7crates setenabled <name> <true/false>");
            player.sendMessage("§8» /§7crates additem <name> <item> <chance>");
            player.sendMessage("§8» /§7crates additem <name> <item> <chance> tokens <tokens>");
            player.sendMessage("§8» /§7crates additem <name> <item> <chance> command <command>");
            player.sendMessage("§8» /§7crates additem <name> <item> <chance> broadcast <message>");
            player.sendMessage("§8» /§7crates clear");
            player.sendMessage("§8» /§7crates give <player> <crate> [amount]");
            player.sendMessage("§8» /§7crates giveall <crate> [amount]");
            return true;
        }
        if (commandSender instanceof ConsoleCommandSender console) {
            if (args.length < 3) {
                console.sendMessage("/crates give <player> <crate> [amount]");
                console.sendMessage("/crates giveall <crate> [amount]");
                return true;
            }
            if (args[0].equalsIgnoreCase("giveall")) {
                String crateName = args[1];
                int amount = args.length == 3 ? Integer.parseInt(args[2]) : 1;

                Crate crate = plugin.getDatabaseManager().getCrateManager().get(crateName);
                if (crate == null) {
                    console.sendMessage("Die Crate " + crateName + " existiert nicht.");
                    return false;
                }
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.getInventory().addItem(plugin.getDatabaseManager().getCrateManager().get(crateName, amount));
                    player.sendMessage(ProjectConstants.PREFIX + "§7Du hast §c" + amount + "§8x '§r" + crate.getDisplayName() + "§8' §7erhalten§8.");
                });
                console.sendMessage("Du hast allen Spielern " + amount + "x '" + crate.getDisplayName() + "' gegeben.");
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                String playerName = args[1];
                String crateName = args[2];
                int amount = args.length == 4 ? Integer.parseInt(args[3]) : 1;

                Player player = Bukkit.getPlayer(playerName);
                if (player == null) {
                    console.sendMessage("Der Spieler " + playerName + " ist nicht online.");
                    return false;
                }
                Crate crate = plugin.getDatabaseManager().getCrateManager().get(crateName);
                if (crate == null) {
                    console.sendMessage("Die Crate " + crateName + " existiert nicht.");
                    return false;
                }
                player.getInventory().addItem(plugin.getDatabaseManager().getCrateManager().get(crateName, amount));
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast §c" + amount + "§8x '§r" + crate.getDisplayName() + "§8' §7erhalten§8.");
                console.sendMessage("Du hast " + playerName + " " + amount + "x '" + crate.getDisplayName() + "' gegeben.");
                return true;
            }
            console.sendMessage("/crates give <player> <crate> [amount]");
            console.sendMessage("/crates giveall <crate> [amount]");
            return true;
        }
        return false;
    }
}
