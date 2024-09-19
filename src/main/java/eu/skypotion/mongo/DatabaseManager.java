package eu.skypotion.mongo;

import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import eu.skypotion.mongo.player.PotionPlayerManager;
import eu.skypotion.mongo.player.repository.PotionPlayerRepository;
import eu.skypotion.mongo.season.SeasonManager;
import eu.skypotion.mongo.season.repository.SeasonRepository;
import eu.skypotion.mongo.teleport.TeleportManager;
import eu.skypotion.mongo.teleport.repository.TeleportRepository;
import lombok.Getter;

@Getter
public class DatabaseManager {

    MongoManager mongoManager;

    SeasonRepository seasonRepository;
    PotionPlayerRepository potionPlayerRepository;
    TeleportRepository teleportRepository;

    PotionPlayerManager potionPlayerManager;
    TeleportManager teleportManager;
    SeasonManager seasonManager;

    public DatabaseManager() {
        mongoManager = new MongoManager(Credentials.of("nigga", "nigga"));

        seasonRepository = mongoManager.create(SeasonRepository.class);
        potionPlayerRepository = mongoManager.create(PotionPlayerRepository.class);
        teleportRepository = mongoManager.create(TeleportRepository.class);

        potionPlayerManager = new PotionPlayerManager(potionPlayerRepository);
        seasonManager = new SeasonManager(seasonRepository);
        teleportManager = new TeleportManager(teleportRepository);
    }

}
