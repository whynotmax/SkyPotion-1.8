package eu.skypotion.command;

import eu.skypotion.ProjectConstants;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public class WorkbenchCommand extends Command {

    public WorkbenchCommand() {
        super("workbench", "", "", List.of("wb", "werkbank"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (!player.hasPermission("skypotion.command.workbench")) {
                player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.workbench"));
                return false;
            }
            player.openInventory(Bukkit.createInventory(null, InventoryType.WORKBENCH, ProjectConstants.PREFIX + "§cWerkbank"));
            return true;
        }
        commandSender.sendMessage("§cNur Spieler können diesen Befehl verwenden.");
        return false;
    }
}
