package eu.skypotion.discord.listener;

import eu.skypotion.ProjectConstants;
import eu.skypotion.discord.DiscordBot;
import eu.skypotion.util.ColorUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildMemberUpdateBoostTimeListener extends ListenerAdapter {

    DiscordBot bot;

    public GuildMemberUpdateBoostTimeListener(DiscordBot bot) {
        this.bot = bot;
        this.bot.getLogger().log(java.util.logging.Level.INFO, "GuildMemberUpdateBoostTimeListener initialized");
    }

    @Override
    public void onGuildMemberUpdateBoostTime(@NotNull GuildMemberUpdateBoostTimeEvent event) {
        Member guildMember = event.getMember();
        Guild guild = event.getJDA().getGuildById(ProjectConstants.BOT_GUILD_ID);
        if (guild == null) return;

        if (guildMember.getTimeBoosted() != null) {
            if (event.getOldTimeBoosted() == null) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Vielen Dank für deinen Boost!");
                embed.setDescription(guildMember.getAsMention() + " hat den Server geboostet! Vielen Dank!\nDu kannst nun die Boost-Vorteile nutzen!\n\nÖffne ein Ticket, um die Vorteile einzulösen!");
                embed.setColor(ColorUtil.getFromHex("#a31c43"));
                embed.setThumbnail(guild.getIconUrl());
                guild.getTextChannelById(ProjectConstants.BOT_BOOST_CHANNEL_ID).sendMessageEmbeds(embed.build()).queue();
            }
        }
    }
}
