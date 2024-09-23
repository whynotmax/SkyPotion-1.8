package eu.skypotion.util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;

@UtilityClass
public class DateUtil {

    public String formatDate(long millis) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(millis);
    }

}
