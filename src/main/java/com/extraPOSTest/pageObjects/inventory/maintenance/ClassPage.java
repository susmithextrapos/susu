package com.extraPOSTest.pageObjects.inventory.maintenance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ClassPage {

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
	
	@FindBy(xpath = "//*[text()='The  brand already exits.']")
	private WebElement validationForDuplicateBrandCode;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[1]/div[2]/div[1]/div/img")
	private WebElement homeButton;
	@FindBy(xpath = "//button[@buttontype='CONFIRMATION_CANCEL']")
	private WebElement cancelButton;
	
	@FindBy(css = ".p-accordion-header-link")
	private WebElement searchWidget;

	@FindBy(css = "div[class='col-span-2'] input")
	private WebElement searchClassCodeTextBox;

	@FindBy(css = "div[class='col-span-3'] input")
	private WebElement searchDescriptionTextBox;

	@FindBy(css = ".p-dropdown-trigger-icon.pi.pi-chevron-down.p-clickable")
	private WebElement status;

	@FindBy(css = "li[aria-label='Active']")
	private WebElement statusActive;

	@FindBy(css = "li[aria-label='Inactive']")
	private WebElement statusInActive;
	
	@FindBy(css = "li[aria-label='Both']")
	private WebElement statusBoth;

	@FindBy(css = "button[buttontype='SEARCH_WITH_LABEL']")
	private WebElement searchButton;

	public ClassPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickAddButton() {

		addButton.click();
	}
	
	public WebElement clickHomeButton_WebElement() {
		return homeButton;
	}
	public WebElement clickTICK_WebElement() {
		return clickTICK;
	}
	public void clickCANCEL() {
		cancelButton.click();
}

	public void clickEditButton() {

		editButton.click();
	}

	public WebElement clickEditButton_WebElement() {

		return editButton;
	}



	public void sendClassDescription(int k, String description) {
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody[1]/tr["+k+"]/td[2]/div/input"))
				.sendKeys(description);
	}

	public void clickInActiveStatus(int k) {

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody[1]/tr["+k+"]/td[3]/div/div[1]/div[1]/div[2]"))
				.click();
	}

	
	public void clickSelectDropDown() {

		clickSelectDropDownForAll.click();
	}
	public WebElement clickSelectDropDown_WebElement() {

		return clickSelectDropDownForAll;
	}

	public boolean validationForEmptyClassCode() {

		boolean textFound = false;  
		try {
		    driver.findElement(By.xpath("//div[text()='The  class field is required.']"));
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
	
	public List<WebElement> allCategoryCodeList() {
		
		return driver.findElements(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]"));
	}										
	
	public By allBy() {
		
		return By.xpath("//*[@id='root']/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]");
	}
	

	
	public void sendClassCode(int k, String classCode) {
		
		driver.findElement(By.xpath("//*[@id='Class_ET_100" + k + "']")).sendKeys(classCode);
	}

	public void clickSearchWidget() {

		searchWidget.click();
	}

	public void sendClassCodeForSearch(String text) {

		searchClassCodeTextBox.sendKeys(text);
	}

	public void sendClassDescriptionForSearch(String text) {

		searchDescriptionTextBox.sendKeys(text);
	}

	public void clickStatus() {

		status.click();
	}

	public void clickActive() {

		statusActive.click();
	}

	public void clickInActive() {

		statusInActive.click();
	}

	public void clickBoth() {

		statusBoth.click();
	}
	public void clickSearchButton() {

		searchButton.click();
	}

	public void clickEnterKeyOnCatCode() {

		searchClassCodeTextBox.sendKeys(Keys.ENTER);
	}
	
	
	public String ClassCodeRow() {

		return driver.findElement(By.xpath("//tbody[@class='p-datatable-tbody']/tr[1]/td[1]/div/div")).getText();
	}
	
	public String ClassDescriptionRow() {

		return driver.findElement(By.xpath("//tbody[@class='p-datatable-tbody']/tr[1]/td[2]/div/div")).getText();
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
		
		//return errorPopup.isDisplayed();
	}
	
	public void clickTICK() {
		clickTICK.click();
	}

	public List<WebElement> allClassCodeList() {
		// TODO Auto-generated method stub
		return driver.findElements(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]"));
	}

	public boolean validationForDuplicateClassCode() {

		boolean textFound = false;  
		try {
		    driver.findElement(By.xpath("//*[text()='The  class already exits.']"));
		    textFound = true;
		} catch (Exception e) {
		    textFound = false;
		}
		return textFound;
	
		//return validationForDuplicateBrandCode.isDisplayed();
	}

	public WebElement validationForEmptyClassCode_WebElement() {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//div[text()='The  class field is required.']"));
	}
	
	public void clickHomeButton() {
		homeButton.click();
	}
	
	public void sendClassCodeForSearchKeys() {

		searchClassCodeTextBox.clear();
	}
	
	public void sendClassDescriptionForSearchKeys() {

		searchDescriptionTextBox.clear();
	}

	
}
	

