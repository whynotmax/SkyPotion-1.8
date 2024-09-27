package eu.skypotion.discord.command;

import eu.skypotion.discord.DiscordBot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public abstract class DiscordSlashCommand extends ListenerAdapter {

    DiscordBot bot;
    String name;
    String description;
    OptionData[] options;

    public DiscordSlashCommand(DiscordBot bot, String name, String description, OptionData[] options) {
        this.bot = bot;
        this.name = name;
        this.description = description;
        this.options = options;
    }

    public abstract void execute(SlashCommandInteractionEvent event);

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals(name)) {
            execute(event);
        }
    }
}
