package eu.skypotion.casino.mongo.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import eu.skypotion.casino.mongo.model.pass.CasinoPass;
import eu.skypotion.casino.mongo.model.stats.dailypot.DailyPotStats;
import eu.skypotion.casino.mongo.model.stats.roulette.RouletteStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CasinoPlayer {

    @Id
    UUID uniqueId;

    double gambleTokens;

    CasinoPass casinoPass;

    DailyPotStats dailyPotStats;
    RouletteStats rouletteStats;

    @Transient
    long joinedCasinoWorld;

}
