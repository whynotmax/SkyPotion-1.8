package eu.skypotion.mongo.season.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import eu.skypotion.mongo.season.model.Season;

@Collection("seasons")
public interface SeasonRepository extends Repository<Season, Integer> {

}
