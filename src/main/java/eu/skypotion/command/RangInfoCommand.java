package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.inventory.RangInfoInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RangInfoCommand extends Command {

    PotionPlugin plugin;

    public RangInfoCommand(PotionPlugin plugin) {
        super("ranginfo", "", "", List.of("ranginformation", "ranks", "ränge", "raenge"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            plugin.getUiManager().open(player, new RangInfoInventory(plugin));
            return true;
        }
        commandSender.sendMessage("§cNur Spieler können diesen Befehl verwenden.");
        return false;
    }
}
