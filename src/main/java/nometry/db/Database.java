package nometry.db;

import java.io.IOException;

import nometry.entity.NometryEntity;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodb.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodb.model.CreateTableRequest;
import com.amazonaws.services.dynamodb.model.DeleteTableRequest;
import com.amazonaws.services.dynamodb.model.DescribeTableRequest;
import com.amazonaws.services.dynamodb.model.KeySchema;
import com.amazonaws.services.dynamodb.model.KeySchemaElement;
import com.amazonaws.services.dynamodb.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodb.model.TableDescription;
import com.amazonaws.services.dynamodb.model.TableStatus;

@Repository
public class Database 
{
	private static final Logger log = Logger.getLogger(Database.class);
	public AmazonDynamoDBClient dynamoDB;
	public DynamoDBMapper mapper;
	
	private Long readCapacityUnitsPerTable;
	private Long writeCapacityUnitsPerTable;
	
    public Database()
    {
    	try 
    	{
			AWSCredentials credentials = new PropertiesCredentials(this.getClass().getClassLoader().getResourceAsStream("AwsCredentials.properties"));
			dynamoDB = new AmazonDynamoDBClient(credentials);
		    mapper = new DynamoDBMapper(dynamoDB);
    	}
    	catch (IOException e) 
    	{
			log.error("Failed to load AWS credentials");
			log.error(e);
		}
    }
    
    public void put(Object bean)
    {
    	mapper.save(bean);
    }
    
    public void delete(Object bean)
    {
    	mapper.delete(bean);
    }
    
    public Object get(Object bean)
    {
    	if( bean instanceof NometryEntity )
    	{
    		NometryEntity nometryEntity = (NometryEntity) bean;
    	    return mapper.load(bean.getClass(), nometryEntity.retrieveHashKey(), nometryEntity.retrieveRangeKey());
    	}
    	else
    	{
    		log.error("Tried to retrieve an object that is not a NomertryEntity");
    		return null;
    	}
    }
    
    public void createTable(String tableName, String hash, String range)
    {
    	KeySchema schema = new KeySchema();
    	schema.setHashKeyElement(new KeySchemaElement().withAttributeName(hash).withAttributeType("S"));
    	schema.setRangeKeyElement(new KeySchemaElement().withAttributeName(range).withAttributeType("S"));
    	
        CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
                .withKeySchema(schema)
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(getReadCapacityUnitsPerTable())
                .withWriteCapacityUnits(getWriteCapacityUnitsPerTable()));
            TableDescription createdTableDescription = dynamoDB.createTable(createTableRequest).getTableDescription();
            log.info("Created Table: " + createdTableDescription);

            // Wait for it to become active
            waitForTableToBecomeAvailable(tableName);
    }
    
    private void waitForTableToBecomeAvailable(String tableName) {
        log.info("Waiting for " + tableName + " to become ACTIVE...");

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (10 * 60 * 1000);
        while (System.currentTimeMillis() < endTime) {
            try {Thread.sleep(1000 * 20);} catch (Exception e) {}
            try {
                DescribeTableRequest request = new DescribeTableRequest().withTableName(tableName);
                TableDescription tableDescription = dynamoDB.describeTable(request).getTable();
                String tableStatus = tableDescription.getTableStatus();
                log.info("  - current state: " + tableStatus);
                if (tableStatus.equals(TableStatus.ACTIVE.toString())) return;
            } catch (AmazonServiceException ase) {
                if (ase.getErrorCode().equalsIgnoreCase("ResourceNotFoundException") == false) throw ase;
            }
        }

        throw new RuntimeException("Table " + tableName + " never went active");
    }
    
    public void deleteTable(String tableName)
    {
    	DeleteTableRequest deleteTableRequest = new DeleteTableRequest().withTableName(tableName);
    	dynamoDB.deleteTable(deleteTableRequest);
    	waitForTableToBeDeleted(tableName);
    }
    
    private void waitForTableToBeDeleted(String tableName) {
        log.info("Waiting for " + tableName + " to be DELETED...");

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (10 * 60 * 1000);
        while (System.currentTimeMillis() < endTime) {
            try {Thread.sleep(1000 * 20);} catch (Exception e) {}
            try {
                DescribeTableRequest request = new DescribeTableRequest().withTableName(tableName);
                TableDescription tableDescription = dynamoDB.describeTable(request).getTable();
                String tableStatus = tableDescription.getTableStatus();
                log.info("  - current state: " + tableStatus);
            } catch (AmazonServiceException ase) {
                return;
            }
        }

        throw new RuntimeException("Table " + tableName + " was never deleted");
    }

	public Long getReadCapacityUnitsPerTable() {
		return readCapacityUnitsPerTable;
	}

	public void setReadCapacityUnitsPerTable(Long readCapacityUnitsPerTable) {
		this.readCapacityUnitsPerTable = readCapacityUnitsPerTable;
	}

	public Long getWriteCapacityUnitsPerTable() {
		return writeCapacityUnitsPerTable;
	}

	public void setWriteCapacityUnitsPerTable(Long writeCapacityUnitsPerTable) {
		this.writeCapacityUnitsPerTable = writeCapacityUnitsPerTable;
	}
}
