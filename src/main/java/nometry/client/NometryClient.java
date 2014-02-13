package nometry.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import nometry.entity.Message;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * Sends requests to remote nodes, marshalling the requests and sending the message.
 * 
 * @author eduthie
 *
 */
public class NometryClient
{
    public static final Logger log = Logger.getLogger(NometryClient.class);
    
    private int port;
    private String host;

    public NometryClient() {}

    public NometryClient(int port, String host)
    {
        this.port = port;
        this.host = host;
    }
    
    public void sendMessageToAllNodes(final Message message)
    {
    	
    }

    public void sendMessage(final Message message)
    {
        log.info("Starting client connection process");
        
        ChannelFactory factory =
                new NioClientSocketChannelFactory(
                      Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool());
  
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
  
        bootstrap.setPipelineFactory(new ClientPipelineFactory(message));
          
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
    
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
         
        log.info("client connection complete");
        
        future.awaitUninterruptibly();
        if (!future.isSuccess()) 
        {
             log.error("client failed to connect");
        }
        future.getChannel().getCloseFuture().awaitUninterruptibly();
        factory.releaseExternalResources();
        
        log.info("client resources released");
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }
}
