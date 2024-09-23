package eu.skypotion.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtil {

    public String formatWithDecimalPlaces(double number, int decimalPlaces) {
        return String.format("%." + decimalPlaces + "f", number);
    }

}
