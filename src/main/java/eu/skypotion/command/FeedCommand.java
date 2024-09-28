package eu.skypotion.command;

import eu.skypotion.ProjectConstants;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public class FeedCommand extends Command {

    public FeedCommand() {
        super("feed", "", "", List.of("füttern"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                if (!player.hasPermission("skypotion.command.feed")) {
                    player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.feed"));
                    return false;
                }
                player.setFoodLevel(20);
                player.sendMessage(ProjectConstants.PREFIX + "§7Du wurdest gefüttert§8.");
                return true;
            }
            if (!player.hasPermission("skypotion.command.feed.others")) {
                player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.feed.others"));
                return false;
            }
            Player target = player.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ProjectConstants.PLAYER_NOT_FOUND.formatted(args[0]));
                return false;
            }
            target.setFoodLevel(20);
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast §c" + target.getName() + " §7gefüttert§8.");
            target.sendMessage(ProjectConstants.PREFIX + "§c" + player.getName() + " §7hat dich gefüttert§8.");
            return true;
        }
        commandSender.sendMessage("§cNur Spieler können diesen Befehl verwenden.");
        return false;
    }
}
