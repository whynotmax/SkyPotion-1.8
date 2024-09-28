package eu.skypotion.mongo.location.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import eu.skypotion.mongo.location.model.MongoLocation;

import java.util.List;

@Collection("locations")
public interface MongoLocationRepository extends Repository<MongoLocation, String> {

    List<MongoLocation> findManyByAvailable(boolean available);

    List<MongoLocation> findManyByWarp(boolean warp);

}
