package eu.skypotion.exception;

import eu.skypotion.PotionPlugin;
import eu.skypotion.buyable.Buyable;
import eu.skypotion.mongo.player.model.PotionPlayer;
import org.jetbrains.annotations.NotNull;

public class InsufficientFundsException extends IllegalStateException {

    /**
     * Constructs an InsufficientFundsException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param s the String that contains a detailed message
     */
    public InsufficientFundsException(@NotNull PotionPlayer player, Buyable buyable) {
        super("Insufficient funds, %s needs %s coins".formatted(player.getUniqueId(), buyable.getPrice()));
    }

    /**
     * Constructs a new InsufficientFundsException with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link Throwable#getCause()} method).  (A {@code null} value
     *                is permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.5
     */
    public InsufficientFundsException(Throwable cause, @NotNull PotionPlayer player, Buyable buyable) {
        super("Insufficient funds, %s needs %s coins".formatted(player.getUniqueId(), buyable.getPrice()), cause);
    }
}
