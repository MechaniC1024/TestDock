package com.PrestaShop.PrestaShopCookie;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;

public class NewTest {

	RemoteWebDriver driver;
	LoginPage loginPage;

	@Parameters({ "threadCountFirefox", "threadCountChrome" })
	@BeforeSuite
	public void beforeSuite(@Optional("1") String threadCountFirefox, @Optional("1") String threadCountChrome, ITestContext cont) {
		
		
		
		System.out.println("===========================================");
		System.out.println("Before Suite " + cont.getCurrentXmlTest().getParameter("browser") + " "
				+ cont.getCurrentXmlTest().getParallel().name() + " " + cont.getCurrentXmlTest().getThreadCount());

		if (cont.getCurrentXmlTest().getParameter("browser").equalsIgnoreCase("firefox")) {
			System.out.println("MAVEN PARAMETR: " + threadCountFirefox);
			cont.getCurrentXmlTest().setThreadCount(Integer.valueOf(threadCountFirefox));
		} else {
			System.out.println("MAVEN PARAMETR: " + threadCountChrome);
			cont.getCurrentXmlTest().setThreadCount(Integer.valueOf(threadCountChrome));
		}
		System.out.println("===========================================");
	}

	@Parameters({ "browser" })
	@BeforeMethod
	public void beforeMethod(String browser) {

		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		PageGenerator page = new PageGenerator(driver);
		loginPage = page.getInstance(LoginPage.class);

	}

	@Parameters({ "browser" })
	@Test
	public void test1(String browser, ITestContext cont) throws InterruptedException {

		System.out.println("===========================================");
		System.out.println("TEST");
		System.out.println("===========================================");
		System.out.println("browser = " + browser);
		System.out.println("threadCount = " + cont.getCurrentXmlTest().getThreadCount());
		System.out.println("===========================================");

		driver.manage().deleteAllCookies();

		File file = new File("src/test/resources/Cookie/Cookies.data");

		int i;
		Set<Cookie> cookies = new HashSet<>();
		try (FileInputStream fileRead = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fileRead);) {

			i = ois.readInt();
			while (i-- > 0) {
				cookies.add((Cookie) ois.readObject());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cookies.forEach(cookie -> System.out.println((cookie)));

		cookies.forEach(cookie -> driver.manage().addCookie(cookie));
		driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0");
		Thread.sleep(5000);
		driver.manage().deleteAllCookies();
	}

	@AfterMethod
	public void afterMethod() {

		driver.quit();
	}
}
