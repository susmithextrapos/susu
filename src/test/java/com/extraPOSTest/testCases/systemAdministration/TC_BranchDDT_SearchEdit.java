package com.extraPOSTest.testCases.systemAdministration;

import java.io.IOException;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.WebDriver;
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

public class TC_BranchDDT_SearchEdit extends BaseClass {

	PostLoginPage postLoginPage;
	BranchFormPage branchFormPage;
	ReadConfig readConfig = new ReadConfig();
	public WebDriver driver;

	String readSheetName = "branchSearch";

	@Parameters("browser")
	@BeforeTest
	public void BrowserSetUp(String browser) {
		this.driver = init(browser);

	}

	@AfterTest
	public void tearDown() {

		driver.quit();
		logger.info("Driver has closed");
	}

	@Test(dataProvider = "branchSearch")
	public void searchBranch(String testCaseNo, String branchCode, String description, String status) {
		
		
		try {
			
			
			postLoginPage = new PostLoginPage(driver);
			branchFormPage = new BranchFormPage(driver);
			
			postLoginPage.clickSystemAdministration();
			postLoginPage.clickBranch();
			branchFormPage.clickSearchWidget();
			branchFormPage.sendSearchBrandCode(branchCode);
			branchFormPage.sendSearchBrandDescription(description);
			
			if(status.equalsIgnoreCase("active")) {
				branchFormPage.clickChangeStatus();
				branchFormPage.clickActiveStatus();
				
			}else if(status.equalsIgnoreCase("inactive")) {
				branchFormPage.clickChangeStatus();
				branchFormPage.clickInActiveStatus();
			}
			branchFormPage.clickSearchButton();
			logger.info("After Search button");

			boolean isTableEmpty = branchFormPage.isBranchTableEmpty();// true means no data populated, 
			
			if(isTableEmpty == false && branchCode.isEmpty() == false) {
				try {
					branchFormPage.clickSmallEditIcon();
				}catch (Exception e) {
					js.executeScript("arguments[0].click();", branchFormPage.clickSmallEditIcon_webelement());
				}
				String editPageBrandCode = "";
				try {
					editPageBrandCode=branchFormPage.grabBrandTextBoxCode();
				}catch (Exception e) {
					js.executeScript("arguments[0].click();", branchFormPage.grabBrandTextBoxCode_webelement());
				}
				
				logger.info("editPageBrandCode------------>>"+editPageBrandCode);
				if(branchCode.equalsIgnoreCase(editPageBrandCode))
				{
				Assert.assertTrue(true);
				}else {
					
					Assert.assertTrue(false,"No data found for corresponding search");
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}

	@DataProvider(name = "branchSearch")
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

}
