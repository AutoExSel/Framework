

import static com.autoexsel.webdriver.WebDriverManager.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "features/", glue = "autoexsel/cucumber/", tags = { "@verifyStepDef" }
//		, dryRun=true
)
public class WebTestRunner extends AbstractTestNGCucumberTests {
	@BeforeSuite
	public void setup() {
//		printMissingLocators(LocatorType.XPath);
		String dbPassword = db().getEncryptedPassword("TESTPASSWORD");
	}
	@AfterSuite
	public void teardown() {
		closeExecution();
	}
}
