package com.extraPOSTest.pageObjects.inventory.maintenance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItemMasterPage {

	WebDriver driver;

	@FindBy(xpath = "//button[@buttontype='ADD']")
	private WebElement addButton;

	@FindBy(xpath = "//input[@placeholder='Item Code']")
	private WebElement itemCode;

	@FindBy(xpath = "//input[@label='Description*']")
	private WebElement description;

	@FindBy(xpath = "//input[@label='Status']")
	private WebElement status;

	// Item Classification 

	@FindBy(xpath = "//*[@id=\"selectedCategory_AH_BTN\"]")
	private WebElement categoryLookUp;

	@FindBy(xpath = "//*[@id=\"selectedSubCategory_AH_BTN\"]")
	private WebElement subCategoryLookUp;

	@FindBy(xpath = "//*[@id=\"selectedBrand_AH_BTN\"]")
	private WebElement brandLookUp;

	@FindBy(xpath = "//*[@id=\"selectedItemLine_AH_BTN\"]")
	private WebElement lineLookUp;

	@FindBy(xpath = "//*[@id=\"selectedItemType_AH_BTN\"]")
	private WebElement typeLookUp;

	@FindBy(xpath = "//*[@id=\"selectedItemClass_AH_BTN\"]")
	private WebElement classLookUp;

	// LOOKUP Actions 

	@FindBy(xpath = "//div[@class='p-dropdown-trigger']")
	private WebElement dropDownArrow_LookUP;
	@FindBy(xpath = "//li[@aria-label='Code']")
	private WebElement selectCODE;
	@FindBy(xpath = "//li[@aria-label='Description']")
	private WebElement selectDescription;
	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement searchLookUp;
	@FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td")
	private WebElement clickFirstRowInTheLookUp_SearchResult;
	@FindBy(xpath = "//button[@buttontype='SEARCH']")
	private WebElement SEARCH_Button;
	@FindBy(xpath = "//button[@buttontype='CLOSE_NORMAL']")
	private WebElement closeIcon;
	
	@FindBy(xpath = "//input[@id='selectedCategory']")
	private WebElement categoryTextBox;
	
	@FindBy(xpath = "//input[@id='selectedSubCategory']")
	private WebElement subCategoryTextBox;
	
	@FindBy(xpath = "//input[@id='selectedBrand']")
	private WebElement brandTextBox;
	@FindBy(xpath = "//input[@id='selectedItemLine']")
	private WebElement lineTextBox;
	@FindBy(xpath = "//input[@id='selectedItemType']")
	private WebElement typeTextBox;
	@FindBy(xpath = "//input[@id='selectedItemClass']")
	private WebElement classTextBox;
	
	
	@FindBy(xpath = "//input[@id='selectedSkuUom']")
	private WebElement SKUTextBox;
	
	@FindBy(xpath = "//input[@id='selectedSellingUom']")
	private WebElement SellingTextBox;

	@FindBy(xpath = "//input[@id='selectedPurchaseUom']")
	private WebElement purchaseTextBox;
	
	@FindBy(xpath = "//input[@id='selectedPackingUom']")
	private WebElement packingUomTextBox;
	
	@FindBy(xpath = "//input[@id='selectedLengthUom']")
	private WebElement lengthUomTextBox;
	
	@FindBy(xpath = "//input[@id='selectedWidthUom']")
	private WebElement widthUomTextBox;
	
	@FindBy(xpath = "//input[@id='selectedHeightUom']")
	private WebElement heightUomTextBox;
	
	@FindBy(xpath = "//input[@id='selectedWeightUom']")
	private WebElement weightUomTextBox;
	
	// Attributes 

	@FindBy(xpath = "//*[@id=\"selectedSkuUom_AH_BTN\"]/div")
	private WebElement SKU_LookUp;

	@FindBy(xpath = "//*[@id=\"selectedSellingUom_AH_BTN\"]/div")
	private WebElement selling_LookUp;

	@FindBy(id="selectedPurchaseUom_AH_BTN")
	private WebElement purchaseUOM_LookUp;
	
	

	@FindBy(xpath = "//input[@label='Packing']")
	private WebElement packingTextBox;
	

	@FindBy(xpath = "//*[@id=\"selectedPackingUom_AH_BTN\"]")
	private WebElement packing_LookUp;

	@FindBy(xpath = "//input[@label='Length']")
	private WebElement lengthTextBox;

	@FindBy(xpath = "//*[@id=\"selectedLengthUom_AH_BTN\"]")
	private WebElement length_LookUp;

	@FindBy(xpath = "//input[@label='Width']")
	private WebElement widthTextBox;

	@FindBy(xpath = "//*[@id=\"selectedWidthUom_AH_BTN\"]")
	private WebElement width_LookUp;

	@FindBy(xpath = "//input[@label='Height']")
	private WebElement heightTextBox;

	@FindBy(xpath = "//*[@id=\"selectedHeightUom_AH_BTN\"]")
	private WebElement height_LookUp;

	@FindBy(xpath = "//input[@label='Weight']")
	private WebElement weightTextBox;

	@FindBy(xpath = "//*[@id=\"selectedWeightUom_AH_BTN\"]")
	private WebElement weight_LookUp;

	// Loyalty 

	@FindBy(xpath = "//div[@class='shadow-card-shadow rounded-5px col-span-4 bg-white mb-11px']/div[1]/div/div[1]/div/div/div/div/div[2]")
	private WebElement LoyaltyCheckBox;
	@FindBy(xpath = "//input[@label='No. of Points']")
	private WebElement noOFPointsTextBox;
	@FindBy(xpath = "//div[@class='shadow-card-shadow rounded-5px col-span-4 bg-white mb-11px']/div[2]/div/div[1]/div/div/div[2]")
	private WebElement ObsoleteCheckBox;
	@FindBy(xpath = "//div[@class='shadow-card-shadow rounded-5px col-span-4 bg-white mb-11px']/div[2]/div/div[2]/div/div/div[2]")
	private WebElement nonSellableCheckBox;
	@FindBy(xpath = "//div[@class='shadow-card-shadow rounded-5px col-span-4 bg-white mb-11px']/div[2]/div/div[3]/div/div/div[2]")
	private WebElement voidCheckBox;
	@FindBy(xpath = "//div[@class='shadow-card-shadow rounded-5px col-span-4 bg-white mb-11px']/div[3]/div/div[1]/div/div/div[2]")
	private WebElement Print_PickingSlipsCheckBox;
	@FindBy(xpath = "//div[@class='shadow-card-shadow rounded-5px col-span-4 bg-white mb-11px']/div[3]/div/div[2]/div/div/div[2]")
	private WebElement Allow_FractionsCheckBox;

	// from Default Vender 

	@FindBy(xpath = "//*[@id=\"selectedVendor_AH_BTN\"]")
	private WebElement defaultVenderLookUp;
	@FindBy(xpath = "//*[@id='selectedPurchaseGroup_AH_BTN']")
	private WebElement purchaseGroupLookUp;
	@FindBy(xpath = "//*[@id=\"selectedInBoundTaxCode_AH_BTN\"]")
	private WebElement taxCode_InBoundLookUp;
	@FindBy(xpath = "//*[@id=\"selectedOutBoundTaxCode_AH_BTN\"]")
	private WebElement taxCode_OutBoundLookUp;
	@FindBy(xpath = "//div[@class='bg-surface-color -m-11px']/div[4]/div/div/div[5]/div[2]/div[3]")
	private WebElement productTypeDropDown;

	@FindBy(xpath = "//li[@aria-label='Kit Item']")
	private WebElement select_KitItem;

	@FindBy(xpath = "//li[@aria-label='Purchase Item']")
	private WebElement select_PurchaseItem;

	@FindBy(xpath = "//div[@class='bg-surface-color -m-11px']/div[4]/div/div/div[6]/div[2]/div[3]")
	private WebElement costingMethodDropDown;

	@FindBy(xpath = "//li[@aria-label='FIFO']")
	private WebElement select_FIFO;

	/*                                          */
	@FindBy(xpath = "//*[@id=\"selectedBranchFilter_AH_BTN\"]/div/img")
	private WebElement branchLookUp;

	@FindBy(xpath = "//*[@id=\"selectedUomFilter_AH_BTN\"]/div/img")
	private WebElement uomLookUp;

	@FindBy(xpath = "//div[@class='bg-surface-color -m-11px']/div[5]/div/div/div[3]/div/div[3]")
	private WebElement statusLookUp;

	@FindBy(xpath = "/html/body/div[2]/div/ul/li[1]")
	private WebElement statusActive;

	@FindBy(xpath = "/html/body/div[2]/div/ul/li[2]")
	private WebElement statusInActive;

	@FindBy(xpath = "/html/body/div[2]/div/ul/li[3]")
	private WebElement statusBoth;
	
	@FindBy(id = "selectedVendor")
	private WebElement defaultVendorTextBox;
	
	@FindBy(id="selectedPurchaseGroup")
	private WebElement purchaseGroupTextBox;
	
	@FindBy(id="selectedInBoundTaxCode")
	private WebElement taxInboundTextBox;
	
	@FindBy(id ="selectedOutBoundTaxCode")
	private WebElement taxOutboundTextBox;
	// Start date and End Date will give later

	// Tabs are here 
	@FindBy(xpath = "//ul[@class='p-tabview-nav']/li[1]/a")
	private WebElement TabBranches;
	
	
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[5]/div/input")
	     private WebElement minStock_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[6]/div/input")
	     private WebElement maxStock_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[7]/div/input")
	     private WebElement reOrderLevel_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[8]/div/input")
	     private WebElement leadTime_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[9]/div/input")
	     private WebElement minOrderQty_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[10]/div/input")
	     private WebElement maxOrderQty_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[11]/div/input")
	     private WebElement orderMultipleOf_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[12]/div/input")
	     private WebElement reOrderQty_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[13]/div/div[1]/div[1]/div")
	     private WebElement status_Branches;
	     @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[14]/div")
	     private WebElement delete_Branches;
	     
	   
	     
	   
	     

	@FindBy(xpath = "//ul[@class='p-tabview-nav']/li[2]/a")
	private WebElement TabMarkUp;
	
	      @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr/td[2]/div/div/div[3]")
	      private WebElement minimumMarkUpType_DropDown;
	      @FindBy(xpath = "//div[@class='p-dropdown-items-wrapper']/ul/li[1]")
	      private WebElement minimumMarkUpType_Amount;
	      @FindBy(xpath = "//div[@class='p-dropdown-items-wrapper']/ul/li[2]")
	      private WebElement minimumMarkUpType_Percentage;
	      
	      @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr/td[2]/div/div[1]")
	      private WebElement minimumMarkUpType_TextBox;
	      
	      @FindBy(xpath = "//input[@placeholder='Minimum Mark-Up ']")
	      private WebElement minimumMarkUp_Text;

	      @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr/td[4]/div/div/div[3]")
	      private WebElement minimumMarkUpTypeEnforceable_DropDown;
	      
	      @FindBy(xpath = "//div[@class='p-dropdown-items-wrapper']/ul/li[1]")
	      private WebElement minimumMarkUpTypeEnforceable_Enforce;
	      @FindBy(xpath = "//div[@class='p-dropdown-items-wrapper']/ul/li[2]")
	      private WebElement minimumMarkUpTypeEnforceable_Ignore;
	      @FindBy(xpath = "//div[@class='p-dropdown-items-wrapper']/ul/li[2]")
	      private WebElement minimumMarkUpTypeEnforceable_Warning;
	      
	      @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr/td[4]/div/div[1]")
	      private WebElement minimumMarkUpTypeEnforceable_TextBox;
	      
	@FindBy(xpath = "//ul[@class='p-tabview-nav']/li[3]/a")
	private WebElement TabUnitOfMeasure;
	
	
		  @FindBy(xpath = "//tbody[@class='p-datatable-tbody']/tr[1]/td[5]/div/div/div/button")
		  private WebElement clickEndDateCalendar;
	      @FindBy(xpath = "//table[@class='p-datepicker-calendar'] //span[@tabindex='0']")
	      private WebElement todayDate;
	      
	      

	@FindBy(xpath = "//ul[@class='p-tabview-nav']/li[4]/a")
	private WebElement TabBarCode;
	
	      @FindBy(xpath = "//input[@placeholder='Barcode']")
	      private WebElement barcodeTextBox;
	

	@FindBy(xpath = "//ul[@class='p-tabview-nav']/li[5]/a")
	private WebElement TabPricing;

	@FindBy(xpath = "//ul[@class='p-tabview-nav']/li[6]/a")
	private WebElement TabOnOrderDetails;

	@FindBy(xpath = "//ul[@class='p-tabview-nav']/li[7]/a")
	private WebElement TabCustomAttribute;

	
	//Inside Tab 
	@FindBy(xpath = "//input[@label='Default Selling Price*']")
	private WebElement defaultSellingPrice;
	
	
	
	// All the Action Buttons
	
	
	
	
	public ItemMasterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public List<WebElement> allItemCodeList() {
		// TODO Auto-generated method stub
		return driver.findElements(By.xpath("//tbody[@class='p-datatable-tbody']/tr/td[1]"));
	}

	public void clickAddButton() {

		addButton.click();
	}

	public WebElement clickAddButton_WebElement() {

		return addButton;
	}

	public void SendItemCode(String text) throws InterruptedException {

		Thread.sleep(1000);
		itemCode.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
		Thread.sleep(1000);
		itemCode.sendKeys(text);
	}

	public WebElement  scrollToITMCode() {
		return itemCode;
	}
	
	public void SendItemDescription(String text) {

		description.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
		description.sendKeys(text);
	}

	
public boolean validationForEmptyItemCode() {

		
		boolean textFound = false;
		try {
			driver.findElement(By.xpath("//*[text()='This is a mandatory field.']"));
			textFound = true;
		} catch (Exception e) {
			textFound = false;
		}
		return textFound;
		
	}

public boolean validationForEmptyminimumMarkUpType() {

	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//*[text()='This is a mandatory field.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;
	
}
public boolean validationForMaxLengthItemCode() {

	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='Max length 30 chars ']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;
	
}

public boolean validationForMaxLengthBarCode() {

	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='Max length 30 chars ']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;
	
}
public boolean validationForDuplicateItemCode() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='The  item  code already exits.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}

public void clearCategory() {
	driver.findElement(By.id("selectedCategory")).sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
}
public void clearSKUuom() {
	driver.findElement(By.id("selectedSkuUom")).sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
}

public void clearSellinguom() {
	driver.findElement(By.id("selectedSellingUom")).sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
}

public void clearPurchasinguom() {
	driver.findElement(By.id("selectedPurchaseUom")).sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
}
public void clearDefaultVendor() {
	defaultVendorTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
}

public void clearPurchaseGroup() {
	purchaseGroupTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
}
public void clearTaxInbound() {
	taxInboundTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
}
public void clearTaxOutbound() {
	taxOutboundTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
}
public void clickCategoryLookUp() {
	
	categoryLookUp.click();
}
public void clickSubCategoryLookUp() {
	
	subCategoryLookUp.click();
}

public void clickBrandLookUp() {
	
	brandLookUp.click();
}

public void clickLineLookUp() {
	
	lineLookUp.click();
}



public void clickClassLookUp() {
	
	classLookUp.click();
}

public void clickTypeLookUp() {
	
	typeLookUp.click();
}


public void clickSKULookUp() {
	
	SKU_LookUp.click();
}

public void clickSellingLookUp() {
	
	selling_LookUp.click();
}
public void clickPurchaseUomLookUp() {
	
	purchaseUOM_LookUp.click();
}



public void clickPackingLookUp() {
	
	packing_LookUp.click();
}

public void clickLengthLookUp() {
	
	length_LookUp.click();
}
public void clickWidthLookUp() {
	
	width_LookUp.click();
}
public void clickHeightLookUp() {
	
	height_LookUp.click();
}
public void clickWeightLookUp() {
	
	weight_LookUp.click();
}
public void sendSearchLookUp(String text) throws InterruptedException {

	Thread.sleep(1000);
	searchLookUp.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
	Thread.sleep(1000);
	searchLookUp.sendKeys(text);
}

public void clickDefaultVendorLookUp() {
	
	defaultVenderLookUp.click();
}
public WebElement sendSearchLookUp_WebElement() {
	return searchLookUp;
}
public void clickSearchButton() {
	SEARCH_Button.click();
}
public String searchResultCode() {

	return driver.findElement(By.xpath("//table[@role='grid']/tbody/tr/td[1]")).getText();
}
public void clickOnSearchResult() {

	driver.findElement(By.xpath("//div[@class='p-datatable-wrapper']/table/tbody/tr/td[1]")).click();
	//clickResult.click();
}

public WebElement clickOnSearchResult_WebElement() {

	return	driver.findElement(By.xpath("//div[@class='p-datatable-wrapper']/table/tbody/tr/td[1]"));
		//clickResult.click();
	}
public void closeIcon() {
	closeIcon.click();
}

public void clickDropDown() {
	dropDownArrow_LookUP.click();

}
public void clickDescription() {

	selectDescription.click();
}

public void clickCode() {
	selectCODE.click();
}
public String searchResultDescription() {

	return driver.findElement(By.xpath("//table[@role='grid']/tbody/tr/td[2]")).getText();
}

public void categoryTextBox() {
	
	categoryTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public void subCategoryTextBox() {
	
	subCategoryTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}

public void brandTextBox() {
	
	brandTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}

public void lineTextBox() {
	
	lineTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}

public void typeTextBox() {
	
	typeTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}


public void classTextBox() {
	
	classTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}


public void SKUTextBox() {
	
	SKUTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public void purchaseTextBox() {
	
	purchaseTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}

public void SellingTextBox() {
	
	SellingTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}

public void packingUomTextBox() {
	
	packingUomTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public void lengthUomTextBox() {
	
	lengthUomTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public void widthUomTextBox() {
	
	widthUomTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public void heightUomTextBox() {
	
	heightUomTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public void weightUomTextBox() {
	
	weightUomTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public void defaultVendorTextBox() {
	
	defaultVendorTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public void purchaseGroupTextBox() {
	
	purchaseGroupTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}

public void taxInboundTextBox() {
	
	taxInboundTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}

public void taxOutboundTextBox() {
	
	taxOutboundTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE,Keys.TAB, Keys.TAB);
}
public boolean validationForEmptyCategoryCode() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='The field must be a valid selected value.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}
public boolean validationForEmptyDescription() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='This is a mandatory field.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}

public boolean isEmptyCategoryCode() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//input[@id='selectedCategory']")).getAttribute("value").isEmpty();
System.out.println("Hi ---- >"+driver.findElement(By.xpath("//input[@id='selectedCategory']")).getAttribute("value").isEmpty());
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}

public boolean validationForEmptySKUCode() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='The field must be a valid selected value.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}

public boolean validationForEmptySellingCode() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='The field must be a valid selected value.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}

public boolean validationForEmptyDefaultSellingPrice() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='The default selling price field is required.']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}
public boolean validationForNegative() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='Negative values are not allowed']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}
public boolean validationForMaxLength() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='Max 12 digits and 8 decimals allowed']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}
public boolean validationForLoyaltyMaxLength() {
	
	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[text()='Max 12 digits allowed']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;

}

public void clickSKULookUP() {
	SKU_LookUp.click();
}
public void clickTaxInboundLookUp() {
	taxCode_InBoundLookUp.click();
}
public void clickTaxOutbound() {
	taxCode_OutBoundLookUp.click();
}
public void clickProductTypeDropDown() {
	productTypeDropDown.click();
}

public void clickKitItem() {
	select_KitItem.click();
}

public void clickPurchaseItem() {
	select_PurchaseItem.click();
}

public void clickCostingMethodDropDown() {
	costingMethodDropDown.click();
}

public void clickFIFO() {
	select_FIFO.click();
}
public void clickBranchTab() {
	
	TabBranches.click();
}
public void clickMarkUpTab() {
	
	TabMarkUp.click();
}

public void clickMinimumMArkUpTypeDropDown_MarkUp () {
	minimumMarkUpType_DropDown.click();
}
 public void clickMinimumMArkUpType_Amount_MarkUp() {
	
	 minimumMarkUpType_Amount.click();
}
 public void clickMinimumMArkUpType_Percentage_MarkUp() {
	minimumMarkUpType_Percentage.click();
}
 
 public void minimumMarkUpTypeTextBox() {
	minimumMarkUpType_TextBox.sendKeys(Keys.TAB,Keys.TAB);
}
 public void minimumMarkUpTextBox(String text) {
	 minimumMarkUp_Text.sendKeys(text);
	}
 public void clickMinimumMArkUpType_Enforceable_DropDown_MarkUp () {
	 minimumMarkUpTypeEnforceable_DropDown.click();
	}
	 
 public void clickMinimumMarkUpTypeEnforceable_Enforce() {
	 minimumMarkUpTypeEnforceable_Enforce.click();
}
 
 public void clickMinimumMarkUpTypeEnforceable_Ignore() {
	 minimumMarkUpTypeEnforceable_Ignore.click();
}
 public void clickMinimumMarkUpTypeEnforceable_Warning() {
	 minimumMarkUpTypeEnforceable_Warning.click();
}
 
 public void minimumMarkUpTypeEnforceable_TextBox() {
	 minimumMarkUpTypeEnforceable_TextBox.sendKeys(Keys.TAB,Keys.TAB);
}
public void clickUnitOfMeasureTab() {
	
	TabUnitOfMeasure.click();
}
public WebElement clickUnitOfMeasureTab_WebElement()  {
	
 return TabUnitOfMeasure;
}
public void clickBarcodeTab() {
	
	TabBarCode.click();
}

public WebElement clickBarcodeTab_WebElement() {
	
	return TabBarCode;
}


public void clickPricingTab() {
	
	TabPricing.click();
}

public void clickOrderDetailsTab() {
	
	TabOnOrderDetails.click();
}
public void clickCustomAttributesTab() {
	
	TabCustomAttribute.click();
}


public void sendDefaultSellingPrize(String defaultSellingPrice2) throws InterruptedException {
	
	defaultSellingPrice.sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.BACK_SPACE);
	Thread.sleep(1000);
	defaultSellingPrice.sendKeys(defaultSellingPrice2);
	
}
public void tabDefaultSellingPrize()
{
	defaultSellingPrice.sendKeys(Keys.TAB);
}
public String sellingUomTextBoxValue() {
	return SellingTextBox.getAttribute("value");
}

public String purchasingUomTextBoxValue() {
	return purchaseTextBox.getAttribute("value");
}

public String packingUomTextBoxValue() {
	return packingUomTextBox.getAttribute("value");
}
public void sendPacking(String text) {
	packingTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
	packingTextBox.sendKeys(text);
}

public void clearPackingTextBox() {
	packingTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
}
public void sendLength(String text) {
	lengthTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
	lengthTextBox.sendKeys(text);
}

public void clearLengthTextBox() {
	lengthTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
}
public void sendWidth(String text) {
	widthTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
	widthTextBox.sendKeys(text);
}
public void clearWidthTextBox() {
	widthTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
}

public void sendHeight(String text) {
	heightTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
	heightTextBox.sendKeys(text);
}

public void clearHeightTextBox() {
	heightTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
}

public void sendWeight(String text) {
	weightTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
	weightTextBox.sendKeys(text);
}

public void clearWeightTextBox() {
	weightTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
}


public String lengthUomTextBoxValue() {
	return lengthUomTextBox.getAttribute("value");
}
public String widthUomTextBoxValue() {
	return widthUomTextBox.getAttribute("value");
}
public String heightUomTextBoxValue() {
	return heightUomTextBox.getAttribute("value");
}
public String weightUomTextBoxValue() {
	return weightUomTextBox.getAttribute("value");
}

public void clickLoyalty() {
	LoyaltyCheckBox.click();
}
public void sendNoOfPoints(String text) {
	noOFPointsTextBox.sendKeys(text);
}

public void clickObsolete() {
	ObsoleteCheckBox.click();
}
public void clickNonSellable() {
	nonSellableCheckBox.click();
}
public void clickVoid() {
	voidCheckBox.click();
}
public void clickPrintPickingSlips() {
	Print_PickingSlipsCheckBox.click();
}
public void clickAllowFractions() {
	Allow_FractionsCheckBox.click();
}


public void clickPurchaseGroupLookUp() {
	
	purchaseGroupLookUp.click();
}
public WebElement clickPurchaseGroupLookUp_WebElement() {
	return purchaseGroupLookUp;
}

public List<WebElement> getDefaultSellingPriceDisplay() {
	return  driver.findElements(By.xpath("//div[@class='py-11px']/div/div[4] //div[@class='text-primary-blue text-25px font-medium']"));
	
}

public boolean isCorrectDefaultSellingPrice(String num) {

	boolean textFound = false;
	try {
		driver.findElement(By.xpath("//div[@class='py-11px']/div/div[4] //*[text()='"+num+"']"));
		textFound = true;
	} catch (Exception e) {
		textFound = false;
	}
	return textFound;
}

public void sendBranch_MinimumStock(String text) {
	minStock_Branches.sendKeys(text);
}
public void sendBranch_MaximumStock(String text) {
	maxStock_Branches.sendKeys(text);
}
public void sendBranch_ReOrderLevel(String text) {
	reOrderLevel_Branches.sendKeys(text);
}
public void sendBranch_LeadTime(String text) {
	leadTime_Branches.sendKeys(text);
}
public void sendBranch_MinOrderQty(String text) {
	minOrderQty_Branches.sendKeys(text);
}
public void sendBranch_MaxOrderQty(String text) {
	maxOrderQty_Branches.sendKeys(text);
}

public void sendBranch_OrderMultipleOf(String text) {
	orderMultipleOf_Branches.sendKeys(text);
}
public void sendBranch_ReOrderQty(String text) {
	reOrderQty_Branches.sendKeys(text);
}
public void clickBranch_Status() {
	status_Branches.click();
}
public WebElement clickBranch_Status_WebElement() {
 return	status_Branches;
}
public void clickBranch_Delete() {
	delete_Branches.click();
}
public void sendBarcode(String barcode) {
	barcodeTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
	barcodeTextBox.sendKeys(barcode);
}

public void clickTodaysDate() {
	todayDate.click();
}
public void clickEndDateCalendar() {
	clickEndDateCalendar.click();
}
}
