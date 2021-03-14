package com.extraPOSTest.pageObjects.inventory.maintenance;

import java.awt.RenderingHints.Key;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UDAPage {

	WebDriver driver;

	@FindBy(xpath = "//button[@buttontype='ADD']")
	private WebElement addButton;

	@FindBy(xpath = "//button[@buttontype='EDIT']")
	private WebElement editButton;

	@FindBy(xpath = "//div[@class='w-16']")
	private WebElement clickSelectDropDownForAll;

	@FindBy(xpath = "//li[@aria-label='All']")
	private WebElement clickAll;

	@FindBy(xpath = "//button[@buttontype='SAVE']")
	private WebElement saveButton;

	@FindBy(xpath = "//div[contains(text(),'Error')]")
	private WebElement errorPopup;

	@FindBy(xpath = "//button[@buttontype='TICK']")
	private WebElement clickTICK;

	@FindBy(xpath = "//li[@aria-label='Lookup']")
	private WebElement lookup;

	@FindBy(xpath = "//li[@aria-label='Free Text']")
	private WebElement freeText;

	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement searchLookUp;

	@FindBy(xpath = "//button[@buttontype='SEARCH']")
	private WebElement searchButton;

	@FindBy(xpath = "//tr[@class='p-highlight p-selectable-row']/td[1]")
	private WebElement clickResult;

	@FindBy(xpath = "//div[@class='flex justify-start w-full pb-6']/div/div/div[1]/div/span")
	private WebElement clickDropdown;

	@FindBy(xpath = "/html/body/div[2]/div/ul/li[2]")
	private WebElement selectDescription;
	
	@FindBy(xpath = "//button[@buttontype='CLOSE_NORMAL']")
	private WebElement closeIcon;

	public UDAPage(WebDriver driver) {
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

	public void clickAll() {
		clickAll.click();
	}

	public WebElement clickAll_WebElement() {
		return clickAll;
	}

	public void clickSelectDropDown() {

		clickSelectDropDownForAll.click();
	}

	public WebElement clickSelectDropDown_WebElement() {

		return clickSelectDropDownForAll;
	}

	public int totalRecords() {

		return driver.findElements(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr/td[3]/div/div[1]/div[2]"))
				.size();
	}

	public String status(int i) {

		return driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[3]/div/div[1]/div[2]"))
				.getText();
	}

	public void statusClick(int i) {

		driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[3]/div/div[1]/div[1]/div[2]"))
				.click();
	}

	public WebElement statusClick_WebElement(int i) {
	return	driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[3]/div/div[1]/div[1]/div[2]"));
	}
	public void clickRequired(int i) {

		driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[4]/div/div[1]/div[1]/div[2]"))
				.click();
	}
	
	public WebElement clickRequired_WebElement(int i) {
		return driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[4]/div/div[1]/div[1]/div[2]"));
	}

	public By allBy() {

		return By.xpath(
				"//*[@id='root']/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]");
	}

	public void sendUserDefinedFieldName(int i, String text) {

		driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[5]/div/input"))
				.clear();
		driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[5]/div/input"))
				.sendKeys(text);
	}

	public void clickFieldType(int i) {

		driver.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[3]/div[1]/div[3]/div[2]/main[1]/div[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/table[1]/tbody[1]/tr["
						+ i + "]/td[6]/div[1]/div[1]/span[1]"))
				.click();
	}
	public WebElement clickFieldType_WebElement(int i) {

		return driver.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[3]/div[1]/div[3]/div[2]/main[1]/div[1]/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/table[1]/tbody[1]/tr["
						+ i + "]/td[6]/div[1]/div[1]/span[1]"));
	}
	
	
	public String getColumnName(int i) {

		return driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[2]/div/div"))
				.getText().trim();
	}

	public void clickLookUptext() {

		lookup.click();
	}

	public void clickFreeText() {

		freeText.click();
	}

	public void clickLookUp(int i) {

		driver.findElement(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["
						+ i + "]/td[7]/div/div/div/button"))
				.click();
	}
	
	public void LovCodeTextBox(int i) {
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["+i+"]/td[7]/div/div[2]/div/div/input")).sendKeys(Keys.TAB,Keys.TAB);
	}

	public void sendSearchLookUp(String text) {

		searchLookUp.sendKeys(text);
	}

	public WebElement sendSearchLookUp_WebElement() {
		return driver.findElement(By.xpath("//input[@placeholder='Search']"));
	}

	public void clickSearchButton() {

		searchButton.click();
	}

	public String searchResultCode() {

		return driver.findElement(By.xpath("//table[@role='grid']/tbody/tr/td[1]")).getText();
	}

	public String searchResultDescription() {

		return driver.findElement(By.xpath("//table[@role='grid']/tbody/tr/td[2]")).getText();
	}

	public void clickOnSearchResult() {

		driver.findElement(By.xpath("//div[@class='p-datatable-wrapper']/table/tbody/tr/td[1]")).click();
		//clickResult.click();
	}
	public WebElement clickOnSearchResult_WebElement() {

	return	driver.findElement(By.xpath("//div[@class='p-datatable-wrapper']/table/tbody/tr/td[1]"));
		//clickResult.click();
	}

	public void clickDropDown() {
		clickDropdown.click();

	}

	public void clickDescription() {

		selectDescription.click();
	}
	
public boolean validationForEmptyUserDefinedField() {
		
		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//*[text()='This is a mandatory field.']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;

	}
public boolean validationForMaxCharUserDefinedField() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//*[text()='Length cannot exceed 30 characters.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}
public boolean validationForEmptyFieldType() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//*[text()='This is a mandatory field.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}


public boolean validationlovCodeEmpty() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//*[text()='The field must be a valid selected value.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}

public boolean validationForDuplicateUserDefinedFieldName() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//*[text()='Field name already exist.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;
}

public String getLOVtextFromPage(int i) {
	System.out.println("inside the getLOVtextFromPage");
	System.out.println(driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["+i+"]/td[7]/div/div[2]/div/div/input")).getAttribute("value"));
	return driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["+i+"]/td[7]/div/div[2]/div/div/input")).getAttribute("value");
}										
public String getSelectTypetextFromPage(int i) {
	return driver.findElement(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr["+i+"]/td[6]/div/div[1] //*[@class='p-dropdown-label p-inputtext']")).getText();
			
}										


public List<WebElement> allUserDefinedFieldNameList() {
	
	return driver.findElements(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[5]/div[1]/div[2]/div[1]/div/div[2]/table/tbody/tr/td[5]/div/input"));
}

public void closeIcon() {
	closeIcon.click();
}
public WebElement closeIcon_WebElement() {
	return closeIcon;
}
}
