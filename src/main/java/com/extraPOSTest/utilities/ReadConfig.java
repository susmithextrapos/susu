package com.extraPOSTest.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {

	Properties pro;

	public ReadConfig() {

		File src = new File("./Configuration/config.properties");

		try {
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			try {
				pro.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public String getApplicationURL() {

		String url = pro.getProperty("baseURL");
		return url;

	}

	public String getUsername() {

		String uname = pro.getProperty("username");
		return uname;
	}

	public String getPassword() {

		String password = pro.getProperty("password");
		return password;
	}

	public String getChromePath() {

		String chromePath = pro.getProperty("chromePath");
		return chromePath;

	}

	public String getFirefoxPath() {

		String firefoxPath = pro.getProperty("firefoxPath");
		return firefoxPath;

	}

	public String getSecurityOutputPath() {

		String securityOutputPath = pro.getProperty("securityOutput");
		return securityOutputPath;
	}

	public String getSecurityInputPath() {

		String securityInputPath = pro.getProperty("securityInput");
		return securityInputPath;
	}

	public String getBranchInputPath() {

		String branchInputPath = pro.getProperty("systemAdministrationInput");
		return branchInputPath;
	}

	public String getBranchOutputPath() {

		String branchOutputPath = pro.getProperty("systemAdministrationOutput");
		return branchOutputPath;
	}

	public String getInventoryMaintenanceInputPath() {

		String InventoryMaintenanceInputPath = pro.getProperty("InventoryMaintenanceInput");
		return InventoryMaintenanceInputPath;
	}

	public String getInventoryMaintenanceOutputPath() {

		String InventoryMaintenanceOutputPath = pro.getProperty("InventoryOutput");
		return InventoryMaintenanceOutputPath;
	}

	//give this
	public String getInventoryOutputPath() {

		String InventoryOutputPath = pro.getProperty("InventoryOutput");
		return InventoryOutputPath;
	}

	public String	getApprovalsOutputPath()
	{
		String InventoryApprovalOutputPath = pro.getProperty("AprovalRoleCodeOutPut");
		return InventoryApprovalOutputPath;
	}
	
}