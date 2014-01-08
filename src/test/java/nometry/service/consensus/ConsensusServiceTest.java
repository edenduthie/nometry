package nometry.service.consensus;

import nometry.BaseTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConsensusServiceTest extends BaseTest
{
    @Autowired ConsensusService consensusService;
    
    @Test
    public void testWiring()
    {
    	Assert.assertEquals(consensusService.getMaxTimeDrift(),1000);
    }
    
    @Test
    public void testGenerateBallotNumberNoneExists()
    {
    	consensusService.setPaxosInstance(null);
    	Assert.assertEquals(consensusService.generateBallotNumber(),1l);
    }
    
    @Test
    public void testGenerateBallotNumberInstanceExists()
    {
    	PaxosInstance paxosInstance = new PaxosInstance();
    	paxosInstance.setBallotNumber(100);
    	consensusService.setPaxosInstance(paxosInstance);
    	Assert.assertEquals(consensusService.generateBallotNumber(),101l);
    }
}
