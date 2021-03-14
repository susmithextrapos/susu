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
import com.extraPOSTest.pageObjects.inventory.maintenance.SubCategoryPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_SubCategory extends BaseClass {

	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	SubCategoryPage subCategoryPage;
	public List<String> allSubCategoryCodeList;
	String readSheetName = "addSubCategory";
	String readTwoRows = "addTwoRows";
	
	int numberOfRecords;
	int flag = 1;
	boolean result = true;
	boolean anySubCatCodeIsEmpty_Validation = false, anySubCatCodeIsDuplicate_Validation = false,
			anySubCatCodeIsSpecialChar_Validation = false,anyCodeIsAboveLimit_validation =false,
					anyDescriptionIsAboveLimit_validation= false;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String writeSheetName = "OutputSubCategory";
	ConvertionAndValidation convertionAndValidation = new ConvertionAndValidation();
	String readSearch = "searchByCodeORDescription";
	String readReverse = "reverseValidation";

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
	public void selectALL_forSubCategoryCode() throws InterruptedException {

		subCategoryPage = new SubCategoryPage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickSubCategory();
		logger.info("Clicked succefully SubCategory");

		try {

			subCategoryPage.clickSelectDropDown();

		} catch (Exception e) {
			js.executeScript("arguments[0].click();", subCategoryPage.clickSelectDropDown_WebElement());
		}
		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(subCategoryPage.allBy()));

		try {
			subCategoryPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", subCategoryPage.clickAll_WebElement());
		}
		allSubCategoryCodeList = new ArrayList<>();
		List<WebElement> allSubCatCode = subCategoryPage.allSubCategoryCodeList();
		logger.info("Size of the SubCatCode List -->> " + allSubCatCode.size());
		for (int i = 0; i < allSubCatCode.size(); i++) {
			allSubCategoryCodeList.add(allSubCatCode.get(i).getText());
		}

	}

	@Test(dataProvider = "addSubCategory", priority = 1, enabled = true)
	public void createSubCategoryCode(String testCaseNumber, String subcategoryCode, String description,
			String status) {

		try {

			subCategoryPage = new SubCategoryPage(driver);
			boolean subCategoryCodeEmpty_bool = false, subCategoryCodeDuplicate_bool = false,
					subCategoryCodeOnlySpecialChar_bool = false, CodeAboveLimit  =false,descriptionAboveLimit=false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					subCategoryPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", subCategoryPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					subCategoryPage.clickAddButton();
				}

			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				subCategoryPage.sendSubCategoryCode(k, subcategoryCode);
				subCategoryPage.sendSubCategoryDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					subCategoryPage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			
			
			// CatagoryCode Length Validation
						if(subcategoryCode.length() > 20) {
							
							logger.info("Code Exceeds the Character Limit");
							try {
								
								anyCodeIsAboveLimit_validation =true;
								CodeAboveLimit = subCategoryPage.validationForCodeLimit();
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
								descriptionAboveLimit = subCategoryPage.validationForDescriptionLimit();
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
			if (convertionAndValidation.isOnlySpecialCharacter(subcategoryCode)) {
				logger.info("SubCategory code contains only special character");

				try {
					anySubCatCodeIsSpecialChar_Validation = true;
					subCategoryCodeOnlySpecialChar_bool = subCategoryPage.validationForEmptySubCategoryCode();
					;
					if (subCategoryCodeOnlySpecialChar_bool) {
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
			if (subcategoryCode.isEmpty()) {
				logger.info("inside the empty SubCatCode ---------------->>");
				try {

					try {
						subCategoryCodeEmpty_bool = subCategoryPage.validationForEmptySubCategoryCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								subCategoryPage.validationForEmptySubCategoryCode_WebElement());
					}

					if (subCategoryCodeEmpty_bool) {
						anySubCatCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Subcategory code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in Subcatcode empty validation");
				}
			}

			/* For Duplicate CatCode */

			else {

				for (int l = 0; l < allSubCategoryCodeList.size(); l++) {

					if (allSubCategoryCodeList.get(l).contains(subcategoryCode)) {

						logger.info("inside the duplicate subcatcode ---------------->>");
						try {
							subCategoryCodeDuplicate_bool = subCategoryPage.validationForDuplicateSubCategoryCode();

							if (subCategoryCodeDuplicate_bool) {
								anySubCatCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate Subcatcode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate Subcatcode");
								Assert.assertTrue(false, "No validation for duplicate Subcatcode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist SubCatCode");
						}

					}
				}

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in createSubCategoryCode() Method");
		} finally {
			finalResultMap.put(testCaseNumber, result);
			allSubCategoryCodeList.add(subcategoryCode);
			result = true;
			flag++;

		}

	}

	@Test(priority = 2, enabled = true)
	public void saveSubCategoryCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			subCategoryPage.clickSave();
			if (anySubCatCodeIsDuplicate_Validation == true || anySubCatCodeIsEmpty_Validation == true
					|| anySubCatCodeIsSpecialChar_Validation == true || anyCodeIsAboveLimit_validation == true 
					|| anyDescriptionIsAboveLimit_validation ==true) {
				logger.info("Save action if part-------------------result" + result);
				if (subCategoryPage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					subCategoryPage.clickTICK();

					subCategoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						subCategoryPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", subCategoryPage.clickTICK_WebElement());
					}
					postLoginPage.clickSubCategory();
					logger.info("Clicked succefully SubCategory");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					subCategoryPage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					subCategoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						subCategoryPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", subCategoryPage.clickTICK_WebElement());
					}
					postLoginPage.clickSubCategory();
					logger.info("Clicked succefully SubCategory");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				subCategoryPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(subCategoryPage.clickEditButton_WebElement()));
				try {
					subCategoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", subCategoryPage.clickHomeButton_WebElement());
				}

				postLoginPage.clickCategory();
				logger.info("Clicked succefully Category");

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveSubCategory");
		} finally {
			finalResultMap.put("SaveActionMultipleSubCatCode", result);
			flag = 1;
			result = true;
			anySubCatCodeIsEmpty_Validation = false;
			anySubCatCodeIsDuplicate_Validation = false;
			anySubCatCodeIsSpecialChar_Validation = false;
			anyCodeIsAboveLimit_validation = false;
			anyDescriptionIsAboveLimit_validation =false;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}

	@DataProvider(name = "addSubCategory")
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

	@Test(dataProvider = "addTwoRows", priority = 3, enabled = true)
	public void addTwoSameSubCategoryCode(String testCaseNumber, String subcategoryCode, String description,
			String status) {

		try {
			logger.info("Inside the Add Two Rows ....................");
			subCategoryPage = new SubCategoryPage(driver);
			boolean subCategoryCodeDuplicate_bool = false, subCategoryCodeEmpty_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					subCategoryPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", subCategoryPage.clickEditButton_WebElement());
				}
				System.out.println("No of Records " + numberOfRecords);
				for (int i = 0; i <= numberOfRecords; i++) {
					subCategoryPage.clickAddButton();
				}
			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				subCategoryPage.sendSubCategoryCode(k, subcategoryCode);
				subCategoryPage.sendSubCategoryDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					subCategoryPage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			/* For empty CatCode */
			if (subcategoryCode.isEmpty()) {
				logger.info("inside the empty SubCatCode ---------------->>");
				try {

					try {
						subCategoryCodeEmpty_bool = subCategoryPage.validationForEmptySubCategoryCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								subCategoryPage.validationForEmptySubCategoryCode_WebElement());
					}

					if (subCategoryCodeEmpty_bool) {
						anySubCatCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Subcategory code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in Subcatcode empty validation");
				}
			}

			/* For Duplicate CatCode */

			else {

				for (int l = 0; l < allSubCategoryCodeList.size(); l++) {

					if (allSubCategoryCodeList.get(l).contains(subcategoryCode)) {

						logger.info("inside the duplicate subcatcode ---------------->>");
						try {
							subCategoryCodeDuplicate_bool = subCategoryPage.validationForDuplicateSubCategoryCode();

							if (subCategoryCodeDuplicate_bool) {
								anySubCatCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate Subcatcode");

								Assert.assertTrue(true);
							} else {
								result = false;
								
								logger.info("No Validation for duplicate Subcatcode");
								Assert.assertTrue(false, "No validation for duplicate Subcatcode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist SubCatCode");
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
			allSubCategoryCodeList.add(subcategoryCode);
			finalResultMap.put(testCaseNumber, result);
			result = true;
		}

	}
	
	@Test(priority = 4,enabled=true)
	public void saveTwoSubCategoryCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			subCategoryPage.clickSave();
			if (anySubCatCodeIsDuplicate_Validation == true || anySubCatCodeIsEmpty_Validation == true) {
				logger.info("Save action if part-------------------result"+result);
				// categoryPage.clickTICK();
				if (subCategoryPage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					subCategoryPage.clickTICK();

					subCategoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						subCategoryPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", subCategoryPage.clickTICK_WebElement());
					}
					postLoginPage.clickSubCategory();
					logger.info("Clicked succefully SubCategory");
					Assert.assertTrue(true);
				} else {
					
					logger.info("inside the error save");
					subCategoryPage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					subCategoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						subCategoryPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", subCategoryPage.clickTICK_WebElement());
					}
					postLoginPage.clickSubCategory();
					logger.info("Clicked succefully SubCategory");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				subCategoryPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(subCategoryPage.clickEditButton_WebElement()));
				
				
				try {
					subCategoryPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", subCategoryPage.clickHomeButton_WebElement());
				}
				postLoginPage.clickSubCategory();
				logger.info("Clicked succefully SubCategory");

			}

		} catch (Exception e) {
			result =false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveSubCategory");
		} finally {
			finalResultMap.put("SaveActionTwoSameSubCatCode", result);
			flag = 1;
			result = true;
			anySubCatCodeIsEmpty_Validation = false;
			anySubCatCodeIsDuplicate_Validation = false;
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
	public void searchCategoryPage(String testCaseNumber, String subcategoryCode, String description, String status, String searchMethod) throws IOException {

		try {
			
			logger.info("Inside the SubCategory Search.............result"+result);
			logger.info("noof record -----"+numberOfRecords);
			subCategoryPage = new SubCategoryPage(driver);
			
			subCategoryPage.clickSearchWidget();
			if(subcategoryCode.isBlank() == false) {
				
				subCategoryPage.sendSubCatCodeForSearch(subcategoryCode);
				logger.info("Send subcatcode for search");
				
			}
			if(description.isBlank() == false) {
				
				subCategoryPage.sendSubCatDescriptionForSearch(description);
				logger.info("Send Description for search");
			}
			
			if(status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive")) {
				
				subCategoryPage.clickStatus();
				if(status.equalsIgnoreCase("active")) {
					
					subCategoryPage.clickActive();
					logger.info("selected Active status");
				}else {
					
					subCategoryPage.clickInActive();
					logger.info("selected InActive status");
				}
				
			}else {
				
				subCategoryPage.clickStatus();
				subCategoryPage.clickBoth();
				logger.info("clicked both status");
			}
			
			if(searchMethod.equalsIgnoreCase("enter")) {
				
				subCategoryPage.clickEnterKeyOnCatCode();
				logger.info("Hits Enter key for Search in the subcatcode ");
			}else {
				
				subCategoryPage.clickSearchButton();
				logger.info("Clicked Search Button");
			}
			
			Thread.sleep(3000);
			
		// Validation is Stating from here	
			
			if(subcategoryCode.isBlank() == false) {
				
				try {
					
					String subcatcode = subCategoryPage.subCatCodeRow();
					logger.info("cat code getText()   ------- "+subcatcode);
					if(subcategoryCode.equalsIgnoreCase(subcatcode)) {
						logger.info("inside the subcatcode match");
						Assert.assertTrue(true);
					}else {
						result =false;
						Assert.assertTrue(false, "There is no match Found(CatCode)");
					}
					
				}catch (Exception e) {
					result= false;
					Assert.assertTrue(false, "Exception inside the SubCatCode validation");
					logger.info("Exception inside the SubCatCode validation");
				
				}
			
				
				
			}
			if(description.isBlank() == false) {
				
				try {
					
					String desc = subCategoryPage.subCatDescriptionRow();
					if (description.equalsIgnoreCase(desc)) {
						
						logger.info("inside the Subcat description match");
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
			
			subCategoryPage.sendSubCatCodeForSearchKeys();
			subCategoryPage.sendSubCatDescriptionForSearchKeys();
			subCategoryPage.clickSearchWidget();
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
	public void checkReverseOrderValidation_SubCategoryCode(String testCaseNumber, String subcategoryCode, String description, String status) throws IOException {
		
		try {
			
			subCategoryPage = new SubCategoryPage(driver);
			boolean subCategoryCodeEmpty_bool = false, subCategoryCodeDuplicate_bool = false,
					subCategoryCodeOnlySpecialChar_bool=false;
			
			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					subCategoryPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", subCategoryPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					subCategoryPage.clickAddButton();
				}
				 k = numberOfRecords+1;
			}

	System.out.println("k is -------------->"+k);		
	System.out.println("Flag is -------------->"+flag);
	System.out.println("Result .................."+result);
			
			// sending values to the text boxes
						while (k >= flag ||k!=flag || k == 1) {

							subCategoryPage.sendSubCategoryCode(k, subcategoryCode);
							subCategoryPage.sendSubCategoryDescription(k, description);

							if (status.equalsIgnoreCase("inactive")) {
								subCategoryPage.clickInActiveStatus(k);
							}
							break;
						}
			
			
						// validation starts


						/* For special Character */
						if (convertionAndValidation.isOnlySpecialCharacter(subcategoryCode)) {
							logger.info("SubCategory code contains only special character");

							try {
								anySubCatCodeIsSpecialChar_Validation = true;
								subCategoryCodeOnlySpecialChar_bool = subCategoryPage.validationForEmptySubCategoryCode();
								;
								if (subCategoryCodeOnlySpecialChar_bool) {
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
						if (subcategoryCode.isEmpty()) {
							logger.info("inside the empty SubCatCode ---------------->>");
							try {

								try {
									subCategoryCodeEmpty_bool = subCategoryPage.validationForEmptySubCategoryCode();
								} catch (Exception e) {

									js.executeScript("arguments[0].click();",
											subCategoryPage.validationForEmptySubCategoryCode_WebElement());
								}

								if (subCategoryCodeEmpty_bool) {
									anySubCatCodeIsEmpty_Validation = true;
									Assert.assertTrue(true);
								} else {
									result = false;
									Assert.assertTrue(false, "No validation for Empty Subcategory code");
								}
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception in Subcatcode empty validation");
							}
						}

						/* For Duplicate CatCode */

						else {

							for (int l = 0; l < allSubCategoryCodeList.size(); l++) {

								if (allSubCategoryCodeList.get(l).contains(subcategoryCode)) {

									logger.info("inside the duplicate subcatcode ---------------->>");
									try {
										subCategoryCodeDuplicate_bool = subCategoryPage.validationForDuplicateSubCategoryCode();

										if (subCategoryCodeDuplicate_bool) {
											anySubCatCodeIsDuplicate_Validation = true;
											logger.info("Validation exist for duplicate Subcatcode");

											Assert.assertTrue(true);
										} else {
											result = false;
											logger.info("No Validation for duplicate Subcatcode");
											Assert.assertTrue(false, "No validation for duplicate Subcatcode");
										}

									} catch (Exception e) {
										e.printStackTrace();
										result = false;
										Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist SubCatCode");
									}

								}
							}

						}

		}
		catch (Exception e) {

			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in SubCategory Reverse Order ");
		}finally {
			finalResultMap.put(testCaseNumber, result);
			allSubCategoryCodeList.add(subcategoryCode);
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
