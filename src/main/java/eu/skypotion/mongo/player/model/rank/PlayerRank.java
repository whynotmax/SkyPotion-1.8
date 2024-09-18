package eu.skypotion.mongo.player.model.rank;

import eu.koboo.en2do.repository.entity.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerRank {

    String name;
    String prefix;
    String suffix;

    boolean isTeam;


    @Transient
    public static PlayerRank DEFAULT = new PlayerRank("Default", "§7Spieler§8 • §7", "", false);

}
