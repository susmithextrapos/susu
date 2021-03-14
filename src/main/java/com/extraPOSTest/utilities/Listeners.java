package com.extraPOSTest.utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.lang.model.element.Element;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.extraPOSTest.pageObjects.inventory.maintenance.CategoryPage;
import com.extraPOSTest.resources.BaseClass;



public class Listeners extends BaseClass implements ITestListener {

	
	ExtentTest test;
	ExtentReports extent=getReportObject();
	ThreadLocal<ExtentTest > extentTest = new ThreadLocal<>();
	

	@Override
	public void onTestStart(ITestResult result) {
	
		test = extent.createTest(result.getMethod().getMethodName());
	    extentTest.set(test);	 
	}

	@Override
	public void onTestSuccess(ITestResult result) {		
		//extentTest.get().log(Status.PASS, "Test Passed");
		extentTest.get().pass(MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
	}

	@Override
	public void onTestFailure(ITestResult result) {
	
	String testMethodName = result.getMethod().getMethodName();
	WebDriver driver = null;
	
	try {
		driver =(WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
	} catch (Exception e) {
		e.printStackTrace();
	}
	try {
		extentTest.get().addScreenCaptureFromPath(getScreenShotPath(testMethodName, driver), result.getMethod().getMethodName());
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	extentTest.get().fail(MarkupHelper.createLabel(result.getThrowable().getMessage(), ExtentColor.RED));
	//test.fail(MarkupHelper.createLabel(result.getThrowable().getMessage(), ExtentColor.RED));
	
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		extentTest.get().log(Status.SKIP, "Test Skipped");
	
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {

	extent.flush();
	String repName = "Test-Report.html";
	String path = System.getProperty("user.dir") + "\\reports\\"+repName ;
	try {
		Desktop.getDesktop().browse(new File(path).toURI());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

}
