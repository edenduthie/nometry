package nometry.service;

import nometry.entity.Cluster;

import org.springframework.stereotype.Service;

@Service
/**
 * Apart from the Create, and Start service, each of these services can be performed by any node. 
 * These services perform actions which manage the cluster and can affect all the nodes. 
 * These services are triggered by a script/command line interface or via a web interface. 
 * These services perform authentication and authorisation.
 * 
 * @author eduthie
 *
 */
public class ClusterService 
{
    public Cluster create(String accountId, String name, Integer replicationLevel)
    {
    	return null;
    }
    
    public void stop(Cluster cluster)
    {
    	
    }
    
    public void start(String accountId, String name)
    {
    	
    }
}
