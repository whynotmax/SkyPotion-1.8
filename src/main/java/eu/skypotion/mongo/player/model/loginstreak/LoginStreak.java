package eu.skypotion.mongo.player.model.loginstreak;

import eu.koboo.en2do.repository.entity.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginStreak {

    int streak;
    long lastLogin;

    public void incrementStreak() {
        streak++;
    }

    public void resetStreak() {
        streak = 0;
    }

    public void updateLastLogin() {
        lastLogin = System.currentTimeMillis();
    }

    @Transient
    public boolean hasStreak() {
        return streak > 0;
    }

    @Transient
    public boolean isSameDay() {
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - lastLogin;

        return difference < 86400000;
    }

    @Transient
    public LoginStreak.Result getResult() {
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - lastLogin;

        if (lastLogin == 0) {
            return LoginStreak.Result.INCREMENT;
        }
        if (difference < 86400000) { // Less than 24 hours
            return LoginStreak.Result.NONE;
        }
        if (difference < 172800000) { // Less than 48 hours and more than 24 hours
            return LoginStreak.Result.INCREMENT;
        }
        return LoginStreak.Result.RESET;

    }

    public enum Result {

        INCREMENT,
        RESET,
        NONE;

    }
}
