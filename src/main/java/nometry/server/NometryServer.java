package nometry.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import nometry.entity.Message;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Responds to requests via netty to take actions. Will require a different interface for HTTP.
 * Requests are unmarshalled and then sent to the appropraite service. The service will then
 * use the client to respond asynchronously.
 * 
 * @author eduthie
 */
public class NometryServer extends Thread
{
    public static final Logger log = Logger.getLogger(NometryServer.class);
    
    public static final ChannelGroup allChannels = new DefaultChannelGroup("nometryServer");

    private int port;

    private ChannelFactory factory;
    @Autowired ServerPipelineFactory serverPipelineFactory;
    
    public NometryServer() {}

    public NometryServer(int port)
    {
        this.port = port;
    }

    public void run()
    {
        log.info("server node is starting up");

        factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), 
                Executors.newCachedThreadPool());

        ServerBootstrap bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(serverPipelineFactory);

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        Channel channel = bootstrap.bind(new InetSocketAddress(port));
        allChannels.add(channel);
        
        log.info("server node is up and listening for connections on port " + port);
        
        addShutdownHook();
    }
    
    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }
    
    public void addShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                log.info("received shutdown signal");
                shutdown();
            }
        });
    }
    
    public void shutdown()
    {
        ChannelGroupFuture future = allChannels.close();
        future.awaitUninterruptibly();
        factory.releaseExternalResources();
        log.info("server shut down gracefully");
    }
    
    public void processMessage(Message message)
    {
        log.info("received message: " + message.toString());
    }
}
