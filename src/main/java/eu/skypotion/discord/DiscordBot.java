package eu.skypotion.discord;

import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.DatabaseManager;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.reflections.Reflections;

@Log
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DiscordBot {

    DatabaseManager databaseManager;
    JDA jda;

    public DiscordBot(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        JDABuilder builder = JDABuilder.createDefault(ProjectConstants.BOT_TOKEN);
        builder.enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));

        Reflections listenerReflections = new Reflections("eu.skypotion.discord.listener");
        listenerReflections.getSubTypesOf(net.dv8tion.jda.api.hooks.ListenerAdapter.class).forEach(listener -> {
            try {
                builder.addEventListeners(listener.getDeclaredConstructor(DiscordBot.class).newInstance(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            jda = builder.build();
            jda.awaitReady();
        } catch (Exception e) {
            log.log(java.util.logging.Level.SEVERE, "Discord Bot could not be started. Aborting... | " + e.getMessage());
            jda.shutdownNow();
        }

    }

}
