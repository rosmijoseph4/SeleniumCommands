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

    public String readData(int i, int j) throws IOException {

        File file = new File("C:\\Users\\Rosmi Joseph\\IdeaProjects\\selenium\\src\\main\\resources\\TestData.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet("Sheet1");
        Row r = sheet.getRow(i);
        Cell c = r.getCell(j);
        if (c.getCellType() == Cell.CELL_TYPE_STRING) {
            return (c.getStringCellValue());
        } else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            c.setCellType(Cell.CELL_TYPE_STRING);
            return (c.getStringCellValue());
        } else {
            return " ";
        }
    }
}