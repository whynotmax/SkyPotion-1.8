package eu.skypotion.casino.mongo.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import eu.skypotion.casino.mongo.model.CasinoPlayer;

import java.util.UUID;

@Collection("casino-players")
public interface CasinoPlayerRepository extends Repository<CasinoPlayer, UUID> {
}
