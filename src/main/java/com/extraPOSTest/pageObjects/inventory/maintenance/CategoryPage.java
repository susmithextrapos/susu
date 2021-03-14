package com.extraPOSTest.pageObjects.inventory.maintenance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage {

	WebDriver driver;

	@FindBy(xpath = "//button[@buttontype='ADD']")
	private WebElement addButton;

	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[2]/div[4]/button")
	private WebElement editButton;

	@FindBy(xpath = "//div[contains(text(),'The  category field is required.')]")
	private WebElement validationForEmptyCatCode;

	@FindBy(xpath = "//*[text()='The  category already exits.']")
	private WebElement validationForDuplicateCatCode;

	@FindBy(xpath = "//*[text()='The  category already exits.']")
	private List<WebElement> validationForDuplicateCatCodeList;

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

	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[1]/div[2]/div[1]/div/img")
	private WebElement homeButton;

	@FindBy(css = ".p-accordion-header-link")
	private WebElement searchWidget;

	@FindBy(css = "div[class='col-span-2'] input")
	private WebElement searchCatCodeTextBox;

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

	@FindBy(xpath = "//button[@buttontype='CONFIRMATION_CANCEL']")
	private WebElement cancelButton;
	
	public CategoryPage(WebDriver driver) {
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

		return editButton;
	}

	public void sendCategoryCode(int k, String catCode) {

		driver.findElement(By.xpath("//*[@id='Category_ET_100" + k + "']")).sendKeys(catCode);
	}

	public void sendCategoryDescription(int k, String description) {
		driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody[1]/tr["
						+ k + "]/td[2]/div/input"))
				.sendKeys(description);
	}

	public void clickInActiveStatus(int k) {

		driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody[1]/tr["
						+ k + "]/td[3]/div/div[1]/div[1]/div[2]"))
				.click();
	}

	public boolean validationForEmptyCategoryCode() {

		
		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//div[contains(text(),'The  category field is required.')]"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
		
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
public WebElement validationForEmptyCategoryCode_WebElement() {

		return validationForEmptyCatCode;
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

	public List<WebElement> allCategoryCodeList() {

		return driver.findElements(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]"));
	}

	public By allBy() {

		return By.xpath(
				"//*[@id='root']/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]");
	}

	public boolean validationForDuplicateCategoryCode() {
		
		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//*[text()='The  category already exits.']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;

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

	public void clickTICK() {
		clickTICK.click();
	}

	public void clickHomeButton() {
		homeButton.click();
	}

	public WebElement clickHomeButton_WebElement() {
		return homeButton;
	}

	public WebElement clickTICK_WebElement() {
		return clickTICK;
	}

	public void clickSearchWidget() {

		searchWidget.click();
	}

	public void sendCatCodeForSearch(String text) {

		searchCatCodeTextBox.sendKeys(text);
	}

	public void sendCatDescriptionForSearch(String text) {

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

		searchCatCodeTextBox.sendKeys(Keys.ENTER);
	}

	public String CatCodeRow() {

		return driver.findElement(By.xpath("//tbody[@class='p-datatable-tbody']/tr[1]/td[1]/div/div")).getText();
	}

	public String CatDescriptionRow() {

		return driver.findElement(By.xpath("//tbody[@class='p-datatable-tbody']/tr[1]/td[2]/div/div")).getText();
	}

	public void sendCatCodeForSearchKeys() {

		searchCatCodeTextBox.clear();
	}
	
	public void sendCatDescriptionForSearchKeys() {

		searchDescriptionTextBox.clear();
	}

	public void clickCANCEL() {
		cancelButton.click();
}
}