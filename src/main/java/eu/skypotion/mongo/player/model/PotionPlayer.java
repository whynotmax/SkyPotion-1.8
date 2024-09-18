package eu.skypotion.mongo.player.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import eu.skypotion.mongo.player.model.rank.PlayerRank;
import eu.skypotion.mongo.player.model.season.SeasonStats;
import eu.skypotion.mongo.player.model.stats.Stats;
import eu.skypotion.mongo.season.model.Season;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PotionPlayer {

    @Id
    UUID uniqueId;

    long lastSeen;
    long playTime;

    PlayerRank rank;

    SeasonStats seasonStats;
    Stats generalStats;

    @Transient
    public void updatePlayTime() {
        long currentTime = System.currentTimeMillis();
        playTime += currentTime - lastSeen;
        lastSeen = currentTime;
    }

    @Transient
    public void addKill() {
        generalStats.setKills(generalStats.getKills() + 1);
        seasonStats.setKills(seasonStats.getKills() + 1);
    }

    @Transient
    public void addDeath() {
        generalStats.setDeaths(generalStats.getDeaths() + 1);
        seasonStats.setDeaths(seasonStats.getDeaths() + 1);
    }

    @Transient
    public void addOneVsOneWin() {
        generalStats.setOneVsOneWins(generalStats.getOneVsOneWins() + 1);
        seasonStats.setOneVsOneWins(seasonStats.getOneVsOneWins() + 1);
    }

    @Transient
    public void addOneVsOneLoss() {
        generalStats.setOneVsOneLosses(generalStats.getOneVsOneLosses() + 1);
        seasonStats.setOneVsOneLosses(seasonStats.getOneVsOneLosses() + 1);
    }

    @Transient
    public void addTokens(double tokens) {
        generalStats.setTokens(generalStats.getTokens() + tokens);
    }

    @Transient
    public void addShards(double shards) {
        generalStats.setShards(generalStats.getShards() + shards);
    }

    @Transient
    public void addBattlePassLevel() {
        seasonStats.setBattlePassLevel(seasonStats.getBattlePassLevel() + 1);
    }

    @Transient
    public void setRank(PlayerRank rank) {
        this.rank = rank;
    }

    @Transient
    public double getKd() {
        if(generalStats.getDeaths() == 0) {
            return generalStats.getKills();
        }
        if (generalStats.getKills() == 0) {
            return 0;
        }
        return (double) generalStats.getKills() / generalStats.getDeaths();
    }

    @Transient
    public double getWinLossRatio() {
        if(generalStats.getOneVsOneLosses() == 0) {
            return generalStats.getOneVsOneWins();
        }
        if (generalStats.getOneVsOneWins() == 0) {
            return 0;
        }
        return (double) generalStats.getOneVsOneWins() / generalStats.getOneVsOneLosses();
    }

    @Transient
    public void resetSeasonStats(Season season) {
        seasonStats = SeasonStats.forSeason(season);
    }

    @Transient
    public void resetGeneralStats() {
        generalStats = Stats.create();
    }

}
