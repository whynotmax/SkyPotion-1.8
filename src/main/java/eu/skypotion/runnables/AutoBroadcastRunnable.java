package eu.skypotion.runnables;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.mongo.player.model.settings.Settings;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AutoBroadcastRunnable implements Runnable {

    final PotionPlugin plugin;
    int currentBroadcast;

    public AutoBroadcastRunnable(PotionPlugin plugin) {
        this.plugin = plugin;
        this.currentBroadcast = 0;
    }

    @Override
    public void run() {
        switch (currentBroadcast) {
            case 0 -> {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(onlinePlayer.getUniqueId());
                    int autoBroadcast = potionPlayer.getSetting(Settings.AUTO_BROADCAST);
                    if (autoBroadcast == 1) continue;
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Du willst uns unterstützen§8?");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Dann vote für uns auf §chttps://skypotion.eu/vote§8!");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                }
                currentBroadcast++;
                break;
            }
            case 1 -> {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(onlinePlayer.getUniqueId());
                    int autoBroadcast = potionPlayer.getSetting(Settings.AUTO_BROADCAST);
                    if (autoBroadcast == 1) continue;
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Du möchtest den §5§lPO§d§lTI§5§lON§7 Rang§8?");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Dann besuche unseren §chttps://store.skypotion.eu/§8!");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                }
                currentBroadcast++;
                break;
            }
            case 2 -> {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(onlinePlayer.getUniqueId());
                    int autoBroadcast = potionPlayer.getSetting(Settings.AUTO_BROADCAST);
                    if (autoBroadcast == 1) continue;
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Du benötigst Hilfe§8?");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Dann gebe doch §8/§chilfe§7 ein§8!");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                }
                currentBroadcast++;
                break;
            }
            case 3 -> {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(onlinePlayer.getUniqueId());
                    int autoBroadcast = potionPlayer.getSetting(Settings.AUTO_BROADCAST);
                    if (autoBroadcast == 1) continue;
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Du möchtest Infos§8?");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Dann trete doch unserem §chttps://skypotion.eu/discord§7 bei§8!");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                }
                currentBroadcast++;
                break;
            }
            case 4 -> {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(onlinePlayer.getUniqueId());
                    int autoBroadcast = potionPlayer.getSetting(Settings.AUTO_BROADCAST);
                    if (autoBroadcast == 1) continue;
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Kennst du schon unsere Partner§8?");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Gebe doch gerne §8/§cpartner§7 ein und lasse Liebe da§8!");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                }
                currentBroadcast++;
                break;
            }
            default -> {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(onlinePlayer.getUniqueId());
                    int autoBroadcast = potionPlayer.getSetting(Settings.AUTO_BROADCAST);
                    if (autoBroadcast == 1) continue;
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7Alle unsere neuen Features findest");
                    onlinePlayer.sendMessage(ProjectConstants.PREFIX + "§7du unter §8/§cchangelog§8!");
                    onlinePlayer.sendMessage("§r");
                    onlinePlayer.sendMessage("§8§m-----------------------------------------------------------§r");
                }
                currentBroadcast = 0;
            }
        }
    }


}
