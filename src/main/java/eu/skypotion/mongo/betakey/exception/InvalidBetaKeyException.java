package eu.skypotion.mongo.betakey.exception;

import java.util.UUID;

public class InvalidBetaKeyException extends Exception {

    public InvalidBetaKeyException(String key, UUID uuid) {
        super("The key " + key + " is invalid, and user " + uuid + " could not be linked to it.");
    }
}
