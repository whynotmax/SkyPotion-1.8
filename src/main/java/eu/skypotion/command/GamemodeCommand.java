package eu.skypotion.command;

import eu.skypotion.ProjectConstants;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GamemodeCommand extends Command {

    public GamemodeCommand() {
        super("gm", "", "", List.of("gamemode"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/gamemode <0,1,2,3> [Spieler]"));
                return false;
            }
            if (args.length == 1) {
                if (!player.hasPermission("skypotion.command.gamemode")) {
                    player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.gamemode"));
                    return false;
                }
                try {
                    int value = Integer.parseInt(args[0]);
                    GameMode gameMode = GameMode.getByValue(value);
                    player.setGameMode(gameMode);
                    player.sendMessage(ProjectConstants.PREFIX + "§7Dein Spielmodus wurde auf §a" + gameMode.name() + "§7 gesetzt§8.");
                    return true;
                } catch (NumberFormatException e) {
                    player.sendMessage(ProjectConstants.INVALID_NUMBER);
                    return false;
                }
            }
            if (!player.hasPermission("skypotion.command.gamemode.other")) {
                player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.command.gamemode.other"));
                return false;
            }
            try {
                int value = Integer.parseInt(args[0]);
                GameMode gameMode = GameMode.getByValue(value);
                Player target = player.getServer().getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(ProjectConstants.PLAYER_NOT_FOUND.formatted(args[1]));
                    return false;
                }
                target.setGameMode(gameMode);
                player.sendMessage(ProjectConstants.PREFIX + "§7Der Spielmodus von §a" + target.getName() + " §7wurde auf §a" + gameMode.name() + "§7 gesetzt§8.");
                target.sendMessage(ProjectConstants.PREFIX + "§7Dein Spielmodus wurde auf §a" + gameMode.name() + "§7 gesetzt§8.");
                return true;
            } catch (NumberFormatException e) {
                player.sendMessage(ProjectConstants.INVALID_NUMBER);
                return false;
            }
        } else {
            if (args.length < 2) {
                commandSender.sendMessage("§cInvalid usage: /gamemode <0,1,2,3> <Spieler>");
                return false;
            }
            try {
                int value = Integer.parseInt(args[0]);
                GameMode gameMode = GameMode.getByValue(value);
                Player target = commandSender.getServer().getPlayer(args[1]);
                if (target == null) {
                    commandSender.sendMessage(ProjectConstants.PLAYER_NOT_FOUND.formatted(args[1]));
                    return false;
                }
                target.setGameMode(gameMode);
                commandSender.sendMessage("Der Spielmodus von " + target.getName() + " wurde auf " + gameMode.name() + " gesetzt.");
                target.sendMessage(ProjectConstants.PREFIX + "§7Dein Spielmodus wurde auf §a" + gameMode.name() + "§7 gesetzt§8.");
                return true;
            } catch (NumberFormatException e) {
                commandSender.sendMessage("§cInvalid number: " + args[0]);
                return false;
            }
        }
    }
}
