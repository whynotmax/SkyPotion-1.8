package eu.skypotion.discord;

import eu.skypotion.PotionPlugin;
import eu.skypotion.ProjectConstants;
import eu.skypotion.mongo.DatabaseManager;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.bukkit.Bukkit;
import org.reflections.Reflections;

import java.util.logging.Logger;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DiscordBot {

    Logger logger;
    DatabaseManager databaseManager;
    PotionPlugin plugin;
    JDA jda;

    public DiscordBot(DatabaseManager databaseManager, PotionPlugin plugin) {
        this.databaseManager = databaseManager;
        this.plugin = plugin;
        this.logger = Logger.getLogger(DiscordBot.class.getName());
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

        Reflections commandReflections = new Reflections("eu.skypotion.discord.command.impl");
        commandReflections.getSubTypesOf(net.dv8tion.jda.api.hooks.ListenerAdapter.class).forEach(command -> {
            try {
                builder.addEventListeners(command.getDeclaredConstructor(DiscordBot.class).newInstance(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            jda = builder.build();
            jda.awaitReady();

            Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
                jda.getPresence().setActivity(Activity.of(Activity.ActivityType.PLAYING, "mit " + Bukkit.getOnlinePlayers().size() + " Spielern"));
            }, 0, 20 * 5L);

            CommandListUpdateAction commands = jda.updateCommands();

            commands = commands.addCommands(
                    Commands.slash("setup", "Setup the Discord Bot")
                            .addOption(OptionType.STRING, "what", "What to setup", true)
                            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
            );

            commands.queue();

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Discord Bot could not be started. Aborting... | " + e.getMessage());
            jda.shutdownNow();
        }



    }

}
