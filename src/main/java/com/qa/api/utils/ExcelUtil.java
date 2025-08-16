package com.qa.api.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private static String TEST_DATA_SHEET_PATH="./src/test/resources/testdata/UserDataSheet.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	public static Object[][] readData(String sheetName)
	{
		Object data[][]=null;
		try {
			FileInputStream fp=new FileInputStream(TEST_DATA_SHEET_PATH);
			try {
				book=WorkbookFactory.create(fp);
				sheet=book.getSheet(sheetName);
				
				data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
				
				for(int i=0;i<sheet.getLastRowNum();i++)
				{
					for(int j=0;j<sheet.getRow(0).getLastCellNum();j++)
					{
						data[i][j]=sheet.getRow(i+1).getCell(j).toString();
					}
				}
				
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
