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
import com.extraPOSTest.pageObjects.inventory.maintenance.ClassPage;
import com.extraPOSTest.pageObjects.inventory.maintenance.LinePage;
import com.extraPOSTest.pageObjects.inventory.maintenance.LinePage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.Listeners;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_Line extends BaseClass{

	
	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	LinePage linePage;
	public List<String> allLineCodeList;
	int numberOfRecords;
	int flag = 1;
	boolean result =true;
	boolean anyLineCodeIsEmpty_Validation = false, anyLineCodeIsDuplicate_Validation = false,
			anyLineCodeIsSpecialChar_Validation = false;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String readSheetName = "addLine";
	String writeSheetName = "OutputLine";
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
	public void selectALL_forLineCode() throws InterruptedException {

		linePage = new LinePage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickLine();
		logger.info("Line Clicked succefully");
	

		try {
		
			linePage.clickSelectDropDown();
			
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", linePage.clickSelectDropDown_WebElement());
		}
		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(linePage.allBy()));

		try {
			linePage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", linePage.clickAll_WebElement());
		}
		allLineCodeList = new ArrayList<>();
		List<WebElement> allLineCode = linePage.allLineCodeList();
		logger.info("Size of the LineCode List -->> " + allLineCode.size());
		for (int i = 0; i < allLineCode.size(); i++) {
			allLineCodeList.add(allLineCode.get(i).getText());
		}

	}
	
	
	@Test(dataProvider = "addLine", priority = 1)
	public void createLineCode(String testCaseNumber, String lineCode, String description, String status) {

		try {
			
			linePage = new LinePage(driver);
			boolean lineCodeEmpty_bool = false, lineCodeDuplicate_bool = false, lineCodeOnlySpecialChar_bool=false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					logger.info("Inside the createLineCode");
					linePage.clickEditButton();
										
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", linePage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					linePage.clickAddButton();
				}

			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {
				System.out.println("Line is : "+lineCode+"  "+ "description is :"+description);
				linePage.sendLineCode(k, lineCode);
				linePage.sendLineDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					linePage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			/* For special Character */
			if (convertionAndValidation.isOnlySpecialCharacter(lineCode)) {
				logger.info("Line code contains only special character");

				try {
					anyLineCodeIsSpecialChar_Validation = true;
					lineCodeOnlySpecialChar_bool = linePage.validationForEmptyLineCode();
					if (lineCodeOnlySpecialChar_bool) {
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

			/* For empty LineCode */
			if (lineCode.isEmpty()) {
				logger.info("inside the empty LineCode ---------------->>");
				try {

					try {
						lineCodeEmpty_bool = linePage.validationForEmptyLineCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								linePage.validationForEmptyLineCode_WebElement());
					}

					if (lineCodeEmpty_bool) {
						anyLineCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Line code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in LineCode empty validation");
				}
			}

			/* For Duplicate LineCode */

			else {

				for (int l = 0; l < allLineCodeList.size(); l++) {

					if (allLineCodeList.get(l).contains(lineCode)) {

						logger.info("inside the duplicate Linecode ---------------->>");
						try {
							lineCodeDuplicate_bool = linePage.validationForDuplicateLineCode();

							if (lineCodeDuplicate_bool) {
								anyLineCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate Linecode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate Linecode");
								Assert.assertTrue(false, "No validation for duplicate Linecode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist LineCode");
						}

					}
				}

			}


		} catch (Exception e) {
			result =false;
			e.printStackTrace();
		} finally {
			finalResultMap.put(testCaseNumber, result);
			flag++;
			
		}

	}
	
	@Test(priority = 2, enabled=true)
	public void saveLineCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			linePage.clickSave();
			if (anyLineCodeIsDuplicate_Validation == true || anyLineCodeIsEmpty_Validation == true
					|| anyLineCodeIsSpecialChar_Validation == true) {
				logger.info("Save action if part-------------------result"+result);
				// categoryPage.clickTICK();
				if (linePage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					linePage.clickTICK();

					linePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						linePage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", linePage.clickTICK_WebElement());
					}
					postLoginPage.clickLine();
					logger.info("Clicked succefully Line");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					linePage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					linePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						linePage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", linePage.clickTICK_WebElement());
					}
					postLoginPage.clickLine();
					logger.info("Clicked succefully Line");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				linePage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(linePage.clickEditButton_WebElement()));
				try {
					linePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", linePage.clickHomeButton_WebElement());
				}
				

				postLoginPage.clickLine();
				logger.info("Clicked succefully Line");

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveLine");
		} finally {
			finalResultMap.put("SaveActionMultipleLineCode", result);
			flag = 1;
			result = true;
			anyLineCodeIsEmpty_Validation = false;
			anyLineCodeIsDuplicate_Validation = false;
			anyLineCodeIsSpecialChar_Validation = false;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}
	
	
	
	
	@DataProvider(name = "addLine")
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
	public void addTwoSameLineCode(String testCaseNumber, String lineCode, String description, String status) {

		try {
			logger.info("Inside the Add Two Rows ....................");
			linePage = new LinePage(driver);
			boolean lineCodeDuplicate_bool = false, lineCodeEmpty_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					linePage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", linePage.clickEditButton_WebElement());
				}
				System.out.println("No of Records " + numberOfRecords);
				for (int i = 0; i <= numberOfRecords; i++) {
					linePage.clickAddButton();
				}
			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				linePage.sendLineCode(k, lineCode);
				linePage.sendLineDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					linePage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			/* For empty LineCode */
			if (lineCode.isEmpty()) {
				logger.info("inside the empty LineCode ---------------->>");
				try {

					try {
						lineCodeEmpty_bool = linePage.validationForEmptyLineCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();",
								linePage.validationForEmptyLineCode_WebElement());
					}

					if (lineCodeEmpty_bool) {
						anyLineCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Line code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in LineCode empty validation");
				}
			}

			/* For Duplicate LineCode */

			else {

				for (int l = 0; l < allLineCodeList.size(); l++) {

					if (allLineCodeList.get(l).contains(lineCode)) {

						logger.info("inside the duplicate Linecode ---------------->>");
						try {
							lineCodeDuplicate_bool = linePage.validationForDuplicateLineCode();

							if (lineCodeDuplicate_bool) {
								anyLineCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate Linecode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate Linecode");
								Assert.assertTrue(false, "No validation for duplicate Linecode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist LineCode");
						}

					}
				}

			}

			
		}

		catch (Exception e) {
			e.printStackTrace();
			result = false;
			Assert.assertTrue(false, "Exception in Add Two Line code");

		} finally {
			flag++;
			allLineCodeList.add(lineCode);
			finalResultMap.put(testCaseNumber, result);
			result = true;
		}

	}

	@Test(priority = 4,enabled=true)
	public void saveTwoLineCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			linePage.clickSave();
			if (anyLineCodeIsDuplicate_Validation == true || anyLineCodeIsEmpty_Validation == true) {
				logger.info("Save action if part-------------------result"+result);

				if (linePage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					linePage.clickTICK();

					linePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						linePage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", linePage.clickTICK_WebElement());
					}
					postLoginPage.clickLine();
					logger.info("Clicked succefully Line");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					linePage.clickCANCEL();//newly added
					logger.info("Clicked succefully CANCELButton****");
					 
					
					linePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						linePage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", linePage.clickTICK_WebElement());
					}
					postLoginPage.clickLine();
					logger.info("Clicked succefully Line");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				linePage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(linePage.clickEditButton_WebElement()));
				try {
					linePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", linePage.clickHomeButton_WebElement());
				}

				postLoginPage.clickLine();
				logger.info("Clicked succefully Line");

			}

		} catch (Exception e) {
			result =false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveLine");
		} finally {
			finalResultMap.put("SaveActionTwoSameLineCode", result);
			flag = 1;
			result = true;
			anyLineCodeIsEmpty_Validation = false;
			anyLineCodeIsDuplicate_Validation = false;
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
	public void searchLinePage(String testCaseNumber, String lineCode, String description, String status, String searchMethod) throws IOException {

		try {
			
			logger.info("Inside the Line Search.............result"+result);
			logger.info("no: of record -----"+numberOfRecords);
			linePage = new LinePage(driver);
			
			linePage.clickSearchWidget();
			if(lineCode.isBlank() == false) {
				
				linePage.sendLineCodeForSearch(lineCode);
				logger.info("Sent Line for search");
				
			}
			if(description.isBlank() == false) {
				
				linePage.sendLineDescriptionForSearch(description);
				logger.info("Sent Description for search");
			}
			
			if(status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive")) {
				
				linePage.clickStatus();
				if(status.equalsIgnoreCase("active")) {
					
					linePage.clickActive();
					logger.info("selected Active status");
				}else {
					
					linePage.clickInActive();
					logger.info("selected InActive status");
				}
				
			}else {
				
				linePage.clickStatus();
				linePage.clickBoth();
				logger.info("clicked both status");
			}
			
			if(searchMethod.equalsIgnoreCase("enter")) {
				
				linePage.clickEnterKeyOnLineCode();
				logger.info("Hits Enter key for Search in the Linecode ");
			}else {
				
				linePage.clickSearchButton();
				logger.info("Clicked Search Button");
			}
			
			Thread.sleep(2000);
			
		// Validation is Stating from here	
			
			if(lineCode.isBlank() == false) {
				
				try {
					
					String linecode = linePage.lineCodeRow();
					logger.info("cat code getText()   ------- "+linecode);
					if(lineCode.equalsIgnoreCase(linecode)) {
						logger.info("inside the Line code match");
						Assert.assertTrue(true);
					}else {
						result =false;
						Assert.assertTrue(false, "There is no match Found(LineCode)");
					}
					
				}catch (Exception e) {
					result= false;
					Assert.assertTrue(false, "Exception inside the LineCode validation");
					logger.info("Exception inside the LineCode validation");
				
				}
			
				
				
			}
			if(description.isBlank() == false) {
				
				try {
					
					String desc = linePage.lineDescriptionRow();
					if (description.equalsIgnoreCase(desc)) {
						
						logger.info("inside the Line description match");
						Assert.assertTrue(true);
					}else {
						result = false;
						Assert.assertTrue(false, "There is no match Found(LineCode)");
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
		logger.info("Exception in searchLinePage() method");
		
		}finally {
			
			linePage.sendLineCodeForSearchKeys();
			linePage.sendLineDescriptionForSearchKeys();
			linePage.clickSearchWidget();
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
	public void checkReverseOrderValidation_LineCode(String testCaseNumber, String lineCode, String description, String status) throws IOException {
		
		try {
			
			linePage = new LinePage(driver);
			boolean lineCodeEmpty_bool = false, lineCodeDuplicate_bool = false,
					lineCodeOnlySpecialChar_bool=false;
			
			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					linePage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", linePage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					linePage.clickAddButton();
				}
				 k = numberOfRecords+1;
			}

	System.out.println("k is -------------->"+k);		
	System.out.println("Flag is -------------->"+flag);
	System.out.println("Result .................."+result);
			
			// sending values to the text boxes
						while (k >= flag ||k!=flag || k == 1) {

							linePage.sendLineCode(k, lineCode);
							linePage.sendLineDescription(k, description);

							if (status.equalsIgnoreCase("inactive")) {
								linePage.clickInActiveStatus(k);
							}
							break;
						}
			
			
						// validation starts

						/* For special Character */
						if (convertionAndValidation.isOnlySpecialCharacter(lineCode)) {
							logger.info("Line code contains only special character");

							try {
								anyLineCodeIsSpecialChar_Validation = true;
								lineCodeOnlySpecialChar_bool = linePage.validationForEmptyLineCode();
								if (lineCodeOnlySpecialChar_bool) {
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

						/* For empty LineCode */
						if (lineCode.isEmpty()) {
							logger.info("inside the empty LineCode ---------------->>");
							try {

								try {
									lineCodeEmpty_bool = linePage.validationForEmptyLineCode();
								} catch (Exception e) {

									js.executeScript("arguments[0].click();",
											linePage.validationForEmptyLineCode_WebElement());
								}

								if (lineCodeEmpty_bool) {
									anyLineCodeIsEmpty_Validation = true;
									Assert.assertTrue(true);
								} else {
									result = false;
									Assert.assertTrue(false, "No validation for Empty Line code");
								}
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception in LineCode empty validation");
							}
						}

						/* For Duplicate LineCode */

						else {

							for (int l = 0; l < allLineCodeList.size(); l++) {

								if (allLineCodeList.get(l).contains(lineCode)) {

									logger.info("inside the duplicate Linecode ---------------->>");
									try {
										lineCodeDuplicate_bool = linePage.validationForDuplicateLineCode();

										if (lineCodeDuplicate_bool) {
											anyLineCodeIsDuplicate_Validation = true;
											logger.info("Validation exist for duplicate Linecode");

											Assert.assertTrue(true);
										} else {
											result = false;
											logger.info("No Validation for duplicate Linecode");
											Assert.assertTrue(false, "No validation for duplicate Linecode");
										}

									} catch (Exception e) {
										e.printStackTrace();
										result = false;
										Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist LineCode");
									}

								}
							}

						}
		}

		catch (Exception e) {

			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in Reverse Order Line");
		
		}finally {
			finalResultMap.put(testCaseNumber, result);
			allLineCodeList.add(lineCode);
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
	
}//class
