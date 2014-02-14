package nometry.client;

import nometry.entity.Message;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ClientHandler extends SimpleChannelHandler
{
    public static final Logger log = Logger.getLogger(ClientHandler.class);
    
    public Message message;
    
    public ClientHandler(Message message)
    {
        this.message = message;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
    {
        log.info("channel connected");
        
        Channel ch = e.getChannel();

        ChannelBuffer messageBuffer = ChannelBuffers.buffer(message.getBytes().length);
        messageBuffer.writeBytes(message.getBytes());
        
        ChannelFuture f = ch.write(messageBuffer);

        f.addListener(new ChannelFutureListener()
        {
            public void operationComplete(ChannelFuture future)
            {
                log.info("message written to channel: " + message);
                Channel ch = future.getChannel();
                ch.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
    {
        e.getCause().printStackTrace();
        log.error("Failed to send message " + message);
        e.getChannel().close();
    }
}
