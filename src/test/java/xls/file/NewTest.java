package xls.file;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class NewTest {
	
	private WebDriver driver;
	
	@BeforeTest
	public void beforeMethod(){
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("http://poczta.interia.pl");
	}

	@AfterTest
	public void afterMethod() {
		driver.quit();
	}

	@Test(dataProvider = "DP")
	public void test(String email, String passwd) {
		driver.findElement(By.id("formEmail")).clear();
		driver.findElement(By.id("formEmail")).sendKeys(email);
		driver.findElement(By.id("formPassword")).clear();
		driver.findElement(By.id("formPassword")).sendKeys(passwd);
		driver.findElement(By.id("formSubmit")).click();
	}
	
	@DataProvider(name = "DP")
	public Object[][] createData() throws Exception {
		Object[][] retObjArr = getExcelData("TestData.xls", "Arkusz2");
		return (retObjArr);
	}
	
	private String[][] getExcelData(String xlPath, String shtName) throws Exception {
		String[][] tabArray = null;
		Workbook workbk = Workbook.getWorkbook(new FileInputStream("TestData.xls"));
		Sheet sht = workbk.getSheet(shtName);
		int totalNoOfCols = sht.getColumns();
		int totalNoOfRows = sht.getRows();
		
		tabArray = new String[totalNoOfRows - 1][totalNoOfCols];
		for (int i= 1 ; i < totalNoOfRows; i++) {
			for (int j=0; j < totalNoOfCols; j++) {
				tabArray[i-1][j] = sht.getCell(j, i).getContents();
			}

		}
		
		return (tabArray);
	}

}
