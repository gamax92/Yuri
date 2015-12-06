package net.dv8tion.discord.bridge.endpoint;

import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.talkable.Group;
import net.dv8tion.discord.Bot;

public class DiscordEndPoint extends EndPoint
{
    private String serverId;
    private String groupId;

    public DiscordEndPoint(EndPointInfo info)
    {
        super(EndPointType.DISCORD);
        this.serverId = info.getConnectorId();
        this.groupId = info.getChannelId();
    }

    public String getServerId()
    {
        return serverId;
    }

    public Server getServer()
    {
        return Bot.getAPI().getServerById(serverId);
    }

    public String getGroupId()
    {
        return groupId;
    }

    public Group getGroup()
    {
        return Bot.getAPI().getGroupById(groupId);
    }

    @Override
    public EndPointInfo toEndPointInfo()
    {
        return new EndPointInfo( this.connectionType, this.serverId, this.groupId);
    }

    @Override
    public void sendMessage(String message)
    {
        if (!connected)
            throw new IllegalStateException("Cannot send message to disconnected EndPoint! EndPoint: " + this.toEndPointInfo().toString());
        getGroup().sendMessage(message);
    }

    @Override
    public void sendMessage(EndPointMessage message)
    {
        if (!connected)
            throw new IllegalStateException("Cannot send message to disconnected EndPoint! EndPoint: " + this.toEndPointInfo().toString());
        switch (message.getType())
        {
            case DISCORD:
                getGroup().sendMessage(message.getDiscordMessage());
                break;
            default:
                sendMessage(String.format("<%s> %s", message.getSenderName(), message.getMessage()));
        }
    }

}
