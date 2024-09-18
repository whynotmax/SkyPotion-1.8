package eu.skypotion.mongo.player.model.stats;

import eu.koboo.en2do.repository.entity.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Stats {

    int kills;
    int deaths;

    int oneVsOneWins;
    int oneVsOneLosses;

    double tokens;
    double shards;



    /**
     * Create a new Stats object
     * @return          The new Stats object
     */
    @Transient
    public static Stats create() {
        return new Stats(0, 0, 0, 0, 1000.0D, 0.0D);
    }

}
