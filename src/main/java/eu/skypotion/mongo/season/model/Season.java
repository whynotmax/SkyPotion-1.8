package eu.skypotion.mongo.season.model;

import eu.koboo.en2do.repository.entity.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

}
