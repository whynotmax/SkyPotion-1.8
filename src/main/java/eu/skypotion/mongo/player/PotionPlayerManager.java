package eu.skypotion.mongo.player;

import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.mongo.player.model.rank.PlayerRank;
import eu.skypotion.mongo.player.model.season.SeasonStats;
import eu.skypotion.mongo.player.model.stats.Stats;
import eu.skypotion.mongo.player.repository.PotionPlayerRepository;
import eu.skypotion.mongo.season.SeasonManager;
import eu.skypotion.mongo.season.model.Season;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PotionPlayerManager {

    PotionPlayerRepository potionPlayerRepository;
    Map<UUID, PotionPlayer> potionPlayerMap;

    public PotionPlayerManager(PotionPlayerRepository potionPlayerRepository) {
        this.potionPlayerRepository = potionPlayerRepository;
        this.potionPlayerMap = new HashMap<>();
    }

    public PotionPlayer create(UUID uniqueId) {
        PotionPlayer potionPlayer = new PotionPlayer(uniqueId, PlayerRank.DEFAULT, SeasonStats.forSeason(SeasonManager.CURRENT_SEASON), new Stats());
        potionPlayer.setUniqueId(uniqueId);
        potionPlayerRepository.save(potionPlayer);
        potionPlayerMap.put(uniqueId, potionPlayer);
        return potionPlayer;
    }

    public void save(PotionPlayer potionPlayer) {
        potionPlayerRepository.save(potionPlayer);
        potionPlayerMap.put(potionPlayer.getUniqueId(), potionPlayer);
    }

    public void save(UUID uniqueId) {
        PotionPlayer potionPlayer = potionPlayerMap.get(uniqueId);
        if (potionPlayer != null) {
            potionPlayerRepository.save(potionPlayer);
        }
    }

    public PotionPlayer get(UUID uniqueId) {
        PotionPlayer potionPlayer = potionPlayerMap.get(uniqueId);
        if (potionPlayer == null) {
            potionPlayer = potionPlayerRepository.findFirstById(uniqueId);
            if (potionPlayer == null) {
                potionPlayer = create(uniqueId);
            }
        }
        return potionPlayer;
    }

}
