package com.extraPOSTest.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.testng.internal.TestResult;

import com.aventstack.extentreports.gherkin.model.Background;

public class XLUtils {

	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;
	
	private static String[] columns = {"FormName","Test_Id", "Test_Scenario", "ExpectResult", "FinalResult"};

	public static int getRowCount(String xlfile, String xlsheet) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		int rowcount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;
	}

	public static int getCellCount(String xlfile, String xlsheet, int rownum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		int cellcount = row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}

	public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public static void setCellData(String xlfile, String xlsheet, int rownum, int colnum, String data)
			throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fo = new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}

	public static void updateResults(String excellLocation, String sheetName, String testCaseName, String testStatus)
			throws IOException {

		try {
			
			System.out.println("Inside the UpdateResults method");
			System.out.println(excellLocation+"  "+sheetName+"  "+testCaseName+"  "+testStatus);
			fi = new FileInputStream(excellLocation);

			wb = new XSSFWorkbook(fi);
			ws = wb.getSheet(sheetName);
			int totalRow = ws.getLastRowNum() + 1;

			CellStyle style = wb.createCellStyle();

			for (int i = 1; i < totalRow; i++) {

				row = ws.getRow(i);
				String ce = row.getCell(0).getStringCellValue();

				if (ce.contains(testCaseName)) {

					if (testStatus.equalsIgnoreCase("pass")) {
						style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
						style.setFillPattern(FillPatternType.BIG_SPOTS);
						XSSFCell cell = row.createCell(3);
						cell.setCellValue(testStatus);
						cell.setCellStyle(style);
					} else if (testStatus.equalsIgnoreCase("fail")) {
						style.setFillBackgroundColor(IndexedColors.RED.getIndex());
						style.setFillPattern(FillPatternType.BIG_SPOTS);
						XSSFCell cell = row.createCell(3);
						cell.setCellValue(testStatus);
						cell.setCellStyle(style);
					}else {
						
						style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
						style.setFillPattern(FillPatternType.BIG_SPOTS);
						XSSFCell cell = row.createCell(3);
						cell.setCellValue(testStatus);
						cell.setCellStyle(style);
					}

					fi.close();
					System.out.println("Result Updated");

					fo = new FileOutputStream(new File(excellLocation));
					wb.write(fo);
					fo.close();
					break;
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
public void printExcel(HashMap<String, Boolean> finalResultMap, String outPutExcellLocation, String writeSheetName) throws IOException {
		

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
	
	
//New OutPut File requirement
public void printOutExcel(HashMap<String,ArrayList<String>> finalResultMaps, String outPutExcellLocation, String writeSheetName) throws IOException {
	
    String formName ="";
	String TC_No="";
	String scenario ="";
	String inputValue ="";
	String expectedResult ="";
	Boolean actualResult;
	ArrayList<String> testDetails = new ArrayList<String>();
	
	for(Entry<String, ArrayList<String>> m:finalResultMaps.entrySet()){    
          
           TC_No =m.getKey();
           testDetails = m.getValue();
           
           	   formName = testDetails.get(0);
        	   scenario = testDetails.get(1);
        	   inputValue = testDetails.get(2);
        	   expectedResult = testDetails.get(3);
        	   actualResult = Boolean.parseBoolean(testDetails.get(4));
        	   System.out.println("FormName :"+formName+'\n'+"scenario :"+scenario +'\n'+ "inputValue :"+inputValue +'\n'+"expectedResult :"+expectedResult+'\n'+"actualResult :"+actualResult);
        			
        	   if (actualResult == true) // for PASS
       		{

       			XLUtils.updateOutPutResults(outPutExcellLocation, writeSheetName,formName, scenario,inputValue,expectedResult,TC_No, "PASS");

       		} else if (actualResult == false) {

       			XLUtils.updateOutPutResults(outPutExcellLocation, writeSheetName,formName, scenario,inputValue,expectedResult,TC_No, "FAIL");
       		}
          
	
	   }
	
}

public static void updateOutPutResults(String excellLocation, String sheetName,String formName,String scenario,String inputValue,String expectedResult, String testCaseName, String testStatus)
		throws IOException {

	try {
		
		System.out.println("Inside the UpdateOutPutResults method*******************NEW");
		System.out.println(excellLocation+"  "+sheetName+"  "+testCaseName+"  "+testStatus);
		fi = new FileInputStream(excellLocation);

		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheetName);
		int totalRow = ws.getLastRowNum() + 1;

		CellStyle style = wb.createCellStyle();

		for (int i = 1; i < totalRow; i++) {

			row = ws.getRow(i);
			String ce = row.getCell(1).getStringCellValue();

			if (ce.contains(testCaseName)) {
				
				       
				row.createCell(0).setCellValue(formName);
				row.createCell(2).setCellValue(scenario);
			 row.createCell(3).setCellValue(inputValue);
			 row.createCell(4).setCellValue(expectedResult);

				if (testStatus.equalsIgnoreCase("pass")) {
					style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
					style.setFillPattern(FillPatternType.BIG_SPOTS);
					XSSFCell cell = row.createCell(5);
					cell.setCellValue(testStatus);
					cell.setCellStyle(style);
				} else if (testStatus.equalsIgnoreCase("fail")) {
					style.setFillBackgroundColor(IndexedColors.RED.getIndex());
					style.setFillPattern(FillPatternType.BIG_SPOTS);
					XSSFCell cell = row.createCell(5);
					cell.setCellValue(testStatus);
					cell.setCellStyle(style);
				}else {
					
					style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
					style.setFillPattern(FillPatternType.BIG_SPOTS);
					XSSFCell cell = row.createCell(5);
					cell.setCellValue(testStatus);
					cell.setCellStyle(style);
				}

				fi.close();
				System.out.println("Result Updated");

				fo = new FileOutputStream(new File(excellLocation));
				wb.write(fo);
				fo.close();
				break;
			}

		}

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}


// newly added 04-03-2021 *****************************************************************



public void printOutPutExcel(HashMap<String,ArrayList<String>> finalResultMaps, String outPutExcellLocation, String writeSheetName) throws IOException {
	
	
	try {
		
		 String formName ="";
			String TC_No="";
			String scenario ="";
			String expectedResult ="";
			String actualResult = "";
			
			 fi = new FileInputStream(outPutExcellLocation);
			 wb = new XSSFWorkbook(fi);
			 ws = wb.getSheet(writeSheetName);
			ArrayList<String> testDetails = new ArrayList<String>();
			ArrayList<String> TC = new ArrayList<String>();
			


			for(Entry<String, ArrayList<String>> m:finalResultMaps.entrySet()){    
		          
				
				
		           		TC_No =m.getKey();
		           		testDetails = m.getValue();
		           		
		           	   formName = testDetails.get(0);
		        	   scenario = testDetails.get(1);
		        	   expectedResult = testDetails.get(2);
		        	   actualResult = testDetails.get(3);
		        	   //actualResult = Boolean.parseBoolean(testDetails.get(4));
		        	   System.out.println("TC_No :"+TC_No+'\n'+"SheetName :"+writeSheetName+'\n'+"FormName :"+formName+'\n'+"scenario :"+scenario +'\n'+"expectedResult :"+expectedResult+'\n'+"actualResult :"+actualResult);
		        			
		        	   
		        			int lastRow = ws.getLastRowNum()+1;
		        	   		
		        			
		        	   		CellStyle style = wb.createCellStyle();
		        	      
		        	   		for(int i=lastRow ; i <= lastRow; i++)
		        			{
		        				
		        				XSSFRow rows = ws.createRow(i);
		        				//XSSFRow row = ws.getRow(i);
		        				
		        				
		        				Iterator<Cell> cell = rows.cellIterator();
		        							
		        				if(cell.hasNext() == false)
		        				{
		        					
		        					rows.createCell(0).setCellValue(formName);
		        					rows.createCell(1).setCellValue(TC_No);
		        					rows.createCell(2).setCellValue(scenario);
		        					rows.createCell(3).setCellValue(expectedResult);

		        					//Styles
		        					
		        					if (actualResult.equalsIgnoreCase("true")) {
		        						style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		        						style.setFillPattern(FillPatternType.BIG_SPOTS);
		        						XSSFCell cells = rows.createCell(4);
		        						cells.setCellValue("PASS");
		        						cells.setCellStyle(style);
		        					} else if (actualResult.equalsIgnoreCase("false")) {
		        						style.setFillBackgroundColor(IndexedColors.RED.getIndex());
		        						style.setFillPattern(FillPatternType.BIG_SPOTS);
		        						XSSFCell cells = rows.createCell(4);
		        						cells.setCellValue("FAIL");
		        						cells.setCellStyle(style);
		        					}
		        				
		        				}
		        			}
		        	  
		    
			   }
			
			finalResultMaps.remove(TC_No);
			
			fi.close();
			System.out.println("Result Updated");

			fo = new FileOutputStream(new File(outPutExcellLocation));
			wb.write(fo);
			fo.close();
			
		
	}
	catch (Exception e) {

	   e.printStackTrace();
	}
	
	
	
   
}





public static String createOutPutExcel(String excellLocation,String sheetName) throws IOException
{
	String timeStamp = new SimpleDateFormat("YYYY-MM-dd_HH-mm-ss").format(new Date());// time stamp
	String pathName = "";
	OutputStream fileOut = null;
	try {
		
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet(sheetName);
	    
	    Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 14);
	    headerFont.setColor(IndexedColors.VIOLET.getIndex());
	    

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);

	    // Create a Row
	    Row headerRow = sheet.createRow(0);

	    for (int i = 0; i < columns.length; i++) {
	      Cell cell = headerRow.createCell(i);
	      cell.setCellValue(columns[i]);
	      cell.setCellStyle(headerCellStyle);
	    }
	    
	    for (int i = 0; i < columns.length; i++) {
	        sheet.autoSizeColumn(i);
	      }

	      // Write the output to a file
	    pathName = excellLocation+"_"+timeStamp+".xlsx";
	      FileOutputStream fOut = new FileOutputStream(pathName);
	      workbook.write(fOut);
	      fOut.close();

		
	} catch (Exception e) {

		e.printStackTrace();
	}
	

System.out.println("Excel created Successfully ");
return pathName;
}

/*
 * String timeStamp = new SimpleDateFormat("dd-MM-yyyy;HH.mm.ss").format(new Date());// time stamp

	fi = new FileInputStream(excellLocation);

	wb = new XSSFWorkbook(fi);
	ws =  wb.createSheet(sheetName+timeStamp);
	String sheetNames = ws.getSheetName();
	System.out.println("Inside the SheetName Cratetion method in XLUtils:: SheetName is :"+sheetNames);
	//int totalRow = ws.getLastRowNum() + 1;

	
	fi.close();
	System.out.println("Result Updated");

	fo = new FileOutputStream(new File(excellLocation));
	wb.write(fo);
	fo.close();
	
	return sheetNames;
	
 * 
 * */


}
