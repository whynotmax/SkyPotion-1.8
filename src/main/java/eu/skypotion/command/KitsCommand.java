package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.inventory.kits.KitsSelectInventory;
import eu.skypotion.inventory.kits.preview.KitPreviewInventory;
import eu.skypotion.kits.model.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitsCommand extends Command {

    PotionPlugin plugin;

    public KitsCommand(PotionPlugin plugin) {
        super("kits", "", "", List.of("kit"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                plugin.getUiManager().open(player, new KitsSelectInventory(plugin));
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("preview")) {
                    player.sendMessage(ProjectConstants.PREFIX + "§cBitte gib ein Kit an§8.");
                    return true;
                }
                String kitName = args[0];
                Kit kit = Kit.getByName(kitName);
                if (kit == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§cDas Kit " + kitName + " existiert nicht§8.");
                    return true;
                }
                String permission = kit.getPermission();
                if (permission != null && !player.hasPermission(permission)) {
                    player.sendMessage(ProjectConstants.NO_PERMISSION.formatted(permission));
                    return true;
                }
                for (ItemStack itemStack : kit.getItems()) {
                    player.getInventory().addItem(itemStack);
                }
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das Kit §c" + kit.getName() + " §7erhalten§8.");
                return true;
            }
            String kitName = args[1];
            Kit kit = Kit.getByName(kitName);
            if (kit == null) {
                player.sendMessage(ProjectConstants.PREFIX + "§cDas Kit " + kitName + " existiert nicht§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("preview")) {
                plugin.getUiManager().open(player, new KitPreviewInventory(plugin, kit));
                return true;
            }
            plugin.getUiManager().open(player, new KitsSelectInventory(plugin));
            return true;
        }
        commandSender.sendMessage("§cDu musst ein Spieler sein§8.");
        return false;
    }
}
