package eu.skypotion.mongo.player.model.settings;

import eu.skypotion.mongo.player.model.settings.category.SettingsCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum Settings {

    JOIN_MESSAGE("joinMessage", SettingsCategory.GENERAL, "Join Message", List.of("§7Erhalte die Standard-Join-Nachricht."), 0, List.of(0, 1), List.of("§aAktiviert", "§cDeaktiviert")),

    AUTO_BROADCAST("autoBroadcast", SettingsCategory.GENERAL, "Auto Broadcasts", List.of("§7Erhalte automatische Broadcasts."), 0, List.of(0, 1), List.of("§aAktiviert", "§cDeaktiviert")),

    CRATE_CONFIRMATION("crateConfirmation", SettingsCategory.CRATES, "Crate Bestätigung", List.of("§7Erhalte eine Bestätigung, wenn", "§7du eine Crate öffnen möchtest."), 0, List.of(0, 1), List.of("§aAktiviert", "§cDeaktiviert")),
    CRATE_ANIMATION("crateAnimation", SettingsCategory.CRATES, "Crate Animation", List.of("§7Sehe die Animation bei dem Öffnen einer Crate."), 0, List.of(0, 1), List.of("§aAktiviert", "§cDeaktiviert")),

    JOIN_TELEPORT("joinTeleport", SettingsCategory.TELEPORT, "Join Teleport", List.of("§7Werde automatisch zu deinem letzten", "§7Standort teleportiert, wenn du dich einloggst."), 0, List.of(0, 1), List.of("§aSpawn", "§cLetzter Standort")),

    //0 = all, 1 = friends, 2 = none
    TELEPORT_REQUEST("teleportRequest", SettingsCategory.TELEPORT, "Teleport Anfrage", List.of("§7Erhalte eine Anfrage, wenn sich jemand", "§7zu dir teleportieren möchte."), 0, List.of(0, 1, 2), List.of("§aAlle", "§eFreunde", "§cKeine")),
    ;

    String name;
    SettingsCategory category;
    String displayName;
    List<String> description;
    int defaultValue; //0 = true, 1 = false
    List<Integer> possibleValues;
    List<String> valueNames;

    public static final Settings[] VALUES = values();

    public static Settings getByName(String name) {
        for (Settings settings : VALUES) {
            if (settings.getDisplayName().equalsIgnoreCase(name)) return settings;
        }
        return null;
    }
}
