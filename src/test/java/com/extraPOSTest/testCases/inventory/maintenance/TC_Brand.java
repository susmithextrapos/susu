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
import com.extraPOSTest.pageObjects.inventory.maintenance.BrandPage;
import com.extraPOSTest.pageObjects.inventory.maintenance.CategoryPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_Brand extends BaseClass{
	
	
	
	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	BrandPage brandPage;
	public List<String> allBrandCodeList;
	int numberOfRecords;
	int flag = 1;
	boolean result =true;
	boolean anyBrandCodeIsEmpty_Validation = false, anyBrandCodeIsDuplicate_Validation = false, anyBrandCodeIsSpecialChar_Validation = false;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String readSheetName = "addBrand";
	String writeSheetName = "OutputBrand";
	String readSearch = "searchByCodeORDescription";
	ConvertionAndValidation convertionAndValidation = new ConvertionAndValidation();
	String readTwoRows = "addTwoRows";
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
	public void selectALL_forBrandCode() throws InterruptedException {

		brandPage = new BrandPage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickBrand();
		logger.info("Clicked succefully brand");
	

		try {
		
			brandPage.clickSelectDropDown();
			
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", brandPage.clickSelectDropDown_WebElement());
		}
		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(brandPage.allBy()));

		try {
			brandPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", brandPage.clickAll_WebElement());
		}
		allBrandCodeList = new ArrayList<>();
		List<WebElement> allbrandCode = brandPage.allbrandCodeList();
		logger.info("Size of the SubCatCode List -->> " + allbrandCode.size());
		for (int i = 0; i < allbrandCode.size(); i++) {
			allBrandCodeList.add(allbrandCode.get(i).getText());
		}

	}

	@Test(dataProvider = "addBrand", priority = 1)
	public void createBrandCode(String testCaseNumber, String brandCode, String description, String status) {

		try {
			
			brandPage = new BrandPage(driver);
			boolean brandCodeEmpty_bool = false, brandCodeDuplicate_bool = false, brandCodeOnlySpecialChar_bool;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					logger.info("Inside the createBrandCode");
					brandPage.clickEditButton();
										
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", brandPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					brandPage.clickAddButton();
				}

			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {
				System.out.println("Brand is : "+brandCode+"  "+ "description is :"+description);
				brandPage.sendBrandCode(k, brandCode);
				brandPage.sendBrandDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					brandPage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			
			/* For special Character */
			if (convertionAndValidation.isOnlySpecialCharacter(brandCode)) {
				logger.info("B code contains only special character");

				try {
					anyBrandCodeIsSpecialChar_Validation = true;
					brandCodeOnlySpecialChar_bool = brandPage.validationForEmptyBrandCode();
					if (brandCodeOnlySpecialChar_bool) {
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

			
			
			
			/* For empty BrandCode */
			if (brandCode.isEmpty()) {
				logger.info("inside the empty brandcode ---------------->>");
				try {

					try {
						brandCodeEmpty_bool = brandPage.validationForEmptyBrandCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								brandPage.validationForEmptyBrandCode_WebElement());
					}

					if (brandCodeEmpty_bool) {
						anyBrandCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Brand code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in Brand empty validation");
				}
			}
			
			/* For Duplicate BrandCode */

			else {

				for (int l = 0; l < allBrandCodeList.size(); l++) {

					if (allBrandCodeList.get(l).contains(brandCode)) {

						logger.info("inside the duplicate brandcode ---------------->>");
						try {
							brandCodeDuplicate_bool = brandPage.validationForDuplicateBrandCode();

							if (brandCodeDuplicate_bool) {
								anyBrandCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate brandCode");
								Assert.assertTrue(true);
							} else {
								logger.info("No Validation for duplicate brandCode");
								result =false;
								Assert.assertTrue(false, "No validation for duplicate brandcode");
							}
							
						} catch (Exception e) {
							result =false;
							e.printStackTrace();
							Assert.assertTrue(false, "Exception for Duplicate/Already Exist brandCode,");
						}

					}
				}

			}

		} catch (Exception e) {
			result =false;
			e.printStackTrace();
			Assert.assertTrue(false,"Exception in createBrandCode() Method");
		} finally {
			finalResultMap.put(testCaseNumber, result);
			allBrandCodeList.add(brandCode);
			result =true;
			flag++;
			
		}

	}
	
	
	@Test(priority = 2, enabled=true)
	public void saveBrandCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			brandPage.clickSave();
			if (anyBrandCodeIsDuplicate_Validation == true || anyBrandCodeIsEmpty_Validation == true
					|| anyBrandCodeIsSpecialChar_Validation == true) {
				logger.info("Save action if part-------------------result"+result);
				if (brandPage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					brandPage.clickTICK();

					brandPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						brandPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", brandPage.clickTICK_WebElement());
					}
					postLoginPage.clickBrand();
					logger.info("Clicked succefully Brand");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					brandPage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					brandPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						brandPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", brandPage.clickTICK_WebElement());
					}
					postLoginPage.clickBrand();
					logger.info("Clicked succefully Brand");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				brandPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(brandPage.clickEditButton_WebElement()));
				try {
					brandPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", brandPage.clickHomeButton_WebElement());
				}
				

				postLoginPage.clickBrand();
				logger.info("Clicked succefully Brand");

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveBrand");
		} finally {
			finalResultMap.put("SaveActionMultipleBrandCode", result);
			flag = 1;
			result = true;
			anyBrandCodeIsEmpty_Validation = false;
			anyBrandCodeIsDuplicate_Validation = false;
			anyBrandCodeIsSpecialChar_Validation = false;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}

	
	
	@DataProvider(name = "addBrand")
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
	public void addTwoSameBrandCode(String testCaseNumber, String brandCode, String description, String status) {

		try {
			logger.info("Inside the Add Two Rows ....................");
			brandPage = new BrandPage(driver);
			boolean brandCodeDuplicate_bool = false, brandCodeEmpty_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					brandPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", brandPage.clickEditButton_WebElement());
				}
				System.out.println("No of Records " + numberOfRecords);
				for (int i = 0; i <= numberOfRecords; i++) {
					brandPage.clickAddButton();
				}
			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				brandPage.sendBrandCode(k, brandCode);
				brandPage.sendBrandDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					brandPage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			/* For empty BrandCode */
			if (brandCode.isEmpty()) {
				logger.info("inside the empty brandcode ---------------->>");
				try {

					try {
						brandCodeEmpty_bool = brandPage.validationForEmptyBrandCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								brandPage.validationForEmptyBrandCode_WebElement());
					}

					if (brandCodeEmpty_bool) {
						anyBrandCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Brand code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in Brand empty validation");
				}
			}
			
			/* For Duplicate BrandCode */

			else {

				for (int l = 0; l < allBrandCodeList.size(); l++) {

					if (allBrandCodeList.get(l).contains(brandCode)) {

						logger.info("inside the duplicate brandcode ---------------->>");
						try {
							brandCodeDuplicate_bool = brandPage.validationForDuplicateBrandCode();

							if (brandCodeDuplicate_bool) {
								anyBrandCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate brandCode");
								Assert.assertTrue(true);
							} else {
								logger.info("No Validation for duplicate brandCode");
								result =false;
								Assert.assertTrue(false, "No validation for duplicate brandcode");
							}
							
						} catch (Exception e) {
							result =false;
							e.printStackTrace();
							Assert.assertTrue(false, "Exception for Duplicate/Already Exist brandCode,");
						}

					}
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
			result = false;
			Assert.assertTrue(false, "Exception in Add Two Brand code");

		} finally {
			flag++;
			allBrandCodeList.add(brandCode);
			finalResultMap.put(testCaseNumber, result);
			result = true;
		}

	}

	@Test(priority = 4,enabled=true)
	public void saveTwoBrandCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			brandPage.clickSave();
			if (anyBrandCodeIsDuplicate_Validation == true || anyBrandCodeIsEmpty_Validation == true) {
				logger.info("Save action if part-------------------result"+result);
				// categoryPage.clickTICK();
				if (brandPage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					brandPage.clickTICK();

					brandPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						brandPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", brandPage.clickTICK_WebElement());
					}
					postLoginPage.clickBrand();
					logger.info("Clicked succefully Brand");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					brandPage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					brandPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						brandPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", brandPage.clickTICK_WebElement());
					}
					postLoginPage.clickBrand();
					logger.info("Clicked succefully Brand");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				brandPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(brandPage.clickEditButton_WebElement()));
				try {
					brandPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", brandPage.clickHomeButton_WebElement());
				}

				postLoginPage.clickBrand();
				logger.info("Clicked succefully Brand");

			}

		} catch (Exception e) {
			result =false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveBrand");
		} finally {
			finalResultMap.put("SaveActionTwoSameBrandCode", result);
			flag = 1;
			result = true;
			anyBrandCodeIsEmpty_Validation = false;
			anyBrandCodeIsDuplicate_Validation = false;
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
	public void searchCategoryPage(String testCaseNumber, String brandCode, String description, String status, String searchMethod) throws IOException {

		try {
			
			logger.info("Inside the Brand Search.............result"+result);
			logger.info("no: of record -----"+numberOfRecords);
			brandPage = new BrandPage(driver);
			
			brandPage.clickSearchWidget();
			if(brandCode.isBlank() == false) {
				
				brandPage.sendBrandCodeForSearch(brandCode);
				logger.info("Sent brandcode for search");
				
			}
			if(description.isBlank() == false) {
				
				brandPage.sendBrandDescriptionForSearch(description);
				logger.info("Sent Description for search");
			}
			
			if(status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive")) {
				
				brandPage.clickStatus();
				if(status.equalsIgnoreCase("active")) {
					
					brandPage.clickActive();
					logger.info("selected Active status");
				}else {
					
					brandPage.clickInActive();
					logger.info("selected InActive status");
				}
				
			}else {
				
				brandPage.clickStatus();
				brandPage.clickBoth();
				logger.info("clicked both status");
			}
			
			if(searchMethod.equalsIgnoreCase("enter")) {
				
				brandPage.clickEnterKeyOnCatCode();
				logger.info("Hits Enter key for Search in the brand code ");
			}else {
				
				brandPage.clickSearchButton();
				logger.info("Clicked Search Button");
			}
			
			Thread.sleep(2000);
			
		// Validation is Stating from here	
			
			if(brandCode.isBlank() == false) {
				
				try {
					
					String brand = brandPage.brandCodeRow();
					logger.info("cat code getText()   ------- "+brand);
					if(brandCode.equalsIgnoreCase(brand)) {
						logger.info("inside the brand code match");
						Assert.assertTrue(true);
					}else {
						result =false;
						Assert.assertTrue(false, "There is no match Found(BrandCode)");
					}
					
				}catch (Exception e) {
					result= false;
					Assert.assertTrue(false, "Exception inside the brandCode validation");
					logger.info("Exception inside the brandCode validation");
				
				}
			
				
				
			}
			if(description.isBlank() == false) {
				
				try {
					
					String desc = brandPage.CatDescriptionRow();
					if (description.equalsIgnoreCase(desc)) {
						
						logger.info("inside the brand description match");
						Assert.assertTrue(true);
					}else {
						result = false;
						Assert.assertTrue(false, "There is no match Found(BrandCode)");
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
		logger.info("Exception in searchBrandPage() method");
		
		}finally {
			
			brandPage.sendBrandCodeForSearchKeys();
			brandPage.sendBrandDescriptionForSearchKeys();
			brandPage.clickSearchWidget();
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
	public void checkReverseOrderValidation_BrandCode(String testCaseNumber, String brandCode, String description, String status) throws IOException {
		
		try {
			
			brandPage = new BrandPage(driver);
			boolean brandCodeEmpty_bool = false, brandCodeDuplicate_bool = false,
					brandCodeOnlySpecialChar_bool=false;
			
			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					brandPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", brandPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					brandPage.clickAddButton();
				}
				 k = numberOfRecords+1;
			}

	System.out.println("k is -------------->"+k);		
	System.out.println("Flag is -------------->"+flag);
	System.out.println("Result .................."+result);
			
			// sending values to the text boxes
						while (k >= flag ||k!=flag || k == 1) {

							brandPage.sendBrandCode(k, brandCode);
							brandPage.sendBrandDescription(k, description);

							if (status.equalsIgnoreCase("inactive")) {
								brandPage.clickInActiveStatus(k);
							}
							break;
						}
			
			
						// validation starts

						/* For special Character */
						if (convertionAndValidation.isOnlySpecialCharacter(brandCode)) {
							logger.info("B code contains only special character");

							try {
								anyBrandCodeIsSpecialChar_Validation = true;
								brandCodeOnlySpecialChar_bool = brandPage.validationForEmptyBrandCode();
								if (brandCodeOnlySpecialChar_bool) {
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

						
						
						
						/* For empty BrandCode */
						if (brandCode.isEmpty()) {
							logger.info("inside the empty brandcode ---------------->>");
							try {

								try {
									brandCodeEmpty_bool = brandPage.validationForEmptyBrandCode();
								} catch (Exception e) {

									js.executeScript("arguments[0].click();",
											brandPage.validationForEmptyBrandCode_WebElement());
								}

								if (brandCodeEmpty_bool) {
									anyBrandCodeIsEmpty_Validation = true;
									Assert.assertTrue(true);
								} else {
									result = false;
									Assert.assertTrue(false, "No validation for Empty Brand code");
								}
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception in Brand empty validation");
							}
						}
						
						/* For Duplicate BrandCode */

						else {

							for (int l = 0; l < allBrandCodeList.size(); l++) {

								if (allBrandCodeList.get(l).contains(brandCode)) {

									logger.info("inside the duplicate brandcode ---------------->>");
									try {
										brandCodeDuplicate_bool = brandPage.validationForDuplicateBrandCode();

										if (brandCodeDuplicate_bool) {
											anyBrandCodeIsDuplicate_Validation = true;
											logger.info("Validation exist for duplicate brandCode");
											Assert.assertTrue(true);
										} else {
											logger.info("No Validation for duplicate brandCode");
											result =false;
											Assert.assertTrue(false, "No validation for duplicate brandcode");
										}
										
									} catch (Exception e) {
										result =false;
										e.printStackTrace();
										Assert.assertTrue(false, "Exception for Duplicate/Already Exist brandCode,");
									}

								}
							}

						}

		}
		catch (Exception e) {

			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in Reverse Order Brand");
		
		}finally {
			finalResultMap.put(testCaseNumber, result);
			allBrandCodeList.add(brandCode);
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
	
	
	
}// class
