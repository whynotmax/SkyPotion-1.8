package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.inventory.settings.SettingsMainInventory;
import eu.skypotion.mongo.player.model.settings.category.SettingsCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SettingsCommand extends Command {

    PotionPlugin plugin;

    public SettingsCommand(PotionPlugin plugin) {
        super("settings", "", "", List.of("einstellungen", "optionen", "setting"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                plugin.getUiManager().open(player, new SettingsMainInventory(plugin));
                return true;
            }
            SettingsCategory category = SettingsCategory.getByName(args[0]);
            if (category == null) {
                player.sendMessage(ProjectConstants.PREFIX + "§cDie Kategorie " + args[0] + " existiert nicht.");
                plugin.getUiManager().open(player, new SettingsMainInventory(plugin));
                return true;
            }
            // Open the settings category inventory
            return false;
        }
        return false;
    }
}
