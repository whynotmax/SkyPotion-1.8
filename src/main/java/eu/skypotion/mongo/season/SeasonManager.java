package eu.skypotion.mongo.season;

import eu.skypotion.mongo.season.model.Season;
import eu.skypotion.mongo.season.repository.SeasonRepository;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SeasonManager {

    private static final long DEFAULT_SEASON_LENGTH = TimeUnit.DAYS.convert(30, TimeUnit.MILLISECONDS);

    public static Season CURRENT_SEASON;
    Map<Integer, Season> seasons;
    SeasonRepository seasonRepository;

    public SeasonManager(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
        this.seasonRepository.save(Season.NO_SEASON);
        CURRENT_SEASON = seasonRepository.findAll().stream().filter(Season::isActive).findFirst().orElse(null);
        this.seasons = seasonRepository.findAll().stream().collect(Collectors.toMap(Season::getSeason, Function.identity()));
    }

    public void createSeason(String name, String description, long start, long end) {
        Season season = Season.builder()
                .season(seasons.size() + 1)
                .name(name)
                .description(description)
                .start(start)
                .end(end)
                .active(false)
                .build();
        seasonRepository.save(season);
        seasons.put(season.getSeason(), season);
    }

    public void startSeason(int season) {
        endSeason();
        Season current = seasons.get(season);
        if (current == null) {
            return;
        }
        current.setActive(true);
        CURRENT_SEASON = current;
        seasonRepository.save(current);
    }

    public void endSeason() {
        CURRENT_SEASON.setActive(false);
        seasonRepository.save(CURRENT_SEASON);
        seasons.put(CURRENT_SEASON.getSeason(), CURRENT_SEASON);
        CURRENT_SEASON = null;
    }

    public Season getSeason(int season) {
        return seasons.get(season);
    }

    public Season getSeason(String name) {
        return seasons.values().stream().filter(season -> season.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Season getSeason(long time) {
        return seasons.values().stream().filter(season -> season.getStart() <= time && season.getEnd() >= time).findFirst().orElse(null);
    }

    public Season getSeason(long start, long end) {
        return seasons.values().stream().filter(season -> season.getStart() == start && season.getEnd() == end).findFirst().orElse(null);
    }

}
