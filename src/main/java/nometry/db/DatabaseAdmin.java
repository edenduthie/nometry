package nometry.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseAdmin 
{
    @Autowired Database db;
    
    public void createTables()
    {
        //db.createTable("Cluster","Id","AccountId");
        db.createTable("Node","Id","ClusterId");
    }
}
