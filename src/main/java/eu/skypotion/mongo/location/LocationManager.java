package eu.skypotion.mongo.location;

import eu.skypotion.mongo.location.model.MongoLocation;
import eu.skypotion.mongo.location.repository.MongoLocationRepository;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LocationManager {

    MongoLocationRepository repository;
    Map<String, MongoLocation> locationCache;

    public LocationManager(MongoLocationRepository repository) {
        this.repository = repository;
        this.locationCache = this.repository.findAll().stream().collect(HashMap::new, (map, location) ->
                map.put(location.getName().toLowerCase(), location), HashMap::putAll);
    }

    public MongoLocation get(String name) {
        return locationCache.get(name.toLowerCase());
    }

    public void save(MongoLocation location) {
        repository.save(location);
        locationCache.put(location.getName().toLowerCase(), location);
    }

    public void delete(MongoLocation location) {
        repository.delete(location);
        locationCache.remove(location.getName().toLowerCase());
    }

    public void delete(String name) {
        MongoLocation location = locationCache.get(name.toLowerCase());
        if (location != null) {
            delete(location);
        }
    }

    public void saveAll() {
        for (MongoLocation location : locationCache.values()) {
            repository.save(location);
        }
    }


}
