package eu.skypotion.mongo;

import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import eu.skypotion.mongo.player.PotionPlayerManager;
import eu.skypotion.mongo.player.repository.PotionPlayerRepository;
import eu.skypotion.mongo.season.SeasonManager;
import eu.skypotion.mongo.season.repository.SeasonRepository;

public class DatabaseManager {

    MongoManager mongoManager;

    SeasonRepository seasonRepository;
    PotionPlayerRepository potionPlayerRepository;

    PotionPlayerManager potionPlayerManager;
    SeasonManager seasonManager;

    public DatabaseManager() {
        mongoManager = new MongoManager(Credentials.of("nigga", "nigga"));

        seasonRepository = mongoManager.create(SeasonRepository.class);
        potionPlayerRepository = mongoManager.create(PotionPlayerRepository.class);

        //potionPlayerManager = new PotionPlayerManager(potionPlayerRepository)
        seasonManager = new SeasonManager(seasonRepository);
    }

}
