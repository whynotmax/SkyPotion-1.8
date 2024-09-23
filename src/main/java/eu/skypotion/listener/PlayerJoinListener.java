package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.mongo.player.model.settings.Settings;
import eu.skypotion.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.TimeUnit;

public record PlayerJoinListener(PotionPlugin plugin) implements Listener {

   @EventHandler
   public void handlePlayerJoinEvent(final PlayerJoinEvent event) {
       Player player = event.getPlayer();
       event.setJoinMessage(null);
       PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());
       long lastSeen = potionPlayer.getLastSeen();
       long now = System.currentTimeMillis();

       for (int i = 0; i < 256; i++) {
           player.sendMessage("§" + String.valueOf(i).charAt(0));
       }

       int joinMessage = potionPlayer.getSetting(Settings.JOIN_MESSAGE);

       plugin.getScoreboardManager().createScoreboard(player);

       Bukkit.getOnlinePlayers().forEach(plugin.getTablistManager()::setRank);

       if (joinMessage == 0) {
           player.sendMessage("§r");
           player.sendMessage(ProjectConstants.JOIN_MESSAGE_PREFIX + "§a§lHerzlich Willkommen auf §5§lSkyPotion§8.§5§lEU");
           player.sendMessage("§r");
           player.sendMessage(ProjectConstants.JOIN_MESSAGE_PREFIX + "§6§lSPENDEN §8» /§6shop");
           player.sendMessage(ProjectConstants.JOIN_MESSAGE_PREFIX + "§f§lVOTE §8» /§fvote");
           player.sendMessage(ProjectConstants.JOIN_MESSAGE_PREFIX + "§b§lDISCORD §8» /§bdiscord");
           player.sendMessage(ProjectConstants.JOIN_MESSAGE_PREFIX + "§2§lCHANGELOG §8» /§2changes");
           player.sendMessage(ProjectConstants.JOIN_MESSAGE_PREFIX + "§e§lTEAM §8» /§eteam");
       }
       if (lastSeen != now) {
           player.sendMessage("§r");
           player.sendMessage(ProjectConstants.PREFIX + "§7Du warst für §c" + TimeUtil.beautifyTime((System.currentTimeMillis() - potionPlayer.getLastSeen()), TimeUnit.MILLISECONDS, true, true) + " §7offline§8.");
       }
       potionPlayer.setLastSeen(System.currentTimeMillis());
   }

}
