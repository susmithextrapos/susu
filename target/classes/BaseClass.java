package com.extraPOSTest.resources;

import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.extraPOSTest.pageObjects.LoginPage;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.internal.org.jline.utils.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.mail.*;

public class BaseClass {

	public static ExtentReports extent;
	ReadConfig readConfig = new ReadConfig();

	public String baseURL = readConfig.getApplicationURL();
	public String userName = readConfig.getUsername();
	public String password = readConfig.getPassword();
	public static JavascriptExecutor js;
	public static WebDriver driver;
	public Actions builder;
	public WebDriverWait wait;
	static String pageLoadStatus = null;
	public static Logger logger;

	
	
	public WebDriver init(String browser) {

		 logger =LogManager.getLogger(BaseClass.class.getName());

		if (browser.equals("chrome")) {
		System.setProperty("webdriver.chrome.driver", readConfig.getChromePath());
			//WebDriverManager.chromedriver().setup();
			
			//System.out.println("chrome path "+readConfig.getChromePath());
			driver = new ChromeDriver();
			logger.info("chrome driver instantiated succefully ");
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", readConfig.getFirefoxPath());
			driver = new FirefoxDriver();
			logger.info("firefox driver instantiated succefully ");
		}

		driver.get(baseURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		wait= new WebDriverWait(driver, 8);
		js=(JavascriptExecutor) driver;
		builder = new Actions(driver);
		logIn();
		return driver;
	}

	

	
private void logIn() {

	
	logger.info("driver has been initialized in userManagement class");

	WebDriverWait wait = new WebDriverWait(driver, 20);
	LoginPage loginPage = new LoginPage(driver);
	loginPage.setUserName(userName);
	loginPage.setPassword(password);
	loginPage.clickLogInButton();

	String homPageValidationText = loginPage.homePageSuccessValidationText(); // in the homepage there should
																				// contain the text "Welcome to
																				// ExtraPOS!", this text is here
																				// validating as Success.
	boolean homePage = driver.getPageSource().contains(homPageValidationText);

	if (homePage) {

		logger.info("Login Successfully");
		Assert.assertTrue(true);
	} else {

		logger.warn("login failed");
		try {
			getScreenShotPath("loginTest", driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.warn("Screenshot Taken");
		Assert.assertTrue(false);
	}

	
}

// Create Report File

public String reportGeneration(String excellLocation, String sheetName) {
	
	String location ="";
	try 
	{
		XLUtils xlUtils = new XLUtils();
		location =	XLUtils.createOutPutExcel(excellLocation, sheetName);
	} 
	catch (Exception e) 
	{
e.printStackTrace();
	
	}
	return location;
}
	


	public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
		logger.info("inside the screenshot method for testcaseName is :" + testCaseName);
		
		TakesScreenshot tc = (TakesScreenshot) driver;
		File source = tc.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		String destinationFile = System.getProperty("user.dir") + "\\Screenshots\\" + testCaseName+"-"+timeStamp+ ".png";
		logger.info("Screen shot path " + destinationFile);
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}

	

	public static ExtentReports getReportObject() {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		String repName = "Test-Report.html";
		
		String path = System.getProperty("user.dir") + "\\reports\\"+repName ;
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setTheme(Theme.DARK);
		reporter.config().setReportName("ExtraPOS Automation Result");
		reporter.config().setDocumentTitle("ExtraPOS Test Results");

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Susmith Surendran");
		return extent;
	}
	
	public boolean invisibiltyOfElements(WebDriver driver,int time ,By element) {
		WebDriverWait wait = new WebDriverWait(driver,time );
		
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
	}
	
	
	
	
	
	
	public void attachEmailReport(String attachmentPath, String formName) throws EmailException, AddressException {
		
		
		//authentication info
		final String username = "susmith.surendran@acsesolutions.com";
		final String password = "Welcome@123456";
		String fromEmail = "susmith.surendran@acsesolutions.com";
		String toEmail = "mailtosusmith@gmail.com";

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.acsesolutions.com");
		properties.put("mail.smtp.port", "587");

		
			
		
		Address[] cc = new Address[] {new InternetAddress("mailtosusmith@gmail.com"),
				new InternetAddress("s.susmith08@gmail.com"), 
				new InternetAddress("abinandh.krishnan@acsesolutions.com")};
		
		
		
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		
		//Start our mail message
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipients(Message.RecipientType.TO, cc);
			msg.setSubject("Automation Test Results");

			Multipart emailContent = new MimeMultipart();

			//Text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(formName);

			//Attachment body part.
			MimeBodyPart pdfAttachment = new MimeBodyPart();
			pdfAttachment.attachFile(attachmentPath);

			//Attach body parts
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(pdfAttachment);

			//Attach multipart to message
			msg.setContent(emailContent);

			Transport.send(msg);
			System.out.println("Sent Mail");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}
	
	
	
	
	
	
	
	

}
