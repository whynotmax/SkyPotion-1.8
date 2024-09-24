package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TPCommand extends Command {

    PotionPlugin plugin;

    public TPCommand(PotionPlugin plugin) {
        super("tp", "", "", List.of("teleport"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                if (!player.hasPermission("skypotion.command.tp")) {
                    player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.tp"));
                    return false;
                }
                player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/tp <Spieler> [Spieler]"));
                return false;
            }
            if (args.length == 1) {
                if (!player.hasPermission("skypotion.command.tp")) {
                    player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.tp"));
                    return false;
                }
                Player target = player.getServer().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ProjectConstants.PLAYER_NOT_FOUND.formatted(args[0]));
                    return false;
                }
                player.teleport(target);
                player.sendMessage(ProjectConstants.PREFIX + "§7Du wurdest zu §a" + target.getName() + " §7teleportiert.");
                return true;
            }
            if (!player.hasPermission("skypotion.command.tp.others")) {
                player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.tp.others"));
                return false;
            }
            Player target = player.getServer().getPlayer(args[0]);
            Player targetsTarget = player.getServer().getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(ProjectConstants.PLAYER_NOT_FOUND.formatted(args[0]));
                return false;
            }
            if (targetsTarget == null) {
                player.sendMessage(ProjectConstants.PLAYER_NOT_FOUND.formatted(args[1]));
                return false;
            }
            target.teleport(targetsTarget);
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast §a" + target.getName() + " §7zu §a" + targetsTarget.getName() + " §7teleportiert.");
            return true;
        } else {
            if (args.length >= 2) {
                Player target = commandSender.getServer().getPlayer(args[0]);
                Player targetsTarget = commandSender.getServer().getPlayer(args[1]);
                if (target == null) {
                    commandSender.sendMessage("Spieler nicht gefunden: %s.".formatted(args[2]));
                    return false;
                }
                if (targetsTarget == null) {
                    commandSender.sendMessage("Spieler nicht gefunden: %s.".formatted(args[1]));
                    return false;
                }
                target.teleport(targetsTarget);
                commandSender.sendMessage(ProjectConstants.PREFIX + "Du hast " + target.getName() + " zu " + targetsTarget.getName() + " teleportiert.");
                return true;
            }
            commandSender.sendMessage(ProjectConstants.PREFIX + "Benuze: /tp <Spieler> <Spieler>");
            return false;
        }
    }
}
