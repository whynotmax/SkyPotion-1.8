package eu.skypotion.command;

import eu.skypotion.ProjectConstants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerTimeCommand extends Command {

    public PlayerTimeCommand() {
        super("ptime", "", "", List.of("playertime"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (!player.hasPermission("skypotion.command.playertime")) {
                player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.playertime"));
                return false;
            }
            if (args.length == 0) {
                player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/ptime <time|reset>"));
                return false;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                player.resetPlayerTime();
                player.sendMessage(ProjectConstants.PREFIX + "§7Deine Zeit wurde zurückgesetzt§8.");
                return true;
            }
            try {
                long time = Long.parseLong(args[0]);
                player.setPlayerTime(time, false);
                player.sendMessage(ProjectConstants.PREFIX + "§7Deine Zeit wurde auf §c" + time + " §7gesetzt§8.");
            } catch (NumberFormatException e) {
                player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/ptime <time|reset>"));
                return false;
            }
            return true;
        }
        commandSender.sendMessage("§cNur Spieler können diesen Befehl verwenden.");
        return false;
    }
}
