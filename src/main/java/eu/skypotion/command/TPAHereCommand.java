package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.mongo.player.model.settings.Settings;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class TPAHereCommand extends Command {

    PotionPlugin plugin;

    public TPAHereCommand(PotionPlugin plugin) {
        super("tpah", "", "", List.of("tpahere", "tprequesthere", "tpanfragehere"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            PotionPlayer requester = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());
            if (args.length == 0) {
                player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/tpahere <Spieler>"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ProjectConstants.PREFIX + "§cDieser Spieler ist nicht online.");
                return false;
            }
            PotionPlayer targetPotionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(target.getUniqueId());
            int settingValue = targetPotionPlayer.getSetting(Settings.TELEPORT_REQUEST);
            if (settingValue == 2) {
                player.sendMessage(ProjectConstants.PREFIX + "§cDieser Spieler hat Teleportanfragen deaktiviert.");
                return false;
            }
            if (settingValue == 1) {
                //TODO: Check if player is friend
                return false;
            }
            plugin.getTeleportRequestManager().requestTeleport(player.getUniqueId(), target.getUniqueId(), true);
            player.sendMessage(ProjectConstants.PREFIX + "§7Teleportanfrage an §a" + target.getName() + " §7gesendet.");
            target.sendMessage(ProjectConstants.PREFIX + "§a" + player.getName() + " §7möchte dich zu ihm teleportieren.");
            target.sendMessage(ProjectConstants.PREFIX + "§7Nutze §a/tpaccept §7um die Anfrage zu akzeptieren.");
            target.sendMessage(ProjectConstants.PREFIX + "§7Nutze §c/tpdeny §7um die Anfrage abzulehnen.");
        } else {
            commandSender.sendMessage(ProjectConstants.PREFIX + "§cNur Spieler können diesen Befehl verwenden.");
            return false;
        }
        return false;
    }
}
