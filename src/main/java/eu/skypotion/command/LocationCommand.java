package eu.skypotion.command;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.location.model.MongoLocation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LocationCommand extends Command {

    PotionPlugin plugin;

    public LocationCommand(PotionPlugin plugin) {
        super("location", "", "", List.of("loc", "locations", "setlocation", "setloc"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (!player.hasPermission("skypotion.location")) {
                player.sendMessage(ProjectConstants.NO_PERMISSION.formatted("skypotion.location"));
                return false;
            }
            if (args.length < 2) {
                player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/location <set|delete|tp> <name>"));
                return false;
            }
            String name = args[1];
            switch (args[0].toLowerCase()) {
                case "set" -> {
                    MongoLocation.MongoLocationBuilder builder = MongoLocation.builder();
                    builder.name(name);
                    builder.world(player.getWorld().getName());
                    builder.x(player.getLocation().getX());
                    builder.y(player.getLocation().getY());
                    builder.z(player.getLocation().getZ());
                    builder.yaw(player.getLocation().getYaw());
                    builder.pitch(player.getLocation().getPitch());
                    builder.warp(true);
                    builder.available(true);
                    MongoLocation location = builder.build();
                    if (plugin.getDatabaseManager().getLocationManager().get(name.toLowerCase()) != null) {
                        player.sendMessage(ProjectConstants.PREFIX + "§7Die Location §c" + name + " §7existiert bereits§8.");
                        return false;
                    }
                    plugin.getDatabaseManager().getLocationManager().save(location);
                    player.sendMessage(ProjectConstants.PREFIX + "§7Location §c" + name + " §7wurde gesetzt§8.");
                    return true;
                }
                case "delete" -> {
                    MongoLocation location = plugin.getDatabaseManager().getLocationManager().get(name);
                    if (location == null) {
                        player.sendMessage(ProjectConstants.PREFIX + "§7Die Location §c" + name + " §7existiert nicht§8.");
                        return false;
                    }
                    plugin.getDatabaseManager().getLocationManager().delete(location);
                    player.sendMessage(ProjectConstants.PREFIX + "§7Location §c" + name + " §7wurde gelöscht§8.");
                    return true;
                }
                case "tp" -> {
                    MongoLocation location = plugin.getDatabaseManager().getLocationManager().get(name);
                    if (location == null) {
                        player.sendMessage(ProjectConstants.PREFIX + "§7Die Location §c" + name + " §7existiert nicht§8.");
                        return false;
                    }
                    player.teleport(location.toLocation());
                    player.sendMessage(ProjectConstants.PREFIX + "§7Du wurdest zur Location §c" + name + " §7teleportiert§8.");
                    return true;
                }
                default -> player.sendMessage(ProjectConstants.INVALID_USAGE.formatted("/location <set|delete|tp> <name>"));
            }
            return true;
        }
        commandSender.sendMessage("§cDu musst ein Spieler sein§8.");
        return false;
    }
}
