package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TPDenyCommand extends Command {

    PotionPlugin plugin;

    public TPDenyCommand(PotionPlugin plugin) {
        super("tpdeny", "", "", List.of("tpdeny", "tpno", "tpablehnen"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/tpdeny <Spieler>"));
                return false;
            }
            Player target = player.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ProjectConstants.PLAYER_NOT_FOUND.formatted(args[0]));
                return false;
            }
            if (plugin.getTeleportRequestManager().hasRequest(target.getUniqueId(), player.getUniqueId())) {
                plugin.getTeleportRequestManager().denyTeleport(target.getUniqueId(), player.getUniqueId());
                player.sendMessage(ProjectConstants.PREFIX + "§7Teleportanfrage von §c" + target.getName() + " §7abgelehnt.");
                target.sendMessage(ProjectConstants.PREFIX + "§c" + player.getName() + " §7hat deine Teleportanfrage abgelehnt.");
                return true;
            }
            player.sendMessage(ProjectConstants.PREFIX + "§cDu hast keine Teleportanfrage von diesem Spieler.");
            return false;
        }
        commandSender.sendMessage("§cNur Spieler können diesen Befehl verwenden.");
        return false;
    }
}
