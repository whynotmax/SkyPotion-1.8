package eu.skypotion.mongo.teleport;

import eu.skypotion.mongo.teleport.model.Teleport;
import eu.skypotion.mongo.teleport.repository.TeleportRepository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TeleportManager {

    Map<String, Teleport> teleportMap;
    TeleportRepository teleportRepository;

    public TeleportManager(TeleportRepository teleportRepository) {
        this.teleportRepository = teleportRepository;
        this.teleportMap = this.teleportRepository.findAll().stream().collect(Collectors.toMap(Teleport::getName, Function.identity()));
    }

    public void save(Teleport teleport) {
        teleportRepository.save(teleport);
        teleportMap.put(teleport.getName(), teleport);
    }

    public Teleport get(String name) {
        Teleport teleport = teleportMap.get(name);
        if (teleport == null) {
            teleport = teleportRepository.findFirstById(name);
            if (teleport == null) {
                throw new NullPointerException("Teleport with name " + name + " not found.");
            }
        }
        return teleport;
    }

}
