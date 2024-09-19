package eu.skypotion.manager.perk;

import eu.skypotion.PotionPlugin;
import eu.skypotion.exception.AlreadyBoughtException;
import eu.skypotion.exception.InsufficientFundsException;
import eu.skypotion.mongo.player.model.PotionPlayer;
import eu.skypotion.perks.Perk;

import java.util.Map;

public class PerkManager {

    public boolean buyPerk(PotionPlayer player, Perk perk) throws InsufficientFundsException, AlreadyBoughtException {
        boolean hasPerk = player.getPerkByName(perk.getName()) != null;
        if (hasPerk) {
            throw new AlreadyBoughtException(player, perk);
        }
        double price = perk.getPrice();
        double tokens = player.getGeneralStats().getTokens();
        if (price > tokens) {
            throw new InsufficientFundsException(player, perk);
        }
        player.removeTokens(price);
        player.addPerk(perk);
        return true;
    }

    public boolean hasPerk(PotionPlayer player, Perk perk) {
        return player.getBoughtPerks().contains(perk);
    }




}
