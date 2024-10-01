package eu.skypotion.mongo;

import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import eu.skypotion.casino.mongo.CasinoPlayerManager;
import eu.skypotion.casino.mongo.repository.CasinoPlayerRepository;
import eu.skypotion.crates.CrateManager;
import eu.skypotion.crates.animation.CrateAnimation;
import eu.skypotion.crates.repository.CrateRepository;
import eu.skypotion.mongo.betakey.BetaKeyManager;
import eu.skypotion.mongo.betakey.repository.BetaKeyRepository;
import eu.skypotion.mongo.location.LocationManager;
import eu.skypotion.mongo.location.repository.MongoLocationRepository;
import eu.skypotion.mongo.player.PotionPlayerManager;
import eu.skypotion.mongo.player.repository.PotionPlayerRepository;
import eu.skypotion.mongo.season.SeasonManager;
import eu.skypotion.mongo.season.repository.SeasonRepository;
import eu.skypotion.mongo.teleport.TeleportManager;
import eu.skypotion.mongo.teleport.repository.TeleportRepository;
import lombok.Getter;
import org.bson.codecs.Codec;
import org.reflections.Reflections;

@Getter
public class DatabaseManager {

    MongoManager mongoManager;

    SeasonRepository seasonRepository;
    PotionPlayerRepository potionPlayerRepository;
    TeleportRepository teleportRepository;
    CrateRepository crateRepository;
    CasinoPlayerRepository casinoPlayerRepository;
    MongoLocationRepository locationRepository;
    BetaKeyRepository betaKeyRepository;

    PotionPlayerManager potionPlayerManager;
    TeleportManager teleportManager;
    SeasonManager seasonManager;
    CrateManager crateManager;
    LocationManager locationManager;
    BetaKeyManager betaKeyManager;

    public DatabaseManager() {
        mongoManager = new MongoManager(Credentials.of("mongodb://keinepixel:r7M3LHbAVxq9uYX5Jdn6gsSFk4DfUGt2@45.81.232.200:27017/", "nigga"));

        Reflections codecReflections = new Reflections("eu.skypotion.mongo.codec");
        codecReflections.getSubTypesOf(Codec.class).forEach(codecClass -> {
            try {
                mongoManager = mongoManager.registerCodec((Codec<?>) codecClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        seasonRepository = mongoManager.create(SeasonRepository.class);
        potionPlayerRepository = mongoManager.create(PotionPlayerRepository.class);
        teleportRepository = mongoManager.create(TeleportRepository.class);
        crateRepository = mongoManager.create(CrateRepository.class);
        casinoPlayerRepository = mongoManager.create(CasinoPlayerRepository.class);
        locationRepository = mongoManager.create(MongoLocationRepository.class);
        betaKeyRepository = mongoManager.create(BetaKeyRepository.class);

        potionPlayerManager = new PotionPlayerManager(potionPlayerRepository);
        seasonManager = new SeasonManager(seasonRepository);
        teleportManager = new TeleportManager(teleportRepository);
        crateManager = new CrateManager(crateRepository);
        locationManager = new LocationManager(locationRepository);
        betaKeyManager = new BetaKeyManager(betaKeyRepository);
    }

}
