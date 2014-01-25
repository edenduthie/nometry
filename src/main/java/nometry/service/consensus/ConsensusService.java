package nometry.service.consensus;

import nometry.client.NometryClient;
import nometry.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsensusService
{
	@Autowired NometryClient nometryClient;
	
	private PaxosInstance paxosInstance;
	private long maxTimeDrift; // NTP buffer time in millis
	
    public boolean initiateConsensus(Lease lease, int instanceNumber)
    {
    	long ballotNumber = generateBallotNumber();
        return false;
    }
    
    public long generateBallotNumber()
    {
    	return getPaxosInstance().getBallotNumber() + 1;
    }
    
    public boolean receiveMessage(Message message)
    {
        return false;
    }
    
    public boolean receivePrepare(Message message)
    {
        return false;
    }
    
    public boolean receiveAccept(Message mesage)
    {
        return false;
    }
    
    public boolean receiveLearn(Message message)
    {
        return false;
    }
    
    public Lease checkLocalState()
    {
        return null;    
    }
    
    public Lease getLease()
    {
        return null;
    }

	public PaxosInstance getPaxosInstance() {
		if( paxosInstance == null ) paxosInstance = new PaxosInstance();
		return paxosInstance;
	}

	public void setPaxosInstance(PaxosInstance paxosInstance) {
		this.paxosInstance = paxosInstance;
	}

	public long getMaxTimeDrift() {
		return maxTimeDrift;
	}

	public void setMaxTimeDrift(long maxTimeDrift) {
		this.maxTimeDrift = maxTimeDrift;
	}
    
    
}
