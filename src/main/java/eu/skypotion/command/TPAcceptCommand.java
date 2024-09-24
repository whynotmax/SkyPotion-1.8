package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.manager.teleports.request.TeleportRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TPAcceptCommand extends Command {

    PotionPlugin plugin;

    public TPAcceptCommand(PotionPlugin plugin) {
        super("tpaccept", "", "", List.of("tpaccept", "tpyes", "tpacceptieren"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/tpaccept <Spieler>"));
                return false;
            }
            Player target = player.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ProjectConstants.PLAYER_NOT_FOUND.formatted(args[0]));
                return false;
            }
            if (plugin.getTeleportRequestManager().hasRequest(target.getUniqueId(), player.getUniqueId())) {
                plugin.getTeleportRequestManager().acceptTeleport(target.getUniqueId(), player.getUniqueId());
                player.sendMessage(ProjectConstants.PREFIX + "§7Teleportanfrage von §a" + target.getName() + " §7angenommen.");
                target.sendMessage(ProjectConstants.PREFIX + "§a" + player.getName() + " §7hat deine Teleportanfrage angenommen.");
                return true;
            }
            player.sendMessage(ProjectConstants.PREFIX + "§cDu hast keine Teleportanfrage von diesem Spieler.");
            return false;
        }
        commandSender.sendMessage("§cNur Spieler können diesen Befehl verwenden.");
        return false;
    }
}
