package com.extraPOSTest.pageObjects.Approvals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ApprovalRoleCodePage {

	
	WebDriver driver;
	
	@FindBy(xpath = "//input[@textlabel='Approval Role Code *']")
	private WebElement approvaleRoleCode;
	
	@FindBy(xpath = "//input[@textlabel='Approval Role Description']")
	private WebElement approvalDescription;
	
	public ApprovalRoleCodePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	
	public List<WebElement> allApprovalRoleCodeList() {
		
	return	driver.findElements(By.xpath("//table[@class='p-datatable-scrollable-body-table']/tbody/tr/td[1]"));
	}
	

	public void sendApprovaleRoleCode(String code) {
		
		approvaleRoleCode.sendKeys(code);
	}
	
	public void sendApprovalDescription(String description) {
		
		approvalDescription.sendKeys(description);
	}
	
	
}
