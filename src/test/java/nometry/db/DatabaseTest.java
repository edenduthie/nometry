package nometry.db;

import nometry.BaseTest;
import nometry.Generator;
import nometry.entity.Cluster;
import nometry.entity.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DatabaseTest extends BaseTest
{
	@Autowired Database db;
	
	@Test
    public void testConstructor()
    {
    	Assert.assertNotNull(db.dynamoDB);
    }
	
	@Test
	public void testPutDeleteCluster()
	{
		Cluster cluster = Generator.cluster();
		db.put(cluster);
		Cluster result = (Cluster) db.get(cluster);
		Assert.assertEquals(result, cluster);
		db.delete(cluster);
	}
	
	@Test
	public void testPutDeleteNode()
	{
		Node node = Generator.node();
		db.put(node);
		Node result = (Node) db.get(node);
		Assert.assertEquals(result, node);
		db.delete(node);
	}
	
	public void testCreateDeleteTable()
	{
		String tableName = "TestTable";
		db.createTable(tableName,"Id","AttributeId");
		db.deleteTable(tableName);
	}
}
