package eu.skypotion.mongo.season.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class Season {

    @Id
    int season;

    String name;
    String description;

    long start;
    long end;

    boolean active;

    @Transient
    public static final Season NO_SEASON = new Season(3, "Keine Saison", "Keine Saison aktiv", System.currentTimeMillis(), System.currentTimeMillis()*2, true);

}
