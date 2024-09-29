package eu.skypotion.mongo.betakey.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import eu.skypotion.mongo.betakey.model.BetaKey;

import java.util.UUID;

@Collection("betakey")
public interface BetaKeyRepository extends Repository<BetaKey, String> {

    BetaKey findFirstByLinkedTo(UUID linkedTo);

}
