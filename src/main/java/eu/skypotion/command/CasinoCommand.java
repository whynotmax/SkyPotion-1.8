package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.casino.mongo.model.pass.CasinoPass;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CasinoCommand extends Command {

    PotionPlugin plugin;

    public CasinoCommand(PotionPlugin plugin) {
        super("casino", "", "", List.of("gamble", "bet"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            String casinoWorld = "casino";
            if (!player.getWorld().getName().equalsIgnoreCase(casinoWorld)) {
                CasinoPass casinoPass = plugin.getCasinoManager().getPlayerManager().get(player.getUniqueId()).getCasinoPass();
                if (casinoPass == null || casinoPass.getExpireDate() < System.currentTimeMillis() || !casinoPass.isActive()) {
                    player.sendMessage(ProjectConstants.PREFIX + "§cDu benötigst einen Casino Pass um das Casino zu betreten§8.");
                    player.sendMessage(ProjectConstants.PREFIX + "§cWenn du den Pass kaufen möchtest§8, §cverwende /casinopass§8.");
                    return false;
                }
                World casino = player.getServer().getWorld(casinoWorld);
                if (casino == null) {
                    player.sendMessage(ProjectConstants.PREFIX + "§cDie Casino Welt konnte nicht gefunden werden§8.");
                    return false;
                }
                player.teleport(casino.getSpawnLocation());
                player.sendMessage("§8§m---------------------------------------§r");
                player.sendMessage("§r");
                player.sendMessage(ProjectConstants.PREFIX + "§7Willkommen im Casino§8!");
                player.sendMessage(ProjectConstants.PREFIX + "§7Hier kannst du verschiedene Spiele spielen§8,");
                player.sendMessage(ProjectConstants.PREFIX + "§7um deine Gamble Tokens zu vermehren§8.");
                player.sendMessage(ProjectConstants.PREFIX + "§7Viel Spaß§8!");
                player.sendMessage("§r");
                player.sendMessage("§8§m---------------------------------------§r");
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(ProjectConstants.PREFIX + "§cDu befindest dich bereits im Casino§8.");
                player.sendMessage(ProjectConstants.PREFIX + "§cBenutze /casino help, um eine Liste der verfügbaren Befehle zu erhalten§8.");
                player.sendMessage(ProjectConstants.PREFIX + "§cBenutze /casino leave, um das Casino wieder zu verlassen§8.");
                return false;
            }
            //TODO: Implement casino commands
        }
        return false;
    }
}
