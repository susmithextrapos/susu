package com.extraPOSTest.pageObjects.inventory.maintenance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LOVPage {

	WebDriver driver;

	@FindBy(xpath = "//button[@buttontype='ADD']")
	private WebElement addButton;

	@FindBy(xpath = "//button[@buttontype='EDIT']")
	private WebElement editButton;

	@FindBy(xpath = "//span[@class='p-dropdown-label p-inputtext']")
	private WebElement clickSelectDropDownForAll;

	@FindBy(xpath = "//li[@aria-label='All']")
	private WebElement clickAll;

	@FindBy(xpath = "//button[@buttontype='SAVE']")
	private WebElement saveButton;

	@FindBy(xpath = "//div[contains(text(),'Error')]")
	private WebElement errorPopup;

	@FindBy(xpath = "//button[@buttontype='TICK']")
	private WebElement clickTICK;

	@FindBy(xpath = "//input[@label='LOV Code*']")
	private WebElement LOVCODE;

	@FindBy(xpath = "//input[@label='LOV Description']")
	private WebElement LOVDescription;

	@FindBy(xpath = "//div[@class='p-checkbox-box p-highlight']")
	private WebElement status;

	@FindBy(xpath = "//div[@class='mainplus flex items-center outline-none p-2']/div[1]")
	private WebElement addRow;

	@FindBy(xpath = "//input[@placeholder='Code']")
	private WebElement codeRow;

	@FindBy(xpath = "//input[@placeholder='Description']")
	private WebElement descriptionRow;

	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[6]/div/div/div[1]/div/div[2]/table/tbody/tr/td[3]/div/div[1]/div[1]/div[2]")
	private WebElement rowStatus;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[1]/div[2]/div[1]/div/img")
	private WebElement homeButton;
	
	@FindBy(xpath = "//button[@buttontype='CONFIRMATION_CANCEL']")
	private WebElement cancelButton;

	public LOVPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickAddButton() {

		addButton.click();
	}

	public void clickEditButton() {

		editButton.click();
	}

	public WebElement clickEditButton_WebElement() {

		return driver.findElement(By.xpath("//button[@buttontype='EDIT']"));
	}
	
	public WebElement clickSaveButton_WebElement() {

		return driver.findElement(By.xpath("//button[@buttontype='SAVE']"));
	}

	public void clickSelectDropDown() {

		clickSelectDropDownForAll.click();
	}

	public WebElement clickSelectDropDown_WebElement() {

		return clickSelectDropDownForAll;
	}

	
	
	
	public boolean validationForCharaterLimitLovCode() {

		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[text()='Length cannot exceed 30 characters.']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
	}
	
	public boolean validationForEmptyLovCode() {

		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[text()='The  lov  code field is required.']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
	}

	public boolean validationForEmptyRowLovCode() {

		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[text()='This is a mandatory field.']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
	}
	public void clickAll() {
		clickAll.click();
	}

	public WebElement clickAll_WebElement() {
		return clickAll;
	}

	public By allBy() {

		return By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[3]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]");
	}

	public void clickSave() {
		saveButton.click();
	}

	public boolean errorMessage() {

		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[text()='Error']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;

		// return errorPopup.isDisplayed();
	}

	public By  errorMessage_Locators() {
		return By.xpath("//div[text()='Error']");
		
	}
	
	
	public void clickTICK() {
		clickTICK.click();
	}

	public WebElement clickTICK_WebElement() {
		return clickTICK;
	}
	
	public List<WebElement> allLOVCodeList() {
		// TODO Auto-generated method stub
		return driver.findElements(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[3]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]"));
	}

	public boolean validationForDuplicateLovCode() {

		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//*[text()='The  lov  code already exits.']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;

	}
	public boolean validationForDuplicateRowLovCode() {

		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//*[text()='Duplicate Records']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;

		// return validationForDuplicateBrandCode.isDisplayed();
	}
	
	
	public void sendLOVCODE(String code) {

		LOVCODE.sendKeys(code);

	}
	public void clearLOVCODE() {

		LOVCODE.clear();

	}

	public void sendLOVDescription(String code) {

		LOVDescription.sendKeys(code);

	}
	
	public void clearLOVDescription() {

		LOVDescription.clear();

	}
	public void clickStatusLOVCODE() {status.click();}
	

	public void clickStatus(int rowId) {

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[6]/div/div[2]/div[1]/div/div[1]/div/table/tbody/tr[1]/td[3]/div/div[1]/div[1]/div[2]")).click();
		//
	}

	public void clickAddRow() {

		addRow.click();
	}

	public By clickAddRow_Locator() {

		return  By.xpath("//div[@class='mainplus flex items-center outline-none p-2']/div[1]");
	}

	
	public void sendRowCode(int rowId, String code) {
		
		driver.findElement(By.xpath("//input[@id='code_ET_100"+rowId+"']")).sendKeys(code);
		//codeRow.sendKeys(code);

	}

	public void sendRowDescription(int rowId, String desc) {

		try {
			driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[6]/div/div[2]/div[1]/div/div[2]/table/tbody[1]/tr/td[2]/div/input")).sendKeys(desc);

		}catch (Exception e) {

		Assert.assertTrue(false);
		}
		//descriptionRow.sendKeys(code);

	}

	public void clickRowStatus() {

		rowStatus.click();
	}
	public void clickDeleteLOVCodeRowOne() {
		
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[6]/div/div[2]/div[1]/div/div[2]/table/tbody[1]/tr/td[4]/div")).click();
		System.out.println("Deleted Successfully..................");
	}
	
	public void clickHomeButton() {

		homeButton.click();
	}
	public void clickCANCEL() {
		cancelButton.click();
}
	
	public WebElement saveAlertPresent() {
		
		return driver.findElement(By.xpath("//*[@role='alert']"));
	}
	
	public By saveTextPopUp() {
		
		return By.xpath("//*[text()='Saved successfully!']");
	}
}
