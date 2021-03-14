package com.extraPOSTest.pageObjects.systemAdministration;

import java.awt.RenderingHints.Key;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BranchFormPage {

	WebDriver driver;

	@CacheLookup
	@FindBy(xpath = "//button[@buttontype='ADD']")
	private WebElement addButton;

	@FindBy(xpath = "//span[@class='p-dropdown-label p-inputtext']")
	private WebElement selectClick;

	@FindBy(xpath = "//li[@aria-label='All']")
	private WebElement clickAll;

	// from here validation msg for empty fields(mandatory)

	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'The  branch code field is required.')]")
	private WebElement validationForBranchRequired;

	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'The  address field is required.')]")
	private WebElement validationForAddressRequired;

	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'The field must be a valid selected value.')]")
	private WebElement validationForAR_AccountRequired;

	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'The field must be a valid selected value.')]")
	private WebElement validationForAP_AccountRequired;

	// END {......... from here validation msg for empty
	// fields(mandatory)............}

	// Branch is already exist
	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'The  branch code already exits.')]")
	private WebElement validationForBranchAlreadyExists;

	// All the fields

	@CacheLookup
	@FindBy(css = "#Branch")
	private WebElement txtBranchCode;

	@CacheLookup
	@FindBy(xpath = "//input[@textlabel='Description']")
	private WebElement txtDescription;

	@CacheLookup
	@FindBy(xpath = "//input[@textlabel='Address*']")
	private WebElement txtAddress1;

	@CacheLookup
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div/div[5]/input")
	private WebElement txtAddress2;

	@CacheLookup
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div/div[6]/input")
	private WebElement txtAddress3;

	@CacheLookup
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div/div[7]/input")
	private WebElement txtAddress4;

	@CacheLookup
	@FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[4]/div/div[8]/input")
	private WebElement txtAddress5;

	@CacheLookup
	@FindBy(xpath = "//input[@textlabel='Phone Number']")
	private WebElement txtPhoneNumber;

	@CacheLookup
	@FindBy(xpath = "//input[@textlabel='Contact Person']")
	private WebElement txtContactPerson;

	@CacheLookup
	@FindBy(xpath = "//div[@class='p-checkbox-box p-highlight']")
	private WebElement clickActive;

	@CacheLookup
	@FindBy(xpath = "//div[@class='p-checkbox-box']")
	private WebElement clickHeadOffice;

	@CacheLookup
	@FindBy(xpath = "//button[@id='selectedArAccountCode_AH_BTN']")
	private WebElement clickAR_AccountCodelookup;

	@CacheLookup
	@FindBy(xpath = "//button[@id='selectedApAccountCode_AH_BTN']")
	private WebElement clickAP_AccountCodelookup;

	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Search AR Account Code*')]")
	private WebElement ARPopUpText;

	@CacheLookup
	@FindBy(xpath = "//*[contains(text(),'Search AP Account Code*')]")
	private WebElement APPopUpText;

	@CacheLookup
	@FindBy(xpath = "//input[@id='selectedArAccountCode_search_text']")
	private WebElement AR_SearchTextBoxPOPUP;

	@CacheLookup
	@FindBy(xpath = "//input[@id='selectedApAccountCode_search_text']")
	private WebElement AP_SearchTextBoxPOPUP;

	@FindBy(xpath = "//*[@class='p-selectable-row']")
	private WebElement clickSearchedCodeROW;

	@FindBy(xpath = "//*[@class='p-selectable-row']")
	private WebElement clickSearchedCodeROWAP;

	@CacheLookup
	@FindBy(xpath = "//input[@id='selectedArAccountCode']")
	private WebElement txtAR;

	@CacheLookup
	@FindBy(xpath = "//input[@id='selectedApAccountCode']")
	private WebElement txtAP;

	// Save, Save&Exit

	@CacheLookup
	@FindBy(xpath = "//button[@buttontype='SAVE_CONTINUE']")
	private WebElement saveAndExit;

	@FindBy(xpath = "//button[@buttontype='TICK']")
	private WebElement clickConfirm;

	@CacheLookup
	@FindBy(xpath = "//div[contains(text(),'Branch List')]")
	private WebElement branchList_txt;

	@FindBy(xpath = "//div[@class='mr-25px w-25px min-w-25px cursor-pointer']")
	private WebElement homeButton;

	@FindBy(xpath = "//div[contains(text(),'Your page has changes. Are you sure you want to leave without saving ?')]")
	private WebElement exitWithoutSaving;

	@FindBy(xpath = "//button[@buttontype ='TICK']")
	private WebElement clickOkWithOutSaving;

	/*
	 * -------------------------------------------------------------
	 * ................................
	 */

	@CacheLookup
	@FindBy(xpath = "//a[@class='p-accordion-header-link']")
	private WebElement searchWidget;

	@CacheLookup
	@FindBy(xpath = "//input[@textlabel='Branch Code']")
	private WebElement searchBranchCodeTxt;

	@CacheLookup
	@FindBy(xpath = "//input[@textlabel='Branch Description']")
	private WebElement searchBranchDescriptionTxt;

	@CacheLookup
	@FindBy(xpath = "//div[@class='flex flex-row']/div[1]/div[3]/div[2]")
	private WebElement changeStatus;

	@CacheLookup
	@FindBy(xpath = "//li[@aria-label='Active']")
	private WebElement selectActive;

	@CacheLookup
	@FindBy(xpath = "//li[@aria-label='Inactive']")
	private WebElement selectInactive;

	@CacheLookup
	@FindBy(xpath = "//button[@buttontype='RESET']")
	private WebElement resetButton;

	@CacheLookup
	@FindBy(xpath = "//button[@buttontype='SEARCH_WITH_LABEL']")
	private WebElement searchButton;

	@CacheLookup
	@FindBy(xpath = "//button[@buttontype='EDIT_NORMAL']")
	private WebElement editIcon;

	@CacheLookup
	@FindBy(xpath = "//input[@id='Branch']")
	private WebElement grabBranchTextData;

	/*----------------------------------------------------------------  */

//	/**************************************************************************************************/

	public BranchFormPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickAddButton() {
		addButton.click();

	}

	public void clickSelectDropDown() {
		selectClick.click();

	}

	public void clickAll() {
		clickAll.click();

	}

	public List<WebElement> allBranchNameList() {

		return driver.findElements(By.xpath(
				"//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[3]/div[1]/div/div[1]/div/div[2]/table/tbody/tr/td[1]/div"));

	}

	public boolean validationForBranchRequired() {

		return validationForBranchRequired.isDisplayed();
	}

	public boolean validationForAddressRequired() {

		return validationForAddressRequired.isDisplayed();
	}

	public boolean validationForAR_AccountRequired() {

		return validationForAR_AccountRequired.isDisplayed();
	}

	public boolean validationForAP_AccountRequired() {

		return validationForAP_AccountRequired.isDisplayed();
	}

	public boolean validationForBranchAlreadyExists() {

		return validationForBranchAlreadyExists.isDisplayed();
	}

	public By validationForBranchAlreadyExistsBy() {

		return By.xpath(" \"//*[contains(text(),'The  branch code already exits.')]\"");
	}

	public void sendBranchCode(String text) {

		txtBranchCode.sendKeys(text);

	}

	public void sendDescription(String text) {

		txtDescription.sendKeys(text);

	}

	public void sendAddress1(String text) {

		txtAddress1.sendKeys(text);

	}

	public void sendAddress2(String text) {

		txtAddress2.sendKeys(text);

	}

	public void sendAddress3(String text) {

		txtAddress3.sendKeys(text);

	}

	public void sendAddress4(String text) {

		txtAddress4.sendKeys(text);

	}

	public void sendAddress5(String text) {

		txtAddress5.sendKeys(text);

	}

	public void sendPhoneNumber(String text) {

		txtPhoneNumber.sendKeys(text);

	}

	public void sendContactPerson(String text) {

		txtContactPerson.sendKeys(text);

	}

	public void clickToDisableStatus() {

		clickActive.click();
	}

	public void clickToEnableHeadOffice() {

		clickHeadOffice.click();
	}

	public void clickAR_AccountCodeLOOKUP() {

		clickAR_AccountCodelookup.click();
	}

	public void clickAP_AccountCodeLOOKUP() {

		clickAP_AccountCodelookup.click();
	}

	public WebElement clickAP_AccountCodeLOOKUP_path() {

		return clickAP_AccountCodelookup;
	}

	public boolean is_AR_PopUpText_Present() {

		return ARPopUpText.isDisplayed();
	}

	public boolean is_AP_PopUpText_Present() {

		return APPopUpText.isDisplayed();
	}

	public void sendPOPUP_AR_SearchTextBox(String text) {

		AR_SearchTextBoxPOPUP.sendKeys(text, Keys.ENTER);

	}

	public void sendPOPUP_AP_SearchTextBox(String text) {

		AP_SearchTextBoxPOPUP.sendKeys(text, Keys.ENTER);

	}

	public void clickCodeFromPopUp() {

		clickSearchedCodeROW.click();
	}

	public WebElement clickCodeFromPopUp_path() {

		return clickSearchedCodeROW;
	}

	public void clickCodeFromPopUpAP() {

		clickSearchedCodeROWAP.click();
	}

	public WebElement clickCodeFromPopUpAP_locator() {

		return clickSearchedCodeROWAP;
	}

	public void sendARAccount(String txt) {
		txtAR.sendKeys(txt, Keys.TAB, Keys.TAB);

	}

	public WebElement sendARAccountWebEle() {
		return driver.findElement(By.xpath("//input[@id='selectedArAccountCode']"));

	}

	public void sendAPAccount() {
		txtAP.sendKeys(Keys.TAB, Keys.TAB);

	}

	public void clickSaveAndExit() {

		saveAndExit.click();
	}

	public WebElement clickSaveAndExit_locator() {

		return saveAndExit;
	}

	public void clickConfirm() {

		clickConfirm.click();
	}

	public String assertBranchText() {

		return branchList_txt.getText().trim();
	}

	public void clickHomeButton() {

		homeButton.click();
	}

	public WebElement clickHomeButton_path() {

		return homeButton;
	}

	public boolean exitWithoutSaving() {

		return exitWithoutSaving.isDisplayed();
	}

	public void clickOkWithOutSaving() {

		clickOkWithOutSaving.click();
	}

	/*--------------------------------   Edit Actions        ------------------------------------------------*/

	public void clickSearchWidget() {

		searchWidget.click();
	}

	public void sendSearchBrandCode(String txt) {

		searchBranchCodeTxt.sendKeys(txt);
	}

	public void sendSearchBrandDescription(String txt) {

		searchBranchDescriptionTxt.sendKeys(txt);
	}

	public void clickChangeStatus() {

		changeStatus.click();
	}

	public void clickActiveStatus() {

		selectActive.click();
	}

	public void clickInActiveStatus() {

		selectInactive.click();
	}

	public void clickResetButton() {

		resetButton.click();
	}

	public void clickSearchButton() {

		searchButton.click();
	}

	public boolean isBranchTableEmpty() {

		return driver.getPageSource().contains("No data found.");
	}

	public void clickSmallEditIcon() {

		editIcon.click();
	}

	public WebElement clickSmallEditIcon_webelement() {

		return editIcon;
	}

	public String grabBrandTextBoxCode() {

		return grabBranchTextData.getAttribute("value");
	}

	public WebElement grabBrandTextBoxCode_webelement() {

		return grabBranchTextData;
	}

}
