package eu.skypotion.casino.mongo.model.stats.dailypot;

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
public class DailyPotStats {

    long wins;
    long losses;

    double totalWon;
    double totalLost;

}
