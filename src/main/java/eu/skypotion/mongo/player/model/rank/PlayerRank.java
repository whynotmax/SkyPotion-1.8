package eu.skypotion.mongo.player.model.rank;

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

}
