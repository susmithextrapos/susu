package com.extraPOSTest.testCases.inventory.maintenance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.extraPOSTest.pageObjects.PostLoginPage;
import com.extraPOSTest.pageObjects.inventory.maintenance.TypePage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;
import com.google.gson.annotations.Until;

public class TC_Type extends BaseClass {

	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	TypePage typePage;
	public List<String> allTypeCodeList;
	int numberOfRecords;
	int flag = 1;
	boolean result = true;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	boolean anyTypeCodeIsEmpty_Validation = false, anyTypeCodeIsDuplicate_Validation = false,
			anyTypeCodeIsSpecialChar_Validation = false;
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String readSheetName = "addType";
	String writeSheetName = "OutputType";
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
	public void selectALL_forTypeCode() throws InterruptedException {

		typePage = new TypePage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickType();
		logger.info("Type Clicked succefully");

		try {

			typePage.clickSelectDropDown();

		} catch (Exception e) {
			js.executeScript("arguments[0].click();", typePage.clickSelectDropDown_WebElement());
		}
		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(typePage.allBy()));

		try {
			typePage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", typePage.clickAll_WebElement());
		}
		allTypeCodeList = new ArrayList<>();
		List<WebElement> allTypeCode = typePage.allTypeCodeList();
		logger.info("Size of the TypeCode List -->> " + allTypeCode.size());
		for (int i = 0; i < allTypeCode.size(); i++) {
			allTypeCodeList.add(allTypeCode.get(i).getText());
		}

	}

	@Test(dataProvider = "addType", priority = 1, enabled = true)
	public void createTypeCode(String testCaseNumber, String typeCode, String description, String status)
			throws IOException {

		try {

			typePage = new TypePage(driver);
			boolean typeCodeEmpty_bool = false, typeCodeDuplicate_bool = false, typeCodeOnlySpecialChar_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					typePage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", typePage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					typePage.clickAddButton();
				}

			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				typePage.sendTypeCode(k, typeCode);
				typePage.sendTypeDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					typePage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts

			/* For special Character */
			if (convertionAndValidation.isOnlySpecialCharacter(typeCode)) {
				logger.info("Typecode contains only special character");

				try {
					anyTypeCodeIsSpecialChar_Validation = true;
					typeCodeOnlySpecialChar_bool = typePage.validationForEmptyTypeCode();
					if (typeCodeOnlySpecialChar_bool) {
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
			if (typeCode.isEmpty()) {
				logger.info("inside the empty Typecode ---------------->>");
				try {

					try {
						typeCodeEmpty_bool = typePage.validationForEmptyTypeCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();", typePage.validationForEmptyTypeCode_WebElement());
					}

					if (typeCodeEmpty_bool) {
						anyTypeCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Type code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in Type empty validation");
				}
			}

			/* For Duplicate CatCode */

			else {

				for (int l = 0; l < allTypeCodeList.size(); l++) {

					if (allTypeCodeList.get(l).contains(typeCode)) {

						logger.info("inside the duplicate TypeCode ---------------->>");
						try {
							typeCodeDuplicate_bool = typePage.validationForDuplicateTypeCode();

							if (typeCodeDuplicate_bool) {
								anyTypeCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate Typecode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate Typecode");
								Assert.assertTrue(false, "No validation for duplicate Typecode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist Typecode");
						}

					}
				}

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in createTypeCode() Method");
		} finally {
			finalResultMap.put(testCaseNumber, result);
			allTypeCodeList.add(typeCode);
			result = true;
			flag++;

		}

	}

	@Test(priority = 2, enabled = true)
	public void saveTypeCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			typePage.clickSave();
			if (anyTypeCodeIsDuplicate_Validation == true || anyTypeCodeIsEmpty_Validation == true
					|| anyTypeCodeIsSpecialChar_Validation == true) {
				logger.info("Save action if part-------------------result" + result);
				// categoryPage.clickTICK();
				if (typePage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					typePage.clickTICK();

					typePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						typePage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", typePage.clickTICK_WebElement());
					}
					postLoginPage.clickType();
					logger.info("Clicked succefully Type");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					typePage.clickCANCEL();// newly added
					logger.info("Clicked succefully CANCELButton****");

					typePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						typePage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", typePage.clickTICK_WebElement());
					}
					postLoginPage.clickType();
					logger.info("Clicked succefully Type");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				typePage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(typePage.clickEditButton_WebElement()));
				try {
					typePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", typePage.clickHomeButton_WebElement());
				}

				postLoginPage.clickType();
				logger.info("Clicked succefully Type");

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveType");
		} finally {
			finalResultMap.put("SaveActionMultipleTypeCode", result);
			flag = 1;
			result = true;
			anyTypeCodeIsEmpty_Validation = false;
			anyTypeCodeIsDuplicate_Validation = false;
			anyTypeCodeIsSpecialChar_Validation = false;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}

	@DataProvider(name = "addType")
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
	public void addTwoSameTypeCode(String testCaseNumber, String typeCode, String description, String status) {

		try {
			logger.info("Inside the Add Two Rows ....................");
			typePage = new TypePage(driver);
			boolean typeCodeDuplicate_bool = false, typeCodeEmpty_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					typePage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", typePage.clickEditButton_WebElement());
				}
				System.out.println("No of Records " + numberOfRecords);
				for (int i = 0; i <= numberOfRecords; i++) {
					typePage.clickAddButton();
				}
			}

			// sending values to the text boxes
			int k = flag;
			while (k == flag) {

				typePage.sendTypeCode(k, typeCode);
				typePage.sendTypeDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					typePage.clickInActiveStatus(k);
				}
				k++;
			}

			// validation starts
			/* For empty CatCode */
			if (typeCode.isEmpty()) {
				logger.info("inside the empty TypeCode ---------------->>");
				try {

					try {
						typeCodeEmpty_bool = typePage.validationForEmptyTypeCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();", typePage.validationForEmptyTypeCode_WebElement());
					}

					if (typeCodeEmpty_bool) {
						anyTypeCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty TypeCode");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in TypeCode empty validation");
				}
			}

			/* For Duplicate CatCode */

			else {

				for (int l = 0; l < allTypeCodeList.size(); l++) {

					if (allTypeCodeList.get(l).contains(typeCode)) {

						logger.info("inside the duplicate TypeCode ---------------->>");
						try {
							typeCodeDuplicate_bool = typePage.validationForDuplicateTypeCode();

							if (typeCodeDuplicate_bool) {
								anyTypeCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate TypeCode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate TypeCode");
								Assert.assertTrue(false, "No validation for duplicate TypeCode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist TypeCode");
						}

					}
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			result = false;
			Assert.assertTrue(false, "Exception in Add Two TypeCode ");

		} finally {
			flag++;
			allTypeCodeList.add(typeCode);
			finalResultMap.put(testCaseNumber, result);
			result = true;
		}

	}

	@Test(priority = 4, enabled = true)
	public void saveTwoTypeCode() throws IOException {

		try {

			// save action starts
			Thread.sleep(3000);
			typePage.clickSave();
			if (anyTypeCodeIsDuplicate_Validation == true || anyTypeCodeIsEmpty_Validation == true) {
				logger.info("Save action if part-------------------result" + result);
				// categoryPage.clickTICK();
				if (typePage.errorMessage()) {
					logger.info("proper msg for save action(can not save)");
					typePage.clickTICK();

					typePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						typePage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", typePage.clickTICK_WebElement());
					}
					postLoginPage.clickType();
					logger.info("Clicked succefully TypeCode");
					Assert.assertTrue(true);
				} else {
					logger.info("inside the error save");
					typePage.clickCANCEL();// newly added
					logger.info("Clicked succefully CANCELButton****");

					typePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
					try {
						typePage.clickTICK();
						logger.info("Clicked succefully Tick");
					} catch (Exception e) {
						js.executeScript("arguments[0].click();", typePage.clickTICK_WebElement());
					}
					postLoginPage.clickType();
					logger.info("Clicked succefully TypeCode");

					result = false;
					logger.info("There is no prper validation msg for save(can not save)");
					Assert.assertTrue(false, "No Error Message validation for save action");
				}

			} else {
				logger.info("Save action else part-------------------");

				typePage.clickTICK();
				wait.until(ExpectedConditions.elementToBeClickable(typePage.clickEditButton_WebElement()));
				try {
					typePage.clickHomeButton();
					logger.info("Clicked succefully HomeButton");
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", typePage.clickHomeButton_WebElement());
				}

				postLoginPage.clickType();
				logger.info("Clicked succefully TypeCode");

			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside saveTypeCode");
		} finally {
			finalResultMap.put("SaveActionTwoSameTypeCode", result);
			flag = 1;
			result = true;
			anyTypeCodeIsEmpty_Validation = false;
			anyTypeCodeIsDuplicate_Validation = false;
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

	int count = 0;

	@Test(priority = 5, dataProvider = "search", enabled = true)
	public void searchTypePage(String testCaseNumber, String typeCode, String description, String status,
			String searchMethod) throws IOException {

		try {

			logger.info("Inside the TypeCode Search.............result" + result);
			logger.info("noof record -----" + numberOfRecords);
			typePage = new TypePage(driver);

			typePage.clickSearchWidget();
			if (typeCode.isBlank() == false) {

				typePage.sendTypeCodeForSearch(typeCode);
				logger.info("Send Typecode for search");

			}
			if (description.isBlank() == false) {

				typePage.sendTypeDescriptionForSearch(description);
				logger.info("Send Description for search");
			}

			if (status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive")) {

				typePage.clickStatus();
				if (status.equalsIgnoreCase("active")) {

					typePage.clickActive();
					logger.info("selected Active status");
				} else {

					typePage.clickInActive();
					logger.info("selected InActive status");
				}

			} else {

				typePage.clickStatus();
				typePage.clickBoth();
				logger.info("clicked both status");
			}

			if (searchMethod.equalsIgnoreCase("enter")) {

				typePage.clickEnterKeyOnCatCode();
				logger.info("Hits Enter key for Search in the Type code ");
			} else {

				typePage.clickSearchButton();
				logger.info("Clicked Search Button");
			}

			Thread.sleep(2000);

			// Validation is Stating from here

			if (typeCode.isBlank() == false) {

				try {

					String typecode = typePage.typeCodeRow();
					logger.info("Type code getText()   ------- " + typecode);
					if (typeCode.equalsIgnoreCase(typecode)) {
						logger.info("inside the typecode match");
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "There is no match Found(typeCode)");
					}

				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception inside the typeCode validation");
					logger.info("Exception inside the typeCode validation");

				}

			}
			if (description.isBlank() == false) {

				try {

					String desc = typePage.typeDescriptionRow();
					if (description.equalsIgnoreCase(desc)) {

						logger.info("inside the cat description match");
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "There is no match Found(typeCode)");
					}

				} catch (Exception e) {

					result = false;
					Assert.assertTrue(false, "Exception inside the description validation");
					logger.info("Exception inside the description validation");

				}

			}

		} catch (Exception e) {

			result = false;
			e.printStackTrace();
			logger.info("Exception in searchTypePage() method");

		} finally {

			typePage.sendTypeCodeForSearchKeys();
			typePage.sendTypeDescriptionForSearchKeys();
			typePage.clickSearchWidget();
			finalResultMap.put(testCaseNumber, result);
			if (count == numberOfRecords) {
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

	int k = 0;

	@Test(priority = 6, dataProvider = "reverse")
	public void checkReverseOrderValidation_TypeCode(String testCaseNumber, String typeCode, String description,
			String status) throws IOException {

		try {

			typePage = new TypePage(driver);
			boolean typeCodeEmpty_bool = false, typeCodeDuplicate_bool = false, typeCodeOnlySpecialChar_bool = false;

			// clicking ADD button , as per our rows of data
			if (flag == 1) {

				try {
					typePage.clickEditButton();

				} catch (Exception e) {
					js.executeScript("arguments[0].click();", typePage.clickEditButton_WebElement());
				}

				for (int i = 0; i <= numberOfRecords; i++) {
					typePage.clickAddButton();
				}
				k = numberOfRecords + 1;
			}

			System.out.println("k is -------------->" + k);
			System.out.println("Flag is -------------->" + flag);
			System.out.println("Result .................." + result);

			// sending values to the text boxes
			while (k >= flag || k != flag || k == 1) {

				typePage.sendTypeCode(k, typeCode);
				typePage.sendTypeDescription(k, description);

				if (status.equalsIgnoreCase("inactive")) {
					typePage.clickInActiveStatus(k);
				}
				break;
			}

			// validation starts

			/* For special Character */
			if (convertionAndValidation.isOnlySpecialCharacter(typeCode)) {
				logger.info("Typecode contains only special character");

				try {
					anyTypeCodeIsSpecialChar_Validation = true;
					typeCodeOnlySpecialChar_bool = typePage.validationForEmptyTypeCode();
					if (typeCodeOnlySpecialChar_bool) {
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
			if (typeCode.isEmpty()) {
				logger.info("inside the empty Typecode ---------------->>");
				try {

					try {
						typeCodeEmpty_bool = typePage.validationForEmptyTypeCode();
					} catch (Exception e) {

						js.executeScript("arguments[0].click();", typePage.validationForEmptyTypeCode_WebElement());
					}

					if (typeCodeEmpty_bool) {
						anyTypeCodeIsEmpty_Validation = true;
						Assert.assertTrue(true);
					} else {
						result = false;
						Assert.assertTrue(false, "No validation for Empty Type code");
					}
				} catch (Exception e) {
					result = false;
					Assert.assertTrue(false, "Exception in Type empty validation");
				}
			}

			/* For Duplicate CatCode */

			else {

				for (int l = 0; l < allTypeCodeList.size(); l++) {

					if (allTypeCodeList.get(l).contains(typeCode)) {

						logger.info("inside the duplicate TypeCode ---------------->>");
						try {
							typeCodeDuplicate_bool = typePage.validationForDuplicateTypeCode();

							if (typeCodeDuplicate_bool) {
								anyTypeCodeIsDuplicate_Validation = true;
								logger.info("Validation exist for duplicate Typecode");

								Assert.assertTrue(true);
							} else {
								result = false;
								logger.info("No Validation for duplicate Typecode");
								Assert.assertTrue(false, "No validation for duplicate Typecode");
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = false;
							Assert.assertTrue(false, "No validation Msg for Duplicate/Already Exist Typecode");
						}

					}
				}

			}
		} catch (Exception e) {

			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in Type Reverse Order");

		} finally {
			finalResultMap.put(testCaseNumber, result);
			allTypeCodeList.add(typeCode);
			result = true;
			flag++;
			k--;
			if (k == 0) {

				xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);
			}

		}

	}

	@DataProvider(name = "reverse")
	String[][] getReverseData() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readReverse);
		int colCount = XLUtils.getCellCount(path, readReverse, 0);
		numberOfRecords = rowCount - 1;
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
