package com.extraPOSTest.testCases.inventory.maintenance;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
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
import com.extraPOSTest.pageObjects.inventory.maintenance.LOVPage;
import com.extraPOSTest.pageObjects.inventory.maintenance.TypePage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

import net.bytebuddy.asm.Advice.Exit;

public class TC_LOV extends BaseClass {

	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
	LOVPage lovPage;
	public List<String> allLOVCodeList;
	public List<String> allRowCodeList = new ArrayList<>();
	int numberOfRecords;
	boolean result = true;
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	String outPutExcellLocation = readConfig.getInventoryMaintenanceOutputPath();
	String readSheetName = "addLOV";
	String readSheetNameLOVRow = "addLOVRow";
	String writeSheetName = "OutputLOV";
	int lovCodeCount = 1;
	public boolean lovRowCodeDuplicate_bool, lovRowCodeEmpty_bool;
	int rowId = 0,iteration=1;
	boolean warning = false;
	
	
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
	public void selectALL_forLOVCode() throws InterruptedException {

		lovPage = new LOVPage(driver);
		postLoginPage = new PostLoginPage(driver);
		postLoginPage.clickInventory();
		logger.info("Clicked succefully Inventory");
		postLoginPage.clickInventoryMaintenance();
		logger.info("Clicked succefully Manintenace");
		postLoginPage.clickLOV();
		logger.info("LOV Clicked succefully");

		try {

			lovPage.clickSelectDropDown();

		} catch (Exception e) {
			js.executeScript("arguments[0].click();", lovPage.clickSelectDropDown_WebElement());
		}
		WebElement all;
		all = wait.until(ExpectedConditions.visibilityOfElementLocated(lovPage.allBy()));

		try {
			lovPage.clickAll();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", lovPage.clickAll_WebElement());
		}
		allLOVCodeList = new ArrayList<>();
		List<WebElement> allLOVCode = lovPage.allLOVCodeList();
		logger.info("Size of the TypeCode List -->> " + allLOVCode.size());
		for (int i = 0; i < allLOVCode.size(); i++) {
			allLOVCodeList.add(allLOVCode.get(i).getText());
		}

	}

	@Test(dataProvider = "addLOV", priority = 1, enabled = true)
	public void createLOVCode(String testCaseNumber, String lovCode, String description, String status)
			throws IOException {

		try {

			lovPage = new LOVPage(driver);
			
			boolean lovCodeEmpty_bool = false, lovCodeDuplicate_bool = false, lovCharacterLimit = false; 
			
			if(iteration ==1 ) {
				lovPage.clickAddButton();
			}
			    
				lovPage.sendLOVCODE(lovCode);
				lovPage.sendLOVDescription(description);

				if (status.equalsIgnoreCase("inactive")) {

					lovPage.clickStatusLOVCODE();
				}

				// validation starts here

				if (lovCode.isEmpty()) {
					logger.info("Inside the empty LOV if--------------");

					try {
						lovCodeEmpty_bool = lovPage.validationForEmptyLovCode();
						if (lovCodeEmpty_bool) {
							logger.info("There is a validation for Empty LOV Code");
							Assert.assertTrue(true);
						} else {
							result = false;
							logger.info("No proper validation msg for Empty LOV");
							Assert.assertTrue(false, "No Proper validation for empty LOV");
						}
					} catch (Exception e) {
						result = false;
						logger.info("Exception happends in LOV IsEmpty?? Block");
						Assert.assertTrue(false, "Exception happends in LOV IsEmpty?? Block");
					}

				}
				else if(lovCode.length() > 30) {
					
					logger.info("LOV Code is greater than 30");
					try {
						
						lovCharacterLimit =lovPage.validationForCharaterLimitLovCode();
						if(lovCharacterLimit) {
							logger.info("Proper validation for LOV Code Character");
							Assert.assertTrue(true);
						}else {
							logger.info("No validation for Character limit LOV Code");
							Assert.assertTrue(false, "No validation for Character limit LOV Code");
						}
						
					}catch (Exception e) {
						
						Assert.assertTrue(false, "Exception happends,in the validation for Character limit LOV Code");
					}
					
				}
				else {

					logger.info("Inside the else LOV (Not Empty)--------------");

					for (int i = 0; i < allLOVCodeList.size(); i++) {
						if (allLOVCodeList.get(i).contains(lovCode)) {

							logger.info("LOV is duplicate ");
							try {

								lovCodeDuplicate_bool = lovPage.validationForDuplicateLovCode();

								if (lovCodeDuplicate_bool) {
									logger.info("There is a validation for Duplicate LOV Code");
									Assert.assertTrue(true);
								} else {
									result = false;
									logger.info("There is no proper validation msg for Duplicate LOV CODE");
									Assert.assertTrue(false,"There is no proper validation msg for Duplicate LOV CODE");
								}

								
								
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception Happend inside the LOV is Duplicate");

							}

						}

					}
					
				
				}
				
				// Save Action
				lovPage.clickSave();
				if(lovCodeEmpty_bool == true || lovCodeDuplicate_bool == true || lovCharacterLimit == true) {
					try {
					logger.info("This will not save , there are some error in the LOV Code* ");
					if(lovPage.errorMessage()) {
						logger.info("Error Message is present for Can not save LOV Code");
						lovPage.clickTICK();
						
						logger.info("Going to clear all the data in the LOV Code and Description");
						lovPage.clearLOVCODE();
						lovPage.clearLOVDescription();
						
					}else {
						logger.info("Expected error msg, But not found");
						result= false;
						lovPage.clickCANCEL();
						logger.info("Going to clear all the data in the LOV Code and Description");
						lovPage.clearLOVCODE();
						lovPage.clearLOVDescription();
						Assert.assertTrue(false,"There is no proper Error Msg for Save Action");
						
					}
					}catch (Exception e) {

					result =false;
					e.printStackTrace();
					Assert.assertTrue(false, "Exception in Can not save Action");
					}
					
				}else {
					try {
					logger.info("There is no error/ Validation msg present in the LOV Code*, Gpoint to save ");
					lovPage.clickTICK();
					
					wait.until(ExpectedConditions.visibilityOfElementLocated(lovPage.saveTextPopUp()));
					
					try
					{
						postLoginPage.clickLOV();
					}
					catch (Exception e) {
						
						js.executeScript("arguments[0].click();", postLoginPage.clickLOV_WebElement());					
					}
					lovPage.clickAddButton();
					
					
					}catch (Exception e) {
						
						result=false;
						e.printStackTrace();
						assertTrue(false, "Exception is Save Action");
					}
					
				}
				

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false,"Exception in the LOV Code*");
		} finally {
			finalResultMap.put(testCaseNumber, result);
			allLOVCodeList.add(lovCode);
			
			logger.info("numberOfRecords "+numberOfRecords);
			logger.info("iteration "+iteration);
			
			if(numberOfRecords+1 == iteration) {
				logger.info("numberOfRecords == iteration");
				lovPage.clickHomeButton();
				lovPage.clickTICK();
				postLoginPage.clickLOV();
				//lovPage.clickAddButton();
			}
			iteration++;
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);

		}

	}
	
	
	
	
	

	@DataProvider(name = "addLOV")
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
	
	
	@Test(dataProvider = "addRowLOV", priority = 2, enabled =true)
	public void addLOVrow(String testCaseNumber, String lovCode, String description, String status) throws IOException {
		
		try {
			
			
			lovPage = new LOVPage(driver);
			
			
				
			logger.info("Lov................");
			// This If Statement is for LOV CODE*
			if(lovCodeCount == 1 )   {  
				logger.info("lovCodeCount == 1...............");
				lovPage.clickAddButton();
				boolean lovCodeEmpty_bool = false, lovCodeDuplicate_bool = false, lovCharacterLimit = false;
				lovPage.sendLOVCODE(lovCode);
				lovPage.sendLOVDescription(description);

				if (status.equalsIgnoreCase("inactive")) {

					lovPage.clickStatusLOVCODE();
				}

				// validation starts here

				if (lovCode.isEmpty()) {
					logger.info("Inside the empty LOV if--------------");

					try {
						lovCodeEmpty_bool = lovPage.validationForEmptyLovCode();
						if (lovCodeEmpty_bool) {
							logger.info("There is a validation for Empty LOV Code");
							Assert.assertTrue(true);
						} else {
							result = false;
							logger.info("No proper validation msg for Empty LOV");
							Assert.assertTrue(false, "No Proper validation for empty LOV");
						}
					} catch (Exception e) {
						result = false;
						logger.info("Exception happends in LOV IsEmpty?? Block");
						Assert.assertTrue(false, "Exception happends in LOV IsEmpty?? Block");
					}

				}
				else if(lovCode.length() > 30) {
					
					logger.info("LOV Code is greater than 30");
					try {
						
						lovCharacterLimit =lovPage.validationForCharaterLimitLovCode();
						if(lovCharacterLimit) {
							logger.info("Proper validation for LOV Code Character");
							Assert.assertTrue(true);
						}else {
							logger.info("No validation for Character limit LOV Code");
							Assert.assertTrue(false, "No validation for Character limit LOV Code");
						}
						
					}catch (Exception e) {
						
						Assert.assertTrue(false, "Exception happends,in the validation for Character limit LOV Code");
					}
					
				}
				else {

					logger.info("Inside the else LOV (Not Empty)--------------");

					for (int i = 0; i < allLOVCodeList.size(); i++) {
						if (allLOVCodeList.get(i).contains(lovCode)) {

							logger.info("LOV is duplicate ");
							try {

								lovCodeDuplicate_bool = lovPage.validationForDuplicateLovCode();

								if (lovCodeDuplicate_bool) {
									logger.info("There is a validation for Duplicate LOV Code");
									Assert.assertTrue(true);
								} else {
									result = false;
									logger.info("There is no proper validation msg for Duplicate LOV CODE");
									Assert.assertTrue(false,"There is no proper validation msg for Duplicate LOV CODE");
								}

								
								
							} catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception Happend inside the LOV is Duplicate");

							}

						}

					}
					
				
				}
			
				// Save Action
				lovPage.clickSave();
				if(lovCodeEmpty_bool == true || lovCodeDuplicate_bool == true || lovCharacterLimit == true) {
					try {
					logger.info("This will not save , there are some error in the LOV Code* ");
					if(lovPage.errorMessage()) {
						logger.info("Error Message is present for Can not save LOV Code");
						lovPage.clickTICK();
						
						logger.info("Going to clear all the data in the LOV Code and Description");
						lovPage.clearLOVCODE();
						lovPage.clearLOVDescription();
						result= false;
						warning =true;
						
						Assert.assertTrue(false,"In this Test It should save successfully");
					}else {
						logger.info("Expected error msg, But not found");
						result= false;
						lovPage.clickCANCEL();
						logger.info("Going to clear all the data in the LOV Code and Description");
						lovPage.clearLOVCODE();
						lovPage.clearLOVDescription();
						warning =true;
						Assert.assertTrue(false,"There is no proper Error Msg for Save Action");
						
						
						
					}
					}catch (Exception e) {

					result =false;
					e.printStackTrace();
					Assert.assertTrue(false, "Exception in Can not save Action");
					}
					
				}else {
					try {
					logger.info("This Will Save LOV CODE*............ ");
					lovPage.clickTICK();
					
					//wait.until(ExpectedConditions.visibilityOfElementLocated(lovPage.saveTextPopUp()));
					
					}catch (Exception e) {
						
						result=false;
						e.printStackTrace();
						assertTrue(false, "Exception is Save Action");
					}
					
				}
		} 
			
			else {//This Else Statement is for Lov Row Add...
			
				if(warning == false) {
				try {
					boolean lovRowCodeDuplicate_bool = false, lovRowCodeEmpty_bool=false;
					logger.info("LOV Row COde is :"+lovCode+"  Description is :"+description+"RowId is :"+rowId);	
					 
					wait.until(ExpectedConditions.visibilityOfElementLocated(lovPage.clickAddRow_Locator()));
					    lovPage.clickAddRow();
						lovPage.sendRowCode(rowId,lovCode);
						lovPage.sendRowDescription(rowId,description);
						if (status.equalsIgnoreCase("inactive")) {

							lovPage.clickStatus(rowId);
						}

						
						// validation Starts from here
						if (lovCode.isEmpty()) {
							logger.info("Inside the empty rowLOV if--------------");

							try {
								lovRowCodeEmpty_bool = lovPage.validationForEmptyRowLovCode();
								if (lovRowCodeEmpty_bool) {
									logger.info("There is a validation for Empty RowLOV Code");
									lovPage.clickDeleteLOVCodeRowOne();
									Assert.assertTrue(true);
									
								} else {
									result = false;
									logger.info("No proper validation msg for Empty RowLOV");
									lovPage.clickDeleteLOVCodeRowOne();
									Assert.assertTrue(false, "No Proper validation for empty RowLOV");
								}
							} catch (Exception e) {
								result = false;
								lovPage.clickDeleteLOVCodeRowOne();
								logger.info("Exception happends in RowLOV IsEmpty?? Block");
								Assert.assertTrue(false, "Exception happends in RowLOV IsEmpty?? Block");
							}
						
						}
						else {
							
							try {
							logger.info("Inside not empty block");
							for(int i=0; i<=allRowCodeList.size();i++) {
								if(allRowCodeList.contains(lovCode)) {
									logger.info("The LOV Row Code already exist Block");
									lovRowCodeDuplicate_bool = lovPage.validationForDuplicateRowLovCode();
									if(lovRowCodeDuplicate_bool) {
										logger.info("There is proper validation for the duplicate lov Row Code");
										lovPage.clickDeleteLOVCodeRowOne();
										Assert.assertTrue(true);
									}else {
										result = false;
										logger.info("There is NO proper validation for the duplicate lov Row Code");
										lovPage.clickDeleteLOVCodeRowOne();
										Assert.assertTrue(false,"There is NO proper validation for the duplicate lov Row Code");
									}
								}
								
							}
									
							
							}catch (Exception e) {
								result = false;
								Assert.assertTrue(false, "Exception happends inside the else block, for LOVROW code already exist");
							}finally {
								allRowCodeList.add(lovCode);
							}
							
						}
						
						
						//save for LOV Row
						if( lovRowCodeDuplicate_bool == false && lovRowCodeEmpty_bool == false) {
							logger.info("Inside LOV ROW code save action");
							try {
								wait.until(ExpectedConditions.elementToBeClickable(lovPage.clickSaveButton_WebElement()));
								lovPage.clickSave();
								lovPage.clickTICK();
								wait.until(ExpectedConditions.visibilityOfElementLocated(lovPage.saveTextPopUp()));
							}
							catch (Exception e) {
								logger.info("Exception is happend inside the Save LOVROW " );
								result = false;
								Assert.assertTrue(false,"Exception is happend inside the Save LOVROW");
							}
							
						}else {
							logger.info("Inside LOV ROW code error save action");
							
							try {
								System.out.println("hiiiiiiiiiiiiii");
								lovPage.clickTICK();
								System.out.println("Clicked................");

							} catch (Exception e) {
								System.out.println("Exception................");
								js.executeScript("arguments[0].click();", lovPage.clickTICK_WebElement());
								System.out.println("Exception. Clicked...............");
							}
							
							
							logger.info("Error msg came");
							wait.until(ExpectedConditions.visibilityOfElementLocated(lovPage.errorMessage_Locators()));
							
							
							
							try {
								lovPage.clickTICK();
							} catch (Exception e) {

							js.executeScript("arguments[0].click();", lovPage.clickTICK_WebElement() );
							}
						
							
							lovPage.clickDeleteLOVCodeRowOne();
							
						}
						
						
					
					}catch (Exception e) {
						result =false;
						e.printStackTrace();
					Assert.assertTrue(false, "There is an Exception in Else if --->Add Row");
					}
					
					
			}else {
				result = false;
				Assert.assertTrue(false, "Forcefully throwing an Exception Because of LOV CODE not Created");
				throw new ElementNotInteractableException("LOV CODE is not created");
			}
		}
			
		} catch (Exception e) {

		result = false;
		e.printStackTrace();
		assertTrue(false, "Exception is addLOVRow");
		
		}finally {
			
			lovCodeCount++;
			
			
				finalResultMap.put(testCaseNumber, result);
			if(numberOfRecords == rowId)
			xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);
			rowId++;
		}
		
		
	}
	
	
	@DataProvider(name = "addRowLOV")
	String[][] getROWLOVData() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readSheetNameLOVRow);
		int colCount = XLUtils.getCellCount(path, readSheetNameLOVRow, 0);
		numberOfRecords = rowCount - 1;
		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readSheetNameLOVRow, i, j).trim();
			}
		}

		return userdata;
	}
	
	
	
}
