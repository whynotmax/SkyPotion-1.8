package eu.skypotion.inventory;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.ui.simple.SimpleUI;
import eu.skypotion.ui.size.UISize;
import eu.skypotion.util.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RangInfoInventory extends SimpleUI {

    public RangInfoInventory(PotionPlugin plugin) {
        super(plugin, ProjectConstants.PREFIX + "§cRanginfo", UISize.SIX_ROWS);

        fill(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 7).withName(" "));
        fillBorders(ItemBuilder.of(Material.STAINED_GLASS_PANE).withDurability((short) 15).withName(" "));

        setItem(39, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §b§lG§3§lUARDIAN §8( #§c1§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §czwei Plots§7 claimen§8.",
                        "§8» §7Du kannst §czwei Homes§7 setzen§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cGuardian-Kit§8.",
                        "§8» /§ctrash §8● §7Öffne den §cMüllbeutel§8.",
                        "§8» /§cfeed §8● §7Füttere dich§8.",
                        "§8» /§cworkbench §8● §7Öffne die §cWerkbank§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o."
                )
        );

        setItem(40, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §e§lVORT§6§lΣ§e§lX §8( #§c2§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §cdrei Plots§7 claimen§8.",
                        "§8» §7Du kannst §cdrei Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cVortex-Kit§8.",
                        "§8» /§cptime §8● §7Ändere deine Zeit auf dem Server§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o."
                )
        );

        setItem(41, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §5§lU§d§lL§5§lT§d§lR§5§lA §8( #§c3§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §cdrei Plots§7 claimen§8.",
                        "§8» §7Du kannst §cfünf Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cUltra-Kit§8.",
                        "§8» /§crepair §8● §7Repariere ein Item ohne Repair-Token§8.",
                        "§8» /§cstack §8● §7Stacke deine Items§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o."
                )
        );

        setItem(28, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §d§lE§4§lX§b§lE§a§lC§6§lU§9§lT§2§lE §8( #§c4§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §cdrei Plots§7 claimen§8.",
                        "§8» §7Du kannst §cfünf Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cExecute-Kit§8.",
                        "§8» /§crucksack §8● §7Öffne deinen mobilen Rucksack§8.",
                        "§8» /§cinvsee §8● §7Sehe das Inventar deines Gegners§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o."
                )
        );

        setItem(29, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §a§lMASTER §8( #§c5§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §cfünf Plots§7 claimen§8.",
                        "§8» §7Du kannst §csechs Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cMaster-Kit§8.",
                        "§8» /§chat §8● §7Setze dir einen Hut auf§8.",
                        "§8» /§cnear §8● §7Finde Spieler in der Nähe§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o."
                )
        );

        setItem(31, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §2§lΣ§a§lX§2§lT§a§lR§2§lE§a§lM§2§lE §8( #§c6§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §csechs Plots§7 claimen§8.",
                        "§8» §7Du kannst §csieben Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cExtreme-Kit§8.",
                        "§8» /§cfly §8● §7Selbsterklärend§8.",
                        "§8» /§cheal §8● §7Heile dich§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o."
                )
        );

        setItem(33, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §5§lV§dΣ§5§lNUS §8( #§c7§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §csechs Plots§7 claimen§8.",
                        "§8» §7Du kannst §csieben Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cVenus-Kit§8.",
                        "§8» /§cblock §8● §7Gebe dir Blöcke§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o."
                )
        );

        setItem(34, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §b§lC§3§lH§b§lA§3§lM§b§lP§3§lI§b§lO§3§lN §8( #§c8§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §csechs Plots§7 claimen§8.",
                        "§8» §7Du kannst §csieben Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cChampion-Kit§8.",
                        "§8» /§cauktion §8● §7Starte eine Auktion§8.",
                        "§8» /§crand §8● §7Setze den Rand deines Plots§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o."
                )
        );

        setItem(11, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §c§lH§4§lΣ§c§lRO §8( #§c9§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §cacht Plots§7 claimen§8.",
                        "§8» §7Du kannst §c32 Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cHero-Kit§8.",
                        "§8» /§cjackpot start §8● §7Starte einen Jackpot§8.",
                        "§8» /§chbonus §8● §7Jede 5 Stunden §8x§c4 Crates§8.",
                        "§8» /§cnick §8● §7Bleibe anonym§8.",
                        "§8» /§cwerbung §8● §7Mache Werbung für dein Grundstück§8.",
                        "§8» /§cvday §8● §7Starte einen VoteDay§8.",
                        "§8» /§cvnight §8● §7Starte einen VoteNight§8.",
                        "§8» /§ckopf §8● §7Erhalte Köpfe§8.",
                        "§8» /§crecipe §8● §7Sehe die Rezepte aller Items§8.",
                        "§8» /§cumfrage §8● §7Starte eine Umfrage§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o.",
                        "§7§oDu kannst dir diesen Rang auch im §8§o/§c§oshop§7§o kaufen§8§o."
                )
        );

        setItem(13, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §e⚔ §6§lK§e§lI§6§lN§e§lG §6⚔ §8( #§c10§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §czehn Plots§7 claimen§8.",
                        "§8» §7Du kannst §c32 Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cKing-Kit§8.",
                        "§8» /§cskin §8● §7Ändere deinen Skin§8.",
                        "§8» /§cspeed §8● §7Werde schneller§8.",
                        "§8» /§cktp §8● §7Teleportiere dich zu Spielern§8.",
                        "§8» /§cjoinmsg §8● §7Setze deine eigene Join-Nachricht§8.",
                        "§8» /§cquitmsg §8● §7Setze deine eigene Quit-Nachricht§8.",
                        "§8» /§cvotekick §8● §7Starte einen Votekick§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang erspielen§8§o.",
                        "§7§oErhalte ihn durch §c§oVote-Points§7§o oder §c§oCrates§8§o.",
                        "§7§oDu kannst dir diesen Rang auch im §8§o/§c§oshop§7§o kaufen§8§o."
                )
        );

        setItem(15, ItemBuilder.of(Material.SKULL_ITEM)
                .withAmount(1)
                .withDurability((short) 3)
                .withSkullOwner("MHF_Chest")
                .withName("§8» §5§lPO§d§lTI§5§lON §8( #§c11§8 )")
                .withLore(
                        "§r",
                        "§8» §7Alle Vorteile des Ranges§8:",
                        "§r",
                        "§8» §7Du kannst dir §czwölf Plots§7 claimen§8.",
                        "§8» §7Du kannst §c32 Homes§7 setzen§8.",
                        "§8» §7Du kannst §af§2a§er§6b§ci§4g§7 schreiben§8.",
                        "§8» §7Du erhältst alle Prefixe §ckostenlos§8.",
                        "§r",
                        "§8» /§ckit §8● §7Erhalte dein §cPotion-Kit§8.",
                        "§8» /§crename §8● §7Setze den Namen eines Items§8.",
                        "§8» /§ccosmetics §8● §7Kosmetisches§8.",
                        "§8» /§cservergeschenk §8● §7Gebe ein kleines Geschenk an alle Online Spieler§8.",
                        "§8» /§cseen §8● §7Sehe Online-Aktivität von Spielern§8.",
                        "§8» /§cmyholo §8● §7Erstelle bis zu §cdrei§7 Hologramme auf deinem Plot§8.",
                        "§8» /§csign §8● §7Signiere Items§8.",
                        "§r",
                        "§7§oDu hast alle Vorteile der niedrigeren Ränge§8§o.",
                        "§r",
                        "§7§oDu kannst dir diesen Rang im §8§o/§c§oshop§7§o kaufen§8§o."
                )
        );
    }

    @Override
    public void open(Player player) {

    }

    @Override
    public void close(Player player) {

    }
}
