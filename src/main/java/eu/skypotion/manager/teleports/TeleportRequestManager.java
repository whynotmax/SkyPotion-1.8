package eu.skypotion.manager.teleports;

import eu.skypotion.PotionPlugin;
import eu.skypotion.manager.teleports.request.TeleportRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeleportRequestManager {

    PotionPlugin plugin;
    List<TeleportRequest> requests;

    public TeleportRequestManager(PotionPlugin plugin) {
        this.plugin = plugin;
        this.requests = new ArrayList<>();
    }

    public void requestTeleport(UUID from, UUID to) {
        requests.add(new TeleportRequest(from, to));
    }

    public void requestTeleport(UUID from, UUID to, boolean here) {
        requests.add(new TeleportRequest(from, to, here));
    }

    public void acceptTeleport(UUID from, UUID to) {
        TeleportRequest request = requests.stream().filter(r -> r.getFrom().equals(from) && r.getTo().equals(to)).findFirst().orElse(null);
        if (request != null) {
            requests.remove(request);
            if (request.isHere()) {
                plugin.getServer().getPlayer(to).teleport(plugin.getServer().getPlayer(from));
            } else {
                plugin.getServer().getPlayer(from).teleport(plugin.getServer().getPlayer(to));
            }
        }
    }

    public void denyTeleport(UUID from, UUID to) {
        TeleportRequest request = requests.stream().filter(r -> r.getFrom().equals(from) && r.getTo().equals(to)).findFirst().orElse(null);
        if (request != null) {
            requests.remove(request);
            plugin.getServer().getPlayer(from).sendMessage("§cTeleport request denied.");
        }
    }

    public boolean hasRequest(UUID from, UUID to) {
        return requests.stream().anyMatch(r -> r.getFrom().equals(from) && r.getTo().equals(to));
    }

}
