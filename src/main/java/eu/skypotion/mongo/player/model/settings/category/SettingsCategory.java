package eu.skypotion.mongo.player.model.settings.category;

import eu.skypotion.mongo.player.model.settings.Settings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SettingsCategory {

    GENERAL("General", "Allgemeine Einstellungen", Material.BOOK),
    CRATES("Crates", "Einstellungen für Crates", Material.CHEST),
    PVP_PVE("PvP/PvE", "Einstellungen für PvP/PvE", Material.DIAMOND_SWORD),
    TELEPORT("Teleport", "Einstellungen für Teleportanfragen", Material.ENDER_PEARL),
    OTHER("Sonstiges", "Sonstige Einstellungen", Material.TNT),
    ;

    String name;
    String description;
    Material displayMaterial;

    public static final SettingsCategory[] VALUES = values();

    public int howManySettingsInCategory() {
        int count = 0;
        for (Settings settings : Settings.VALUES) {
            if (settings.getCategory() == this) count++;
        }
        return count;
    }

    public Settings[] getSettingsInCategory() {
        Settings[] settings = new Settings[howManySettingsInCategory()];
        int index = 0;
        for (Settings setting : Settings.VALUES) {
            if (setting.getCategory() == this) {
                settings[index] = setting;
                index++;
            }
        }
        return settings;
    }

    public static SettingsCategory getByName(String name) {
        for (SettingsCategory category : VALUES) {
            if (category.getName().equalsIgnoreCase(name)) return category;
        }
        return null;
    }

}
