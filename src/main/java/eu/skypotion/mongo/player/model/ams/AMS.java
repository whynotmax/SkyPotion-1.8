package eu.skypotion.mongo.player.model.ams;

import eu.skypotion.mongo.player.model.ams.booster.AMSBooster;
import eu.skypotion.mongo.player.model.ams.mode.AMSMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AMS {

    long spawners;

    AMSMode mode;
    long value;

    List<AMSBooster> activeBoosters;

}
