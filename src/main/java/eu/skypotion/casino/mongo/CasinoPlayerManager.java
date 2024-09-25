package eu.skypotion.casino.mongo;

import eu.skypotion.casino.mongo.model.CasinoPlayer;
import eu.skypotion.casino.mongo.model.stats.dailypot.DailyPotStats;
import eu.skypotion.casino.mongo.model.stats.roulette.RouletteStats;
import eu.skypotion.casino.mongo.repository.CasinoPlayerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CasinoPlayerManager {

    CasinoPlayerRepository casinoPlayerRepository;
    Map<UUID, CasinoPlayer> casinoPlayerMap;

    public CasinoPlayerManager(CasinoPlayerRepository casinoPlayerRepository) {
        this.casinoPlayerRepository = casinoPlayerRepository;
        this.casinoPlayerMap = new HashMap<>();
    }

    public CasinoPlayer create(UUID uniqueId) {
        CasinoPlayer casinoPlayer = new CasinoPlayer(uniqueId, 0, null, new DailyPotStats(), new RouletteStats());
        casinoPlayerRepository.save(casinoPlayer);
        casinoPlayerMap.put(uniqueId, casinoPlayer);
        return casinoPlayer;
    }

    public void save(CasinoPlayer casinoPlayer) {
        casinoPlayerRepository.save(casinoPlayer);
        casinoPlayerMap.put(casinoPlayer.getUniqueId(), casinoPlayer);
    }

    public void save(UUID uniqueId) {
        CasinoPlayer casinoPlayer = casinoPlayerMap.get(uniqueId);
        if (casinoPlayer != null) {
            casinoPlayerRepository.save(casinoPlayer);
        }
    }

    public CasinoPlayer get(UUID uniqueId) {
        CasinoPlayer casinoPlayer = casinoPlayerMap.get(uniqueId);
        if (casinoPlayer == null) {
            casinoPlayer = casinoPlayerRepository.findFirstById(uniqueId);
            if (casinoPlayer == null) {
                casinoPlayer = create(uniqueId);
            }
        }
        return casinoPlayer;
    }

}
