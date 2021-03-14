package com.extraPOSTest.testCases.Approvals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.extraPOSTest.pageObjects.PostLoginPage;
import com.extraPOSTest.pageObjects.Approvals.ApprovalRoleCodePage;
import com.extraPOSTest.pageObjects.inventory.maintenance.UomPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_ApprovalRoleCode extends BaseClass {
	
	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	
	String readSheetName = "addAprovalRoleCode";
	public List<String> allAprovalRoleCodeList;
	ApprovalRoleCodePage approvalRoleCodePage;
	
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getApprovalsOutputPath();
	String writeSheetName = "Approvals";
	
	@BeforeSuite
	public void createExcelReportExcel() {
		outPutExcellLocation =	reportGeneration(outPutExcellLocation, writeSheetName);
		System.out.println("outPutExcellLocation :"+outPutExcellLocation);
	}
	
	
	@Parameters("browser")
	@BeforeTest
	public void BrowserSetUp(String browser) {
		System.out.println("Inside the BrowserSetUp");
		this.driver = init(browser);
		System.out.println("BrowserSetUp is completed");
		
	}

	@AfterTest
	public void tearDown() {

		driver.quit();
		logger.info("Driver has closed");
	}

	@Test(priority = 0)
	public void selectALL_forApprovalRoleCode() throws InterruptedException {

		approvalRoleCodePage = new ApprovalRoleCodePage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickApprovals();
		logger.info("Clicked succefully Approvals");
		postLoginPage.clickMaintainence();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickApprovalRoleCode();
		logger.info("Clicked succefully Approval Role Code");

		try {

			postLoginPage.clickSelectDropDown();

		} catch (Exception e) {
			js.executeScript("arguments[0].click();", postLoginPage.clickSelectDropDown_WebElement());
		}
		
		System.out.println("Clicked Select DropDown");
		try {
			postLoginPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", postLoginPage.clickAll_WebElement());
		}
		allAprovalRoleCodeList = new ArrayList<>();
		Thread.sleep(1000);
		List<WebElement> allApprovalRoleCode = approvalRoleCodePage.allApprovalRoleCodeList();
		logger.info("Size of the ApprovalRoleCode List -->> " + allApprovalRoleCode.size());
		for (int i = 0; i < allApprovalRoleCode.size(); i++) {
			allAprovalRoleCodeList.add(allApprovalRoleCode.get(i).getText());
		}

	}
	
	
	@Test(dataProvider = "addApprovalRoleCode", enabled = true, priority = 1 )
	public void createApprovalRoleCode() {
		
	
	try
	{
		//ADD Button.
		try 
		{
			postLoginPage.clickAddButton();
		} 
		catch (Exception e) 
		{
			js.executeAsyncScript("arguments[0].click();", postLoginPage.clickAddRowButton_WebElement());
		}
		
		
		
		
		
		
		
	} 
	catch (Exception e) 
	{
		// TODO: handle exception
	}
	
	
	}
	

}//class
