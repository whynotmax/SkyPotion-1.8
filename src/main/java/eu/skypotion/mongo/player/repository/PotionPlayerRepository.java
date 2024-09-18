package eu.skypotion.mongo.player.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import eu.skypotion.mongo.player.model.PotionPlayer;

import java.util.UUID;

@Collection("potion_players")
public interface PotionPlayerRepository extends Repository<PotionPlayer, UUID> {
}
