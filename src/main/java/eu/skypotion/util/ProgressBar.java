package eu.skypotion.util;

public class ProgressBar {

    public static String createProgressBar(double currentValue, double maxValue, int totalBars) {

        if (currentValue > maxValue) {
            throw new IllegalArgumentException("Current value cannot be greater than the max value.");
        }
        // Calculate the percentage
        double percent = (currentValue / maxValue) * 100;

        int filledBars = (int) Math.round((percent / 100) * totalBars);
        int halfBars = totalBars / 2;

        if (currentValue <= 0) {
            StringBuilder progressBar = new StringBuilder();
            progressBar.append("§8[");
            for (int i = 0; i < halfBars; i++) {
                progressBar.append("§c");
                progressBar.append("|");
            }
            progressBar.append("§7 ").append(String.format("%.2f", 0.00D)).append("% ");
            for (int i = halfBars; i < totalBars; i++) {
                progressBar.append("§c");
                progressBar.append("|");
            }
            progressBar.append("§8]");
            return (progressBar.toString());
        }

        StringBuilder progressBar = new StringBuilder();
        progressBar.append("§8[");
        for (int i = 0; i < halfBars; i++) {
            if (i < filledBars) {
                progressBar.append("§a");
                progressBar.append("|");
            } else {
                progressBar.append("§c");
                progressBar.append("|");
            }
        }
        progressBar.append("§7 ").append(String.format("%.2f", percent)).append("% ");
        for (int i = halfBars; i < totalBars; i++) {
            if (i < filledBars) {
                progressBar.append("§a");
                progressBar.append("|");
            } else {
                progressBar.append("§c");
                progressBar.append("|");
            }
        }
        progressBar.append("§8]");

        return (progressBar.toString());
    }
}
