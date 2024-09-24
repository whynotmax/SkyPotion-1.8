package eu.skypotion.manager.teleports.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TeleportRequest {

    UUID from;
    UUID to;
    boolean here;

    long time;

    public TeleportRequest(UUID from, UUID to) {
        this.from = from;
        this.to = to;
        this.here = false;
        this.time = System.currentTimeMillis();
    }

    public TeleportRequest(UUID from, UUID to, boolean here) {
        this.from = from;
        this.to = to;
        this.here = here;
        this.time = System.currentTimeMillis();
    }


}
