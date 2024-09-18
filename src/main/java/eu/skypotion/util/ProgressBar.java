package eu.skypotion.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ProgressBar {

    public static Component createProgressBar(double currentValue, double maxValue, int totalBars) {

        if (currentValue > maxValue) {
            throw new IllegalArgumentException("Current value cannot be greater than the max value.");
        }
        // Calculate the percentage
        double percent = (currentValue / maxValue) * 100;

        int filledBars = (int) Math.round((percent / 100) * totalBars);
        int halfBars = totalBars / 2;

        if (currentValue <= 0) {
            StringBuilder progressBar = new StringBuilder();
            progressBar.append("<dark_gray>[");
            for (int i = 0; i < halfBars; i++) {
                progressBar.append("<red>");
                progressBar.append("|");
            }
            progressBar.append("<gray> ").append(String.format("%.2f", 0.00D)).append("% ");
            for (int i = halfBars; i < totalBars; i++) {
                progressBar.append("<red>");
                progressBar.append("|");
            }
            progressBar.append("<dark_gray>]");
            return MiniMessage.miniMessage().deserialize(progressBar.toString());
        }

        StringBuilder progressBar = new StringBuilder();
        progressBar.append("<dark_gray>[");
        for (int i = 0; i < halfBars; i++) {
            if (i < filledBars) {
                progressBar.append("<green>");
                progressBar.append("|");
            } else {
                progressBar.append("<red>");
                progressBar.append("|");
            }
        }
        progressBar.append("<gray> ").append(String.format("%.2f", percent)).append("% ");
        for (int i = halfBars; i < totalBars; i++) {
            if (i < filledBars) {
                progressBar.append("<green>");
                progressBar.append("|");
            } else {
                progressBar.append("<red>");
                progressBar.append("|");
            }
        }
        progressBar.append("<dark_gray>]");

        return MiniMessage.miniMessage().deserialize(progressBar.toString());
    }
}
