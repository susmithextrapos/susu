package com.extraPOSTest.pageObjects.inventory.maintenance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchaseGroupPage {

	WebDriver driver;
	
	@FindBy(xpath = "//table[@class='p-datatable-scrollable-body-table']/tbody/tr/td[1]/div/div")
	private WebElement allPurchaseGroupList;
	
	@FindBy(xpath = "//div[contains(text(),'The  category field is required.')]")
	private WebElement validationForEmptyCatCode;
	
	
	
	
	
	public PurchaseGroupPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}




	public List<WebElement> allPurchaseGroupList() {
		// TODO Auto-generated method stub
		return driver.findElements(By.xpath("//table[@class='p-datatable-scrollable-body-table']/tbody/tr/td[1]/div/div"));
	}




	public void sendPurchaseGroupCode(int k, String purchaseGroupCode) {

		driver.findElement(By.xpath("//*[@id='purchaseGroup_ET_100"+k+"']")).sendKeys(purchaseGroupCode);
		
	}




	public void sendPurchaseGroupDescription(int k, String description) {

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody[1]/tr["+k+"]/td[2]/div/input")).sendKeys(description);
		
	}




	public void clickVendorCodeLookUp(int k) {

		System.out.println("---->>> "+"//*[@id=\"selectedApplyToValue__ET_100"+k+"_AH_BTN\"]/div/img");
		driver.findElement(By.xpath("//*[@id=\"selectedApplyToValue__ET_100"+k+"_AH_BTN\"]/div/img")).click();
	}

	public void clickVendorSITECodeLookUp(int k) {

		driver.findElement(By.xpath("//*[@id=\"selectedApplyToValueSite__ET_100"+k+"_AH_BTN\"]/div/img")).click();
	}

	
	public void clickInActiveStatus(int k) {

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody[1]/tr["+k+"]/td[5]/div/div[1]/div[1]/div[2]")).click();
	}



	public boolean validationForCodeLimit() {


		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[contains(text(),'Max length 20 chars ')]"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
	}
	
	public boolean validationForDescriptionLimit() {

		
		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[contains(text(),'Max length 50 chars ')]"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
		
		
	}
	
	
	
public boolean validationForEmptyCode() {

		
		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[contains(text(),'The  purchase  group field is required.')]"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
		
	}

public WebElement validationForEmptyCode_WebElement() {

	return validationForEmptyCatCode;
	
	
}




public boolean validationForDuplicatePurchaseGroupCode() {

	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[contains(text(),'The  purchase  group already exits.')]"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;
	
}


public boolean validationForEmptyVendorCode() {
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[contains(text(),'The field must be a valid selected value.')]"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;
	
}

public void vendorCodeTextBox(int k) {
	
	driver.findElement(By.xpath("//*[@id='selectedApplyToValue__ET_100"+k+"']")).sendKeys(Keys.TAB,Keys.TAB);
}




public WebElement clickVendorCodeLookUp_WebElement(int k) {
	// TODO Auto-generated method stub
	return driver.findElement(By.xpath("//*[@id=\"selectedApplyToValue__ET_100"+k+"_AH_BTN\"]/div/img"));
}




public Object sendPurchaseGroupCode_WebElement(int k) {
	// TODO Auto-generated method stub
	return driver.findElement(By.xpath("//*[@id='purchaseGroup_ET_100"+k+"']"));
}




public void sendPurchaseGroupCodeForSearch(String purchaseGroupCode) {

	driver.findElement(By.xpath("//input[@label='Purchase Group']")).sendKeys(purchaseGroupCode);
}




public void sendPurchaseGroupDescriptionForSearch(String description) {

	driver.findElement(By.xpath("//input[@label='Purchase Group Description']")).sendKeys(description);
}




public void clickEnterKeyOnPurchaseGroupCode() {

	driver.findElement(By.xpath("//input[@label='Purchase Group']")).sendKeys(Keys.ENTER);
}

public String purchaseGroupCodeRow() {

	return driver.findElement(By.xpath("//tbody[@class='p-datatable-tbody']/tr[1]/td[1]/div/div")).getText();
}
public String PurchaseGroupDescriptionRow() {

	return driver.findElement(By.xpath("//tbody[@class='p-datatable-tbody']/tr[1]/td[2]/div")).getText();
}




public void clearPurchaseGroupCode() {

	driver.findElement(By.xpath("//input[@label='Purchase Group']")).sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
}




public void clearPurchaseGroupDescription() {

	driver.findElement(By.xpath("//input[@label='Purchase Group Description']")).sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
}


	}
