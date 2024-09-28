package eu.skypotion.mongo.player.model.ams.booster;

import eu.skypotion.mongo.player.model.ams.mode.AMSMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AMSBooster {

    long duration;

    AMSMode mode;

}
