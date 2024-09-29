package eu.skypotion.runnables;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.util.NumberUtil;
import eu.skypotion.util.ProgressBar;
import eu.skypotion.util.StringUtil;
import lombok.Getter;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
@SuppressWarnings("LoopStatementThatDoesntLoop")
public class TebexGoalRunnable implements Runnable {

    PotionPlugin plugin;
    HolographicDisplaysAPI hologramApi;
    Hologram hologram;

    public TebexGoalRunnable(PotionPlugin plugin) {
        this.plugin = plugin;
        this.hologramApi = plugin.getHologramApi();
        this.hologram = hologramApi.createHologram(plugin.getDatabaseManager().getLocationManager().get("holo-tebex").toLocation());
        hologram.getLines().appendText("§8§m----------------------------§r");
        hologram.getLines().appendText("§r");
        hologram.getLines().appendText(ProjectConstants.PREFIX + "§cSpendenziel");
        hologram.getLines().appendText("§r");
        hologram.getLines().appendText("§8» §7Letzter Spender§8: §c-/-");
        hologram.getLines().appendText("§r");
        hologram.getLines().appendText(ProgressBar.createProgressBar(10, 30, 50));
        hologram.getLines().appendText("§r");
        hologram.getLines().appendText("§r");
        hologram.getLines().appendText("§8§m----------------------------§r");


        run();
        System.out.println("TebexGoalRunnable started");
    }

    @Override
    public void run() {
        double[] goal = getGoal();
        TextHologramLine lastDonator = (TextHologramLine) hologram.getLines().get(4);
        lastDonator.setText("§8» §7Letzter Spender§8: §c" + getLastDonator());
        TextHologramLine progressBar = (TextHologramLine) hologram.getLines().get(6);
        progressBar.setText(ProgressBar.createProgressBar(goal[1], goal[0], 50));
        TextHologramLine infoLine = (TextHologramLine) hologram.getLines().get(7);
        infoLine.setText("§8» §7Es wurden bereits §e" + NumberUtil.formatWithDecimalPlaces(goal[1], 2) + "€ §7von §e" + NumberUtil.formatWithDecimalPlaces(goal[0], 2) + "€ §7gespendet§8.");
        System.out.println("TebexGoalRunnable updated");
    }

    private String getLastDonator() {
        //TODO: Implement this
        return "-/-";
    }

    private double[] getGoal() {
        try {
            URL url = new URL("https://plugin.tebex.io/community_goals");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Tebex-Secret", ProjectConstants.TEBEX_SECRET);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("GET request successful");
                System.out.println(response);

                JsonArray jsonArray = new JsonParser().parse(response.toString()).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject goal = jsonArray.get(i).getAsJsonObject();
                    if (!goal.has("target") && goal.has("current")) {
                        return new double[]{30.0, 10.0};
                    }
                    double target = goal.get("target").getAsDouble();
                    double current = goal.get("current").getAsDouble();
                    System.out.println("Tebex goal: " + current + "/" + target);
                    return new double[]{target, current};
                }
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new double[]{30.0, 10.0};
    }
}
