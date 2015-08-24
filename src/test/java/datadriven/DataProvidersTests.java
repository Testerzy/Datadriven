package datadriven;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProvidersTests {

	public WebDriver driver;
	
	@BeforeTest(alwaysRun=true)
	public void setUp() throws Exception
	{
		driver = new FirefoxDriver();
	}
	
	@AfterTest(alwaysRun=true)
	public void tearDown() throws Exception
	{
		driver.quit();
	}
	
	
	@DataProvider(name="provider1", parallel=false)
	public Object[][] createData1() {
	 return new Object[][] {
	   { "Selenium" },
	   { "Testerzy.pl" },
	   { "Wiadomości TVN" }
	 };
	}
	
	@Test(dataProvider = "staticProvider2", dataProviderClass = StaticDataProvider.class)
	public void testDataProvider(String phrase) throws Exception{
		driver.get("http://google.pl");
		driver.findElement(By.name("q")).sendKeys(phrase);
		driver.findElement(By.name("btnG")).click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleContains(phrase));
		assertTrue(driver.findElement(By.tagName("body")).getText().contains(phrase));
	}
	
//	@Test(dataProvider = "provider1")
	public void testDataProviderLocal(String phrase) throws Exception{
		driver.get("http://google.pl");
		driver.findElement(By.name("q")).sendKeys(phrase);
		driver.findElement(By.name("btnG")).click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleContains(phrase));
		assertTrue(driver.findElement(By.tagName("body")).getText().contains(phrase));
	}
	
}
