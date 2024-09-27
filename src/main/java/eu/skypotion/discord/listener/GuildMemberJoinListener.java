package eu.skypotion.discord.listener;

import eu.skypotion.ProjectConstants;
import eu.skypotion.discord.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GuildMemberJoinListener extends ListenerAdapter {

    DiscordBot bot;

    public GuildMemberJoinListener(DiscordBot bot) {
        this.bot = bot;
        this.bot.getLogger().log(java.util.logging.Level.INFO, "GuildMemberJoinListener initialized");
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Member guildMember = event.getMember();
        Guild guild = event.getJDA().getGuildById(ProjectConstants.BOT_GUILD_ID);
        if (guild == null) return;
        if (guildMember.getUser().isBot()) return;
        guild.addRoleToMember(guildMember, guild.getRoleById(ProjectConstants.BOT_MEMBER_ROLE_ID)).queue(success -> {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Willkommen auf dem Discord-Server!");
            embed.setDescription(guildMember.getAsMention() + " ist dem Discord-Server beigetreten! Willkommen!\n\nBitte lies dir die Regeln durch und beachte diese!\nViel Spaß auf dem Server!\n\nWir sind nun **" + guild.getMembers().size() + " Mitglieder**!");
            embed.setColor(Color.GREEN.darker());
            embed.setThumbnail(guildMember.getUser().getAvatarUrl());
            guild.getTextChannelById(ProjectConstants.BOT_JOINS_CHANNEL_ID).sendMessageEmbeds(embed.build()).queue();
        });
    }
}
