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
import com.extraPOSTest.pageObjects.inventory.maintenance.PurchaseGroupPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_PurchaseGroup extends BaseClass{

	
	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	String readSheetName = "addPurchaseGroup";
	public List<String> allPurchaseGroupList;
	int numberOfRecords;
	int flag = 1;
	boolean result = true;
	boolean anyPurchaseGroupIsEmpty_Validation = false, anyPurchaseGroupIsDuplicate_Validation = false,
			anyPurchaseGroupIsSpecialChar_Validation = false, anyCodeIsAboveLimit_validation = false
			,anyDescriptionIsAboveLimit_validation = false , anyVendorCodeIsEmpty_Validation = false;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String writeSheetName = "OutputPurchaseGroup";
	String readTwoRows = "addTwoRows";
	String readSearch = "searchByPurchaseGroup";
	String readReverse = "reverseValidationForPurch-Group";
	ConvertionAndValidation convertionAndValidation = new ConvertionAndValidation();
	PurchaseGroupPage purchaseGroupPage;
	
	
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
	public void selectALL_forPurchaseGroup() throws InterruptedException {

		purchaseGroupPage = new PurchaseGroupPage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickPurchaseGroup();
		logger.info("Clicked succefully Category");

		try {

			postLoginPage.clickSelectDropDown();

		} catch (Exception e) {
			js.executeScript("arguments[0].click();", postLoginPage.clickSelectDropDown_WebElement());
		}
		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(postLoginPage.allBy()));

		try {
			postLoginPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", postLoginPage.clickAll_WebElement());
		}
		Thread.sleep(3000);
		allPurchaseGroupList = new ArrayList<>();
		List<WebElement> allPurchaseGroupCode = purchaseGroupPage.allPurchaseGroupList();
		logger.info("Size of the PurchaseGroupCode List -->> " + allPurchaseGroupCode.size());
		for (int i = 0; i < allPurchaseGroupCode.size(); i++) {
			allPurchaseGroupList.add(allPurchaseGroupCode.get(i).getText());
		}

	}

	
	@Test(dataProvider = "addPurchaseGroup", priority = 1, enabled=false)
	public void createPurchaseGroupCode(String testCaseNumber, String purchaseGroupCode, String description, String vendorCode, String vendorSiteCode, String status ) {

		try {

			purchaseGroupPage = new PurchaseGroupPage(driver);
			boolean purchaseGroupCodeEmpty_bool = false, purchaseGroupCodeDuplicate_bool = false,
					purchaseGroupCodeOnlySpecialChar_bool = false, CodeAboveLimit=false,
							descriptionAboveLimit=false, vendorCodeEmpty_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					postLoginPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", postLoginPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					postLoginPage.clickAddButton();
				}

			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				Thread.sleep(1000);
				try {
					purchaseGroupPage.sendPurchaseGroupCode(k, purchaseGroupCode);
				} catch (Exception e) {
					js.executeScript("arguments[0].click();",purchaseGroupPage.sendPurchaseGroupCode_WebElement(k) );
				}
				
				purchaseGroupPage.sendPurchaseGroupDescription(k, description);

				
				
				if(vendorCode.isEmpty()==false)
				{
					try 
					{
						purchaseGroupPage.clickVendorCodeLookUp(k);
					} catch (Exception e) {

					js.executeScript("arguments[0].click();",purchaseGroupPage.clickVendorCodeLookUp_WebElement(k) );
					}
				
				
				try 
				{
					postLoginPage.sendSearchLookUp(vendorCode);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", postLoginPage.sendSearchLookUp_WebElement());

				}
				convertionAndValidation.lookUpSearch("code", vendorCode, driver, js);
				
			   
				//Vendor Site Code
				      if(vendorSiteCode.isEmpty() == false)
				      {
				    	  purchaseGroupPage.clickVendorSITECodeLookUp(k);

				    	  try 
							{
								postLoginPage.sendSearchLookUp(vendorSiteCode);
							} 
							catch (Exception e)
							{

								js.executeScript("arguments[0].click();", postLoginPage.sendSearchLookUp_WebElement());

							}
							convertionAndValidation.lookUpSearch("code", vendorSiteCode, driver, js);
				    	  
				    	  
				      }
				}
				else
				{
					logger.info("Inside the Empty Vendor Code*, Tab Key actions");
					purchaseGroupPage.vendorCodeTextBox(k);
				}
				if (status.equalsIgnoreCase("inactive")) {
					purchaseGroupPage.clickInActiveStatus(k);
				}
				
				
				k++;
			}
			
			
			
			// validation starts

			// purchaseGroupCode Length Validation
			if(purchaseGroupCode.length() > 20) {
				
				logger.info("Code Exceeds the Character Limit");
				try {
					
					anyCodeIsAboveLimit_validation =true;
					CodeAboveLimit = purchaseGroupPage.validationForCodeLimit();
					if(CodeAboveLimit) {
						logger.info("Proper validation for Limit ");
						Assert.assertTrue(true);
					}else {
						logger.info("No validation for Limit ");
						result = false;
						Assert.assertTrue(false, "No validation for Limit");
					}
					
				} catch (Exception e) {

					logger.info("Exception in Code Limit ");
					result = false;
					Assert.assertTrue(false, "Exception in Code Limit ");

				}
			}
			//Description Validation
			if(description.length() > 50) {
				
				logger.info("Description Exceeds the Character Limit");
				try {
					
					anyDescriptionIsAboveLimit_validation =true;
					descriptionAboveLimit = purchaseGroupPage.validationForDescriptionLimit();
					if(descriptionAboveLimit) {
						logger.info("Proper validation for Limit ");
						Assert.assertTrue(true);
					}else {
						logger.info("No validation for Limit ");
						result = false;
						Assert.assertTrue(false, "No validation for Limit");
					}
					
				} catch (Exception e) {

					logger.info("Exception in Description Limit ");
					result = false;
					Assert.assertTrue(false, "Exception in Description Limit ");

				}
			}
			
			
			/* For special Character */
			if (convertionAndValidation.isOnlySpecialCharacter(purchaseGroupCode)) {
				logger.info("purchaseGroupCode contains only special character");

				try {
					anyPurchaseGroupIsEmpty_Validation = true;
					purchaseGroupCodeOnlySpecialChar_bool = purchaseGroupPage.validationForEmptyCode();
					if (purchaseGroupCodeOnlySpecialChar_bool) {
						logger.info("Proper validation for Only special char ");
						Assert.assertTrue(true);
					} else {
						logger.info("No validation for Only special char ");
						result = false;
						Assert.assertTrue(false, "No validation for only special char");
					}

				} catch (Exception e) {
					logger.info("Exception in Only special char ");
					result = false;
					Assert.assertTrue(false, "Exception in Only special char ");

				}

			}

			/* For empty purchaseGroupCode */
			if (purchaseGroupCode.isEmpty()) {
				logger.info("inside the empty purchaseGroupCode ---------------->>");
				try {

					try {
						purchaseGroupCodeEmpty_bool = purchaseGroupPage.validationForEmptyCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								purchaseGroupPage.validationForEmptyCode_WebElement());
					}

					if (purchaseGroupCodeEmpty_bool) {
						anyPurchaseGroupIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty purchaseGroupCode");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in purchaseGroupCode empty validation");
				}
			}

			/* For Duplicate purchaseGroupCode */

			else {

				for (int l = 0; l < allPurchaseGroupList.size(); l++) {

					if (allPurchaseGroupList.get(l).contains(purchaseGroupCode)) {

						logger.info("inside the duplicate purchaseGroupCode ---------------->>");
						try {
							purchaseGroupCodeDuplicate_bool = purchaseGroupPage.validationForDuplicatePurchaseGroupCode();

							if (purchaseGroupCodeDuplicate_bool) {
								anyPurchaseGroupIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate purchaseGroupCode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate purchaseGroupCode");
								Assert.assertTrue(false, "No validation for duplicate purchaseGroupCode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist purchaseGroupCode");
						}

					}
				}

			}

			if(vendorCode.isEmpty())
			{
				logger.info("inside the empty VendorCode ---------------->>");
				try {
						vendorCodeEmpty_bool = purchaseGroupPage.validationForEmptyVendorCode();
					if (vendorCodeEmpty_bool) {
						anyVendorCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						logger.info("Empty Vendor");
						Assert.assertTrue(false, "No validation for Empty vendorCode");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in vendorCode empty validation");
				}
			}
			
			
			
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false,"Exception in createPurchaseGroupCode() Method");
		} finally {
			finalResultMap.put(testCaseNumber, result);
			allPurchaseGroupList.add(purchaseGroupCode);
			result =true;
			flag++;

		}

	}
	
	@Test(priority = 2, enabled=false)
	public void savePurchaseGroupCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			postLoginPage.clickSave();
			if (anyPurchaseGroupIsDuplicate_Validation == true || anyPurchaseGroupIsEmpty_Validation == true
					|| anyPurchaseGroupIsSpecialChar_Validation == true || anyCodeIsAboveLimit_validation == true
					|| anyDescriptionIsAboveLimit_validation == true || anyVendorCodeIsEmpty_Validation == true) {
				logger.info("Save action if part-------------------result"+result);

				if (postLoginPage.errorMessage()) 
				{
					logger.info("proper msg for save action(can not save)");
					postLoginPage.clickTICK();

					postLoginPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						postLoginPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", postLoginPage.clickTICK_WebElement());
					}
					postLoginPage.clickPurchaseGroup();
					logger.info("Clicked succefully Purchase Group");
					Assert.assertTrue(true);
				}
				else 
				{
					logger.info("inside the error save");
					postLoginPage.clickCANCELPopUp();//newly added
					logger.info("Clicked succefully CANCELButton PopUp****");
					 
					
					postLoginPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						postLoginPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", postLoginPage.clickTICK_WebElement());
					}
					postLoginPage.clickPurchaseGroup();
					logger.info("Clicked succefully PurchaseGroup");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				postLoginPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(postLoginPage.clickEditButton_WebElement()));
				try {
					postLoginPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", postLoginPage.clickHomeButton_WebElement());
				}
				

				postLoginPage.clickPurchaseGroup();
				logger.info("Clicked succefully Purchase Group");

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside savePurchase Group");
		} finally {
			finalResultMap.put("SaveActionMultiplePurchaseGroupCode", result);
			flag = 1;
			result = true;
			anyPurchaseGroupIsEmpty_Validation = false;
			anyPurchaseGroupIsDuplicate_Validation = false;
			anyPurchaseGroupIsSpecialChar_Validation = false;
			anyVendorCodeIsEmpty_Validation = false;
			anyCodeIsAboveLimit_validation =false;
			anyDescriptionIsAboveLimit_validation=false;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}

	
	@DataProvider(name = "addPurchaseGroup")
	String[][] getData() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readSheetName);
		int colCount = XLUtils.getCellCount(path, readSheetName, 0);
		numberOfRecords = rowCount - 1;
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

	int count=0;
	@Test(priority = 3 , dataProvider = "search", enabled=false)
	public void searchPurchaseGroupPage(String testCaseNumber, String purchaseGroupCode, String description, String status, String searchMethod) throws IOException {

		try {
			
			logger.info("Inside the purchaseGroup Search.............result"+result);
			logger.info("no:of record -----"+numberOfRecords);
			purchaseGroupPage = new PurchaseGroupPage(driver);
			
			postLoginPage.clickSearchWidget();
			if(purchaseGroupCode.isBlank() == false) {
				
				purchaseGroupPage.sendPurchaseGroupCodeForSearch(purchaseGroupCode);
				logger.info("Send Purchase Group for search");
				
			}
			if(description.isBlank() == false) {
				
				purchaseGroupPage.sendPurchaseGroupDescriptionForSearch(description);
				logger.info("Send Description for search");
			}
			
			if(status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive")) {
				
				postLoginPage.clickStatus();
				if(status.equalsIgnoreCase("active")) {
					
					postLoginPage.clickActive();
					logger.info("selected Active status");
				}else {
					
					postLoginPage.clickInActive();
					logger.info("selected InActive status");
				}
				
			}else {
				
				postLoginPage.clickStatus();
				postLoginPage.clickBoth();
				logger.info("clicked both status");
			}
			
			if(searchMethod.equalsIgnoreCase("enter")) {
				
				purchaseGroupPage.clickEnterKeyOnPurchaseGroupCode();
				logger.info("Hits Enter key for Search in the PurchaseGroupCode ");
			}else {
				
				postLoginPage.clickSearchButtonLabel();
				logger.info("Clicked Search Button");
			}
			
			Thread.sleep(2000);
			
		// Validation is Stating from here	
			
			if(purchaseGroupCode.isBlank() == false) {
				
				try {
					
					String purchaseGroup = purchaseGroupPage.purchaseGroupCodeRow();
					logger.info("Purchase code getText()   ------- "+purchaseGroup);
					if(purchaseGroupCode.equalsIgnoreCase(purchaseGroup)) {
						logger.info("inside the purchaseGroup code match");
						Assert.assertTrue(true);
					}else {
						result =false;
						Assert.assertTrue(false, "There is no match Found(purchaseGroup)");
					}
					
				}catch (Exception e) {
					result= false;
					Assert.assertTrue(false, "Exception inside the purchaseGroup validation");
					logger.info("Exception inside the purchaseGroup validation");
				
				}
			
				
				
			}
			if(description.isBlank() == false) {
				
				try {
					logger.info("inside  the Description...................");
					String desc = purchaseGroupPage.PurchaseGroupDescriptionRow();
					if (description.equalsIgnoreCase(desc)) {
						
						logger.info("inside the purchaseGroup description match");
						Assert.assertTrue(true);
					}else {
						result = false;
						Assert.assertTrue(false, "There is no match Found(purchaseGroup)");
					}
					
				} catch (Exception e) {

					result= false;
					Assert.assertTrue(false, "Exception inside the description validation");
					logger.info("Exception inside the description validation");
				
				}
				
				
			}
						
		}
		catch (Exception e) {

		result =false;
		e.printStackTrace();
		logger.info("Exception in searchPurchaseGroupPage() method");
		
		}finally {
			
			purchaseGroupPage.clearPurchaseGroupCode();
			purchaseGroupPage.clearPurchaseGroupDescription();
			postLoginPage.clickSearchWidget();
			finalResultMap.put(testCaseNumber, result);
			if(count == numberOfRecords) {
				xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);
				
			}
			count++;
			result = true;
		}
	
	
	}
	
	
	
	
	
	@DataProvider(name = "search")
	String[][] getSearchData() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readSearch);
		int colCount = XLUtils.getCellCount(path, readSearch, 0);
		numberOfRecords = rowCount - 1;
		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readSearch, i, j).trim();
			}
		}

		return userdata;
	}	
	
	
	
	int k =0;
	@Test(priority = 6 , dataProvider = "reverse")
	public void checkReverseOrderValidation_PurchaseGroupCode(String testCaseNumber, String purchaseGroupCode, String description, String vendorCode, String vendorSiteCode, String status) throws IOException {
		
		try {
			
			purchaseGroupPage = new PurchaseGroupPage(driver);


			boolean purchaseGroupCodeEmpty_bool = false, purchaseGroupCodeDuplicate_bool = false,
					purchaseGroupCodeOnlySpecialChar_bool = false, CodeAboveLimit=false,
							descriptionAboveLimit=false, vendorCodeEmpty_bool = false;
			
			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					postLoginPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", postLoginPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					postLoginPage.clickAddButton();
				}
				 k = numberOfRecords+1;
			}

	System.out.println("k is -------------->"+k);		
	System.out.println("Flag is -------------->"+flag);
	System.out.println("Result .................."+result);
			
			// sending values to the text boxes
						while (k >= flag ||k!=flag || k == 1) {

							purchaseGroupPage.sendPurchaseGroupCode(k, purchaseGroupCode);
							purchaseGroupPage.sendPurchaseGroupDescription(k, description);
							
							if(vendorCode.isEmpty()==false)
							{
								try 
								{
									purchaseGroupPage.clickVendorCodeLookUp(k);
								} catch (Exception e) {

								js.executeScript("arguments[0].click();",purchaseGroupPage.clickVendorCodeLookUp_WebElement(k) );
								}
							
							
							try 
							{
								postLoginPage.sendSearchLookUp(vendorCode);
							} 
							catch (Exception e)
							{

								js.executeScript("arguments[0].click();", postLoginPage.sendSearchLookUp_WebElement());

							}
							convertionAndValidation.lookUpSearch("code", vendorCode, driver, js);
							
						   
							//Vendor Site Code
							      if(vendorSiteCode.isEmpty() == false)
							      {
							    	  purchaseGroupPage.clickVendorSITECodeLookUp(k);

							    	  try 
										{
											postLoginPage.sendSearchLookUp(vendorSiteCode);
										} 
										catch (Exception e)
										{

											js.executeScript("arguments[0].click();", postLoginPage.sendSearchLookUp_WebElement());

										}
										convertionAndValidation.lookUpSearch("code", vendorSiteCode, driver, js);
							    	  
							    	  
							      }
							}
							else
							{
								logger.info("Inside the Empty Vendor Code*, Tab Key actions");
								purchaseGroupPage.vendorCodeTextBox(k);
							}
							if (status.equalsIgnoreCase("inactive")) {
								purchaseGroupPage.clickInActiveStatus(k);
							}
							
						
							break;
						}
			
			

						// validation starts

						// purchaseGroupCode Length Validation
						if(purchaseGroupCode.length() > 20) {
							
							logger.info("Code Exceeds the Character Limit");
							try {
								
								anyCodeIsAboveLimit_validation =true;
								CodeAboveLimit = purchaseGroupPage.validationForCodeLimit();
								if(CodeAboveLimit) {
									logger.info("Proper validation for Limit ");
									Assert.assertTrue(true);
								}else {
									logger.info("No validation for Limit ");
									result = false;
									Assert.assertTrue(false, "No validation for Limit");
								}
								
							} catch (Exception e) {

								logger.info("Exception in Code Limit ");
								result = false;
								Assert.assertTrue(false, "Exception in Code Limit ");

							}
						}
						//Description Validation
						if(description.length() > 50) {
							
							logger.info("Description Exceeds the Character Limit");
							try {
								
								anyDescriptionIsAboveLimit_validation =true;
								descriptionAboveLimit = purchaseGroupPage.validationForDescriptionLimit();
								if(descriptionAboveLimit) {
									logger.info("Proper validation for Limit ");
									Assert.assertTrue(true);
								}else {
									logger.info("No validation for Limit ");
									result = false;
									Assert.assertTrue(false, "No validation for Limit");
								}
								
							} catch (Exception e) {

								logger.info("Exception in Description Limit ");
								result = false;
								Assert.assertTrue(false, "Exception in Description Limit ");

							}
						}
						
						
						/* For special Character */
						if (convertionAndValidation.isOnlySpecialCharacter(purchaseGroupCode)) {
							logger.info("purchaseGroupCode contains only special character");

							try {
								anyPurchaseGroupIsEmpty_Validation = true;
								purchaseGroupCodeOnlySpecialChar_bool = purchaseGroupPage.validationForEmptyCode();
								if (purchaseGroupCodeOnlySpecialChar_bool) {
									logger.info("Proper validation for Only special char ");
									Assert.assertTrue(true);
								} else {
									logger.info("No validation for Only special char ");
									result = false;
									Assert.assertTrue(false, "No validation for only special char");
								}

							} catch (Exception e) {
								logger.info("Exception in Only special char ");
								result = false;
								Assert.assertTrue(false, "Exception in Only special char ");

							}

						}

						/* For empty purchaseGroupCode */
						if (purchaseGroupCode.isEmpty()) {
							logger.info("inside the empty purchaseGroupCode ---------------->>");
							try {

								try {
									purchaseGroupCodeEmpty_bool = purchaseGroupPage.validationForEmptyCode();
								} catch (Exception e) {

									js.executeScript("arguments[0].click();",
											purchaseGroupPage.validationForEmptyCode_WebElement());
								}

								if (purchaseGroupCodeEmpty_bool) {
									anyPurchaseGroupIsEmpty_Validation = true;
									Assert.assertTrue(true);
								} else {
									result = false;
									Assert.assertTrue(false, "No validation for Empty purchaseGroupCode");
								}
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception in purchaseGroupCode empty validation");
							}
						}

						/* For Duplicate purchaseGroupCode */

						else {

							for (int l = 0; l < allPurchaseGroupList.size(); l++) {

								if (allPurchaseGroupList.get(l).contains(purchaseGroupCode)) {

									logger.info("inside the duplicate purchaseGroupCode ---------------->>");
									try {
										purchaseGroupCodeDuplicate_bool = purchaseGroupPage.validationForDuplicatePurchaseGroupCode();

										if (purchaseGroupCodeDuplicate_bool) {
											anyPurchaseGroupIsDuplicate_Validation = true;
											logger.info("Validation exist for duplicate purchaseGroupCode");

											Assert.assertTrue(true);
										} else {
											result = false;
											logger.info("No Validation for duplicate purchaseGroupCode");
											Assert.assertTrue(false, "No validation for duplicate purchaseGroupCode");
										}

									} catch (Exception e) {
										e.printStackTrace();
										result = false;
										Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist purchaseGroupCode");
									}

								}
							}

						}

						if(vendorCode.isEmpty())
						{
							logger.info("inside the empty VendorCode ---------------->>");
							try {
									vendorCodeEmpty_bool = purchaseGroupPage.validationForEmptyVendorCode();
								if (vendorCodeEmpty_bool) {
									anyVendorCodeIsEmpty_Validation = true;
									Assert.assertTrue(true);
								} else {
									result = false;
									logger.info("Empty Vendor");
									Assert.assertTrue(false, "No validation for Empty vendorCode");
								}
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception in vendorCode empty validation");
							}
						}
						

						
						
						
						
		}
		catch (Exception e) {

			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in Reverse Order Category");
		
		}finally {
			finalResultMap.put(testCaseNumber, result);
			allPurchaseGroupList.add(purchaseGroupCode);
			result = true;
			flag++;
			k--;
			if(k == 0) {
				
				xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);
			}
			
		}
		
	}
	
	
	
	@DataProvider(name = "reverse")
	String[][] getReverseData() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readReverse);
		int colCount = XLUtils.getCellCount(path, readReverse, 0);
		numberOfRecords = rowCount-1 ;
		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readReverse, i, j).trim();
			}
		}

		return userdata;
	}
	
	
	
	
}// class closed
