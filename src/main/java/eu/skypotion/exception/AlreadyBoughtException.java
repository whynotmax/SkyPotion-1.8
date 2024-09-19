package eu.skypotion.exception;

import eu.skypotion.buyable.Buyable;
import eu.skypotion.mongo.player.model.PotionPlayer;

import java.security.PrivilegedActionException;

public class AlreadyBoughtException extends IllegalStateException {

    /**
     * Constructs an AlreadyBoughtException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public AlreadyBoughtException(PotionPlayer player, Buyable boughtObject) {
        super("%s already bought %s for %s coins".formatted(player.getUniqueId(), boughtObject, boughtObject.getPrice()));
    }

    /**
     * Constructs a new AlreadyBoughtException with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.5
     */
    public AlreadyBoughtException(PotionPlayer player, Buyable boughtObject, Throwable cause) {
        super("%s already bought %s for %s coins".formatted(player.getUniqueId(), boughtObject, boughtObject.getPrice()), cause);
    }
}
