package eu.skypotion.discord.command.impl;

import eu.skypotion.discord.DiscordBot;
import eu.skypotion.discord.command.DiscordSlashCommand;
import eu.skypotion.mongo.betakey.BetaKeyManager;
import eu.skypotion.mongo.betakey.model.BetaKey;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class BetaKeyCommand extends DiscordSlashCommand {

    public BetaKeyCommand(DiscordBot bot) {
        super(bot, "betakey", "Command for managing beta keys.",
            new OptionData[] {
                new OptionData(OptionType.STRING, "action", "The action to perform", true),
                new OptionData(OptionType.STRING, "key", "The key to view info", false),
                new OptionData(OptionType.INTEGER, "value", "The amount of keys to generate", false),
                new OptionData(OptionType.STRING, "username", "Check if the user already got a beta key", false)
            }
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String action = event.getOption("action").getAsString();
        String key = event.getOption("key") != null ? event.getOption("key").getAsString() : null;
        Integer value = event.getOption("value") != null ? event.getOption("value").getAsInt() : null;
        String userName = event.getOption("username") != null ? event.getOption("username").getAsString() : null;

        BetaKeyManager betaKeyManager = getBot().getPlugin().getDatabaseManager().getBetaKeyManager();

        switch (action.toLowerCase()) {
            case "generate":
                if (value != null) {
                    BetaKey[] keys = betaKeyManager.generateKeys(value);
                    String keyString = Arrays.stream(keys).map(BetaKey::getKey).collect(Collectors.joining("\n"));
                    event.reply("Generated `" + value + "` beta keys.\n\n```" + keyString.substring(0, keyString.length()) + "```").setEphemeral(true).queue();
                } else {
                    event.reply("Please specify the number of keys to generate.").setEphemeral(true).queue();
                }
                break;
            case "delete":
                if (key != null) {
                    BetaKey betaKey = Arrays.stream(betaKeyManager.getKeys()).filter(bk -> bk.getKey().equals(key)).findFirst().orElse(null);
                    if (betaKey == null) {
                        event.reply("Key not found.").setEphemeral(true).queue();
                        return;
                    }
                    betaKeyManager.deleteKey(betaKey);
                    event.reply("Key `" + betaKey.getKey() + "` has been deleted.").setEphemeral(true).queue();
                } else {
                    event.reply("Please specify a key to delete.").setEphemeral(true).queue();
                }
                break;
            case "view":
                if (key != null) {
                    BetaKey betaKey = Arrays.stream(betaKeyManager.getKeys()).filter(bk -> bk.getKey().equals(key)).findFirst().orElse(null);
                    if (betaKey == null) {
                        event.reply("Key not found.").setEphemeral(true).queue();
                        return;
                    }
                    event.reply("Key `" + betaKey.getKey() + "` is " + (betaKey.isLinked() ? "linked to `" + betaKey.getLinkedTo() + "`" : "not linked.")).setEphemeral(true).queue();
                } else {
                    event.reply("Please specify a key to view.").setEphemeral(true).queue();
                }
                break;
            case "check":
                if (userName != null) { //TODO: add uuid fetching
                    BetaKey betaKey = Arrays.stream(betaKeyManager.getKeys()).filter(bk -> bk.getLinkedTo().equals(UUID.randomUUID())).findFirst().orElse(null);
                    if (betaKey == null) {
                        event.reply("User " + userName + " does not have a beta key.").setEphemeral(true).queue();
                    } else {
                        event.reply("User " + userName + " has beta key: `" + betaKey.getKey() + "`.").setEphemeral(true).queue();
                    }
                } else {
                    event.reply("Please specify a user to check.").setEphemeral(true).queue();
                }
                break;
            default:
                event.reply("Unknown action: " + action).setEphemeral(true).queue();
                break;
        }
    }
}
