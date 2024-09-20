package eu.skypotion.exception;

import com.avaje.ebean.validation.NotNull;
import eu.skypotion.shop.buyable.Buyable;
import eu.skypotion.mongo.player.model.PotionPlayer;
import lombok.NonNull;

public class InsufficientFundsException extends IllegalStateException {

    public InsufficientFundsException(@NonNull PotionPlayer player, Buyable buyable) {
        super("Insufficient funds, %s needs %s coins".formatted(player.getUniqueId(), buyable.getPrice()));
    }

    public InsufficientFundsException(Throwable cause, @NonNull PotionPlayer player, Buyable buyable) {
        super("Insufficient funds, %s needs %s coins".formatted(player.getUniqueId(), buyable.getPrice()), cause);
    }
}
