package nometry.server;

import nometry.entity.Message;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("serverHandler")
@Scope("prototype")
public class ServerHandler extends SimpleChannelHandler
{
    public static final Logger log = Logger.getLogger(ServerHandler.class);

    @Autowired NometryServer nometryServer;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
    {
        String message = (String) e.getMessage();
        Message result = new Message(message);
        nometryServer.processMessage(result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
    {
        log.error(e.getCause().getMessage());
        Channel ch = e.getChannel();
        ch.close();
    }
    
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
          NometryServer.allChannels.add(e.getChannel());
    }
}
