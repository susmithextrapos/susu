package com.extraPOSTest.testCases.inventory.maintenance;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.extraPOSTest.pageObjects.inventory.maintenance.ItemMasterPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;
import com.microsoft.schemas.vml.STTrueFalse;

public class TC_ItemMaster extends BaseClass {
	
	
	public WebDriver driver;
	PostLoginPage postLoginPage;
	ReadConfig readConfig = new ReadConfig();
	XLUtils xlutils = new XLUtils();
    ItemMasterPage itemMasterPage;
	String readSheetName_ItemMaster = "IM";
	//String readSheetName_ItemCode = "ItemMaster";
	String code ="code";
	
	List<WebElement> defaultValueWebList = new ArrayList<>();
	String defaultValueIs;
	
	public List<String> allItemCodeList;public List<String> allDefaultList;
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
		Thread.sleep(3000);
		allItemCodeList = new ArrayList<>();
		List<WebElement> allItemCode = itemMasterPage.allItemCodeList();
		logger.info("Size of the Item Master List -->> " + allItemCode.size());
		for (int i = 0; i < allItemCode.size(); i++) {
			allItemCodeList.add(allItemCode.get(i).getText());
		}
	}

	@Test(dataProvider = "itemMaster", priority = 1, enabled=true)
	public void ItemCodeValidation(String testCaseNumber, String itemCode, String description, String category,
			String categorySearchBy, String subCategory,String subCategorySearchBy, String brand,String brandSearchBy ,
			String line,String lineSearchBy, String type,String typeSearchBy,String clas,String classSearchBy, String SKUuom, 
			 String sellingUom,  String purchaseUom,String packingText,String packingUom,String lengthText,
			 String lengthUom,String widthText, String widthUom,String heightText, String heightUom, 
			 String weightText,String weightUom, String loyalty, String noOfPoints, String obsolete, String nonsellable,
			 String voiid,String printPickingSlips,String allowFractions, String defaultVendor, String purchaseGroup,
			 String taxCodeInbound, String taxCodeOutbound, String productType, String costingMethod,String branch_Branches,
			 String minStock_Branch,String maxStock_Branch,String reOrderLevel_Branch, String leadTime_Branch,
			 String minOrderQty_Branch,String maxOrderQty_Branch, String orderMultilpleOf_Branch, String reOrderQty_Branch,
			 String status_Branch ,String MarkUp_branch,String MarkUp_minimumMarkUpType,String MarkUp_minimumMarkUpText,
			 String MarkUp_minimumMArkUpTypeEnforceable,String barcode_Barcode,String defaultSellingPrice) throws IOException, InterruptedException {

		
		try {
			logger.info("inside the ItemMasterValidation");
			itemMasterPage = new ItemMasterPage(driver);
			
			boolean emptyItemCode= false, maxLenItemCode= false, duplicateItemCode = false,emptyDescription = false,emptycategory = false,
					emptySKUuom = false,emptySellingUom=false,integer_decimalValidation=false, NegativeValidation= false, noOfPointsNegative = false
					, emptydefaultSellingPrice =false,emptyminimumMarkUpType = false, minimumMArkUpType_Enforceable= false, maxLenBarCode= false;
			
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
		
			// Category
			 
			//itemMasterPage.clearCategory(); 
			if(category.length() !=0)
			{
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
				
				lookUpSearch(categorySearchBy,category);
				
				} // if closed here for category length>0;;;; This else
				else // this else for if the category code is empty
				{
					itemMasterPage.categoryTextBox();// TAB key twise
				}
			
			
			// Sub Category
			
			
			if(subCategory.length() !=0)
			{
				itemMasterPage.clickSubCategoryLookUp(); // click SubCategory LookUp
				// SubCategory PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(subCategory);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(subCategorySearchBy,subCategory);
				
				
				} // if closed here for subcategory length>0;;;; This else
				else // this else for if the Subcategory code is empty
				{
					itemMasterPage.subCategoryTextBox();// TAB key twise
				}
			
			
			
			//Brand
			
			if(brand.length() !=0)
			{
				itemMasterPage.clickBrandLookUp(); // click Brand LookUp
				// SubCategory PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(brand);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(brandSearchBy,brand);
				
				
				} // if closed here for brand length>0;;;; This else
				else // this else for if the brand code is empty
				{
					itemMasterPage.brandTextBox();// TAB key twise
				}
			
			
			//Line
			
			if(line.length() !=0)
			{
				itemMasterPage.clickLineLookUp(); // click brand LookUp
				// Brand PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(line);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(lineSearchBy,line);
				
				
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.lineTextBox();// TAB key twise
				}
			
	//Type
			
			if(type.length() !=0)
			{
				itemMasterPage.clickTypeLookUp(); // click type LookUp
				// type PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(type);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(typeSearchBy,type);
				
				
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.typeTextBox();// TAB key twise
				}
			
			//class
			
			if(clas.length() !=0)
			{
				itemMasterPage.clickClassLookUp(); // click class LookUp
				// class PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(clas);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(classSearchBy,clas);
				
				
			} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.classTextBox();// TAB key twise
				}
			
			
			// SKU Uom 
			itemMasterPage.clearSKUuom();
			if(SKUuom.length() !=0)
			{
				itemMasterPage.clickSKULookUp(); // click SKU  LookUp
				// class PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(SKUuom);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(code,SKUuom);
				
				
				//inside SKU we are checking Selling Uom also
				//Selling UOM
				itemMasterPage.clearPurchasinguom();
				if(sellingUom.length() != 0 && itemMasterPage.sellingUomTextBoxValue().isEmpty())
				{
					itemMasterPage.clickSellingLookUp(); // click SellingUom  LookUp
					// class PopUp Actions
					try 
					{
						itemMasterPage.sendSearchLookUp(sellingUom);
					} 
					catch (Exception e)
					{

						js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

					}
					
					lookUpSearch(code,sellingUom);
					
					
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.SellingTextBox();// TAB key twise
				}
				
				
				//inside SKU we are checking Purchasing Uom also
				//Purchasing UOM
				if(purchaseUom.length() != 0 && itemMasterPage.purchasingUomTextBoxValue().isEmpty())
				{
					itemMasterPage.clickPurchaseUomLookUp(); // click purchase  LookUp
					// class PopUp Actions
					try 
					{
						itemMasterPage.sendSearchLookUp(purchaseUom);
					} 
					catch (Exception e)
					{

						js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

					}
					
					lookUpSearch(code,purchaseUom);
					
					
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.purchaseTextBox();// TAB key twise
				}
				
				//Packing Text Box
				if(packingText.length() !=0)
				{
					itemMasterPage.sendPacking(packingText);
				}
				else
				{
					itemMasterPage.clearPackingTextBox();
				}
				
				//inside SKU we are checking Packing Uom also
				//Packing UOM
				if(packingUom.length() != 0 && itemMasterPage.packingUomTextBoxValue().isEmpty())
				{
					itemMasterPage.clickPackingLookUp(); // click packing  LookUp
					// class PopUp Actions
					try 
					{
						itemMasterPage.sendSearchLookUp(packingUom.trim());
					} 
					catch (Exception e)
					{

						js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

					}
					
					lookUpSearch(code,packingUom);
					
					
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.packingUomTextBox();// TAB key twise
				}
				
				// Length Text box
				if(lengthText.length() !=0)
				{
					itemMasterPage.sendLength(lengthText);
				}
				else
				{
					itemMasterPage.clearLengthTextBox();
				}
				//inside SKU we are checking Length Uom also
				//Length UOM
				if(lengthUom.length() != 0 && itemMasterPage.lengthUomTextBoxValue().isEmpty())
				{
					itemMasterPage.clickLengthLookUp(); // click length  LookUp
					// class PopUp Actions
					try 
					{
						itemMasterPage.sendSearchLookUp(lengthUom);
					} 
					catch (Exception e)
					{

						js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

					}
					
					lookUpSearch(code,lengthUom);
					
					
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.lengthUomTextBox();// TAB key twise
				}
				
				// Width Text box
				if(widthText.length() !=0)
				{
					itemMasterPage.sendWidth(widthText);
				}
				else
				{
					itemMasterPage.clearWidthTextBox();
				}
				//inside SKU we are checking Width Uom also
				//Width UOM
				if(widthUom.length() != 0 && itemMasterPage.widthUomTextBoxValue().isEmpty())
				{
					itemMasterPage.clickWidthLookUp(); // click Width  LookUp
					// class PopUp Actions
					try 
					{
						itemMasterPage.sendSearchLookUp(widthUom);
					} 
					catch (Exception e)
					{

						js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

					}
					
					lookUpSearch(code,widthUom);
					
					
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.widthUomTextBox();// TAB key twise
				}
				
				// height Text box
				if(heightText.length() !=0)
				{
					itemMasterPage.sendHeight(heightText);
				}
				else
				{
					itemMasterPage.clearHeightTextBox();
				}
				
				//inside SKU we are checking Height Uom also
				//Height UOM
				if(heightUom.length() != 0 && itemMasterPage.heightUomTextBoxValue().isEmpty())
				{
					itemMasterPage.clickHeightLookUp(); // click Height  LookUp
					// class PopUp Actions
					try 
					{
						itemMasterPage.sendSearchLookUp(heightUom);
					} 
					catch (Exception e)
					{

						js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

					}
					
					lookUpSearch(code,heightUom);
					
					
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.heightUomTextBox();// TAB key twise
				}
				
				// Weight Text box
				if(weightText.length() !=0)
				{
					itemMasterPage.sendWeight(weightText);
				}
				else
				{
					itemMasterPage.clearWeightTextBox();
				}
				
				//inside SKU we are checking Weight Uom also
				//Weight UOM
				if(weightUom.length() != 0 && itemMasterPage.weightUomTextBoxValue().isEmpty())
				{
					itemMasterPage.clickWeightLookUp(); // click Weight  LookUp
					// class PopUp Actions
					try 
					{
						itemMasterPage.sendSearchLookUp(weightUom);
					} 
					catch (Exception e)
					{

						js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

					}
					
					lookUpSearch(code,weightUom);
					
					
				} // if closed here for line length>0;;;; This else
				else // this else for if the line code is empty
				{
					itemMasterPage.weightUomTextBox();// TAB key twise
				}
				
				
			} // if closed here for line length>0;;;; This else
			else // this else for if the line code is empty
			{
				itemMasterPage.SKUTextBox();// TAB key twise
			}
			
			
			
			// Loyalty Started
			
			if(loyalty.equalsIgnoreCase("active"))
			{
				itemMasterPage.clickLoyalty();
				
				if(noOfPoints.isEmpty() == false)
				{
					itemMasterPage.sendNoOfPoints(noOfPoints);
				}
			}
			
			if(obsolete.equalsIgnoreCase("active"))
			{
				itemMasterPage.clickObsolete();
			}
			
			if(nonsellable.equalsIgnoreCase("active"))
			{
				itemMasterPage.clickNonSellable();
			}
			
			if(voiid.equalsIgnoreCase("active"))
			{
				itemMasterPage.clickVoid();
			}
			
			if(printPickingSlips.equalsIgnoreCase("atcive"))
			{
				itemMasterPage.clickPrintPickingSlips();
			}
			if(allowFractions.equalsIgnoreCase("active"))
			{
				itemMasterPage.clickAllowFractions();
			}
			
			//Default Vendor
			itemMasterPage.clearDefaultVendor();
			if(defaultVendor.length() != 0 )
			{
				itemMasterPage.clickDefaultVendorLookUp(); // click default Vendor  LookUp
				// method PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(defaultVendor);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(code,defaultVendor);
				
				
			} // if closed here for line length>0;;;; This else
			else // this else for if the line code is empty
			{
				itemMasterPage.defaultVendorTextBox();// TAB key twise
			}
			
			//Purchase Group
			itemMasterPage.clearPurchaseGroup();
			if(purchaseGroup.length() != 0 )
			{
				
				
				try 
				{
					itemMasterPage.clickPurchaseGroupLookUp(); // click Purchase Group LookUp
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.clickPurchaseGroupLookUp_WebElement());

				}
				// method PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(purchaseGroup);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(code,purchaseGroup);
				
				
			} // if closed here for line length>0;;;; This else
			else // this else for if the line code is empty
			{
				itemMasterPage.purchaseGroupTextBox();// TAB key twise
			}
			
			
			// Tax Inbound
			
			itemMasterPage.clearTaxInbound();
			if(taxCodeInbound.length() != 0 )
			{
				itemMasterPage.clickTaxInboundLookUp(); // click Tax Inbound LookUp
				// method PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(taxCodeInbound);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(code,taxCodeInbound);
				
				
			} // if closed here for line length>0;;;; This else
			else // this else for if the line code is empty
			{
				itemMasterPage.taxInboundTextBox();// TAB key twise
			}
			
			
			// Tax Outbound
			
			itemMasterPage.clearTaxOutbound();
			if(taxCodeOutbound.length() != 0 )
			{
				itemMasterPage.clickTaxOutbound(); // click Tax Outbound LookUp
				// method PopUp Actions
				try 
				{
					itemMasterPage.sendSearchLookUp(taxCodeOutbound);
				} 
				catch (Exception e)
				{

					js.executeScript("arguments[0].click();", itemMasterPage.sendSearchLookUp_WebElement());

				}
				
				lookUpSearch(code,taxCodeOutbound);
				
				
			} // if closed here for line length>0;;;; This else
			else // this else for if the line code is empty
			{
				itemMasterPage.taxOutboundTextBox();// TAB key twise
			}
			
			// Product Type
			
			itemMasterPage.clickProductTypeDropDown();
			if(productType.equalsIgnoreCase("kit item"))
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
			
//Tabs************************************************************************************************************
			logger.info("Going to Branch Tab......................");
			//Branches
			itemMasterPage.clickBranchTab();
			if(branch_Branches.length() !=0)
			{
				if(postLoginPage.isAddButtonClickable())
				{
				   try 
				   {
					postLoginPage.clickAddRowButton();
				   }
				   catch (Exception e) 
				   {
					js.executeScript("arguments[0].click();", postLoginPage.clickAddRowButton_WebElement());
				
				   }
				   
				   itemMasterPage.sendBranch_MinimumStock(minStock_Branch);
				   itemMasterPage.sendBranch_MaximumStock(maxStock_Branch);
				   itemMasterPage.sendBranch_ReOrderLevel(reOrderLevel_Branch);
				   itemMasterPage.sendBranch_LeadTime(leadTime_Branch);
				   itemMasterPage.sendBranch_MinOrderQty(minOrderQty_Branch);
				   itemMasterPage.sendBranch_MaxOrderQty(maxOrderQty_Branch);
				   itemMasterPage.sendBranch_OrderMultipleOf(orderMultilpleOf_Branch);
				   itemMasterPage.sendBranch_ReOrderQty(reOrderQty_Branch);

				   if(status_Branch.equalsIgnoreCase("inactive"))
				   {
					   try
					   {
						   itemMasterPage.clickBranch_Status();
					   } 
					   catch (Exception e)
					   {

						   js.executeScript("arguments[0].click();", itemMasterPage.clickBranch_Status_WebElement());
					   }
					   
					   
				   }
				   
				   
				}
				
				
			}
			logger.info("Left to Branch Tab......................");
			logger.info("Going to MarkUp.......................");
			//MarkUp Tab
			
			itemMasterPage.clickMarkUpTab();
			if(postLoginPage.isAddButtonClickable())
			{
			  /* try 
			   {
				postLoginPage.clickAddRowButton();
			   }
			   catch (Exception e) 
			   {
				js.executeScript("arguments[0].click();", postLoginPage.clickAddRowButton_WebElement());
			
			   }
			   */
			   //Minimum Mark-Up Type*
			   itemMasterPage.clickMinimumMArkUpTypeDropDown_MarkUp();
			   if(MarkUp_minimumMarkUpType.equalsIgnoreCase("amount")) 
			   {
				   itemMasterPage.clickMinimumMArkUpType_Amount_MarkUp();
			   }
			   else if(MarkUp_minimumMarkUpType.equalsIgnoreCase("percentage"))
			   {
				 itemMasterPage.clickMinimumMArkUpType_Percentage_MarkUp();  
			   }
			   else //if empty tab
			   {
				   itemMasterPage.minimumMarkUpTypeTextBox();
			   }
			   
			   //Minimum Mark-Up
			   if(MarkUp_minimumMarkUpText.isEmpty() == false)
			   {
				   itemMasterPage.minimumMarkUpTextBox(MarkUp_minimumMarkUpText);
			   }
			   logger.info("MarkUp_minimumMarkUpText is completed "+MarkUp_minimumMarkUpText);
			   //Minimum Mark-Up Type Enforceable*
			   
			   itemMasterPage.clickMinimumMArkUpType_Enforceable_DropDown_MarkUp();
			   Thread.sleep(1000);
			   if(MarkUp_minimumMArkUpTypeEnforceable.equalsIgnoreCase("enforce"))
			   {
				   itemMasterPage.clickMinimumMarkUpTypeEnforceable_Enforce();
			   }
			   else if(MarkUp_minimumMArkUpTypeEnforceable.equalsIgnoreCase("ignore"))
			   {
				   itemMasterPage.clickMinimumMarkUpTypeEnforceable_Ignore();
			   }
			   else if(MarkUp_minimumMArkUpTypeEnforceable.equalsIgnoreCase("warning"))
			   {
				   itemMasterPage.clickMinimumMarkUpTypeEnforceable_Warning();
			   }
			   else //if empty tab
			   {
				   itemMasterPage.minimumMarkUpTypeEnforceable_TextBox();
			   }
			   
			}
			
			//Price Tab
			itemMasterPage.clickPricingTab();
			
			
			if(defaultSellingPrice.length() !=0)
			{
				itemMasterPage.sendDefaultSellingPrize(defaultSellingPrice);
			}
			else
			{
				itemMasterPage.tabDefaultSellingPrize();
			}
			
			
			// Validation Starts  /////////////////////////////////////////////////////////////////////////////
			
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
			//Empty Description
			
			
			if(description.isEmpty()  )
			{
				
				try
				{
					emptyDescription =itemMasterPage.validationForEmptyDescription();
					if(emptyDescription)
					{
						logger.info("There is a proper validation msg for empty Description");
					}
					else
					{
						result = false;
						logger.info("There is NO proper validation msg for empty Description");
						Assert.assertTrue(false, "There is NO proper validation msg for empty Description");
					}
					
				}
				catch (Exception e) 
				{
					result = false;
					logger.info("There is an Exeception in  empty Description");
					Assert.assertTrue(false, "There is an Exeception in  empty Description");
				
				}
			}
			
			
			//Empty Category Code
			
			if(category.isEmpty() && itemMasterPage.isEmptyCategoryCode() == true )
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
			
					
			// SKU Uom
			
			if(SKUuom.isEmpty())
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
			
			//selling UOm
			if(sellingUom.isEmpty())
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
			
			
			 
			//Pricing Tab
			if(defaultSellingPrice.isEmpty())
			{
				
				try
				{
					// It should return $0
					logger.info("inside the Default Selling price validation-> Empty");
					
					defaultValueWebList = itemMasterPage.getDefaultSellingPriceDisplay();
					logger.info("Size is Default :"+defaultValueWebList.size());
					for(int i=0; i< defaultValueWebList.size(); i++)
					{
						logger.info("Hiiiiii "+defaultValueWebList.get(i).getText());
						defaultValueIs =defaultValueWebList.get(i).getText();
						//allDefaultList.add(defaultValueWebList.get(i).getText());
					}
					
					if(defaultValueIs.equals("$0")) 
					{
						Assert.assertTrue(true, "If No input , It will display 0 by default");
						logger.info("If No input , It will display $0 by default");
					}
					else
					{
						result =false;
						logger.info("It should display $0, if the user give empty input. But it does't showing.");
						Assert.assertTrue(false, "It should display $0, if the user give empty input. But it does't showing.");
						
					}
				}
				catch (Exception e) 
				{
					result = false;
					logger.info("There is an Exeception in  empty DefaultPricing");
					Assert.assertTrue(false, "There is an Exeception in  empty DefaultPricing");
				
				}
			}
			else
			{
				try
				{
				defaultValueWebList = itemMasterPage.getDefaultSellingPriceDisplay();
				logger.info("Size is Default :"+defaultValueWebList.size());
				for(int i=0; i< defaultValueWebList.size(); i++)
				{
					logger.info("Hiiiiii "+defaultValueWebList.get(i).getText());
					defaultValueIs =defaultValueWebList.get(i).getText();
					//allDefaultList.add(defaultValueWebList.get(i).getText());
				}
				
				if(defaultValueIs.equals("$"+defaultSellingPrice)) 
				{
					Assert.assertTrue(true, "If there is a input , It will display $\"+defaultSellingPrice+\" by default");
				}
				else
				{
					result =false;
					logger.info("It should display $defaultSellingPrice, if the user give empty input. But it does't showing.");
					Assert.assertTrue(false, "It should display $"+defaultSellingPrice+", if the user give empty input. But it does't showing.");
					
				}
			}
			catch (Exception e) 
			{
				result = false;
				logger.info("There is an Exeception in  NOT Empty DefaultPricing");
				Assert.assertTrue(false, "There is an Exeception in  NOT Empty  DefaultPricing");
			
			}	
			}
			
			// Max-Length Validation
			if((convertionAndValidation.numberOfIntegerPlaces(packingText) > 12 || convertionAndValidation.numberOfDecimalPlaces(packingText) >8 )||
			   (convertionAndValidation.numberOfIntegerPlaces(lengthText) > 12  || convertionAndValidation.numberOfDecimalPlaces(lengthText) >8 )||
			   (convertionAndValidation.numberOfIntegerPlaces(widthText) > 12   || convertionAndValidation.numberOfDecimalPlaces(widthText) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(heightText) > 12  || convertionAndValidation.numberOfDecimalPlaces(heightText) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(weightText) > 12  || convertionAndValidation.numberOfDecimalPlaces(weightText) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(noOfPoints) > 12  || convertionAndValidation.numberOfDecimalPlaces(noOfPoints) >8 ) ||   
			   (convertionAndValidation.numberOfIntegerPlaces(minStock_Branch) > 12 || convertionAndValidation.numberOfDecimalPlaces(minStock_Branch) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(maxStock_Branch) > 12 || convertionAndValidation.numberOfDecimalPlaces(maxStock_Branch) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(reOrderLevel_Branch) > 12 || convertionAndValidation.numberOfDecimalPlaces(reOrderLevel_Branch) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(leadTime_Branch) > 12 || convertionAndValidation.numberOfDecimalPlaces(leadTime_Branch) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(minOrderQty_Branch) > 12 || convertionAndValidation.numberOfDecimalPlaces(minOrderQty_Branch) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(maxOrderQty_Branch) > 12 || convertionAndValidation.numberOfDecimalPlaces(maxOrderQty_Branch) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(orderMultilpleOf_Branch) > 12 || convertionAndValidation.numberOfDecimalPlaces(orderMultilpleOf_Branch) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(reOrderQty_Branch) > 12 || convertionAndValidation.numberOfDecimalPlaces(reOrderQty_Branch) >8 ) ||
			   (convertionAndValidation.numberOfIntegerPlaces(MarkUp_minimumMarkUpText) > 12 || convertionAndValidation.numberOfDecimalPlaces(MarkUp_minimumMarkUpText) >8 )
			   ) 
			{
				
				try 
				{
					integer_decimalValidation = itemMasterPage.validationForMaxLength();
					if(integer_decimalValidation)
					{
						logger.info("There is a proper validation msg for Max Length values");
					}
					else
					{
						result = false;
						logger.info("There is No a proper validation msg for Max Length values");
						Assert.assertTrue(false, "There is NO proper validation msg for Negative values");
					}
				} 
				catch (Exception e)
				{
					result = false;
					logger.info("There is an Exeception in  Max Length validation");
					Assert.assertTrue(false, "There is an Exeception in  Max Length validation");
				}
				
			}
		
			// Loyalty Max-Length Validation
			
			if(noOfPoints.length() > 12)
			{
				try 
				{
					noOfPointsNegative = itemMasterPage.validationForLoyaltyMaxLength();
					if(noOfPointsNegative)
					{
						logger.info("There is a proper validation msg for Loyalty Max Length values");
					}
					else
					{
						result = false;
						logger.info("There is No a proper validation msg for Loyalty Max Length values");
						Assert.assertTrue(false, "There is NO proper validation msg for Loyalty Negative values");
					}
				} 
				catch (Exception e)
				{
					result = false;
					logger.info("There is an Exeception in Loyalty Max Length validation");
					Assert.assertTrue(false, "There is an Exeception in Loyalty Max Length validation");
				}
			}
			
			
			// Markup validation 
			
			if(MarkUp_minimumMarkUpType.isEmpty())
			{
				try {
					logger.info("Inside the Empty minimumMarkUpType Validation");
					emptyminimumMarkUpType = itemMasterPage.validationForEmptyminimumMarkUpType();
					
					if(emptyminimumMarkUpType) {
						logger.info("There is proper validation for empty minimumMarkUpType field");
					}
					else {
						logger.info("There is NO validation for empty minimumMarkUpType field");
						result = false;
						Assert.assertTrue(false,"There is NO validation for empty minimumMarkUpType field" );
					}
				} catch (Exception e) {
					result =false;
					e.printStackTrace();
					logger.info("There is a exception in Empty minimumMarkUpType");
					Assert.assertTrue(false);
				}
			}
			
			if(MarkUp_minimumMArkUpTypeEnforceable.isEmpty())
			{
				try {
					logger.info("Inside the Empty minimumMArkUpTypeEnforceable Validation");
					minimumMArkUpType_Enforceable = itemMasterPage.validationForEmptyminimumMarkUpType();
					
					if(minimumMArkUpType_Enforceable) {
						logger.info("There is proper validation for empty minimumMArkUpTypeEnforceable field");
					}
					else {
						logger.info("There is NO validation for empty minimumMArkUpTypeEnforceable field");
						result = false;
						Assert.assertTrue(false,"There is NO validation for empty minimumMArkUpTypeEnforceable field" );
					}
				} catch (Exception e) {
					result =false;
					e.printStackTrace();
					logger.info("There is a exception in Empty minimumMArkUpTypeEnforceable");
					Assert.assertTrue(false);
				}
			}
			
			
			  System.out.println("here we are..............."); // Negative Validation SHOULD be On Last 
			  if(packingText.isEmpty())packingText = "0";
			  if(lengthText.isEmpty())lengthText = "0";
			  if(widthText.isEmpty())widthText = "0";
			  if(heightText.isEmpty())heightText = "0";
			  if(weightText.isEmpty())weightText = "0"; 
			  if(noOfPoints.isEmpty())noOfPoints = "0";
			  if(minStock_Branch.isEmpty())minStock_Branch = "0";
			  if(maxStock_Branch.isEmpty())maxStock_Branch = "0";
			  if(reOrderLevel_Branch.isEmpty())reOrderLevel_Branch = "0";
			  if(leadTime_Branch.isEmpty())leadTime_Branch = "0";
			  if(minOrderQty_Branch.isEmpty())minOrderQty_Branch = "0";
			  if(maxOrderQty_Branch.isEmpty())maxOrderQty_Branch = "0";
			  if(orderMultilpleOf_Branch.isEmpty())orderMultilpleOf_Branch = "0";
			  if(reOrderQty_Branch.isEmpty())reOrderQty_Branch = "0";
			  if(defaultSellingPrice.isEmpty())defaultSellingPrice = "0";
			  if(MarkUp_minimumMarkUpText.isEmpty())MarkUp_minimumMarkUpText="0";
			  
			  if(packingText.charAt(0) == '-' || lengthText.charAt(0) == '-' ||
			  widthText.charAt(0) == '-' || heightText.charAt(0) == '-'||
			  weightText.charAt(0) == '-'|| noOfPoints.charAt(0) == '-' ||
			  
			  
			  minStock_Branch.charAt(0) == '-' || maxStock_Branch.charAt(0) == '-'||
			  reOrderLevel_Branch.charAt(0) == '-'|| leadTime_Branch.charAt(0) == '-'||
			  minOrderQty_Branch.charAt(0) == '-'|| maxOrderQty_Branch.charAt(0) == '-'||
			  orderMultilpleOf_Branch.charAt(0) == '-'|| reOrderQty_Branch.charAt(0) ==
			  '-'||
			  
			  MarkUp_minimumMarkUpText.charAt(0) == '-' ||
			  defaultSellingPrice.charAt(0) == '-'
			  
			  ) {
			  
			  try { 
				  NegativeValidation = itemMasterPage.validationForNegative();
			  if(NegativeValidation) 
			  {
			  logger.info("There is a proper validation msg for Negative values");
			  } 
			  else
			  {
			  result = false;
			  logger.info("There is No a proper validation msg for Negative values");
			  Assert.assertTrue(false,
			  "There is NO proper validation msg for Negative values");
			  } 
			  }
			  catch (Exception e) 
			  {
				  result = false;
			  logger.info("There is an Exeception in  Negative validation");
			  Assert.assertTrue(false, "There is an Exeception in  Negative validation"); 
			  }
			  
			  }
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			 
			  //Save Action..*************************************************************
			  
			  js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//button[@buttontype='SAVE']")) );
			  try 
			  {
				  postLoginPage.clickSave();
			 } 
			  catch (Exception e)
			  {
				  js.executeScript("arguments[0].click();", postLoginPage.clickSave_WebElement());
			  }
			  
			  logger.info("Save Button Clicked....");
			  if(emptyItemCode== false && maxLenItemCode== false && duplicateItemCode == false &&
					  emptyDescription == false && emptycategory == false &&
						emptySKUuom == false && emptySellingUom == false && integer_decimalValidation == false &&
						NegativeValidation == false && noOfPointsNegative == false &&
					    emptydefaultSellingPrice == false && emptyminimumMarkUpType == false &&
					   minimumMArkUpType_Enforceable == false)
			  {
				 logger.info("No Errors in the Item Master, So Please confirm Pop up should present");
				 if(postLoginPage.pleaseConfirm())
				 {
					 postLoginPage.clickTICK();
				 }
				 
				 
				 logger.info("Saved Successfully, going to add Barcode");
				 
				 
				 
				 //Unit of Measure
			/*	 try
				 {
					itemMasterPage.clickUnitOfMeasureTab();
				} 
				 catch (Exception e)
				 {
					
					 js.executeScript("arguments[0].click();", itemMasterPage.clickUnitOfMeasureTab_WebElement());
				}
				 itemMasterPage.clickEndDateCalendar();
				 itemMasterPage.clickTodaysDate();*/
				 
				 
				 
				 //BARCODE
				 
				 try
				 {
					itemMasterPage.clickBarcodeTab();
				 }
				 catch (Exception e) 
				 {
					 js.executeScript("arguments[0].click();", itemMasterPage.clickBarcodeTab_WebElement());
				 
				 }
				 itemMasterPage.sendBarcode(barcode_Barcode); 
				 logger.info("Barcode sent successfully");
				 
				 // validation added for Barcode
				 
				 if(barcode_Barcode.length() > 30)
				 {
					 try {
							logger.info("Inside the Max Length Barcode Validation");
							maxLenBarCode = itemMasterPage.validationForMaxLengthBarCode();
							
							if(maxLenBarCode) {
								logger.info("There is proper validation for Max: Length BarCode field");
							}
							else {
								logger.info("There is NO validation for Max: Length Barcode field");
								result = false;
								Assert.assertTrue(false,"There is NO validation for Max: Length BarCode field" );
							}
							
						} catch (Exception e) {

							result =false;
							e.printStackTrace();
							logger.info("There is a exception in Max: Lenth BarCode");
							Assert.assertTrue(false);
						}
					 
				 }
				
				 //SAVE Action
				if(maxLenBarCode == false)
				{
					 try 
					  {
						  postLoginPage.clickSaveExit();
					 } 
					  catch (Exception e)
					  {
						  js.executeScript("arguments[0].click();", postLoginPage.clickSaveExit_WebElement());
					  }
					  
					  logger.info("Save&Exit Button Clicked....");
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
					try 
					  {
						  postLoginPage.clickCancel();
					 } 
					  catch (Exception e)
					  {
						  js.executeScript("arguments[0].click();", postLoginPage.clickCancel_WebElement());
					  }
					  
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
				 
				 
				 
			  }
			  
			  // if any validations are present/ INLINE ERROR IN The Page
			  else
			  {
				  if (postLoginPage.errorMessage())
				  {
						logger.info("proper msg for save action(can not save)");
						postLoginPage.clickTICK();
						
						try 
						{
							postLoginPage.clickCancel();
						} catch (Exception e) {

						js.executeScript("arguments[0].click();", postLoginPage.clickCancel_WebElement());
						}
						
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
			  }
			
		} catch (Exception e) {
			
			result = false;
			e.printStackTrace();
			Assert.assertTrue(false, "Exception is happened inside ItemCodeValidation() ");
		
		}finally {
			
			System.out.println("inside the finally..........");
			finalResultMap.put(testCaseNumber, result);
			if(result == false)
			{
				try 
				{
					postLoginPage.clickCancel();
				} catch (Exception e) {

				js.executeScript("arguments[0].click();", postLoginPage.clickCancel_WebElement());
				}
				
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
			result = true;
			System.out.println("last the finally..........");
			Thread.sleep(10000);
//			if(  iterationCount == numberOfRecords)
//			{
//				xlutils.printExcel(finalResultMap, outPutExcellLocation, writeSheetName);
//				postLoginPage.clickHomeButton();
//				postLoginPage.clickTICK();
//				postLoginPage.clickInventory();
//				flag=1;
//		   }
			
			// If save successfully then there will be an error
		}
		
	}
	
	
	
	@DataProvider(name = "itemMaster")
	String[][] getItemCode() throws IOException {

		String path = readConfig.getInventoryMaintenanceInputPath();
		int rowCount = XLUtils.getRowCount(path, readSheetName_ItemMaster);
		int colCount = XLUtils.getCellCount(path, readSheetName_ItemMaster, 0);
		numberOfRecords = rowCount ;
		System.out.println("Row count " + rowCount);
		System.out.println("col count " + colCount);
		String userdata[][] = new String[rowCount][colCount];
	

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < colCount; j++) {

				userdata[i - 1][j] = XLUtils.getCellData(path, readSheetName_ItemMaster, i, j).trim().toString();
				
			}
			
		}

		return userdata;
	}

	public void lookUpSearch(String SearchBy, String value) throws InterruptedException {
		
		
		if(SearchBy.equalsIgnoreCase("code")) 
		{
			
			logger.info("Search by code ");
			itemMasterPage.clickDropDown();
			logger.info("........... clicked dropdown");
			itemMasterPage.clickCode();
			Thread.sleep(2000);
			itemMasterPage.clickSearchButton();
			Thread.sleep(3000);
			String codeValueIs = itemMasterPage.searchResultCode();
			System.out.println("codeValueIs.........." + codeValueIs);

			if (codeValueIs.equalsIgnoreCase(value))
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
			
			if (itemMasterPage.searchResultDescription().equalsIgnoreCase(value)) 
			{
				logger.info("found search result by description");
				try {
					itemMasterPage.clickOnSearchResult();
				} catch (Exception e) {
					js.executeAsyncScript("arguments[0].click();",itemMasterPage.clickOnSearchResult_WebElement());

				}

			} else {
				itemMasterPage.closeIcon();
				result = false;
				Assert.assertTrue(false, "Search Description is not found");
			}
		}
		
	}

}
