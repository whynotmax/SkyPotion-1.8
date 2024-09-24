package eu.skypotion.ui;

import com.avaje.ebean.Page;
import eu.skypotion.PotionPlugin;
import eu.skypotion.ui.paged.PagedUI;
import eu.skypotion.ui.simple.SimpleUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class UIManager implements Listener {

    PotionPlugin plugin;

    Map<UUID, SimpleUI> simpleInventoryMap;
    Map<UUID, PagedUI> pagedInventoryMap;

    public UIManager(PotionPlugin plugin) {
        this.plugin = plugin;

        this.simpleInventoryMap = new HashMap<>();
        this.pagedInventoryMap = new HashMap<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void open(Player player, SimpleUI simpleUI) {
        if (simpleUI instanceof PagedUI pagedUI) {
            pagedUI.open(player);
            pagedUI.update(0);
            pagedUI.openTo(player);
            pagedInventoryMap.put(player.getUniqueId(), pagedUI);
            return;
        }
        simpleInventoryMap.put(player.getUniqueId(), simpleUI);
        simpleUI.open(player);
        simpleUI.openTo(player);
    }

    @EventHandler
    public void handleInventoryCloseEvent(final InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (simpleInventoryMap.containsKey(uuid)) {
            SimpleUI simpleUI = simpleInventoryMap.get(uuid);
            simpleUI.close(player);
            simpleInventoryMap.remove(uuid);
            return;
        }
        if (pagedInventoryMap.containsKey(uuid)) {
            PagedUI pagedUI = pagedInventoryMap.get(uuid);
            pagedUI.close(player);
            pagedInventoryMap.remove(uuid);
        }
    }

    @EventHandler
    public void handleInventoryClickEvent(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        if (simpleInventoryMap.containsKey(uuid)) {
            event.setCancelled(true);
            SimpleUI simpleUI = simpleInventoryMap.get(uuid);
            Consumer<InventoryClickEvent> clickAction = simpleUI.getClickActions().get(event.getSlot());
            if (clickAction != null) {
                clickAction.accept(event);
                return;
            }
            return;
        }
        if (pagedInventoryMap.containsKey(uuid)) {
            event.setCancelled(true);
            PagedUI pagedUI = pagedInventoryMap.get(uuid);
            Consumer<InventoryClickEvent> clickAction = pagedUI.getClickActions().get(event.getSlot());
            if (clickAction != null) {
                clickAction.accept(event);
            }
        }
    }

}
