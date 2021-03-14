package com.extraPOSTest.testCases.inventory.maintenance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
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
import com.extraPOSTest.pageObjects.inventory.maintenance.UDAPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_UDA extends BaseClass {

	public WebDriver driver;
	PostLoginPage postLoginPage;
	UDAPage udaPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	String readSheetName = "addUDA";
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String writeSheetName = "OutputUDA";
	String readSearch = "searchByCodeORDescription";
	ConvertionAndValidation convertionAndValidation = new ConvertionAndValidation();
	public boolean result = true;
	boolean anyUserDefinedFieldNameEmpty_validation = false, anyFieldTypeEmpty_validation = false,
			anylovCodeEmpty_validation = false, anyduplicateUserdefinedfieldname = false;
	private int firstTime = 1;
	List<String> allUserDefinedFieldNamearrayList;
	int totalRecords;

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

	public void clickUserDefinedAttribute() throws InterruptedException {

		udaPage = new UDAPage(driver);
		postLoginPage = new PostLoginPage(driver);

		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickUDA();
		logger.info("Clicked succefully UDA");
		udaPage.clickEditButton();

		logger.info("clicked EDIT Button");
		Thread.sleep(3000);
		try {
			udaPage.clickSelectDropDown();
			logger.info("Clicked Select Drop Down....");

		} catch (Exception e) {
			js.executeScript("arguments[0].click();", udaPage.clickSelectDropDown_WebElement());
			logger.info("Clicked Select Drop Down");
		}

		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(udaPage.allBy()));

		try {
			udaPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", udaPage.clickAll_WebElement());
		}

		totalRecords = udaPage.totalRecords();

		allUserDefinedFieldNamearrayList = new ArrayList<String>();
		List<WebElement> allUserNameList = udaPage.allUserDefinedFieldNameList();
		for (int i = 0; i < allUserNameList.size(); i++) {
			allUserDefinedFieldNamearrayList.add(allUserNameList.get(i).getAttribute("value"));
			logger.info(allUserNameList.get(i).getAttribute("value"));
		}
		System.out.println("totalRecords --------->>" + totalRecords);

	}

	@Test(dataProvider = "addUDA", priority = 0, enabled = true)
	public void createUDA(String testCaseNumber, String columnName, String required, String userDefinedFieldName,
			String fieldType, String LOVcode, String searchBy) throws IOException {

		try {

			boolean userdefinedfieldnameEmpty_bool = false, userdefinedfieldnameExceedsMaxChar_bool = false,
					fieldType_bool = false, lovCode_bool = false, freetext = false,
					duplicateUserdefinedfieldname_bool = false;
			if (firstTime == 1) {
				clickUserDefinedAttribute();
			}

			for (int i = 1; i <= totalRecords; i++) {

				if (columnName.equalsIgnoreCase(udaPage.getColumnName(i))) {

					logger.info("Both are equal-----------------------------");
					if (udaPage.status(i).equalsIgnoreCase("inactive")) {

						try {
							logger.info("Clicks Status ....... ");
							udaPage.statusClick(i);
						} catch (Exception e) {

							js.executeScript("arguments[0].click();", udaPage.statusClick_WebElement(i));
						}

						if (required.equalsIgnoreCase("true")) {
							logger.info("Clicks Required ....... ");
							try {
								udaPage.clickRequired(i);
							} catch (Exception e) {

								js.executeScript("arguments[0].click();", udaPage.clickRequired_WebElement(i));
							}

						}
						logger.info("Check boxes are crossed");
						logger.info("The Test case ID ====" + testCaseNumber);
						logger.info("LookUP/Free TEXT ===========" + fieldType);

						udaPage.sendUserDefinedFieldName(i, userDefinedFieldName);

						try {
							udaPage.clickFieldType(i);
						} catch (Exception e) {
							js.executeScript("arguments[0].click();", udaPage.clickFieldType_WebElement(i));

						}

						if (fieldType.equalsIgnoreCase("lookup")) {
							logger.info("Inside the lookup");
							udaPage.clickLookUptext();

							// here need to add if stmt for empty Lov values
							if (LOVcode.length() != 0) {
								udaPage.clickLookUp(i);

								try {
									udaPage.sendSearchLookUp(LOVcode);
								} catch (Exception e) {

									js.executeScript("arguments[0].click();", udaPage.sendSearchLookUp_WebElement());

								}

								if (searchBy.equalsIgnoreCase("code")) {
									logger.info("Search by code ");
									udaPage.clickSearchButton();
									Thread.sleep(3000);
									String codeValueIs = udaPage.searchResultCode();
									System.out.println("codeValueIs.........." + codeValueIs);

									if (codeValueIs.equalsIgnoreCase(LOVcode)) {
										logger.info("found search result by code");
										try {
											udaPage.clickOnSearchResult();
										} catch (Exception e) {

											js.executeAsyncScript("arguments[0].click();",
													udaPage.clickOnSearchResult_WebElement());
										}

									} else {
										udaPage.closeIcon();
										result = false;
										Assert.assertTrue(false, "Search Code is not found");
									}

								} // Search by description
								else {
									logger.info("Search by description ");
									udaPage.clickDropDown();
									logger.info("........... clicked dropdown");
									udaPage.clickDescription();

									udaPage.clickSearchButton();
									Thread.sleep(3000);

									if (udaPage.searchResultDescription().equalsIgnoreCase(LOVcode)) {
										logger.info("found search result by description");
										try {
											udaPage.clickOnSearchResult();
										} catch (Exception e) {
											js.executeAsyncScript("arguments[0].click();",
													udaPage.clickOnSearchResult_WebElement());

										}

									} else {
										udaPage.closeIcon();
										result = false;
										Assert.assertTrue(false, "Search Description is not found");
									}

								}
							} else {
								logger.info("inside the LovCode is empty block");

								udaPage.LovCodeTextBox(i);
							}
						}

						else {

							try {
								udaPage.clickFreeText();
								logger.info("inside  the freeText");
								freetext = true;

							} catch (Exception e) {

								result = false;
								logger.info("Exception in FREETEXT");
								e.printStackTrace();
								Assert.assertTrue(false,
										"There is an exception in clicking FreeText in FieldType coloumn");
							}

						}
System.out.println("Result is 1======="+result);
						// validation starts
						if (userDefinedFieldName.isEmpty()) {

							try {
								logger.info("Inside the Empty User Defined Field Name");
								userdefinedfieldnameEmpty_bool = udaPage.validationForEmptyUserDefinedField();

								if (userdefinedfieldnameEmpty_bool) {
									logger.info("validation exist for UserDefinedField is Empty");
									anyUserDefinedFieldNameEmpty_validation = true;

								} else {
									result = false;
									logger.info("There is No validation msg for empty UserDefinedFieldName");
									Assert.assertTrue(false,
											"There is No validation msg for empty UserDefinedFieldName");
								}
							} catch (Exception e) {

								result = false;
								e.printStackTrace();
								logger.info("Exception in UserDefinedField is Empty");
								Assert.assertTrue(false, "There is an Exception in Empty UserNameField validation");
							}

						}
						System.out.println("Result is 2======="+result);
						// max char limit
						if (userDefinedFieldName.length() > 30) {
							try {

								logger.info("Max Char is exceeds the limit of 30");
								userdefinedfieldnameExceedsMaxChar_bool = udaPage
										.validationForMaxCharUserDefinedField();
								if (userdefinedfieldnameExceedsMaxChar_bool) {
									logger.info("There is a proper validation for Max Char");

								} else {
									result = false;
									logger.info(
											"There is No validation msg for empty UserDefinedFieldName Max Charater Limit");
									Assert.assertTrue(false,
											"There is No validation msg for Max Charater Limit in UserDefinedFieldName ");
								}

							} catch (Exception e) {

								result = false;
								e.printStackTrace();
								logger.info("Exception in UserDefinedField is Max Character Limit ");
								Assert.assertTrue(false,
										"There is an Exception in Max Character Limit UserNameField validation");

							}

						}
						System.out.println("Result is 3======="+result);
						// Duplicate User Defined Field Name

						for (int l = 0; l < allUserDefinedFieldNamearrayList.size(); l++) {

							if (allUserDefinedFieldNamearrayList.get(l).contains(userDefinedFieldName)) {

								logger.info("inside the duplicate User Defined Field Name ---------------->>");
								try {
									duplicateUserdefinedfieldname_bool = udaPage
											.validationForDuplicateUserDefinedFieldName();

									if (duplicateUserdefinedfieldname_bool) {
										anyduplicateUserdefinedfieldname = true;
										logger.info("Validation exist for  duplicateUserdefinedfieldname");

										Assert.assertTrue(true);
									} else {
										result = false;
										logger.info("No Validation for duplicateUserdefinedfieldname");
										Assert.assertTrue(false, "No validation for duplicateUserdefinedfieldname");
									}

								} catch (Exception e) {
									e.printStackTrace();
									result = false;
									Assert.assertTrue(false,
											"No validation Msg for Duplicate/Already Exist USERDEFINED FIELD NAME");
								}

							}
						}
						System.out.println("Result is 4======="+result);
						if (udaPage.getSelectTypetextFromPage(i).isEmpty()) {

							try {
								logger.info("Inside the Empty Field Type");
								fieldType_bool = udaPage.validationForEmptyFieldType();

								if (fieldType_bool) {
									logger.info("validation exist for FieldType is Empty");
									anyFieldTypeEmpty_validation = true;

								} else {
									result = false;
									logger.info("Error in Fieldtype is Empty");
									Assert.assertTrue(false, "There is No validation msg for empty FieldType");

								}
							} catch (Exception e) {
								e.printStackTrace();
								result = false;
								Assert.assertTrue(false, "Exception in Empty FIELDTYPE ");

							}

						}
						System.out.println("Result is 5======="+result);
						if (fieldType.equalsIgnoreCase("lookup"))
							if (udaPage.getLOVtextFromPage(i).isEmpty() && freetext == false) {

								try {

									logger.info("Inside the Empty LOV Code");
									lovCode_bool = udaPage.validationlovCodeEmpty();
									if (lovCode_bool) {
										logger.info("validation exist for lovCode is Empty");
										anylovCodeEmpty_validation = true;
									} else {
										result = false;
										logger.info("Error in lovCode is Empty");
										Assert.assertTrue(false, "There is No validation msg for empty lovCode");

									}

								} catch (Exception e) {

									result = false;
									e.printStackTrace();
									logger.info("Exception in lovCode is Empty");
									Assert.assertTrue(false, "There is Exception in empty lovCode Validation");
								}

							}

					}
				}
			}
			
					
		} catch (Exception e) {
			logger.info("There is an error");
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception in for loop");
		} finally {
			logger.info("Result is  in finally block: "+result);
			finalResultMap.put(testCaseNumber, result);
			allUserDefinedFieldNamearrayList.add(userDefinedFieldName);
			firstTime++;

			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);
			result =true;
		}

	}

	@DataProvider(name = "addUDA")
	String[][] getData() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
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
