package eu.skypotion.mongo.betakey.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BetaKey {

    @Id
    String key;
    UUID linkedTo;

    @Transient
    public boolean isLinked() {
        return linkedTo != null;
    }

}
