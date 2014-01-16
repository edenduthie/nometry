package nometry;

import nometry.db.DatabaseAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class CreateDatabase extends BaseTest
{
    @Autowired DatabaseAdmin admin;
    
    public void createDatabase()
    {
    	admin.createTables();
    }
}
