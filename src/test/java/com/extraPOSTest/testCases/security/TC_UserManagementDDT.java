package com.extraPOSTest.testCases.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.extraPOSTest.pageObjects.LoginPage;
import com.extraPOSTest.pageObjects.PostLoginPage;
import com.extraPOSTest.pageObjects.security.UserManagementPage;
import com.extraPOSTest.resources.BaseClass;
import com.extraPOSTest.resources.ConvertionAndValidation;
import com.extraPOSTest.utilities.Listeners;
import com.extraPOSTest.utilities.ReadConfig;
import com.extraPOSTest.utilities.XLUtils;

public class TC_UserManagementDDT extends BaseClass {

	boolean result = false;
	boolean skip = false;
	public String testCaseID = "";
	public List<String> allUserNameList;
	public Listeners listeners;
	ReadConfig readConfig = new ReadConfig();
	HashMap<String, Boolean> finalResultMap = new HashMap<String, Boolean>();
	public WebDriver driver;
	
	String readSheetName = "userManage";

	String outPutExcellLocation = readConfig.getSecurityOutputPath();
	String writeSheetName = "userManage";

	
	@Parameters("browser")
	@BeforeTest
	public void BrowserSetUp(String browser) {
	this.driver=init(browser);
	}
	
	@AfterTest
	public void tearDown() {

		driver.quit();
		logger.info("Driver has closed");
	}

	
	@Test(priority = 1)
	public void clickSelectAll() {
		
		UserManagementPage userManagementPage = new UserManagementPage(driver);
		PostLoginPage postLoginPage = new PostLoginPage(driver);
		allUserNameList = new ArrayList<>();
		WebDriverWait wait = new WebDriverWait(driver,15);
		WebElement showingText;
		
		postLoginPage.clickSecurity();
		postLoginPage.clickUserManagement();

		showingText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div[3]/div/div[3]/div[2]/main/div/div[3]/div[1]/div/div[2]/div[2]/div")));
		userManagementPage.clickSelectDropDown();
		logger.info("after select click ");

		userManagementPage.clickAll();

		List<WebElement> allUserName = userManagementPage.allUserNameList();
		int uNameCount = allUserName.size();
		logger.info("username count is ->> " + uNameCount);
		for (int i = 0; i < uNameCount; i++) {

			allUserNameList.add(allUserName.get(i).getText());
		}

		postLoginPage.clickSecurity();
		logger.info("Array list size is -->> " + allUserNameList.size());
		
		
	}
	
	@Test(priority = 3, dataProvider = "user")
	public void addUserManagementDDT(String testCaseNo, String uName, String fName, String lastName, String Email,
			String Password, String ConfirmPassword, String Address1, String address2, String address3, String city,
			String country, String phoneNumber, String status) throws InterruptedException, IOException {
		
		try {
			UserManagementPage userManagementPage = new UserManagementPage(driver);
			PostLoginPage postLoginPage = new PostLoginPage(driver);
			
			postLoginPage.clickSecurity();
			postLoginPage.clickUserManagement();

		
			
			String userName = uName;
			String firstName = fName;
			String email = Email;
			String password = Password;
			String confirmPassword = ConfirmPassword;
			String address1 = Address1;

			logger.info("inside the loginDDT");
			ConvertionAndValidation convertionAndValidation = new ConvertionAndValidation();

			logger.info(userName + "  " + firstName + " " + lastName + " " + email + " " + password + "  "
					+ confirmPassword + " " + address1 + " " + address2 + "  " + address3 + " " + city + " " + country
					+ " " + phoneNumber + "  " + status);

			boolean unameEmpty_bool = false, unameAlreadyExist_bool = false, fname_bool = false, email_bool = false,
					paswdReqrd_bool = false, pswdLength_bool = false, pswdWhiteSpace_bool = false,
					conPaswdReqrd_bool = false, conPswdMatch_bool = false, addr1_bool = false;

			
			
			if (driver.getPageSource().contains("Please Confirm !")) {
				userManagementPage.clickConfirmSaveButtonAlert();

			}

	
			userManagementPage.clickAddButton();
			userManagementPage.sendUserName(userName);
			userManagementPage.sendFirstName(firstName);
			userManagementPage.sendLastName(lastName);
			userManagementPage.sendEmail(email);
			userManagementPage.sendPassword(password);
			userManagementPage.sendConfirmPassword(confirmPassword);
			userManagementPage.sendAddress1(address1);

			userManagementPage.sendAddress2(address2);
			userManagementPage.sendAddress3(address3);
			userManagementPage.sendCity(city);
			userManagementPage.sendCountry(country);
			userManagementPage.sendPhoneNo(phoneNumber);

			if (status.equalsIgnoreCase("inactive")) {
				logger.info("inside the status");
				userManagementPage.clickInActiveStatus();
			}
			/* ****************** Username Validation **************************** */

			// empty user name
			if (userName.isBlank()) {

				logger.info("empty username validation starts");
				unameEmpty_bool = userManagementPage.UserNameRequired();

				if (unameEmpty_bool) {
					logger.info("validation for empty username works");
					Assert.assertTrue(true, "There is a validation for empty username field");
				} else {
					logger.warn("no validation for empty user name");
					Assert.assertTrue(false, "There is no validation for empty username field");
				}
			}

			// username is not empty , checking is already exist

			if (userName.isEmpty() == false) {
				logger.info("inside username is not empty block");

				for (int l = 0; l < allUserNameList.size(); l++) {
					if (allUserNameList.get(l).equals(userName)) {

						logger.info("inside username already exist block");
						unameAlreadyExist_bool = userManagementPage.userNameValidation1();
						if (unameAlreadyExist_bool) {
							logger.info("Username already exist validation Success");
							Assert.assertTrue(true, "Validation msg is exist for UserName Already Exist ");
						} else {
							logger.info("There is NO validaton msg for USERNAME Already Exist");
							Assert.assertTrue(false, "No Validation Msg for UserName Already Exist");
						}
					}

				}
			}
			logger.info("UserName validation Block is ended");
			/* **********USERNAME************* end */

			/* ********** firstName validation starts************* end */

			if (firstName.isBlank()) {

				fname_bool = userManagementPage.firstNameValidation();
				if (fname_bool) {
					logger.info("FIRST NAME validation done successfully");
					Assert.assertTrue(true, "No Validation for First Name Empty ");
				} else {

					logger.info("FIRST NAME validation ISNOT done");
					Assert.assertTrue(false, "No Validation for First Name Empty ");
				}

			}
			logger.info("firstnname validation is ended");
			/* **********FIRSTNAME************* end */

			/* ********** email validation starts************* end */

			boolean emailVal = convertionAndValidation.emailChecks(email);

			/*
			 * if (email.isBlank()) {
			 * 
			 * logger.info("Inside Blank.............."); if
			 * (convertionAndValidation.containsWhiteSpace(email)) { email_bool = true;
			 * logger.
			 * info("there is no validation for the email, if the email contains whitespaces"
			 * ); Assert.assertTrue(false,
			 * "No validation message for email(White sapce are not allowed)"); }
			 * 
			 * } else
			 */
			if (email.isEmpty()) {

				logger.info("inside email empty block");
				Assert.assertTrue(true);

			}

			// email format validation (if the email is correct format)
			else if (emailVal == true) {

				logger.info("proper email validation  ");
				Assert.assertTrue(true, "Email is correct format");
			}

// neagtive cases for invalied format
			else {

				logger.info("No proper email format");
				email_bool = userManagementPage.isEmailvalidationPresent();

				if (email_bool) {
					result = false;
					logger.info("email validation is present");
					Assert.assertTrue(true, "There is validation msg for incorrect email"); // changed here forcefully
																							// to fail(true -> false)
				}

				else {
					logger.info("email validation is Not present");
					Assert.assertTrue(false, "No email validation for incorrect email format");
				}

			}

			logger.info("email validation block is ended");
			/* **********EMAIL************* end */

			/* ********** Password validation starts************* end */
			String pwdS = " ";
			int pswrd = password.compareTo(confirmPassword);
			logger.info("comparing pswrd and conPaswrd " + pswrd);

			if (password.length() != 0) {
				char pwd = password.toString().charAt(0);
				pwdS = String.valueOf(pwd);
				logger.info("Password first char is =----->" + pwdS + ".");
			}
			// Empty Password
			if (password.isEmpty()) {

				logger.info("inside the empty password");
				paswdReqrd_bool = userManagementPage.passwordEmptyValidation();
				logger.info("paswdReqrd_bool   ----------->" + paswdReqrd_bool);
				if (paswdReqrd_bool) {

					logger.info("if block, for validation for Empty Password");
					Assert.assertTrue(true, " validation for Empty Password  ");
				} else {
					logger.warn("else block, for ' NO validation for Empty Password '");
					Assert.assertTrue(false, "No validation for Empty Password  ");
				}

			}

			// whitespace

			else if (pwdS.equals(" ")) {
				pswdWhiteSpace_bool = userManagementPage.passwordWhiteSpaceValidation();

				logger.info("password whitespace validation ");
				if (pswdWhiteSpace_bool) {
					logger.info("if block, for validation for Password whitespace");
					Assert.assertTrue(true, "validation for password whitespace");
				} else {
					logger.info("else block, for validation for Password whitespace");
					Assert.assertTrue(false, "No validation for password whitespace ");
				}
			}

			// Password Length lessthan 8 char
			else if (password.length() < 8) {

				logger.info("password length less than 8 ");
				pswdLength_bool = userManagementPage.passwordLenghthValidation();

				if (pswdLength_bool) {
					logger.info("if block, for validation for Password < 8");
					Assert.assertTrue(true, "validation for password length less than 8");
				} else {
					logger.info("else block, for validation for Password < 8");
					Assert.assertTrue(false, "No validation for password length less than 8  ");
				}

			}
			logger.info("Password validation block finished");
			/* **********Password************* end */

			/* ********** Con Password validation starts************* end */
			// Con Password Empty validation

			if (confirmPassword.isEmpty()) {
				logger.info("inside the Empty Confirm Password");
				conPaswdReqrd_bool = userManagementPage.confirmPasswordEmptyValidation();
				logger.info("conPaswdReqrd_bool   ----------->" + conPaswdReqrd_bool);
				if (conPaswdReqrd_bool) {
					logger.info("if block , validation for conPswd is empty validation ");
					Assert.assertTrue(true, "validation for confirm password empty");
				} else {
					logger.info("else block , validation for conPswd is empty validation ");
					Assert.assertTrue(false, "No validation for confirm password empty");
				}
			}

			// Con Password Need MAtch Validation

			else if (pswrd != 0) {

				logger.info("inside the confirm password, Passwor not match with conpasswrd");
				conPswdMatch_bool = userManagementPage.ConfirmPasswordValidation();
				if (conPswdMatch_bool) {
					logger.info("inside if, validation for confirm password need match");
					Assert.assertTrue(true, "validation for confirm password need match");
				} else {
					logger.warn("inside else,No validation for confirm password need match");
					Assert.assertTrue(false, "No validation for confirm password need match");
				}
			}
			logger.info("Con Password validation block finished");

			/*
			 * **********Conf Password************* end
			 * 
			 * 
			 * /* ********** Address 1 validation starts************* end
			 */

			if (address1.isBlank() || address1.isEmpty()) {
				logger.info("inside the address field");
				addr1_bool = userManagementPage.addressValidation();
				if (addr1_bool) {
					logger.info("inside if , the address field contains the validation");
					Assert.assertTrue(true, "validation for address");
				} else {
					logger.warn("inside else , the address field contains no validation");
					Assert.assertTrue(false, "No validation for address");
				}
			}
			logger.info("address validation block finished");
			/* ********** address 1************* end */

			if (unameEmpty_bool == false && unameAlreadyExist_bool == false && fname_bool == false
					&& email_bool == false && paswdReqrd_bool == false && conPaswdReqrd_bool == false
					&& pswdLength_bool == false && pswdWhiteSpace_bool == false && conPswdMatch_bool == false
					&& addr1_bool == false) {
				logger.info("inside the Save action if");
				/*
				 * userManagementPage.clickSaveButton();
				 * userManagementPage.clickConfirmSaveButtonAlert();
				 * Assert.assertEquals("Create/Maintain Users",
				 * userManagementPage.assetUserManagementText());
				 * logger.info("Save action completed Successfully");
				 */
				
				userManagementPage.clickSaveAndExitButton();
				logger.info("Save & exit click Successfully");
				userManagementPage.clickConfirmSaveButtonAlert();
				Assert.assertEquals("Users List", userManagementPage.assetUserListText());
				postLoginPage.clickSecurity();
				allUserNameList.add(userName);
				result = true;
				logger.info("Save and exit action completed Successfully");

			} else {

				if (unameEmpty_bool == true && fname_bool == true && paswdReqrd_bool == true
						&& conPaswdReqrd_bool == true && addr1_bool == true && email_bool == true) {
					logger.info("full empty");
					userManagementPage.clickHomeButton();
					postLoginPage.clickSecurity();
					result = true;
				} else {
					logger.info("without save -> home");
					userManagementPage.clickHomeButton();
					Assert.assertTrue(userManagementPage.exitWithoutSaving());
					userManagementPage.clickOkWithOutSaving();
					postLoginPage.clickSecurity();
					result = true;
				}

			}

		}

		catch (Exception e) {

			e.printStackTrace();
		}

		finally {

			finalResultMap.put(testCaseNo, result);
		/*	if (result == true) // for PASS
			{

				XLUtils.updateResults(outPutExcellLocation, writeSheetName, testCaseID, "PASS");

			} else if (result == false) {

				XLUtils.updateResults(outPutExcellLocation, writeSheetName, testCaseID, "FAIL");
			}*/
		}

	}// method end

	
	
	@DataProvider(name = "user")
	String[][] getData() throws IOException {

		String path = readConfig.getSecurityInputPath();
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

	
	@Test(priority = 3)
	public void printExcel() throws IOException {
		
		String TC_No="";
		Boolean res;
		for(Entry<String, Boolean> m:finalResultMap.entrySet()){    
	          
			System.out.println(m.getKey()+" "+m.getValue());    
	           TC_No =m.getKey();
	           res = m.getValue();
	           
		if (res == true) // for PASS
		{

			XLUtils.updateResults(outPutExcellLocation, writeSheetName, TC_No, "PASS");

		} else if (res == false) {

			XLUtils.updateResults(outPutExcellLocation, writeSheetName, TC_No, "FAIL");
		}
		
		   }
		
	}
	
}
