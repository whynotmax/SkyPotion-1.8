package eu.skypotion.mongo.location.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class MongoLocation {

    @Id
    String name;

    String world;
    double x;
    double y;
    double z;
    float yaw;
    float pitch;

    boolean warp;
    boolean available;

    @Transient
    public Location toLocation() {
        return new Location(org.bukkit.Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

}
