package eu.skypotion.mongo;

import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;

public class DatabaseManager {

    MongoManager mongoManager;

    public DatabaseManager() {
        mongoManager = new MongoManager(Credentials.of("nigga", "nigga"));
    }

}
