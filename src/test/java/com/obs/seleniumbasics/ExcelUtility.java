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
    public String readData(int i, int j, String Login) throws IOException {
        String filepath = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.xlsx";
        File file = new File(filepath);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        //XSSFSheet sheet = wb.getSheet(sheetname);
        XSSFSheet sheet = wb.getSheet(Login);
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

    /*public Object[][] readDataExcel(String filePath,String sheetname) throws IOException {
        String path=System.getProperty("user.dir")+File.separator+filePath;
        Object[][] data=new Object[][];

        DataFormatter formatter = new DataFormatter();
        FileInputStream file = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        for (Row r : sheet) {
            for (Cell c : r) {
                Object value=formatter.formatCellValue(c);
                data= (Object[][]) value;
            }
        }
        return data;
    }*/

        }

