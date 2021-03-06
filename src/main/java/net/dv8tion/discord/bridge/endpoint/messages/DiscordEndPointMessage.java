package net.dv8tion.discord.bridge.endpoint.messages;

import net.dv8tion.discord.bridge.endpoint.EndPointMessage;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.guild.GenericGuildMessageEvent;

public class DiscordEndPointMessage extends EndPointMessage
{
    private GenericGuildMessageEvent discordEvent;
    private User discordUser;
    private Message discordMessage;

    public DiscordEndPointMessage(GenericGuildMessageEvent event)
    {
        super(event.getAuthor().getUsername(), null);
        this.setDiscordMessage(event.getMessage());
        this.discordEvent = event;
        this.discordUser = event.getAuthor();
    }

    public User getDiscordUser()
    {
        return discordUser;
    }

    public GenericGuildMessageEvent getDiscordEvent()
    {
         return discordEvent;
    }

    public Message getDiscordMessage()
    {
        return discordMessage;
    }

    public void setDiscordMessage(Message discordMessage)
    {
        String parsedMessage = discordMessage.getContent();
        for (Message.Attachment attach : discordMessage.getAttachments())
        {
            parsedMessage += "\n" + attach.getUrl();
        }

        this.setMessage(parsedMessage);
        this.discordMessage = discordMessage;
    }
}
