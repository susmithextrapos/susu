package com.extraPOSTest.pageObjects.inventory.maintenance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SubCategoryPage {

	WebDriver driver;

	@FindBy(xpath = "//span[@class='p-dropdown-label p-inputtext']")
	private WebElement clickSelectDropDownForAll;

	@FindBy(xpath = "//li[@aria-label='All']")
	private WebElement clickAll;

	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[2]/div[4]/button")
	private WebElement editButton;

	@FindBy(xpath = "//button[@buttontype='ADD']")
	private WebElement addButton;

	@FindBy(xpath = "//div[contains(text(),'The  sub- category field is required.')]")
	private WebElement validationForEmptySubCatCode;

	@FindBy(xpath = "//div[contains(text(),'The  sub- category already exits.')]")
	private WebElement validationForDuplicateSubCatCode;
	
	@FindBy(xpath = "//button[@buttontype='SAVE']")
	private WebElement saveButton;
	
	@FindBy(xpath = "//button[@buttontype='TICK']")
	private WebElement clickTICK;
	
	@FindBy(xpath = "//div[contains(text(),'Error')]")
	private WebElement errorPopup;

	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[1]/div[2]/div[1]/div/img")
	private WebElement homeButton;
	
	@FindBy(xpath = "//button[@buttontype='CONFIRMATION_CANCEL']")
	private WebElement cancelButton;
	
	@FindBy(css = ".p-accordion-header-link")
	private WebElement searchWidget;
	
	@FindBy(css = "div[class='col-span-2'] input")
	private WebElement searchCatCodeTextBox;


	@FindBy(css = "div[class='col-span-3'] input")
	private WebElement searchDescriptionTextBox;
	
	@FindBy(css = "li[aria-label='Active']")
	private WebElement statusActive;

	@FindBy(css = "li[aria-label='Inactive']")
	private WebElement statusInActive;
	
	@FindBy(css = "li[aria-label='Both']")
	private WebElement statusBoth;

	@FindBy(css = "button[buttontype='SEARCH_WITH_LABEL']")
	private WebElement searchButton;

	@FindBy(css = ".p-dropdown-trigger-icon.pi.pi-chevron-down.p-clickable")
	private WebElement status;

	
	public SubCategoryPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickSelectDropDown() {

		clickSelectDropDownForAll.click();
	}

	public WebElement clickSelectDropDown_WebElement() {

		return clickSelectDropDownForAll;
	}

	public void clickAll() {
		clickAll.click();
	}

	public WebElement clickAll_WebElement() {
		return clickAll;
	}

	public By allBy() {

		return By.xpath(
				"//*[@id='root']/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]");
	}

	public List<WebElement> allSubCategoryCodeList() {

		return driver.findElements(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]/div/div"));
	}

	public void clickEditButton() {

		editButton.click();
	}

	public WebElement clickEditButton_WebElement() {

		return editButton;
	}

	public void clickAddButton() {

		addButton.click();
	}

	public void sendSubCategoryCode(int k, String subCatCode) {

		driver.findElement(By.xpath("//*[@id='Sub-Category_ET_100" + k + "']")).sendKeys(subCatCode);
	}

	public void sendSubCategoryDescription(int k, String description) {
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody[1]/tr["+k+"]/td[2]/div/input")).sendKeys(description);
	}

	public void clickInActiveStatus(int k) {

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody[1]/tr["+k+"]/td[3]/div/div[1]/div[1]/div[2]")).click();
	}

	public boolean validationForEmptySubCategoryCode() {
		
		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[contains(text(),'The  sub- category field is required.')]"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
		
		//return validationForEmptySubCatCode.isDisplayed();
	}

	public WebElement validationForEmptySubCategoryCode_WebElement() {

		return validationForEmptySubCatCode;
	}

	public boolean validationForDuplicateSubCategoryCode() {

		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[contains(text(),'The  sub- category already exits.')]"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
		
		//return validationForDuplicateSubCatCode.isDisplayed();

	}
	
	public void clickHomeButton() {
		homeButton.click();
	}

	
	public WebElement validationForDuplicateSubCategoryCode_WebElement() {

		return validationForDuplicateSubCatCode;

	}

	public void clickSave() {
		saveButton.click();
	}
	
	public void clickTICK() {
		clickTICK.click();
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
	public WebElement clickTICK_WebElement() {
		return clickTICK;
	}

	public void clickCANCEL() {
		cancelButton.click();
	}
	
	public WebElement clickHomeButton_WebElement() {
		return homeButton;
	}
	public void clickSearchWidget() {

		searchWidget.click();
	}
	
	public void sendSubCatCodeForSearch(String text) {

		searchCatCodeTextBox.sendKeys(text);
	}

	public void sendSubCatDescriptionForSearch(String text) {

		searchDescriptionTextBox.sendKeys(text);
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

		searchCatCodeTextBox.sendKeys(Keys.ENTER);
	}
	
	public void clickStatus() {

		status.click();
	}
	public String subCatCodeRow() {

		return driver.findElement(By.xpath("//tbody[@class='p-datatable-tbody']/tr[1]/td[1]/div/div")).getText();
	}

	public String subCatDescriptionRow() {

		return driver.findElement(By.xpath("//tbody[@class='p-datatable-tbody']/tr[1]/td[2]/div/div")).getText();
	}
	
	public void sendSubCatCodeForSearchKeys() {

		searchCatCodeTextBox.clear();
	}
	
	public void sendSubCatDescriptionForSearchKeys() {

		searchDescriptionTextBox.clear();
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
}
