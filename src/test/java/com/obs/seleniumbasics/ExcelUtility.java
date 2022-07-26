package com.obs.seleniumbasics;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtility {
    public String readData(int i, int j, String sheetname) throws IOException {
            String filepath = System.getProperty("user.dir") + "\\src\\main\\resources\\TestData.xlsx";
            File file = new File(filepath);
            FileInputStream inputStream = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = wb.getSheet(sheetname);
            Row r = sheet.getRow(i);
            Cell c = r.getCell(j);
            String value;
            if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                value = c.getStringCellValue();
            } else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                c.setCellType(Cell.CELL_TYPE_STRING);
                value = c.getStringCellValue();
            } else {
                value = " ";
            }
            return value;
    }

    public Object[][] readDataFromExcel(String filePath, String sheetName) throws IOException {
        DataFormatter formatter = new DataFormatter();
        FileInputStream file = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        int cellCount=sheet.getRow(0).getLastCellNum();
        Object[][] data=new  Object[rowCount][cellCount];
        for (int i = 1; i <=rowCount; i++) {
            Row r = sheet.getRow(i);
            for (int j = 0; j <cellCount; j++) {
                Cell c=r.getCell(j);
                if(c.getCellType()==Cell.CELL_TYPE_STRING){
                    data[i-1][j]=c.getStringCellValue();
                }
                else if (c.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    data[i-1][j]=c.getStringCellValue();
                }
                else {
                    data[i-1][j]=" ";
                }
            }
        }
        return data;
    }

}

