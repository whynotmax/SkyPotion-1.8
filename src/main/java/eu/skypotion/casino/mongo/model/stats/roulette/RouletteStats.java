package eu.skypotion.casino.mongo.model.stats.roulette;

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
public class RouletteStats {

    long timesPlayed;

    long wins;
    long losses;

    long betsOnRed;
    long betsOnBlack;
    long betsOnGreen;

    double totalWon;
    double totalLost;

}
