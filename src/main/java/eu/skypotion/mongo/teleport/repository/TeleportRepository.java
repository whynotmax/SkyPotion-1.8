package eu.skypotion.mongo.teleport.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import eu.skypotion.mongo.teleport.model.Teleport;

import java.util.List;

@Collection("teleports")
public interface TeleportRepository extends Repository<Teleport, String> {

    List<Teleport> findAllByAvailable(boolean available);

    List<Teleport> findAllByWarp(boolean warp);

}
