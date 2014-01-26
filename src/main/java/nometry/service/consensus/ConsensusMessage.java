package nometry.service.consensus;

import nometry.entity.Message;

public class ConsensusMessage extends Message 
{
	Lease lease;
	long ballotNumber;
	long instanceNumber;
	
	public ConsensusMessage() 
	{
	    super();
	}

	public ConsensusMessage(String message) 
	{
		super(message);
	}

	public Lease getLease() {
		return lease;
	}

	public void setLease(Lease lease) {
		this.lease = lease;
	}

	public long getBallotNumber() {
		return ballotNumber;
	}

	public void setBallotNumber(long ballotNumber) {
		this.ballotNumber = ballotNumber;
	}

	public long getInstanceNumber() {
		return instanceNumber;
	}

	public void setInstanceNumber(long instanceNumber) {
		this.instanceNumber = instanceNumber;
	}

}
