package com.PrestaShop.PrestaShopCookie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.grid.selenium.GridLauncherV3;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Load {

	public static void main(String[] args) throws InterruptedException {

		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
		
		new PageGenerator((RemoteWebDriver)driver).getInstance(LoginPage.class).inputLogin("webinar.test@gmail.com")
				.inputPassword("Xcg7299bnSmMuRLp9ITw").clickOnLoginButton();
		
		WebDriverWait wait = new WebDriverWait(driver, 20);	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='ajax_running']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[@id='ajax_running']")));
				
		Set<Cookie> cookies = driver.manage().getCookies();
		
		System.out.println(cookies);

		File file = new File("src/test/resources/Cookie/Cookies.data");
		try {
			file.delete();
			file.createNewFile();
			FileOutputStream fileWrite = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fileWrite);
			oos.writeInt(cookies.size());
			for (Cookie ck : cookies) {
				oos.writeObject(ck);
			}
			oos.close();
			fileWrite.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println(cookies);
		driver.quit();
	}
}


/*
 * В основе переделать загрузку cookie
 * потом указать передачу параметров количества потоков они же
 * количество открытых браузеров. Удалить все коментарии.
 * Убрать Android.
 * WebDriver надо приводить к RemoteWebDriver.
 */






















