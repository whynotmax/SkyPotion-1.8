package eu.skypotion.listener;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.location.model.MongoLocation;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.mongo.player.model.loginstreak.LoginStreak;
import eu.skypotion.mongo.player.model.settings.Settings;
import eu.skypotion.util.NumberUtil;
import eu.skypotion.util.TimeUtil;
import eu.skypotion.util.builder.ItemBuilder;
import kotlin.UNINITIALIZED_VALUE;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public record PlayerJoinListener(PotionPlugin plugin) implements Listener {

    @EventHandler
    public void handlePlayerJoinEvent(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        PotionPlayer potionPlayer = plugin.getDatabaseManager().getPotionPlayerManager().get(player.getUniqueId());
        long lastSeen = potionPlayer.getLastSeen();
        long now = System.currentTimeMillis();

        MongoLocation spawn = plugin.getDatabaseManager().getLocationManager().get("spawn");

        for (int i = 0; i < 256; i++) {
            player.sendMessage("§" + String.valueOf(i).charAt(0));
        }

        int joinMessage = potionPlayer.getSetting(Settings.JOIN_MESSAGE);
        int lastPosition = potionPlayer.getSetting(Settings.JOIN_TELEPORT);

        plugin.getScoreboardManager().createScoreboard(player);

        Bukkit.getOnlinePlayers().forEach(plugin.getTablistManager()::setRank);

        if (lastPosition == 0) {
            player.teleport(spawn.toLocation());
        }

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

        LoginStreak loginStreak = potionPlayer.getLoginStreak();
        if (loginStreak.getResult() == LoginStreak.Result.RESET) {
            player.sendMessage("§r");
            player.sendMessage(ProjectConstants.LOGINSTREAK_PREFIX + "§7Dein Login-Streak wurde zurückgesetzt§8.");
            player.sendMessage(ProjectConstants.LOGINSTREAK_PREFIX + "§7Dein Streak§8: §b1");
            loginStreak.setStreak(1);
        } else if (loginStreak.getResult() == LoginStreak.Result.INCREMENT) {
            player.sendMessage("§r");
            player.sendMessage(ProjectConstants.LOGINSTREAK_PREFIX + "§7Dein Login-Streak wurde um §b1 §7erhöht§8.");
            player.sendMessage(ProjectConstants.LOGINSTREAK_PREFIX + "§7Dein Streak§8: §b" + loginStreak.getStreak());
            loginStreak.setStreak(loginStreak.getStreak() + 1);
        } else {
            player.sendMessage("§r");
            player.sendMessage(ProjectConstants.LOGINSTREAK_PREFIX + "§7Du hast dich heute schon eingeloggt§8.");
            player.sendMessage(ProjectConstants.LOGINSTREAK_PREFIX + "§7Dein Streak§8: §b1");
        }
        loginStreak.setLastLogin(now);
        potionPlayer.setLastSeen(System.currentTimeMillis());

        potionPlayer.setLoginStreak(loginStreak);

        if (!player.hasPlayedBefore()) {
            player.sendMessage("§r");
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast das neue §5§lSkyPotion§8.§5§lEU §7zum ersten mal betreten§8.");
            player.sendMessage(ProjectConstants.PREFIX + "§7Viel Spaß§8!");

            double newRecord = (double) plugin.getConfigManager().getConfig("PlayerRecordConfig").get("recordAllTime");

            if (newRecord <= 3) {
                player.sendMessage("§r");
                player.sendMessage(ProjectConstants.PREFIX + "§7Da du einer der ersten §cdrei Spieler§7 bist§8,");
                player.sendMessage(ProjectConstants.PREFIX + "§7erhältst du den §5§lPO§d§lTI§5§lON §7Rang§8.");
                Bukkit.getConsoleSender().sendMessage("/pex user " + player.getName() + " group set potion");
            }

            newRecord += 1;
            plugin.getConfigManager().getConfig("PlayerRecordConfig").set("recordAllTime", newRecord);

            Bukkit.broadcastMessage("§r");
            Bukkit.broadcastMessage(ProjectConstants.PREFIX + "§7Der Spieler §c" + player.getName() + " §7ist neu§8! ( #" + NumberFormat.getInstance().format(Integer.parseInt(Double.toString(newRecord).replace(".0", ""))) + " )");

            potionPlayer.getGeneralStats().setTokens(10000.0D);
            potionPlayer.getGeneralStats().setShards(10.0D);

            player.sendMessage("§r");
            player.sendMessage(ProjectConstants.PREFIX + "§7Du hast §c10 Shards §7und §c10.000 Tokens §7erhalten§8.");
            player.sendMessage(ProjectConstants.PREFIX + "§7Nutze §c/shop §7um dir Items zu kaufen§8.");

            player.getInventory().setHeldItemSlot(0);
            player.getInventory().clear();
            player.getInventory().setHelmet(ItemBuilder.of(Material.IRON_HELMET).withName("§8» §cStarterhelm").withEnchantment(Enchantment.DURABILITY, 1));
            player.getInventory().setChestplate(ItemBuilder.of(Material.IRON_CHESTPLATE).withName("§8» §cStarterbrustplatte").withEnchantment(Enchantment.DURABILITY, 1));
            player.getInventory().setLeggings(ItemBuilder.of(Material.IRON_LEGGINGS).withName("§8» §cStarterhose").withEnchantment(Enchantment.DURABILITY, 1));
            player.getInventory().setBoots(ItemBuilder.of(Material.IRON_BOOTS).withName("§8» §cStarterstiefel").withEnchantment(Enchantment.DURABILITY, 1));
            player.getInventory().setItem(0, ItemBuilder.of(Material.IRON_SWORD).withName("§8» §cStarterschwert").withEnchantment(Enchantment.DURABILITY, 1).withEnchantment(Enchantment.DAMAGE_ALL, 2));
            player.getInventory().setItem(1, ItemBuilder.of(Material.COOKED_BEEF).withName("§8» §cStarteressen").withAmount(16));
            player.getInventory().setItem(2, ItemBuilder.of(Material.GOLDEN_APPLE).withName("§8» §cStarterapfel").withAmount(4));
            player.getInventory().setItem(3, ItemBuilder.of(Material.BOW).withName("§8» §cStarterbogen").withEnchantment(Enchantment.DURABILITY, 1));
            player.getInventory().setItem(9, ItemBuilder.of(Material.ARROW).withName("§8» §cStarterpfeil").withAmount(16));
        }

    }

}
