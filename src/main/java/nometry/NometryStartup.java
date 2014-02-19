package nometry;

import nometry.server.NometryServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class NometryStartup 
{
    @Autowired NometryServer nometryServer;
    
    private int port = -1;

	public static void main(String[] args) 
	{	    
        // starts the nometry server and the nometry web server
	    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    NometryStartup startup = new NometryStartup();
	    applicationContext.getAutowireCapableBeanFactory().autowireBean(startup);
	    startup.run(args);
	}
	
	public NometryStartup() {}
	
	public void parseArgs(String[] args)
	{
	    if( args.length > 1 )
	    {
	        this.port = new Integer(args[1]);
	    }
	}
	
	public void run(String[] args)
	{
	    parseArgs(args);
	    if( port != -1 ) nometryServer.setPort(port);
	    nometryServer.start();
	}

}
