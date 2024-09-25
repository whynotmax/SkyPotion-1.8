package eu.skypotion.casino;

import eu.skypotion.casino.mongo.CasinoPlayerManager;
import eu.skypotion.mongo.DatabaseManager;

public class CasinoManager {

    CasinoPlayerManager playerManager;

    public CasinoManager(DatabaseManager databaseManager) {
        this.playerManager = new CasinoPlayerManager(databaseManager.getCasinoPlayerRepository());
    }

}
