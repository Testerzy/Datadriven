package datadriven;

import static org.testng.Assert.assertTrue;

import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import au.com.bytecode.opencsv.CSVReader;

public class NewTest {

	public RemoteWebDriver driver;

	@Test
	public void test() throws Exception {
		CSVReader reader = new CSVReader(new FileReader("dane.csv"), '|', '"', 1);
		String [] line;
		while ((line = reader.readNext()) != null) {
			Reporter.log(line[0]+";"+line[1]+";"+line[2]);
			driver.navigate().to("http://silarobocza.pl");
			driver.findElement(By.id("keywords_present")).click();
			driver.findElement(By.id("keywords")).sendKeys(line[0]);
			new Select(driver.findElement(By.name("category"))).selectByVisibleText(line[1]);
			new Select(driver.findElement(By.name("region"))).selectByVisibleText(line[2]);
			driver.findElement(By.cssSelector("input[value=\"Szukaj ofert\"]")).click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), line[0]));
			assertTrue(driver.findElement(By.tagName("body")).getText().contains(line[0]));
		}
	}

	@BeforeTest
	public void beforeClass() throws Exception {
//		DesiredCapabilities cap = DesiredCapabilities.firefox();
//		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@AfterTest
	public void afterClass() {
		driver.quit();
	}

}
