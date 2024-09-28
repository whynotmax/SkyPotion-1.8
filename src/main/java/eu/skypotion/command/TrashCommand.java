package eu.skypotion.command;

import eu.skypotion.ProjectConstants;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TrashCommand extends Command {

    public TrashCommand() {
        super("trash", "", "", List.of("garbage", "müll"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (!player.hasPermission("skypotion.command.trash")) {
                player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.trash"));
                return false;
            }
            player.openInventory(Bukkit.createInventory(null, 36, ProjectConstants.PREFIX + "§cMülltonne"));
            return true;
        }
        commandSender.sendMessage("§cNur Spieler können diesen Befehl verwenden.");
        return false;
    }
}
