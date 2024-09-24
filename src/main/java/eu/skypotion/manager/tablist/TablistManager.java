package eu.skypotion.manager.tablist;


import eu.skypotion.PotionPlugin;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TablistManager {

    PotionPlugin plugin;

    Scoreboard scoreboard;
    Map<String, Team> teamMap;

    public TablistManager(PotionPlugin plugin) {
        this.plugin = plugin;
        this.scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        this.teamMap = new HashMap<>();

        int teamBefore = 0;
        for (PermissionGroup group : PermissionsEx.getPermissionManager().getGroupList()) {
            final String name;
            if (teamBefore < 10) {
                name = "0" + teamBefore + group.getName();
            } else {
                name = teamBefore + group.getName();
            }
            teamBefore++;
            Team scoreboardTeam = scoreboard.registerNewTeam(Objects.equals(group.getName(), "default") ? "99default" : name);
            scoreboardTeam.setPrefix(group.getPrefix().replace("&", "§"));
            scoreboardTeam.setDisplayName(group.getSuffix().replace("&", "§"));
            teamMap.put(group.getName(), scoreboardTeam);
        }
        for (Map.Entry<String, Team> teamEntry : teamMap.entrySet()) {
            System.out.println("Team: " + teamEntry.getKey() + " - " + teamEntry.getValue().getName());
        }
    }

    public void setRank(Player player) {
        String group = PermissionsEx.getUser(player).getGroupsNames()[0];
        Team team = teamMap.get(group);
        if (team == null) {
            team = scoreboard.getTeam("99default");
        }
        team.addPlayer(player);
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.setScoreboard(scoreboard);
            updateTablistHeaderAndFooter(onlinePlayer);
        });
    }

    public void updateTablistHeaderAndFooter(Player player) {
        sendTab(player,
                "§r\n" +
                        "§8» §5§lSkyPotion§8.§5§lEU §8(§71§8.§78 §8- §71§8.§720§8) §8«\n" +
                        "§r\n" +
                        "§7Online§8: §a" + plugin.getServer().getOnlinePlayers().size() + "§8/§a" + plugin.getServer().getMaxPlayers() + "§8 ┃ §7Rekord§8: §a0§r\n§r",
                "§r\n" +
                        "§7Discord§8: §chttps://discord.gg/7ZzQ3QJ§r\n" +
                        "§7Twitter§8: §c@SkyPotionEU§r\n" +
                        "§7Store§8: §cstore.skypotion.eu§r\n" +
                        "§7Forum§8: §cforum.skypotion.eu§r\n" +
                        "§7Bewerben§8: §capply.skypotion.eu§r\n" +
                        "§r");
    }

    private void sendTab(Player player, String head, String foot) {
        IChatBaseComponent header = new ChatMessage(head);
        IChatBaseComponent footer = new ChatMessage(foot);
        PacketPlayOutPlayerListHeaderFooter tabList = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field headerField = tabList.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(tabList, header);
            Field footerField = tabList.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(tabList, footer);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        CraftPlayer cp = (CraftPlayer) player;
        cp.getHandle().playerConnection.sendPacket(tabList);
    }

    public String getRankPrefix(Player player) {
        String group = PermissionsEx.getUser(player).getGroupsNames()[0];
        return teamMap.get(group).getPrefix();
    }

    public String getRankPrefixScoreboard(Player player) {
        return PermissionsEx.getUser(player).getGroupsNames()[0];
    }

    public String getRankPrefixChat(Player player) {
        String group = PermissionsEx.getUser(player).getGroupsNames()[0];
        return teamMap.get(group).getSuffix();
    }

}