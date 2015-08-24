package datadriven;

import org.testng.annotations.DataProvider;

//public class DataProviders {

	public class StaticDataProvider{
		@DataProvider(name="staticProvider1",parallel=false)
		public static Object[][] createData1() {
			 return new Object[][] {
			   { "Selenium" },
			   { "Testerzy.pl" },
			   { "Wiadomości TVN" },
			   { "szkolenia Selenium" },
			   { "pogoda na weekend" },
			 };
		}
		
		@DataProvider(name="staticProvider2",parallel=false)
		public static Object[][] createData2() {
			return new Object[][] {
					{ "Katowice" },
					{ "Warszawa" },
					{ "Gdańsk" },
					{ "Wrocław" },
					{ "Kraków" },
			};
		}
	}
	
//}
