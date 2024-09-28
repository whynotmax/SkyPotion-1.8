package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.config.impl.MaintenanceConfig;
import eu.skypotion.mongo.player.model.PotionPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public record AsyncPlayerPreLoginListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void handleAsyncPlayerPreLoginEvent(final AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        MaintenanceConfig maintenanceConfig = plugin.getConfigManager().getConfig(MaintenanceConfig.class);
        if (maintenanceConfig.isEnabled() && !maintenanceConfig.getWhitelist().contains(uuid)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ProjectConstants.MAINTENANCE_KICK_MESSAGE);
            return;
        }
    }
}
