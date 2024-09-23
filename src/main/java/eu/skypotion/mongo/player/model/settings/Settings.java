package eu.skypotion.mongo.player.model.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum Settings {

    JOIN_MESSAGE("joinMessage", "Join Message", List.of("Erhalte die Standard-Join-Nachricht."), 0, List.of(0, 1)),

    CRATE_CONFIRMATION("crateConfirmation", "Crate Bestätigung", List.of("Erhalte eine Bestätigung, wenn du eine Crate öffnen möchtest."), 0, List.of(0, 1)),
    CRATE_ANIMATION("crateAnimation", "Crate Animation", List.of("Sehe die Animation bei dem Öffnen einer Crate."), 0, List.of(0, 1)),
    ;

    String name;
    String displayName;
    List<String> description;
    int defaultValue; //0 = true, 1 = false
    List<Integer> possibleValues;

    public static final Settings[] VALUES = values();

}
