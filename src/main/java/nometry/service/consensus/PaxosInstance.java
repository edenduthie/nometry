package nometry.service.consensus;

public class PaxosInstance 
{
    private Lease leaseIssued; // the lease eventually issued by the system
    private Lease leaseAccepted; // the last lease locally accepted during a Paxos accept phase
    private long ballotNumber; // the largest accepted ballot number
    private long i; // the number of the paxos instance
    
	public Lease getLeaseIssued() {
		return leaseIssued;
	}
	public void setLeaseIssued(Lease leaseIssued) {
		this.leaseIssued = leaseIssued;
	}
	public Lease getLeaseAccepted() {
		return leaseAccepted;
	}
	public void setLeaseAccepted(Lease leaseAccepted) {
		this.leaseAccepted = leaseAccepted;
	}
	public long getBallotNumber() {
		return ballotNumber;
	}
	public void setBallotNumber(long ballotNumber) {
		this.ballotNumber = ballotNumber;
	}
	public long getI() {
		return i;
	}
	public void setI(long i) {
		this.i = i;
	}
}
