package eu.skypotion.util;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class ColorUtil {

    public Color getFromHex(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16)
        );
    }

}
