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
import com.extraPOSTest.pageObjects.inventory.maintenance.ClassPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_Class extends BaseClass {
	
	
	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	ClassPage classPage;
	public List<String> allClassCodeList;
	int numberOfRecords;
	int flag = 1;
	boolean result =true;
	boolean anyClassCodeIsEmpty_Validation = false, anyClassCodeIsDuplicate_Validation = false,
			anyClassCodeIsSpecialChar_Validation = true;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String readSheetName = "addClass";
	String writeSheetName = "OutputClass";
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
	public void selectALL_forClassCode() throws InterruptedException {

		classPage = new ClassPage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickClass();
		logger.info("Clicked succefully Class");
	

		try {
		
			classPage.clickSelectDropDown();
			
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", classPage.clickSelectDropDown_WebElement());
		}
		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(classPage.allBy()));

		try {
			classPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", classPage.clickAll_WebElement());
		}
		allClassCodeList = new ArrayList<>();
		List<WebElement> allClassCode = classPage.allClassCodeList();
		logger.info("Size of the ClassCode List -->> " + allClassCode.size());
		for (int i = 0; i < allClassCode.size(); i++) {
			allClassCodeList.add(allClassCode.get(i).getText());
		}

	}


	@Test(dataProvider = "addClass", priority = 1)
	public void createClassCode(String testCaseNumber, String classCode, String description, String status) {

		try {
			
			classPage = new ClassPage(driver);
			boolean classCodeEmpty_bool = false, classCodeDuplicate_bool = false,classCodeOnlySpecialChar_bool;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					logger.info("Inside the createClassCode");
					classPage.clickEditButton();
										
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", classPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					classPage.clickAddButton();
				}

			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {
				System.out.println("Class is : "+classCode+"  "+ "description is :"+description);
				classPage.sendClassCode(k, classCode);
				classPage.sendClassDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					classPage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			/* For special Character */
			if (convertionAndValidation.isOnlySpecialCharacter(classCode)) {
				logger.info("Class code contains only special character");

				try {
					anyClassCodeIsSpecialChar_Validation = true;
					classCodeOnlySpecialChar_bool = classPage.validationForEmptyClassCode();
					if (classCodeOnlySpecialChar_bool) {
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

			/* For empty ClassCode */
			if (classCode.isEmpty()) {
				logger.info("inside the empty Classcode ---------------->>");
				try {

					try {
						classCodeEmpty_bool = classPage.validationForEmptyClassCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								classPage.validationForEmptyClassCode_WebElement());
					}

					if (classCodeEmpty_bool) {
						anyClassCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Class code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in Class empty validation");
				}
			}

			/* For Duplicate ClassCode */

			else {

				for (int l = 0; l < allClassCodeList.size(); l++) {

					if (allClassCodeList.get(l).contains(classCode)) {

						logger.info("inside the duplicate Class ---------------->>");
						try {
							classCodeDuplicate_bool = classPage.validationForDuplicateClassCode();

							if (classCodeDuplicate_bool) {
								anyClassCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate Class Code");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate ClassCode");
								Assert.assertTrue(false, "No validation for duplicate Classcode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist ClassCode");
						}

					}
				}

			}


		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false,"Exception in createClassCode() Method");
		} finally {
			finalResultMap.put(testCaseNumber, result);
			allClassCodeList.add(classCode);
			result =true;
			flag++;

		}

	}

	@Test(priority = 2, enabled=true)
	public void saveClassCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			classPage.clickSave();
			if (anyClassCodeIsDuplicate_Validation == true || anyClassCodeIsEmpty_Validation == true
					|| anyClassCodeIsSpecialChar_Validation == true) {
				logger.info("Save action if part-------------------result"+result);
				// categoryPage.clickTICK();
				if (classPage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					classPage.clickTICK();

					classPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						classPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", classPage.clickTICK_WebElement());
					}
					postLoginPage.clickClass();
					logger.info("Clicked succefully Class");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					classPage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					classPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						classPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", classPage.clickTICK_WebElement());
					}
					postLoginPage.clickClass();
					logger.info("Clicked succefully Class");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				classPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(classPage.clickEditButton_WebElement()));
				try {
					classPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", classPage.clickHomeButton_WebElement());
				}
				

				postLoginPage.clickClass();
				logger.info("Clicked succefully Class");

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveClass");
		} finally {
			finalResultMap.put("SaveActionMultipleClassCode", result);
			flag = 1;
			result = true;
			anyClassCodeIsEmpty_Validation = false;
			anyClassCodeIsDuplicate_Validation = false;
			anyClassCodeIsSpecialChar_Validation = false;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}

	
	
	@DataProvider(name = "addClass")
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
	public void addTwoSameClassCode(String testCaseNumber, String classCode, String description, String status) {

		try {
			logger.info("Inside the Add Two Rows ....................");
			classPage = new ClassPage(driver);
			boolean classCodeDuplicate_bool = false, classCodeEmpty_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					classPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", classPage.clickEditButton_WebElement());
				}
				System.out.println("No of Records " + numberOfRecords);
				for (int i = 0; i <= numberOfRecords; i++) {
					classPage.clickAddButton();
				}
			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				classPage.sendClassCode(k, classCode);
				classPage.sendClassDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					classPage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			/* For empty ClassCode */
			if (classCode.isEmpty()) {
				logger.info("inside the empty Classcode ---------------->>");
				try {

					try {
						classCodeEmpty_bool = classPage.validationForEmptyClassCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								classPage.validationForEmptyClassCode_WebElement());
					}

					if (classCodeEmpty_bool) {
						anyClassCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Class code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in Class empty validation");
				}
			}

			/* For Duplicate ClassCode */

			else {

				for (int l = 0; l < allClassCodeList.size(); l++) {

					if (allClassCodeList.get(l).contains(classCode)) {

						logger.info("inside the duplicate Class ---------------->>");
						try {
							classCodeDuplicate_bool = classPage.validationForDuplicateClassCode();

							if (classCodeDuplicate_bool) {
								anyClassCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate Class Code");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate ClassCode");
								Assert.assertTrue(false, "No validation for duplicate Classcode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist ClassCode");
						}

					}
				}

			}


		}
		catch (Exception e) {
			e.printStackTrace();
			result = false;
			Assert.assertTrue(false, "Exception in Add Two Class code");

		} finally {
			flag++;
			allClassCodeList.add(classCode);
			finalResultMap.put(testCaseNumber, result);
			result = true;
		}

	}

	@Test(priority = 4,enabled=true)
	public void saveTwoCategoryCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			classPage.clickSave();
			if (anyClassCodeIsDuplicate_Validation == true || anyClassCodeIsEmpty_Validation == true) {
				logger.info("Save action if part-------------------result"+result);
				
				if (classPage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					classPage.clickTICK();

					classPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						classPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", classPage.clickTICK_WebElement());
					}
					postLoginPage.clickClass();
					logger.info("Clicked succefully Class");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					classPage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					classPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						classPage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", classPage.clickTICK_WebElement());
					}
					postLoginPage.clickClass();
					logger.info("Clicked succefully Class");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				classPage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(classPage.clickEditButton_WebElement()));
				try {
					classPage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", classPage.clickHomeButton_WebElement());
				}

				postLoginPage.clickClass();
				logger.info("Clicked succefully Class");

			}

		} catch (Exception e) {
			result =false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveClass");
		} finally {
			finalResultMap.put("SaveActionTwoSameClassCode", result);
			flag = 1;
			result = true;
			anyClassCodeIsEmpty_Validation = false;
			anyClassCodeIsDuplicate_Validation = false;
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
	public void searchClassPage(String testCaseNumber, String classCode, String description, String status, String searchMethod) throws IOException {

		try {
			
			logger.info("Inside the Class Search.............result"+result);
			logger.info("noof record -----"+numberOfRecords);
			classPage = new ClassPage(driver);
			
			classPage.clickSearchWidget();
			if(classCode.isBlank() == false) {
				
				classPage.sendClassCodeForSearch(classCode);
				logger.info("Send classcode for search");
				
			}
			if(description.isBlank() == false) {
				
				classPage.sendClassDescriptionForSearch(description);
				logger.info("Send Description for search");
			}
			
			if(status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive")) {
				
				classPage.clickStatus();
				if(status.equalsIgnoreCase("active")) {
					
					classPage.clickActive();
					logger.info("selected Active status");
				}else {
					
					classPage.clickInActive();
					logger.info("selected InActive status");
				}
				
			}else {
				
				classPage.clickStatus();
				classPage.clickBoth();
				logger.info("clicked both status");
			}
			
			if(searchMethod.equalsIgnoreCase("enter")) {
				
				classPage.clickEnterKeyOnCatCode();
				logger.info("Hits Enter key for Search in the class code ");
			}else {
				
				classPage.clickSearchButton();
				logger.info("Clicked Search Button");
			}
			
			Thread.sleep(2000);
			
		// Validation is Stating from here	
			
			if(classCode.isBlank() == false) {
				
				try {
					
					String classcode = classPage.ClassCodeRow();
					logger.info("class code getText()   ------- "+classcode);
					if(classCode.equalsIgnoreCase(classcode)) {
						logger.info("inside the class code match");
						Assert.assertTrue(true);
					}else {
						result =false;
						Assert.assertTrue(false, "There is no match Found(ClassCode)");
					}
					
				}catch (Exception e) {
					result= false;
					Assert.assertTrue(false, "Exception inside the ClassCode validation");
					logger.info("Exception inside the ClassCode validation");
				
				}
			
				
				
			}
			if(description.isBlank() == false) {
				
				try {
					
					String desc = classPage.ClassDescriptionRow();
					if (description.equalsIgnoreCase(desc)) {
						
						logger.info("inside the cat description match");
						Assert.assertTrue(true);
					}else {
						result = false;
						Assert.assertTrue(false, "There is no match Found(classCode)");
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
		logger.info("Exception in searchClassPage() method");
		
		}finally {
			
			classPage.sendClassCodeForSearchKeys();
			classPage.sendClassDescriptionForSearchKeys();
			classPage.clickSearchWidget();
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
	public void checkReverseOrderValidation_ClassCode(String testCaseNumber, String classCode, String description, String status) throws IOException {
		
		try {
			
			classPage = new ClassPage(driver);
			boolean classCodeEmpty_bool = false, classCodeDuplicate_bool = false,
					classCodeOnlySpecialChar_bool=false;
			
			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					classPage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", classPage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					classPage.clickAddButton();
				}
				 k = numberOfRecords+1;
			}

	System.out.println("k is -------------->"+k);		
	System.out.println("Flag is -------------->"+flag);
	System.out.println("Result .................."+result);
			
			// sending values to the text boxes
						while (k >= flag ||k!=flag || k == 1) {

							classPage.sendClassCode(k, classCode);
							classPage.sendClassDescription(k, description);

							if (status.equalsIgnoreCase("inactive")) {
								classPage.clickInActiveStatus(k);
							}
							break;
						}
			
			
						// validation starts

						/* For special Character */
						if (convertionAndValidation.isOnlySpecialCharacter(classCode)) {
							logger.info("Class code contains only special character");

							try {
								anyClassCodeIsSpecialChar_Validation = true;
								classCodeOnlySpecialChar_bool = classPage.validationForEmptyClassCode();
								if (classCodeOnlySpecialChar_bool) {
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

						/* For empty ClassCode */
						if (classCode.isEmpty()) {
							logger.info("inside the empty Classcode ---------------->>");
							try {

								try {
									classCodeEmpty_bool = classPage.validationForEmptyClassCode();
								} catch (Exception e) {

									js.executeScript("arguments[0].click();",
											classPage.validationForEmptyClassCode_WebElement());
								}

								if (classCodeEmpty_bool) {
									anyClassCodeIsEmpty_Validation = true;
									Assert.assertTrue(true);
								} else {
									result = false;
									Assert.assertTrue(false, "No validation for Empty Class code");
								}
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception in Class empty validation");
							}
						}

						/* For Duplicate ClassCode */

						else {

							for (int l = 0; l < allClassCodeList.size(); l++) {

								if (allClassCodeList.get(l).contains(classCode)) {

									logger.info("inside the duplicate Class ---------------->>");
									try {
										classCodeDuplicate_bool = classPage.validationForDuplicateClassCode();

										if (classCodeDuplicate_bool) {
											anyClassCodeIsDuplicate_Validation = true;
											logger.info("Validation exist for duplicate Class Code");

											Assert.assertTrue(true);
										} else {
											result = false;
											logger.info("No Validation for duplicate ClassCode");
											Assert.assertTrue(false, "No validation for duplicate Classcode");
										}

									} catch (Exception e) {
										e.printStackTrace();
										result = false;
										Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist ClassCode");
									}

								}
							}

						}

		}
		catch (Exception e) {

			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in Class Reverse Order ");
		
		}finally {
			finalResultMap.put(testCaseNumber, result);
			allClassCodeList.add(classCode);
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
