package nometry;

import java.util.Calendar;

import nometry.entity.Cluster;
import nometry.entity.Node;

public class Generator 
{
    public static Cluster cluster()
    {
    	Cluster cluster = new Cluster();
    	cluster.setAccountId("237462348723");
    	cluster.setCreatedTimestamp(Calendar.getInstance().getTimeInMillis());
    	cluster.setId(null);
    	cluster.setName("cluster-test");
    	cluster.setReplicationLevel(1);
    	cluster.setStatus(Cluster.STATUS_NEW);
    	cluster.setUpdateTimestamp(Calendar.getInstance().getTimeInMillis());
    	return cluster;
    }

	public static Node node() 
	{
		Node node = new Node();
		node.setClusterId("23423423423423");
		node.setDnsName("dnsnode1.nometry.com");
		node.setId(null);
		node.setServerStatus(Node.STATUS_UNRESPONSIVE);
		return node;
	}
}
