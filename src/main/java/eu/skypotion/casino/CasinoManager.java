package eu.skypotion.casino;

import eu.skypotion.casino.mongo.CasinoPlayerManager;
import eu.skypotion.mongo.DatabaseManager;
import lombok.Getter;

@Getter
public class CasinoManager {

    CasinoPlayerManager playerManager;

    public CasinoManager(DatabaseManager databaseManager) {
        this.playerManager = new CasinoPlayerManager(databaseManager.getCasinoPlayerRepository());
    }

}
