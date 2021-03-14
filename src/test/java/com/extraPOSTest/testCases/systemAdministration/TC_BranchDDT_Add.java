package com.extraPOSTest.testCases.systemAdministration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.extraPOSTest.pageObjects.PostLoginPage;
import com.extraPOSTest.pageObjects.systemAdministration.BranchFormPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_BranchDDT_Add extends BaseClass {

	public List<String> allBranchList;
	ReadConfig readConfig = new ReadConfig();
	public WebDriver driver;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getBranchOutputPath();
	String writeSheetName = "branch";
	Action actions;
	
	String readSheetName = "branch";
	boolean result = false;

	@Parameters("browser")
	@BeforeTest
	public void BrowserSetUp(String browser) {
		// init(browser);
		this.driver = init(browser);

	}

	@AfterTest
	public void tearDown() {

		driver.quit();
		logger.info("Driver has closed");
	}

	@Test(priority = 1)
	public void clickSelectAll() throws InterruptedException {

		PostLoginPage postLoginPage = new PostLoginPage(driver);
		BranchFormPage branchFormPage = new BranchFormPage(driver);
		allBranchList = new ArrayList<>();

		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement showingText;

		postLoginPage.clickSystemAdministration();
		postLoginPage.clickBranch();

		showingText = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[3]/div[1]/div/div[2]/div[2]/div")));
		branchFormPage.clickSelectDropDown();
		logger.info("Clicked on Select");
		branchFormPage.clickAll();
		List<WebElement> allBranch = branchFormPage.allBranchNameList();
		int branchListCount = allBranch.size();
		logger.info("The Total count of All Branch is " + branchListCount);

		for (int i = 0; i < branchListCount; i++) {

			allBranchList.add(allBranch.get(i).getText().toString());
		}
		postLoginPage.clickSystemAdministration();
		logger.info("Get all the branch codes");

	}

	@Test(priority = 2, dataProvider = "branch")
	public void branchCreate(String testCaseNo, String branchCode, String description, String address1, String address2,
			String address3, String address4, String address5, String phoneNumber, String contactPerson, String ARcode,
			String APcode, String status, String headOffice) throws InterruptedException {

		try {
			PostLoginPage postLoginPage = new PostLoginPage(driver);
			BranchFormPage branchFormPage = new BranchFormPage(driver);
			WebDriverWait wait = new WebDriverWait(driver, 10);

			logger.info(testCaseNo + "  " + branchCode + "  " + description + "  " + address1 + "  " + address2 + "  "
					+ address3 + "  " + address4 + "  " + address5 + "  " + phoneNumber + "  " + testCaseNo + "  "
					+ contactPerson + "  " + ARcode + "  " + APcode + "  " + status + "  " + headOffice);

			boolean emptyBranchCode_bool = false, branchAlreadyExist_bool = false, address1_bool = false,
					arAccount_bool = false, apAccount_bool = false;

			postLoginPage.clickSystemAdministration();
			postLoginPage.clickBranch();
			branchFormPage.clickAddButton();

			branchFormPage.sendBranchCode(branchCode);
			branchFormPage.sendDescription(description);

			if (status.equalsIgnoreCase("inactive")) {
				branchFormPage.clickToDisableStatus();
			}
			if (headOffice.equalsIgnoreCase("active")) {
				branchFormPage.clickToEnableHeadOffice();
			}

			branchFormPage.sendAddress1(address1);
			branchFormPage.sendAddress2(address2);
			branchFormPage.sendAddress3(address3);
			branchFormPage.sendAddress4(address4);
			branchFormPage.sendAddress5(address5);
			branchFormPage.sendPhoneNumber(phoneNumber);
			branchFormPage.sendContactPerson(contactPerson);

			if (branchCode.isEmpty()) {

				logger.info("Inside the empty Branch Validation");
				emptyBranchCode_bool = branchFormPage.validationForBranchRequired();

				if (emptyBranchCode_bool) {

					logger.info("Validation is present for empty Branch");
					Assert.assertTrue(true, "Proper validation for empty Branch code");
				} else {

					logger.info("Validation is NOT present for empty Branch");
					Assert.assertTrue(false, "No validation for empty Branch code");
				}

			} else {
				logger.info("Branch is not Empty Block, need to check is Already exist????");

				for (int i = 0; i < allBranchList.size(); i++) {

					if (allBranchList.get(i).equals(branchCode)) {
						logger.info("inside Branch code already exist IF Block");

						branchAlreadyExist_bool = branchFormPage.validationForBranchAlreadyExists();
						//By branchBy = branchFormPage.validationForBranchAlreadyExistsBy();
						//branchAlreadyExist_bool= isElementPresent(branchBy, driver);
						if (branchAlreadyExist_bool) {
							logger.info("Inside the Branch already exist validation present");
							Assert.assertTrue(true, "There is a proper validation for Branch Already Exist");

						} else {
							logger.info("Inside the Branch already exist validation isNot present");
							Assert.assertTrue(false, "There is a NO proper validation for Branch Already Exist");
						}

					}

				}

			}

			if (address1.isEmpty()) {
				logger.info("Inside the Empty Address Validation");

				address1_bool = branchFormPage.validationForAddressRequired();
				if (address1_bool) {
					logger.info("Inside the address1 validation present ");
					Assert.assertTrue(true);
				} else {

					logger.info("Inside the address1 validation NOT present ");
					Assert.assertTrue(false, "There is no validation for Address1");
				}

			}


			if (ARcode.isEmpty()) {
				logger.info("Inside the ARCode is empty");
				
				try {
					branchFormPage.sendARAccount(ARcode);

				}catch (Exception e) {
					js.executeScript("arguments[0].click();", branchFormPage.sendARAccountWebEle());
				}
					
				
			}
			// AR code Is not empty
			if (ARcode.isEmpty() == false) {

				logger.info("Inside the ARCode is not empty lookup");
				branchFormPage.clickAR_AccountCodeLOOKUP();
				boolean ARPOPUP = branchFormPage.is_AR_PopUpText_Present();
				if (ARPOPUP) {

					logger.info("AR LookUp validated");
					Assert.assertTrue(true);
					branchFormPage.sendPOPUP_AR_SearchTextBox(ARcode);
					logger.info("After AR table load");
					
					try {
						branchFormPage.clickCodeFromPopUp();
					} catch (Exception e) {
						js.executeScript("arguments[0].click();",branchFormPage.clickCodeFromPopUp_path());
					}
					
					logger.info("clicked AR first row successfully");
				}

			}



			if (APcode.isEmpty()) {
				logger.info("Inside the APCode is empty");
				branchFormPage.sendAPAccount();
			}
			

			if (APcode.isEmpty() == false) {

				logger.info("Inside the APCode is not empty lookup");
				try {
					branchFormPage.clickAP_AccountCodeLOOKUP();
				}catch (Exception e) {
					js.executeScript("arguments[0].click();",branchFormPage.clickAP_AccountCodeLOOKUP_path() );
				}
					
				boolean APPOP = branchFormPage.is_AP_PopUpText_Present();
				if (APPOP) {
					logger.info("AP LookUp validated");
					Assert.assertTrue(true);
					branchFormPage.sendPOPUP_AP_SearchTextBox(APcode);
					logger.info("After AP table load");
					try {
						branchFormPage.clickCodeFromPopUpAP();
					} catch (Exception e) {
						js.executeScript("arguments[0].click();",branchFormPage.clickCodeFromPopUpAP_locator() );
					
					}
					
					logger.info("clicked AP first row successfully");
				}

			}

			if (ARcode.isEmpty()) {
				logger.info("inside the AR Account code is empty");
				arAccount_bool = branchFormPage.validationForAR_AccountRequired();

				if (arAccount_bool) {
					logger.info("AR validation is present");
					Assert.assertTrue(true);
				} else {

					Assert.assertTrue(false, "No AR validation is present");
				}
			}

			if (APcode.isEmpty()) {
				logger.info("inside the AP Account code is empty");
				apAccount_bool = branchFormPage.validationForAP_AccountRequired();

				if (apAccount_bool) {
					logger.info("AP validation is present");
					Assert.assertTrue(true);
				} else {
					logger.info("AP validation is Not present");
					Assert.assertTrue(false, "No AP validation is present");
				}

			}

			if (emptyBranchCode_bool == false && branchAlreadyExist_bool == false && address1_bool == false
					&& arAccount_bool == false && apAccount_bool == false) {
				logger.info("Inside the save");
				try {
					branchFormPage.clickSaveAndExit();
				}
				catch (Exception e) {

				js.executeScript("arguments[0].click();",branchFormPage.clickSaveAndExit_locator() );
				}
				logger.info("Clicked Save and Exit");
				branchFormPage.clickConfirm();
				Assert.assertEquals("Branch List", branchFormPage.assertBranchText());
				postLoginPage.clickSystemAdministration();
				allBranchList.add(branchCode);
				result = true;
				logger.info("Save and exit action completed Successfully");
			}

			else {

				if (emptyBranchCode_bool == true && branchAlreadyExist_bool == true && address1_bool == true
						&& arAccount_bool == true && apAccount_bool == true) {

					logger.info("fields are full empty");
					branchFormPage.clickHomeButton();
					postLoginPage.clickSystemAdministration();
					result = true;

				} else {

					logger.info("some fields are filled, but without save --->> click home");
					try {
						branchFormPage.clickHomeButton();
					} catch (Exception e) {
						js.executeScript("arguments[0].click();",branchFormPage.clickHomeButton_path() );
					}
					
					// Assert.assertTrue(branchFormPage.exitWithoutSaving());
					branchFormPage.clickOkWithOutSaving();
					postLoginPage.clickSystemAdministration();
					result = true;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			finalResultMap.put(testCaseNo, result);
		}

	}// method close

	@DataProvider(name = "branch")
	String[][] getData() throws IOException {

		String path = readConfig.getBranchInputPath();
		int rowCount = XLUtils.getRowCount(path, readSheetName);
		int colCount = XLUtils.getCellCount(path, readSheetName, 0);

		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readSheetName, i, j).trim();
			}
		}

		return userdata;
	}

	
	@Test(priority = 3)
	public void printExcel() throws IOException {
		
		try {
			
			String TC_No="";
			Boolean res;
			for(Entry<String, Boolean> m:finalResultMap.entrySet()){    
				System.out.println(m.getKey()+" "+m.getValue());    
		           TC_No =m.getKey();
		           res = m.getValue();
			if ( res == true) // for PASS
			{

				XLUtils.updateResults(outPutExcellLocation, writeSheetName, TC_No, "PASS");

			} else if ( res == false) {

				XLUtils.updateResults(outPutExcellLocation, writeSheetName, TC_No, "FAIL");
			}
			
			   }
		}catch (Exception e) {

		e.printStackTrace();
		}
		
		
	}
	
}
