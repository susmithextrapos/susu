package com.extraPOSTest.pageObjects.security;


import java.util.List;

import javax.xml.xpath.XPath;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class UserManagementPage {

	WebDriver driver;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[3]/div[2]/div/button")
	private WebElement addButton;
	
	@FindBy(xpath = "//*[@id=\"UserName\"]")
	private WebElement userName;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[1]/div[2]/input")
	private WebElement firstName;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[1]/div[3]/input")
	private WebElement lastName;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[1]/input")
	private WebElement email;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[2]/input")
	private WebElement password;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[3]/input")
	private WebElement confirmPassword;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[5]/input")
	private WebElement address1;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[6]/input")
	private WebElement address2;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[7]/input")
	private WebElement address3;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[9]/input")
	private WebElement city;
	
	@CacheLookup
	@FindBy (xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[10]/input")
	private WebElement country;
	
	
	@CacheLookup
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[11]/input")
	private WebElement phoneNo;
	
	@CacheLookup
	@FindBy(xpath = "//button[@buttontype= 'SAVE']")
	private WebElement clickSave;
	
	@CacheLookup
	@FindBy(xpath = "//button[@buttontype= 'SAVE_CONTINUE']")
	private WebElement clickSaveAndExit;
	
	
	@FindBy(xpath = "//button[@buttontype ='TICK']")
	private WebElement clickConfirmSave;
	
	@CacheLookup
	@FindBy(xpath = "//div[contains(text(),'Create/Maintain Users')]")
	private WebElement createMaintain_userText;
	
	@FindBy(xpath = "//div[contains(text(),'Users List')]")
	private WebElement usersList_Text;
	
	// starts high level testing
	
	
	/*User name already exists*/
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[1]/div[1]/div[2]")
	private WebElement UserNameValidationMsg1;
	
	
	/*First Name validation*/
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[1]/div[2]/div[2]/div[2]")
	private WebElement firstNameValidationMsg;
	
	/* email validation */
	@FindBy(xpath = "//div[contains(text(),'Incorrect e-mail format.')]")
	private WebElement emailValidationMsg;
	
	/*Password validation for atleast 8 character */
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[2]/div[2]/div[2]")
	private WebElement passwordValidationMsg1;
	
	/*Password validation for white space */
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[2]/div[2]/div[2]")
	private WebElement passwordValidationMsg2;
	
	/*Confirm Password for Con Pswd field is required*/
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[3]/div[2]/div[2]")
	private WebElement conPswdValidationMsg1;
	
	/*con pswd need  match*/
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[3]/div[2]/div[2]")
	private WebElement conPswdValidationMsg2;
	
	
	/*First Address*/
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[2]/div[5]/div[2]/div[2]")
	private WebElement firstAddresValidation;
	
	/*This is a error msg pop up , it comes only username is already exist, and rest of the fields are validated successfully */
	@FindBy(xpath = "//button[@buttontype=\"TICK\"]")
	private WebElement errorDetailsForUName;
	
	
	 @FindBy(xpath = "//span[@class='p-dropdown-label p-inputtext']") 
	//@FindBy (xpath = "//div[@class='p-dropdown p-component p-inputwrapper tabledropdown bg-white border border-text_black rounded-5px text-left text-12px w-full h-20px p-inputwrapper-filled']")
	private WebElement clickSelectDropDownForAll;
	
	@FindBy(xpath = "//li[@aria-label='All']")
	private WebElement clickAll;
	
	@CacheLookup
	@FindBy(xpath = "//div[@class=\"p-checkbox-box p-highlight\"]")
	private WebElement inActiveClick;
	
	/*Need to edit after Yash deployment*/
	@FindBy(xpath = "//*[text()='The  user  name field is required.']")
	private WebElement userNameRequired;
	
	
	@FindBy(xpath = "//div[contains(text(),'Your page has changes. Are you sure you want to leave without saving ?')]")
	private WebElement exitWithoutSaving;
	
	@FindBy(xpath = "//button[@buttontype ='TICK']")
	private WebElement clickOkWithOutSaving;
	
	@FindBy(xpath = "//div[@class='mr-25px w-25px min-w-25px cursor-pointer']")
	private WebElement homeButton;
	
	
	@FindBy(xpath = "//div[contains(text(),'Please Confirm !')]")
	private WebElement isPresent;
	/////////////////////////////////////////////////
	
	public UserManagementPage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver,this);
		
	}
	
	public void clickHomeButton() {
		
		 homeButton.click();
	}
	
	
	public void alertPleaseConfirm() {
		
		isPresent.click();
	}
	
	public void clickSelectDropDown() {
		
		clickSelectDropDownForAll.click();		
	}
	
	public void clickOkWithOutSaving() {
		
		clickOkWithOutSaving.click();		
	}
	
	
public boolean exitWithoutSaving() {
		
		return exitWithoutSaving.isDisplayed();		
	}
	
	
	public void clickAll() {
		clickAll.click();
	}
	
	public boolean UserNameRequired() {
		
		//return driver.getPageSource().contains("The  username field is required.");
		return driver.findElement(By.xpath("//*[contains(text(),'The  username field is required.')]")).isDisplayed();

	}
	
	public void clickAddButton() {
		
		addButton.click();
	}
	
	public void sendUserName(String username) {
		
		userName.sendKeys(username);
	}
	
	public void sendFirstName(String firstname) {
		
		firstName.sendKeys(firstname);
	}
	
public void sendLastName(String lastname) {
		
		lastName.sendKeys(lastname);
	}
	
public void sendEmail(String email) {
	
	this.email.sendKeys(email);
}

public void sendPassword(String password) {
	
	this.password.sendKeys(password);
}

public void sendConfirmPassword(String confirmPassword) {
	
	this.confirmPassword.sendKeys(confirmPassword);
}

public void sendAddress1(String address1) {
	
	this.address1.sendKeys(address1);
}

public void sendAddress2(String address2) {
	
	this.address2.sendKeys(address2);
}

public void sendAddress3(String address3) {
	
	this.address3.sendKeys(address3);
}

public void sendCity(String city) {
	
	this.city.sendKeys(city);
}

public void sendCountry(String country) {
	
	this.country.sendKeys(country);
}
public void sendPhoneNo(String phoneno) {
	
	this.phoneNo.sendKeys(phoneno);
}



public void clickSaveButton() {
	
	clickSave.click();
}

public void clickSaveAndExitButton() {
	
	clickSaveAndExit.click();
}


public void clickConfirmSaveButtonAlert() {
	
	clickConfirmSave.click();
}

public String assetUserManagementText() {
	
	return createMaintain_userText.getText().trim();
		
}

public String assetUserListText() {
	
	return usersList_Text.getText().trim();
		
}

// new/complete automation by susmith

public List<WebElement> allUserNameList() {
	
	System.out.println("inside all allUserNameList ");
	return driver.findElements(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[3]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]"));
	
}



public boolean userNameValidation1() {

	return driver.findElement(By.xpath("//div[contains(text(),'The username already exists.')]")).isDisplayed();
	//08-01-2021 edited return  driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div[1]/div[1]/div[2]/div[2]")).isDisplayed();
	//return driver.findElement(By.xpath("//*[contains(text(),'The  username field is required.')]")).isDisplayed();
}

public boolean firstNameValidation() {
	
	return driver.findElement(By.xpath("//div[contains(text(),'The  first  name field is required.')]")).isDisplayed();
}

public boolean emailValidation() {
	
	return driver.findElement(By.xpath("//div[contains(text(),'Incorrect e-mail format.')]")).isDisplayed();
		
}

public boolean isEmailvalidationPresent() {
	
	return driver.getPageSource().contains("Incorrect e-mail format.");
}



public boolean passwordLenghthValidation() {
	
	return driver.findElement(By.xpath("//div[contains(text(),'The  password must be at least 8 characters.')]")).isDisplayed();
		
}

public boolean passwordWhiteSpaceValidation() {
	
	return driver.findElement(By.xpath("//div[contains(text(),'Cannot contain only spaces.')]")).isDisplayed();
		
}


public boolean ConfirmPasswordValidation() {
	
	//return driver.findElement(By.xpath("//div[contains(text(),'Passwords need to match!')]")).isDisplayed();
	return driver.getPageSource().contains("Passwords need to match!");
		
}

public boolean addressValidation() {
	
	return driver.findElement(By.xpath("//div[contains(text(),'The  address field is required.')]")).isDisplayed();
		
}

public boolean passwordEmptyValidation() {
	
	return driver.findElement(By.xpath("//div[contains(text(),'The  password field is required.')]")).isDisplayed();
}

public boolean confirmPasswordEmptyValidation() {
	
	//return driver.findElement(By.xpath("//div[contains(text(),'The confirm password field is required.')]")).isDisplayed();
	return driver.getPageSource().contains("The confirm password field is required.");

}

public void clickInActiveStatus() {
	
	inActiveClick.click();
}


}
