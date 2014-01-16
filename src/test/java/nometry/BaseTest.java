package nometry;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations={"file:src/test/java/nometry/applicationContext.xml"})
public class BaseTest extends AbstractTestNGSpringContextTests {}
