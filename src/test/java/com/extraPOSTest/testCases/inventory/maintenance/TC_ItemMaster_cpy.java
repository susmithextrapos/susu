package com.extraPOSTest.testCases.inventory.maintenance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.extraPOSTest.pageObjects.PostLoginPage;
import com.extraPOSTest.pageObjects.inventory.maintenance.CategoryPage;
import com.extraPOSTest.pageObjects.inventory.maintenance.ItemMasterPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_ItemMaster_cpy extends BaseClass {
	
	
	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
    ItemMasterPage itemMasterPage;
	String readSheetName_ItemCode = "IM-ItemCode";
	String readSheetName_Category = "IM-Category";
	String readSheetName_SKU = "IM-SKU-Selling";
	
	public List<String> allItemCodeList;
	int numberOfRecords;
	int flag = 1,iterationCount = 0;
	boolean result = true;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String writeSheetName = "OutputItemMaster";
	ConvertionAndValidation convertionAndValidation = new ConvertionAndValidation();

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

	@Test(priority = 0)
	public void selectALL_forCategoryCode() throws InterruptedException {

		itemMasterPage = new ItemMasterPage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickItemMaster();
		logger.info("Clicked succefully Item Master");

		try {

			postLoginPage.clickSelectDropDown();

		} catch (Exception e) {
			js.executeScript("arguments[0].click();", postLoginPage.clickSelectDropDown_WebElement());
		}
		Thread.sleep(2000);
		try {
			postLoginPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", postLoginPage.clickAll_WebElement());
		}
		Thread.sleep(7000);
		allItemCodeList = new ArrayList<>();
		List<WebElement> allItemCode = itemMasterPage.allItemCodeList();
		logger.info("Size of the Item Master List -->> " + allItemCode.size());
		for (int i = 0; i < allItemCode.size(); i++) {
			allItemCodeList.add(allItemCode.get(i).getText());
		}
	}

	@Test(dataProvider = "itemCode", priority = 1, enabled=true)
	public void ItemCodeValidation(String testCaseNumber, String itemCode, String description) throws IOException, InterruptedException {

		
		try {
			logger.info("inside the ItemCodeValidation");
			itemMasterPage = new ItemMasterPage(driver);
			
			if(flag == 1) {
				try
				{
					itemMasterPage.clickAddButton();
				} 
				catch (Exception e) 
				{
					js.executeScript("arguments[0].click();", itemMasterPage.clickAddButton_WebElement());
				}
				
				flag++;
			}
			itemMasterPage.SendItemCode(itemCode);
			itemMasterPage.SendItemDescription(description);
			
			boolean emptyItemCode= false, maxLenItemCode= false, duplicateItemCode = false;
			// Validation Starts 
			
			// Empty Item Code
			if(itemCode.isEmpty()) {
				
				try {
					logger.info("Inside the Empty Item Code Validation");
					emptyItemCode = itemMasterPage.validationForEmptyItemCode();
					
					if(emptyItemCode) {
						logger.info("There is proper validation for empty Item Code field");
					}
					else {
						logger.info("There is NO validation for empty Item Code field");
						result = false;
						Assert.assertTrue(false,"There is NO validation for empty Item Code field" );
					}
				} catch (Exception e) {
					result =false;
					e.printStackTrace();
					logger.info("There is a exception in Empty Item Code");
					Assert.assertTrue(false);
				}
			}
			
			if(itemCode.length()>30)
			{
				try {
					logger.info("Inside the Max Length ItemCode Validation");
					maxLenItemCode = itemMasterPage.validationForMaxLengthItemCode();
					
					if(maxLenItemCode) {
						logger.info("There is proper validation for Max: Length ItemCode field");
					}
					else {
						logger.info("There is NO validation for Max: Length ItemCode field");
						result = false;
						Assert.assertTrue(false,"There is NO validation for Max: Length ItemCode field" );
					}
					
				} catch (Exception e) {

					result =false;
					e.printStackTrace();
					logger.info("There is a exception in Max: Lenth Item Code");
					Assert.assertTrue(false);
				}
				
			}
			
			// Duplicate Item Code
			for(int i=0; i< allItemCodeList.size(); i++)
			{
				
				if(allItemCodeList.get(i).equalsIgnoreCase(itemCode)) 
				{
					logger.info("Duplication Validation Block");
					try 
					{
						duplicateItemCode = itemMasterPage.validationForDuplicateItemCode();
						if(duplicateItemCode) {
							logger.info(" Proper Validation for Duplicate ItemCode");
						}else {
							logger.info("There is NO validation for Duplicate ItemCode field");
							result = false;
							Assert.assertTrue(false,"There is NO validation for Duplicate ItemCode field" );
						}
					} 
					catch (Exception e) 
					{

						result =false;
						e.printStackTrace();
						logger.info("There is a exception in Duplicate Item Code");
						Assert.assertTrue(false);
					}
					
				}
				
				
			}
			
			
		} catch (Exception e) {
			
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside ItemCodeValidation() ");
		
		}finally {
			
			finalResultMap.put(testCaseNumber, result);
			result = true;
			
			iterationCount++;
			
			if(  iterationCount == numberOfRecords)
			{
				xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);
				postLoginPage.clickHomeButton();
				postLoginPage.clickTICK();
				postLoginPage.clickInventory();
				flag=1;
		   }
		}
		
	}
	
	
	@DataProvider(name = "itemCode")
	String[][] getItemCode() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readSheetName_ItemCode);
		int colCount = XLUtils.getCellCount(path, readSheetName_ItemCode, 0);
		numberOfRecords = rowCount ;
		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {

			System.out.println("i is --->"+i);
			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readSheetName_ItemCode, i, j).trim();
				
			}
			
		}

		return userdata;
	}

	
	
	
	
	@Test(dataProvider = "IMcategory", priority = 2, enabled=true)
	public void IMcategoryValidation(String testCaseNumber, String itemCode, String description, String category,
			String searchBy, String SKU, String taxInBound, String taxOutBound, String productType,
			String costingMethod, String defaultSellingPrice) throws IOException, InterruptedException {

		try 
		{
		
			logger.info("inside the IMcategoryValidation");
			itemMasterPage = new ItemMasterPage(driver);
			
			boolean emptycategory = false;
			if (flag == 1) {
				
				
				  itemMasterPage = new ItemMasterPage(driver);
				  postLoginPage = new  PostLoginPage(driver);
				  postLoginPage.clickInventory();
				  logger.info("Clicked succefully Inventory");
				  postLoginPage.clickInventoryMaintenance();
				  logger.info("Clicked succefully Manintenace");
				  postLoginPage.clickItemMaster();
				  logger.info("Clicked succefully Item Master");
				 
				
				try
				{
					itemMasterPage.clickAddButton();
				} 
				catch (Exception e) 
				{
					js.executeScript("arguments[0].click();", itemMasterPage.clickAddButton_WebElement());
				}
			}
			
			int randomNumber =convertionAndValidation.generateRandomNumber();
			itemMasterPage.SendItemCode(itemCode+randomNumber);
			itemMasterPage.SendItemDescription(description);
			
			if(category.length() !=0) {
			itemMasterPage.clickCategoryLookUp(); // click Category LookUp
			// Category PopUp Actions
			try 
			{
				itemMasterPage.sendSearchLookUp(category);
			} 
			catch (Exception e)
			{

				js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

			}
			
			if(searchBy.equalsIgnoreCase("code")) 
			{
				logger.info("Search by code(Category) ");
				itemMasterPage.clickSearchButton();
				Thread.sleep(3000);
				String codeValueIs = itemMasterPage.searchResultCode();
				System.out.println("codeValueIs.........." + codeValueIs);

				if (codeValueIs.equalsIgnoreCase(category))
				{
					logger.info("found search result by code");
					try {
						itemMasterPage.clickOnSearchResult();
					} catch (Exception e) {

						js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
					}
				
			    }
				else
				{
					    itemMasterPage.closeIcon();
						result = false;
						Assert.assertTrue(false, "Search Code is not found");
				}
			}
			else //this is for search by description 
			{
				logger.info("Search by description ");
				itemMasterPage.clickDropDown();
				logger.info("........... clicked dropdown");
				itemMasterPage.clickDescription();

				itemMasterPage.clickSearchButton();
				Thread.sleep(3000);
				
				if (itemMasterPage.searchResultDescription().equalsIgnoreCase(category)) 
				{
					logger.info("found search result by description");
					try {
						itemMasterPage.clickOnSearchResult();
					} catch (Exception e) {
						js.executeAsyncScript("arguments[0].click();",
								itemMasterPage.clickOnSearchResult_WebElement());

					}

				} else {
					itemMasterPage.closeIcon();
					result = false;
					Assert.assertTrue(false, "Search Description is not found");
				}
			}
			
			
			} // if closed here for category length>0;;;; This else
			else // this else for if the category code is empty
			{
				itemMasterPage.categoryTextBox();// TAB key twise
			}
		/*************************************************************/
			// Attributes SKU
			itemMasterPage.clickSKULookUP();
			try 
			{
				itemMasterPage.sendSearchLookUp(SKU);
			} 
			catch (Exception e)
			{

				js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

			}
			itemMasterPage.clickSearchButton();
			Thread.sleep(3000);
			String sku = itemMasterPage.searchResultCode();
			System.out.println("codeValueIs.........." + sku);

			if (sku.equalsIgnoreCase(SKU))
			{
				logger.info("found search result by code");
				try {
					itemMasterPage.clickOnSearchResult();
				} catch (Exception e) {

					js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
				}
			
		    }
			else
			{
				    itemMasterPage.closeIcon();
					result = false;
					Assert.assertTrue(false, "Search Code is not found");
			}
			
			// Tax Code Inbound
			itemMasterPage.clickTaxInboundLookUp();
			try 
			{
				itemMasterPage.sendSearchLookUp(taxInBound);
			} 
			catch (Exception e)
			{

				js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

			}
			itemMasterPage.clickSearchButton();
			Thread.sleep(3000);
			String taxinboundis = itemMasterPage.searchResultCode();
			System.out.println("codeValueIs.........." + taxinboundis);

			if (taxinboundis.equalsIgnoreCase(taxInBound))
			{
				logger.info("found search result by code");
				try {
					itemMasterPage.clickOnSearchResult();
				} catch (Exception e) {

					js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
				}
			
		    }
			else
			{
				    itemMasterPage.closeIcon();
					result = false;
					Assert.assertTrue(false, "Search Code is not found");
			}
			
			
			// Tax code OutBound
			
			itemMasterPage.clickTaxOutbound();
			try 
			{
				itemMasterPage.sendSearchLookUp(taxOutBound);
			} 
			catch (Exception e)
			{

				js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

			}
			itemMasterPage.clickSearchButton();
			Thread.sleep(3000);
			String taxoutbound = itemMasterPage.searchResultCode();
			System.out.println("codeValueIs.........." + taxoutbound);

			if (taxoutbound.equalsIgnoreCase(taxOutBound))
			{
				logger.info("found search result by code");
				try {
					itemMasterPage.clickOnSearchResult();
				} catch (Exception e) {

					js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
				}
			
		    }
			else
			{
				    itemMasterPage.closeIcon();
					result = false;
					Assert.assertTrue(false, "Search Code is not found");
			}
			
			
			// Product Type
			
			itemMasterPage.clickProductTypeDropDown();
			if(productType.equalsIgnoreCase("kititem"))
			{
				itemMasterPage.clickKitItem();
			}
			else
			{
				itemMasterPage.clickPurchaseItem();
			}
			
			// Costing Method
			
			itemMasterPage.clickCostingMethodDropDown();
			if(costingMethod.equalsIgnoreCase("fifo"))
			{
				itemMasterPage.clickFIFO();
			}
			
			
			//Price Tab
			itemMasterPage.clickPricingTab();
			itemMasterPage.sendDefaultSellingPrize(defaultSellingPrice);
			
			/*********************************************************/
			// Validation starts
			
			// empty category code
			
			if(category.isEmpty())
			{
				
				try
				{
					emptycategory =itemMasterPage.validationForEmptyCategoryCode();
					if(emptycategory)
					{
						logger.info("There is a proper validation msg for empty Category");
					}
					else
					{
						result = false;
						logger.info("There is NO proper validation msg for empty Category");
						Assert.assertTrue(false, "There is NO proper validation msg for empty Category");
					}
					
				}
				catch (Exception e) 
				{
					result = false;
					logger.info("There is an Exeception in  empty Category");
					Assert.assertTrue(false, "There is an Exeception in  empty Category");
				
				}
			}
			
			
			//Save Action, Here we checking only Category Validation.
			
			if(emptycategory == false) {
				
				try
				{
					postLoginPage.clickSave();
				} 
				catch (Exception e) 
				{
					js.executeScript("arguments[0].click();", postLoginPage.clickSave_WebElement());
				}
				logger.info("Save Button clicked succefully in the Item Master");
				
				if(postLoginPage.pleaseConfirm()) // This if for saved successfully, and go to categroy page and try to delete
				{
					postLoginPage.clickTICK();
					
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(postLoginPage.savedSuccessfullyPopUp_WebElement()));
					try 
					{
						postLoginPage.clickCategory();
					} catch (Exception e) 
					{
						js.executeScript("arguments[0].click();", postLoginPage.clickCategory_WebElement());
					}
					
					
					
					CategoryPage categoryPage = new CategoryPage(driver);
					
					categoryPage.clickSearchWidget();
						
					if(searchBy.equalsIgnoreCase("code"))
					{
						categoryPage.sendCatCodeForSearch(category);	
						logger.info("Sent catcode for search");
					}
					else
					{
						categoryPage.sendCatDescriptionForSearch(category);
						logger.info("Sent cat  description for search");
					}
						
						categoryPage.clickSearchButton();
						logger.info("Clicked Search Button");
						Thread.sleep(2000);
						postLoginPage.clickEditButton();
						postLoginPage.clickDeleteButton();
						try
						{
							postLoginPage.clickSave();
						} 
						catch (Exception e) 
						{
							js.executeScript("arguments[0].click();", postLoginPage.clickSave_WebElement());
						}
						
						logger.info("Save Button clicked succefully in the Item Master for the Category");
						postLoginPage.clickTICK();
						
						// validation for can not delete category , used in Item Master.
						if(postLoginPage.errorMessage())
						{
							logger.info("Correct validations are exist, can not delete Category as it is used in Item Master");
							postLoginPage.clickTICK();
						//	postLoginPage.clickHomeButton();
						//	postLoginPage.clickTICK();
							
						//	postLoginPage.clickInventory();
						//    logger.info("Clicked succefully Inventory");
						//	postLoginPage.clickInventoryMaintenance();
						//	logger.info("Clicked succefully Manintenace");
							postLoginPage.clickItemMaster();
							postLoginPage.clickTICK();
							try
							{
								itemMasterPage.clickAddButton();
							} 
							catch (Exception e) 
							{
								js.executeScript("arguments[0].click();", itemMasterPage.clickAddButton_WebElement());
							}
							
							
						}
						else
						{
							result =false;
							logger.info("Incorrect flow, can not delete Category as it is used in Item Master");
							Assert.assertTrue(false, "It against to the functionality :can not delete Category as it is used in Item Master");
						}
				}
				else // if any errors in the ITM page, 
				{
					
					postLoginPage.clickTICK();
				}
				
			}
			
			
		}//try closed
		catch (Exception e) 
		{
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside ItemCodeValidation() ");
		
		}
	 finally {
		
		 finalResultMap.put(testCaseNumber, result);
			result = true;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);
			flag++;
	}
	
	}	
	
	
	@DataProvider(name = "IMcategory")
	String[][] getCategory() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readSheetName_Category);
		int colCount = XLUtils.getCellCount(path, readSheetName_Category, 0);
		numberOfRecords = rowCount - 1;
		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readSheetName_Category, i, j).trim();
			}
		}

		return userdata;
	}
	
	
	
	
	@Test(dataProvider = "IM_UOM", priority = 3, enabled=false)
	public void IMSKU_Selling_UomValidation(String testCaseNumber, String itemCode, String description, String category,
			 String SKU,String SKUsearchBy,String SellingUom,String SellingsearchBy, String taxInBound, String taxOutBound, String productType,
			String costingMethod, String defaultSellingPrice) throws IOException, InterruptedException {

		try 
		{
		
			logger.info("inside the IMSKUValidation");
			itemMasterPage = new ItemMasterPage(driver);
			
			boolean emptycategory = false, emptySKUuom = false,emptySellingUom = false;
			if (flag == 1) {
				
				
				  itemMasterPage = new ItemMasterPage(driver);
				  postLoginPage = new  PostLoginPage(driver);
				  postLoginPage.clickInventory();
				  logger.info("Clicked succefully Inventory");
				  postLoginPage.clickInventoryMaintenance();
				  logger.info("Clicked succefully Manintenace");
				  postLoginPage.clickItemMaster();
				  logger.info("Clicked succefully Item Master");
				 
				
				try
				{
					itemMasterPage.clickAddButton();
				} 
				catch (Exception e) 
				{
					js.executeScript("arguments[0].click();", itemMasterPage.clickAddButton_WebElement());
				}
			}
			
			int randomNumber =convertionAndValidation.generateRandomNumber();
			itemMasterPage.SendItemCode(itemCode+randomNumber);
			itemMasterPage.SendItemDescription(description);
			
			// CategoryCode
			
			if(category.length() !=0) {
			itemMasterPage.clickCategoryLookUp(); // click Category LookUp
			// Category PopUp Actions
			try 
			{
				itemMasterPage.sendSearchLookUp(category);
			} 
			catch (Exception e)
			{

				js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

			}
			
				itemMasterPage.clickSearchButton();
				Thread.sleep(3000);
				String codeValueIs = itemMasterPage.searchResultCode();
				System.out.println("codeValueIs.........." + codeValueIs);

				if (codeValueIs.equalsIgnoreCase(category))
				{
					logger.info("found search result by code");
					try {
						itemMasterPage.clickOnSearchResult();
					} catch (Exception e) {

						js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
					}
				
			    }
				else
				{
					    itemMasterPage.closeIcon();
						result = false;
						Assert.assertTrue(false, "Search Code is not found");
				}
		
			
			} // if closed here for category length>0;;;; This else
			else // this else for if the category code is empty
			{
				itemMasterPage.categoryTextBox();// TAB key twise
			}
	
			//SKU
			if(SKU.length() !=0)
			{
				
				itemMasterPage.clickSKULookUp(); // click SKU LookUp
				// SKU PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(SKU);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				if(SKUsearchBy.equalsIgnoreCase("code")) 
				{
					logger.info("Search by code(SKU Uom) ");
					itemMasterPage.clickSearchButton();
					Thread.sleep(3000);
					String codeValueIs = itemMasterPage.searchResultCode();
					System.out.println("codeValueIs.........." + codeValueIs);

					if (codeValueIs.equalsIgnoreCase(SKU))
					{
						logger.info("found search result by code");
						try {
							itemMasterPage.clickOnSearchResult();
						} catch (Exception e) {

							js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
						}
					
				    }
					else
					{
						    itemMasterPage.closeIcon();
							result = false;
							Assert.assertTrue(false, "Search Code is not found");
					}
				}
				else //this is for search by description 
				{
					logger.info("Search by description ");
					itemMasterPage.clickDropDown();
					logger.info("........... clicked dropdown");
					itemMasterPage.clickDescription();

					itemMasterPage.clickSearchButton();
					Thread.sleep(3000);
					
					if (itemMasterPage.searchResultDescription().equalsIgnoreCase(SKU)) 
					{
						logger.info("found search result by description");
						try {
							itemMasterPage.clickOnSearchResult();
						} catch (Exception e) {
							js.executeAsyncScript("arguments[0].click();",
									itemMasterPage.clickOnSearchResult_WebElement());

						}

					} else {
						itemMasterPage.closeIcon();
						result = false;
						Assert.assertTrue(false, "Search Description is not found");
					}
				}
				
				//SKU UoM finished(If the SKU contains input)
				//If the Sku found and Ok, Then Selling Uom will execute.
				
				/** Selling Uom**/
				if(SellingUom.length() != 0 && itemMasterPage.sellingUomTextBoxValue().isEmpty()) 
				{
					itemMasterPage.clickSellingLookUp(); // click Selling Uom LookUp
					// Selling PopUp Actions
					try 
					{
						itemMasterPage.sendSearchLookUp(SellingUom);
					} 
					catch (Exception e)
					{

						js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

					}
					
					if(SellingsearchBy.equalsIgnoreCase("code")) 
					{
						logger.info("Search by code(Selling Uom) ");
						itemMasterPage.clickSearchButton();
						Thread.sleep(3000);
						String codeValueIs = itemMasterPage.searchResultCode();
						System.out.println("codeValueIs.........." + codeValueIs);

						if (codeValueIs.equalsIgnoreCase(SellingUom))
						{
							logger.info("found search result by code");
							try {
								itemMasterPage.clickOnSearchResult();
							} catch (Exception e) {

								js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
							}
						
					    }
						else
						{
							    itemMasterPage.closeIcon();
								result = false;
								Assert.assertTrue(false, "Search Code is not found");
						}
					}
					else //this is for search by description 
					{
						logger.info("Search by description ");
						itemMasterPage.clickDropDown();
						logger.info("........... clicked dropdown");
						itemMasterPage.clickDescription();

						itemMasterPage.clickSearchButton();
						Thread.sleep(3000);
						
						if (itemMasterPage.searchResultDescription().equalsIgnoreCase(SellingUom)) 
						{
							logger.info("found search result by description");
							try {
								itemMasterPage.clickOnSearchResult();
							} catch (Exception e) {
								js.executeAsyncScript("arguments[0].click();",
										itemMasterPage.clickOnSearchResult_WebElement());

							}

						} else {
							itemMasterPage.closeIcon();
							result = false;
							Assert.assertTrue(false, "Search Description is not found");
						}
					}
					
				}
				else
				{
					itemMasterPage.SellingTextBox(); // Will press Tab twise
				}
				
			}
			else
			{
				itemMasterPage.SKUTextBox();  // Will press Tab twise
			}
	
			// Tax Code Inbound
						itemMasterPage.clickTaxInboundLookUp();
						try 
						{
							itemMasterPage.sendSearchLookUp(taxInBound);
						} 
						catch (Exception e)
						{

							js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

						}
						itemMasterPage.clickSearchButton();
						Thread.sleep(3000);
						String taxinboundis = itemMasterPage.searchResultCode();
						System.out.println("codeValueIs.........." + taxinboundis);

						if (taxinboundis.equalsIgnoreCase(taxInBound))
						{
							logger.info("found search result by code");
							try {
								itemMasterPage.clickOnSearchResult();
							} catch (Exception e) {

								js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
							}
						
					    }
						else
						{
							    itemMasterPage.closeIcon();
								result = false;
								Assert.assertTrue(false, "Search Code is not found");
						}
						
						
						// Tax code OutBound
						
						itemMasterPage.clickTaxOutbound();
						try 
						{
							itemMasterPage.sendSearchLookUp(taxOutBound);
						} 
						catch (Exception e)
						{

							js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

						}
						itemMasterPage.clickSearchButton();
						Thread.sleep(3000);
						String taxoutbound = itemMasterPage.searchResultCode();
						System.out.println("codeValueIs.........." + taxoutbound);

						if (taxoutbound.equalsIgnoreCase(taxOutBound))
						{
							logger.info("found search result by code");
							try {
								itemMasterPage.clickOnSearchResult();
							} catch (Exception e) {

								js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());
							}
						
					    }
						else
						{
							    itemMasterPage.closeIcon();
								result = false;
								Assert.assertTrue(false, "Search Code is not found");
						}
						
						
						// Product Type
						
						itemMasterPage.clickProductTypeDropDown();
						if(productType.equalsIgnoreCase("kititem"))
						{
							itemMasterPage.clickKitItem();
						}
						else
						{
							itemMasterPage.clickPurchaseItem();
						}
						
						// Costing Method
						
						itemMasterPage.clickCostingMethodDropDown();
						if(costingMethod.equalsIgnoreCase("fifo"))
						{
							itemMasterPage.clickFIFO();
						}
						
						
						//Price Tab
						itemMasterPage.clickPricingTab();
						itemMasterPage.sendDefaultSellingPrize(defaultSellingPrice);
						
						/*********************************************************/
						// Validation starts
						
						// empty category code
						
						if(category.isEmpty())
						{
							
							try
							{
								emptycategory =itemMasterPage.validationForEmptyCategoryCode();
								if(emptycategory)
								{
									logger.info("There is a proper validation msg for empty Category");
								}
								else
								{
									result = false;
									logger.info("There is NO proper validation msg for empty Category");
									Assert.assertTrue(false, "There is NO proper validation msg for empty Category");
								}
								
							}
							catch (Exception e) 
							{
								result = false;
								logger.info("There is an Exeception in  empty Category");
								Assert.assertTrue(false, "There is an Exeception in  empty Category");
							
							}
						}
						
						if(SKU.isEmpty())
						{
							
							try
							{
								emptySKUuom =itemMasterPage.validationForEmptySKUCode();
								if(emptySKUuom)
								{
									logger.info("There is a proper validation msg for empty SKU");
								}
								else
								{
									result = false;
									logger.info("There is NO proper validation msg for empty SKU");
									Assert.assertTrue(false, "There is NO proper validation msg for empty SKU");
								}
								
							}
							catch (Exception e) 
							{
								result = false;
								logger.info("There is an Exeception in  empty SKU");
								Assert.assertTrue(false, "There is an Exeception in  empty SKU");
							
							}
						}
						
						if(SellingUom.isEmpty())
						{
							
							try
							{
								emptySellingUom =itemMasterPage.validationForEmptySellingCode();
								if(emptySellingUom)
								{
									logger.info("There is a proper validation msg for empty Selling Uom");
								}
								else
								{
									result = false;
									logger.info("There is NO proper validation msg for empty Selling Uom");
									Assert.assertTrue(false, "There is NO proper validation msg for empty Selling Uom");
								}
								
							}
							catch (Exception e) 
							{
								result = false;
								logger.info("There is an Exeception in  empty Selling Uom");
								Assert.assertTrue(false, "There is an Exeception in  empty Selling Uom");
							
							}
						}

						//Save Action, Here we checking only Category Validation.
						
						if(emptycategory == false && emptySKUuom == false && emptySellingUom == false) 
						{
							
							try
							{
								postLoginPage.clickSave();
							} 
							catch (Exception e) 
							{
								js.executeScript("arguments[0].click();", postLoginPage.clickSave_WebElement());
							}
							logger.info("Save Button clicked succefully in the Item Master");
							
							if(postLoginPage.pleaseConfirm()) // This if for saved successfully, and go to categroy page and try to delete
							{
								postLoginPage.clickTICK();
								
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(postLoginPage.savedSuccessfullyPopUp_WebElement()));
								try 
								{
									postLoginPage.clickCategory();
								} catch (Exception e) 
								{
									js.executeScript("arguments[0].click();", postLoginPage.clickCategory_WebElement());
								}
							}
						}
							
	
		}// try closed
		catch (Exception e)
		{

			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside ItemCodeValidation() ");
		
		}
	 finally
	 {
		
		 finalResultMap.put(testCaseNumber, result);
			result = true;
			flag++;
			
	}
	}
	
	@DataProvider(name = "IM_UOM")
	String[][] getSKU_Uom() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readSheetName_SKU);
		int colCount = XLUtils.getCellCount(path, readSheetName_SKU, 0);
		numberOfRecords = rowCount - 1;
		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readSheetName_SKU, i, j).trim();
			}
		}

		return userdata;
	}
	
	
	

}
