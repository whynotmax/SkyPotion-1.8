package eu.skypotion.mongo.teleport.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import eu.koboo.en2do.repository.methods.transform.Transform;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class Teleport {

    @Id
    String name;

    boolean warp;
    boolean available;

    String world;

    double x;
    double y;
    double z;

    float yaw;
    float pitch;

    @Transient
    public Location toBukkitLocation() {
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }

}
