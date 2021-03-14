package com.extraPOSTest.resources;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.spi.DirStateFactory.Result;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.extraPOSTest.pageObjects.PostLoginPage;
import com.extraPOSTest.pageObjects.security.UserManagementPage;

public class ConvertionAndValidation {

	UserManagementPage userManagementPage;

	public static String convertDoubletoString(String str) {

		double val = 0.0;
		String catCode = "";
		if (str.contains(".")) {

			val = Double.parseDouble(str);
			int code = (int) val;
			catCode = String.valueOf(code);
		}

		return catCode;

	}

	public boolean isUserNameExist(List<String> allUserNameList, String username) {

		boolean unameAlreadyExist_bool = false;
		for (int l = 0; l < allUserNameList.size(); l++) {
			if (allUserNameList.get(l).contains(username)) {

				unameAlreadyExist_bool = userManagementPage.userNameValidation1();
				Assert.assertTrue(unameAlreadyExist_bool, "No validation for User Name already exist ");
			}

		}

		return unameAlreadyExist_bool;
	}

	public static boolean containsWhiteSpace(String value) {
		boolean space = false;
		if (value != null) {

			for (int i = 0; i < value.length(); i++) {

				if (value.charAt(i) == ' ') {
					space = true;
				}

			}
		}
		return space;
	}

	public boolean emailChecks(String str) {

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (str == null)
			return false;
		return pat.matcher(str).matches();
	}
	
	
	public boolean isAnySpecialCharacter(String text) {
		
		 Pattern pattern = Pattern.compile("[^a-zA-Z0-9_]");
	        Matcher matcher = pattern.matcher(text);
	        boolean isStringContainsSpecialCharacter = matcher.find();
	        if(isStringContainsSpecialCharacter)
	          return true;
	        else    
	         return false;
	}
	
	public boolean isOnlySpecialCharacter(String str) {
		
		 
	      String specialCharacters = "[" + "-/@#!*$%^&.'+={}()"+ "]+" ;
	      
	      if ( str.matches(specialCharacters)) {
	    	  return true;
	      } else {
	    	  return false;
	      }
	}
	
	public int generateRandomNumber() {
		Random random = new Random();
		
		int randomNumber = random.nextInt(10000);
		return randomNumber;
	}

	
	public int numberOfDecimalPlaces(String text) {
		
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;
		return decimalPlaces;
	}
public int numberOfIntegerPlaces(String text) {
		
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;
		return integerPlaces;
	}



public void lookUpSearch(String SearchBy, String value, WebDriver driver,JavascriptExecutor js) throws InterruptedException {
	PostLoginPage postLoginPage = new PostLoginPage(driver);
	boolean result;
	if(SearchBy.equalsIgnoreCase("code")) 
	{

		try 
		{
			postLoginPage.sendSearchLookUp(value);
		} 
		catch (Exception e)
		{

			js.executeScript("arguments[0].click();", postLoginPage.sendSearchLookUp_WebElement());

		}
		System.out.println("Search by code");
		postLoginPage.clickDropDown();
		System.out.println("........... clicked dropdown");
		postLoginPage.clickCode();
		Thread.sleep(2000);
		
		postLoginPage.clickSearchButton();
		Thread.sleep(3000);
		String codeValueIs = postLoginPage.searchResultCode();
		System.out.println("codeValueIs.........." + codeValueIs);

		if (codeValueIs.equalsIgnoreCase(value))
		{
			System.out.println("found search result by code");
			try {
				postLoginPage.clickOnSearchResult();
			} catch (Exception e) {

				js.executeAsyncScript("arguments[0].click();",postLoginPage.clickOnSearchResult_WebElement());
			}
		
	    }
		else
		{
			    postLoginPage.closeIcon();
				result = false;
				Assert.assertTrue(false, "Search Code is not found");
		}
	}
	else //this is for search by description 
	{
		
		try 
		{
			postLoginPage.sendSearchLookUp(value);
		} 
		catch (Exception e)
		{

			js.executeScript("arguments[0].click();", postLoginPage.sendSearchLookUp_WebElement());

		}
System.out.println("Search by description ");
		postLoginPage.clickDropDown();
System.out.println("........... clicked dropdown");
		postLoginPage.clickDescription();

		postLoginPage.clickSearchButton();
		Thread.sleep(3000);
		
		if (postLoginPage.searchResultDescription().equalsIgnoreCase(value)) 
		{
System.out.println("found search result by description");
			try {
				postLoginPage.clickOnSearchResult();
			} catch (Exception e) {
				js.executeAsyncScript("arguments[0].click();",postLoginPage.clickOnSearchResult_WebElement());

			}

		} else {
			postLoginPage.closeIcon();
			result = false;
			Assert.assertTrue(false, "Search Description is not found");
		}
	}
	
}

}
