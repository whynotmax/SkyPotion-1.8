package eu.skypotion.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    /**
     * Generate a random string with a given segment length, segment separator and how many segments
     * @param segmentLength     The length of each segment
     * @param segmentSeparator  The separator between each segment
     * @param howManySegments   How many segments to generate
     * @return                  The generated string
     * <br>
     * Example: <code>generateRandomString(5, '-', 3)</code> -> <code>"abcde-fghij-klmno"</code>
     */
    public String generateRandomString(int segmentLength, char segmentSeparator, int howManySegments) {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < howManySegments; i++) {
            for (int j = 0; j < segmentLength; j++) {
                builder.append(CHARACTERS.charAt((int) (Math.random() * CHARACTERS.length())));
            }
            if (i < howManySegments - 1) {
                builder.append(segmentSeparator);
            }
        }
        return builder.toString();
    }

}
