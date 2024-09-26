package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.casino.mongo.model.CasinoPlayer;
import eu.skypotion.casino.mongo.model.pass.CasinoPass;
import eu.skypotion.util.TimeUtil;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
            CasinoPlayer casinoPlayer = plugin.getCasinoManager().getPlayerManager().get(player.getUniqueId());
            if (!player.getWorld().getName().equalsIgnoreCase(casinoWorld)) {
                CasinoPass casinoPass = casinoPlayer.getCasinoPass();
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
                casinoPlayer.setJoinedCasinoWorld(System.currentTimeMillis());
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
            if (args[0].equalsIgnoreCase("leave")) {
                long timeElapsed = System.currentTimeMillis() - casinoPlayer.getJoinedCasinoWorld();
                player.teleport(player.getServer().getWorld("world").getSpawnLocation());
                player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das Casino verlassen§8.");
                player.sendMessage(ProjectConstants.PREFIX + "§7Du warst §c" + TimeUtil.beautifyTime((timeElapsed), TimeUnit.MILLISECONDS, true, true) + "§7 Sekunden im Casino§8.");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage("§8§m---------------------------------------§r");
                player.sendMessage("§r");
                player.sendMessage(ProjectConstants.PREFIX + "§7Casino Befehle§8:");
                player.sendMessage("§r");
                player.sendMessage("§7- §a/casino§8: §7Betritt das Casino§8.");
                player.sendMessage("§7- §a/casino leave§8: §7Verlasse das Casino§8.");
                player.sendMessage("§7- §a/casino help§8: §7Zeigt diese Hilfe an§8.");
                player.sendMessage("§7- §a/casino convert§8: §7Konvertiert Tokens in Gamble Tokens und zurück§8.");
                player.sendMessage("§r");
                player.sendMessage("§8§m---------------------------------------§r");
                return true;
            }
            if (args[0].equalsIgnoreCase("convert")) {

                return true;
            }
            player.sendMessage("§8§m---------------------------------------§r");
            player.sendMessage("§r");
            player.sendMessage(ProjectConstants.PREFIX + "§7Casino Befehle§8:");
            player.sendMessage("§r");
            player.sendMessage("§7- §a/casino§8: §7Betritt das Casino§8.");
            player.sendMessage("§7- §a/casino leave§8: §7Verlasse das Casino§8.");
            player.sendMessage("§7- §a/casino help§8: §7Zeigt diese Hilfe an§8.");
            player.sendMessage("§7- §a/casino convert§8: §7Konvertiert Tokens in Gamble Tokens und zurück§8.");
            player.sendMessage("§r");
            player.sendMessage("§8§m---------------------------------------§r");
            return false;
        }
        return false;
    }
}
