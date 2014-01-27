package nometry.service;

import nometry.entity.Node;

import org.springframework.stereotype.Service;

@Service
/**
 * These services are local to a single node and are triggered by network communication with the node. 
 * These services are sub-services of the larger global services. 
 * Requests must be authenticated as coming from another node in the cluster.
 * 
 * @author eduthie
 *
 */
public class LocalManagementService 
{
    public Node create(String replicaGroupId)
    {
    	return null;
    }
}
