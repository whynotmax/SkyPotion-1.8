package eu.skypotion.discord.command.impl;

import eu.skypotion.ProjectConstants;
import eu.skypotion.discord.DiscordBot;
import eu.skypotion.discord.command.DiscordSlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;

public class SetupCommand extends DiscordSlashCommand {

    public SetupCommand(DiscordBot bot) {
        super(bot, "setup", "Sets up all necessary stuff", new OptionData[]{
                new OptionData(OptionType.STRING, "what", "What to setup", true)
        });
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Setting up...").setEphemeral(true).queue(msg -> {
            String what = event.getOption("what").getAsString();
            if (what.isEmpty()) {
                msg.editOriginal("Please provide a valid setup option!").queue();
                return;
            }
            if (what.equals("rules")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Regelwerk");
                embed.setColor(Color.RED);
                embed.setDescription("""
                        **§1 - Allgemeines**
                        `1.1 - Beleidigungen, Rassismus, Sexismus, Mobbing und jegliche Art von Diskriminierung sind verboten.`
                        `1.2 - Das Verbreiten von illegalen Inhalten ist verboten.`
                        `1.3 - Das Verbreiten von Links zu anderen Discord-Servern ist verboten.`
                        `1.4 - Das Verbreiten von Links zu anderen Webseiten ist verboten. (Ausnahme: YouTube, Twitch, Twitter)`
                        `1.5 - Das Verbreiten von Links zu anderen sozialen Medien ist verboten.`
                        `1.6 - Das Verbreiten von Links zu anderen Plattformen ist verboten.`
                        
                        **§2 - Chat**
                        `2.1 - Das Spammen von Nachrichten ist verboten.`
                        `2.2 - Das Verwenden von Capslock ist verboten.`
                        `2.3 - Das Verwenden von Emojis in einer Nachricht ist auf maximal 5 beschränkt.`
                        `2.4 - Das Verwenden von Emojis in einem Namen ist verboten.`
                        
                        **§3 - Voice**
                        `3.1 - Das Betreten eines Voice-Channels ohne Erlaubnis ist verboten.`
                        `3.2 - Das Betreten eines Voice-Channels mit einem unangemessenen Namen ist verboten.`
                        
                        **§4 - Support**
                        `4.1 - Das Erstellen eines Tickets ohne Grund ist verboten.`
                        `4.2 - Das Erstellen eines Tickets mit einem unangemessenen Grund ist verboten.`
                        
                        **§5 - Sonstiges**
                        `5.1 - Das Verwenden von Bots ist nur in dafür vorgesehenen Channels erlaubt.`
                        
                        **§6 - Strafen**
                        `6.1 - Bei einem Verstoß gegen die Regeln wird eine angemessene Strafe verhängt.`
                        
                        **§7 - Schlusswort**
                        `7.1 - Die Regeln können jederzeit geändert werden.`
                        `7.2 - Das Team behält sich das Recht vor, Personen ohne Angabe von Gründen zu verwarnen oder zu bannen.`
                        
                        **§8 - Kontakt**
                        `8.1 - Bei Fragen oder Problemen wende dich an das Team.`
                        """);
                event.getGuild().getTextChannelById(ProjectConstants.BOT_RULES_CHANNEL_ID)
                        .sendMessageEmbeds(embed.build()).queue(RestAction.getDefaultSuccess());
                return;
            }
            msg.editOriginal("Invalid setup option!").queue();
        });
    }
}
