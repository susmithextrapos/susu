package com.extraPOSTest.testCases.inventory.maintenance;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.extraPOSTest.pageObjects.PostLoginPage;
import com.extraPOSTest.pageObjects.inventory.maintenance.CategoryPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.Listeners;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_Category extends BaseClass {

	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	CategoryPage categoryPage;
	String readSheetName = "addCategory";
	public List<String> allCategoryCodeList;
	int numberOfRecords;
	int flag = 1;
	boolean result = true;
	boolean anyCatCodeIsEmpty_Validation = false, anyCatCodeIsDuplicate_Validation = false,
			anyCatCodeIsSpecialChar_Validation = false, anyCodeIsAboveLimit_validation = false
			,anyDescriptionIsAboveLimit_validation = false ;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String writeSheetName = "OutputCategory";
	String readTwoRows = "addTwoRows";
	String readSearch = "searchByCodeORDescription";
	String readReverse = "reverseValidation";
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

		categoryPage = new CategoryPage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickCategory();
		logger.info("Clicked succefully Category");

		try {

			categoryPage.clickSelectDropDown();

		} catch (Exception e) {
			js.executeScript("arguments[0].click();", categoryPage.clickSelectDropDown_WebElement());
		}
		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryPage.allBy()));

		try {
			categoryPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", categoryPage.clickAll_WebElement());
		}
		allCategoryCodeList = new ArrayList<>();
		List<WebElement> allCatCode = categoryPage.allCategoryCodeList();
		logger.info("Size of the CatCode List -->> " + allCatCode.size());
		for (int i = 0; i < allCatCode.size(); i++) {
			allCategoryCodeList.add(allCatCode.get(i).getText());
		}

	}

	@Test(dataProvider = "addCategory", priority = 1, enabled=true)
	public void createCategoryCode(String testCaseNumber, String categoryCode, String description, String status) {

		try {

			categoryPage = new CategoryPage(driver);
			boolean categoryCodeEmpty_bool = false, categoryCodeDuplicate_bool = false,
					categoryCodeOnlySpecialChar_bool = false, CodeAboveLimit=false,
							descriptionAboveLimit=false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					categoryPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", categoryPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					categoryPage.clickAddButton();
				}

			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				categoryPage.sendCategoryCode(k, categoryCode);
				categoryPage.sendCategoryDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					categoryPage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts

			// CatagoryCode Length Validation
			if(categoryCode.length() > 20) {
				
				logger.info("Code Exceeds the Character Limit");
				try {
					
					anyCodeIsAboveLimit_validation =true;
					CodeAboveLimit = categoryPage.validationForCodeLimit();
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
					descriptionAboveLimit = categoryPage.validationForDescriptionLimit();
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
			if (convertionAndValidation.isOnlySpecialCharacter(categoryCode)) {
				logger.info("Category code contains only special character");

				try {
					anyCatCodeIsSpecialChar_Validation = true;
					categoryCodeOnlySpecialChar_bool = categoryPage.validationForEmptyCategoryCode();
					if (categoryCodeOnlySpecialChar_bool) {
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

			/* For empty CatCode */
			if (categoryCode.isEmpty()) {
				logger.info("inside the empty catcode ---------------->>");
				try {

					try {
						categoryCodeEmpty_bool = categoryPage.validationForEmptyCategoryCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								categoryPage.validationForEmptyCategoryCode_WebElement());
					}

					if (categoryCodeEmpty_bool) {
						anyCatCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty category code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in catcode empty validation");
				}
			}

			/* For Duplicate CatCode */

			else {

				for (int l = 0; l < allCategoryCodeList.size(); l++) {

					if (allCategoryCodeList.get(l).contains(categoryCode)) {

						logger.info("inside the duplicate catcode ---------------->>");
						try {
							categoryCodeDuplicate_bool = categoryPage.validationForDuplicateCategoryCode();

							if (categoryCodeDuplicate_bool) {
								anyCatCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate catcode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate catcode");
								Assert.assertTrue(false, "No validation for duplicate catcode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist CatCode");
						}

					}
				}

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false,"Exception in createCategoryCode() Method");
		} finally {
			finalResultMap.put(testCaseNumber, result);
			allCategoryCodeList.add(categoryCode);
			result =true;
			flag++;

		}

	}

	@Test(priority = 2, enabled=true)
	public void saveCategoryCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			categoryPage.clickSave();
			if (anyCatCodeIsDuplicate_Validation == true || anyCatCodeIsEmpty_Validation == true
					|| anyCatCodeIsSpecialChar_Validation == true || anyCodeIsAboveLimit_validation == true
					|| anyDescriptionIsAboveLimit_validation == true) {
				logger.info("Save action if part-------------------result"+result);
				// categoryPage.clickTICK();
				if (categoryPage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					categoryPage.clickTICK();

					categoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						categoryPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", categoryPage.clickTICK_WebElement());
					}
					postLoginPage.clickCategory();
					logger.info("Clicked succefully Category");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					categoryPage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					categoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						categoryPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", categoryPage.clickTICK_WebElement());
					}
					postLoginPage.clickCategory();
					logger.info("Clicked succefully Category");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				categoryPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(categoryPage.clickEditButton_WebElement()));
				try {
					categoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", categoryPage.clickHomeButton_WebElement());
				}
				

				postLoginPage.clickCategory();
				logger.info("Clicked succefully Category");

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveCategory");
		} finally {
			finalResultMap.put("SaveActionMultipleCatCode", result);
			flag = 1;
			result = true;
			anyCatCodeIsEmpty_Validation = false;
			anyCatCodeIsDuplicate_Validation = false;
			anyCatCodeIsSpecialChar_Validation = false;
			anyCodeIsAboveLimit_validation =false;
			anyDescriptionIsAboveLimit_validation=false;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}

	@DataProvider(name = "addCategory")
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

	@Test(dataProvider = "addTwoRows", priority = 3 ,enabled=true)
	public void addTwoSameCategoryCode(String testCaseNumber, String categoryCode, String description, String status) {

		try {
			logger.info("Inside the Add Two Rows ....................");
			categoryPage = new CategoryPage(driver);
			boolean categoryCodeDuplicate_bool = false, categoryCodeEmpty_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					categoryPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", categoryPage.clickEditButton_WebElement());
				}
				System.out.println("No of Records " + numberOfRecords);
				for (int i = 0; i <= numberOfRecords; i++) {
					categoryPage.clickAddButton();
				}
			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				categoryPage.sendCategoryCode(k, categoryCode);
				categoryPage.sendCategoryDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					categoryPage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			/* For empty CatCode */
			if (categoryCode.isEmpty()) {
				logger.info("inside the empty catcode ---------------->>");
				try {

					try {
						categoryCodeEmpty_bool = categoryPage.validationForEmptyCategoryCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								categoryPage.validationForEmptyCategoryCode_WebElement());
					}

					if (categoryCodeEmpty_bool) {
						anyCatCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty category code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in catcode empty validation");
				}
			}

			/* For Duplicate CatCode */

			else {

				for (int l = 0; l < allCategoryCodeList.size(); l++) {

					if (allCategoryCodeList.get(l).contains(categoryCode)) {

						logger.info("inside the duplicate catcode ---------------->>");
						try {
							categoryCodeDuplicate_bool = categoryPage.validationForDuplicateCategoryCode();

							if (categoryCodeDuplicate_bool) {
								anyCatCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate catcode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate catcode");
								Assert.assertTrue(false, "No validation for duplicate catcode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist CatCode");
						}

					}
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			result = false;
			Assert.assertTrue(false, "Exception in Add Two Category code");

		} finally {
			flag++;
			allCategoryCodeList.add(categoryCode);
			finalResultMap.put(testCaseNumber, result);
			result = true;
		}

	}

	@Test(priority = 4,enabled=true)
	public void saveTwoCategoryCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			categoryPage.clickSave();
			if (anyCatCodeIsDuplicate_Validation == true || anyCatCodeIsEmpty_Validation == true) {
				logger.info("Save action if part-------------------result"+result);
				// categoryPage.clickTICK();
				if (categoryPage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					categoryPage.clickTICK();

					categoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						categoryPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", categoryPage.clickTICK_WebElement());
					}
					postLoginPage.clickCategory();
					logger.info("Clicked succefully Category");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					categoryPage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					categoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						categoryPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", categoryPage.clickTICK_WebElement());
					}
					postLoginPage.clickCategory();
					logger.info("Clicked succefully Category");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				categoryPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(categoryPage.clickEditButton_WebElement()));
				try {
					categoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", categoryPage.clickHomeButton_WebElement());
				}

				postLoginPage.clickCategory();
				logger.info("Clicked succefully Category");

			}

		} catch (Exception e) {
			result =false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveCategory");
		} finally {
			finalResultMap.put("SaveActionTwoSameCatCode", result);
			flag = 1;
			result = true;
			anyCatCodeIsEmpty_Validation = false;
			anyCatCodeIsDuplicate_Validation = false;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}

	@DataProvider(name = "addTwoRows")
	String[][] getTwoRowsData() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readTwoRows);
		int colCount = XLUtils.getCellCount(path, readTwoRows, 0);
		numberOfRecords = rowCount - 1;
		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readTwoRows, i, j).trim();
			}
		}

		return userdata;
	}
	
	
	int count=0;
	
	@Test(priority = 5 , dataProvider = "search", enabled=true)
	public void searchCategoryPage(String testCaseNumber, String categoryCode, String description, String status, String searchMethod) throws IOException {

		try {
			
			logger.info("Inside the Category Search.............result"+result);
			logger.info("noof record -----"+numberOfRecords);
			categoryPage = new CategoryPage(driver);
			
			categoryPage.clickSearchWidget();
			if(categoryCode.isBlank() == false) {
				
				categoryPage.sendCatCodeForSearch(categoryCode);
				logger.info("Send catcode for search");
				
			}
			if(description.isBlank() == false) {
				
				categoryPage.sendCatDescriptionForSearch(description);
				logger.info("Send Description for search");
			}
			
			if(status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive")) {
				
				categoryPage.clickStatus();
				if(status.equalsIgnoreCase("active")) {
					
					categoryPage.clickActive();
					logger.info("selected Active status");
				}else {
					
					categoryPage.clickInActive();
					logger.info("selected InActive status");
				}
				
			}else {
				
				categoryPage.clickStatus();
				categoryPage.clickBoth();
				logger.info("clicked both status");
			}
			
			if(searchMethod.equalsIgnoreCase("enter")) {
				
				categoryPage.clickEnterKeyOnCatCode();
				logger.info("Hits Enter key for Search in the cat code ");
			}else {
				
				categoryPage.clickSearchButton();
				logger.info("Clicked Search Button");
			}
			
			Thread.sleep(2000);
			
		// Validation is Stating from here	
			
			if(categoryCode.isBlank() == false) {
				
				try {
					
					String catcode = categoryPage.CatCodeRow();
					logger.info("cat code getText()   ------- "+catcode);
					if(categoryCode.equalsIgnoreCase(catcode)) {
						logger.info("inside the cat code match");
						Assert.assertTrue(true);
					}else {
						result =false;
						Assert.assertTrue(false, "There is no match Found(CatCode)");
					}
					
				}catch (Exception e) {
					result= false;
					Assert.assertTrue(false, "Exception inside the CatCode validation");
					logger.info("Exception inside the CatCode validation");
				
				}
			
				
				
			}
			if(description.isBlank() == false) {
				
				try {
					
					String desc = categoryPage.CatDescriptionRow();
					if (description.equalsIgnoreCase(desc)) {
						
						logger.info("inside the cat description match");
						Assert.assertTrue(true);
					}else {
						result = false;
						Assert.assertTrue(false, "There is no match Found(CatCode)");
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
		logger.info("Exception in searchCategoryPage() method");
		
		}finally {
			
			categoryPage.sendCatCodeForSearchKeys();
			categoryPage.sendCatDescriptionForSearchKeys();
			categoryPage.clickSearchWidget();
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
	public void checkReverseOrderValidation_CategoryCode(String testCaseNumber, String categoryCode, String description, String status) throws IOException {
		
		try {
			
			categoryPage = new CategoryPage(driver);
			boolean categoryCodeEmpty_bool = false, categoryCodeDuplicate_bool = false,
					categoryCodeOnlySpecialChar_bool=false;
			
			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					categoryPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", categoryPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					categoryPage.clickAddButton();
				}
				 k = numberOfRecords+1;
			}

	System.out.println("k is -------------->"+k);		
	System.out.println("Flag is -------------->"+flag);
	System.out.println("Result .................."+result);
			
			// sending values to the text boxes
						while (k >= flag ||k!=flag || k == 1) {

							categoryPage.sendCategoryCode(k, categoryCode);
							categoryPage.sendCategoryDescription(k, description);

							if (status.equalsIgnoreCase("inactive")) {
								categoryPage.clickInActiveStatus(k);
							}
							break;
						}
			
			
						// validation starts

						/* For special Character */
						if (convertionAndValidation.isOnlySpecialCharacter(categoryCode)) {
							logger.info("Category code contains only special character");

							try {
								anyCatCodeIsSpecialChar_Validation = true;
								categoryCodeOnlySpecialChar_bool = categoryPage.validationForEmptyCategoryCode();
								if (categoryCodeOnlySpecialChar_bool) {
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

						/* For empty CatCode */
						if (categoryCode.isEmpty()) {
							logger.info("inside the empty catcode ---------------->>");
							try {

								try {
									categoryCodeEmpty_bool = categoryPage.validationForEmptyCategoryCode();
								} catch (Exception e) {

									js.executeScript("arguments[0].click();",
											categoryPage.validationForEmptyCategoryCode_WebElement());
								}

								if (categoryCodeEmpty_bool) {
									anyCatCodeIsEmpty_Validation = true;
									Assert.assertTrue(true);
								} else {
									result = false;
									Assert.assertTrue(false, "No validation for Empty category code");
								}
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception in catcode empty validation");
							}
						}

						/* For Duplicate CatCode */

						else {

							for (int l = 0; l < allCategoryCodeList.size(); l++) {

								if (allCategoryCodeList.get(l).contains(categoryCode)) {

									logger.info("inside the duplicate catcode ---------------->>");
									try {
										categoryCodeDuplicate_bool = categoryPage.validationForDuplicateCategoryCode();

										if (categoryCodeDuplicate_bool) {
											anyCatCodeIsDuplicate_Validation = true;
											logger.info("Validation exist for duplicate catcode");

											Assert.assertTrue(true);
										} else {
											result = false;
											logger.info("No Validation for duplicate catcode");
											Assert.assertTrue(false, "No validation for duplicate catcode");
										}

									} catch (Exception e) {
										e.printStackTrace();
										result = false;
										Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist CatCode");
									}

								}
							}

						}
		}
		catch (Exception e) {

			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in Reverse Order Category");
		
		}finally {
			finalResultMap.put(testCaseNumber, result);
			allCategoryCodeList.add(categoryCode);
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
	
	
}
