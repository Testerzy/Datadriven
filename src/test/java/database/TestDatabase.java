package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestDatabase {

	WebDriver driver;
	Connection con = null;

	@BeforeTest
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("http://192.168.1.42/?action=login");
	}

	@AfterTest
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void test() throws InterruptedException {
		try {
			con = DriverManager.getConnection("jdbc:mysql://192.168.1.42:3306/bledy?user=root&password=");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT email, pass FROM users LIMIT 0,5");
			while (rs.next()) {
				System.out.println("Login: " + rs.getString("login"));
				System.out.println("Pass: " + rs.getString("pass"));
				driver.findElement(By.id("f_login")).clear();
				driver.findElement(By.id("f_login")).sendKeys(rs.getString("login"));
				driver.findElement(By.id("f_pass")).clear();
				driver.findElement(By.name("f_pass")).sendKeys(rs.getString("pass"));
				driver.findElement(By.xpath("//input[@type='submit']")).click();
				new WebDriverWait(driver, 10).until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),"Jesteś zalogowany jako:"));
				Thread.sleep(2000);
				AssertJUnit.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Jesteś zalogowany jako: "	+ rs.getString("login")));
				driver.findElement(By.linkText("Wyloguj się")).click();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
