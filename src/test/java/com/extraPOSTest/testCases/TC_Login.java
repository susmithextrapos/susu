package com.extraPOSTest.testCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.extraPOSTest.pageObjects.LoginPage;
import com.extraPOSTest.resources.BaseClass;

public class TC_Login extends BaseClass{
	
	
	
	
	@Test
	public void loginTest() throws InterruptedException, IOException {
		
		LoginPage loginPage = new LoginPage(driver);
		driver.get(baseURL);
		loginPage.setUserName(userName);
		loginPage.setPassword(password);
		loginPage.clickLogInButton();
		
		String homPageValidationText = loginPage.homePageSuccessValidationText(); // in the homepage there should contain the text "Welcome to ExtraPOS!", this text is here validating as Success.
		boolean homePage =driver.getPageSource().contains(homPageValidationText);
		
		if(homePage) {
			
			logger.info("Login Successfully");
			Assert.assertTrue(true);
		}
		else {
			
			Thread.sleep(2000);
			logger.warn("login failed");
			getScreenShotPath("loginTest",driver);
			logger.warn("Screenshot Taken");
			Assert.assertTrue(false);
			
			
		}
	}

}
