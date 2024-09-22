package eu.skypotion.mongo.player.model.season;

import eu.koboo.en2do.repository.entity.Transient;
import eu.skypotion.mongo.season.model.Season;
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
public class SeasonStats {

    int currentSeason;

    double elo;

    int kills;
    int deaths;

    int oneVsOneWins;
    int oneVsOneLosses;

    int battlePassLevel;

    /**
     * Create a new SeasonStats object for the given season
     * @param season    The season
     * @return          The new SeasonStats object
     */
    @Transient
    public static SeasonStats forSeason(Season season) {
        return new SeasonStats(season.getSeason(), 100.0D, 0, 0, 0, 0, 0);
    }

    @Transient
    public void addElo(double elo) {
        this.elo += elo;
    }

    @Transient
    public void removeElo(double elo) {
        this.elo -= elo;
    }

    @Transient
    public void addDeath() {
        this.deaths++;
    }

    @Transient
    public void addKill() {
        this.kills++;
    }
}
