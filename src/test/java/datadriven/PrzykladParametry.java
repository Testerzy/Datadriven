package datadriven;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PrzykladParametry {

	private WebDriver driver;
	
	@BeforeTest(description="Laczenie sie z serwerem",alwaysRun=true)
	@Parameters({"seleniumHost","seleniumPort","browser","url"})
	public void setUp(String seleniumHost, int seleniumPort, String browser, String url) throws Exception {
		System.setProperty("webdriver.ie.driver", "c://selenium/webdriver/IEDriverServer.exe");
//		driver = new FirefoxDriver();
		driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
	}
	
	@AfterTest(description="Zamykanie drivera",alwaysRun=true)
	public void closeDriver(){
		driver.quit();
	}
	
	@Test
	@Parameters({"phrase"})
	public void makeTest(String phrase) throws Exception{
		driver.get("http://google.pl");
		driver.findElement(By.name("q")).sendKeys(phrase);
		driver.findElement(By.name("btnG")).click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleContains(phrase));
		assertTrue(driver.findElement(By.tagName("body")).getText().toLowerCase().contains(phrase));
	}
}

