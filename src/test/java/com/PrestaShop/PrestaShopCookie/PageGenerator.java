package com.PrestaShop.PrestaShopCookie;

import java.lang.reflect.Constructor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class PageGenerator {

	protected WebDriver driver;
	private final int implicitWait = 10;

	public PageGenerator(RemoteWebDriver driver) {
		this.driver = driver;
	}
	
	public void setURL(String url) {

		driver.get(url);
	}
	
	public <P extends PageGenerator> P getInstance(Class<P> pageClass) {
		try {
			Constructor<P> constructor = pageClass.getDeclaredConstructor(RemoteWebDriver.class);
			constructor.setAccessible(true);
			P sampleObject = (P) constructor.newInstance(driver);

			PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWait), sampleObject);
			return sampleObject;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}