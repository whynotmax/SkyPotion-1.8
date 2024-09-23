package eu.skypotion.mongo.player;

import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.mongo.player.model.season.SeasonStats;
import eu.skypotion.mongo.player.model.settings.Settings;
import eu.skypotion.mongo.player.model.stats.Stats;
import eu.skypotion.mongo.player.repository.PotionPlayerRepository;
import eu.skypotion.mongo.season.SeasonManager;

import java.util.ArrayList;
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
        PotionPlayer potionPlayer = new PotionPlayer(uniqueId, System.currentTimeMillis(), 0, new HashMap<>(), SeasonStats.forSeason(SeasonManager.CURRENT_SEASON), new Stats(), new ArrayList<>());
        potionPlayer.setUniqueId(uniqueId);
        for (Settings setting : Settings.VALUES) {
            potionPlayer.getSettings().put(setting, setting.getDefaultValue());
        }
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
