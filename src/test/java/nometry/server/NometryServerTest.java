package nometry.server;

import nometry.BaseTest;
import nometry.client.NometryClient;
import nometry.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NometryServerTest extends BaseTest
{
    @Autowired NometryServer server;
    @Autowired NometryClient client;
    
    @Test
    public void testClientServer() throws InterruptedException
    {
        server.run();
        String message = "This is a test";
        client.sendMessage(new Message().setMessageString(message));
        Thread.sleep(50);
    }
}
