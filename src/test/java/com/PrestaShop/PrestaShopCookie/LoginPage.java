package com.PrestaShop.PrestaShopCookie;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class LoginPage extends PageGenerator {

	private String url = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0";

	@CacheLookup
	@FindBy(xpath = "//input[@id = 'email']")
	private WebElement loginField;

	@CacheLookup
	@FindBy(xpath = "//input[@id = 'passwd']")
	private WebElement passwordField;

	@CacheLookup
	@FindBy(xpath = "//button[@name = 'submitLogin']")
	private WebElement buttonLogin;

	private LoginPage(RemoteWebDriver driver) {
		super(driver);
		setURL(url);
	}

	@Step("Ввод логина.")
	public LoginPage inputLogin(String login) {

		loginField.clear();
		loginField.sendKeys(login);
		return this;

	}

	@Step("Ввод пароля.")
	public LoginPage inputPassword(String password) {

		passwordField.clear();
		passwordField.sendKeys(password);
		return this;

	}

	@Step("Нажатие кнопки логин.")
	public void clickOnLoginButton() {

		buttonLogin.click();
	}
}
