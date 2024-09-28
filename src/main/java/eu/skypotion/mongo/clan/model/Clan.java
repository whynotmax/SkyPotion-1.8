package eu.skypotion.mongo.clan.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.skypotion.mongo.clan.model.rank.ClanRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Clan {

    @Id
    String name;
    String tag;

    UUID owner;

    List<ClanRank> ranks;
    Map<UUID, ClanRank> members;


}
