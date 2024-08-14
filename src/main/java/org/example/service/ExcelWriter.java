package org.example.service;

import lombok.Getter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    private static final Workbook workbook = new XSSFWorkbook();
    private static final Sheet sheet = workbook.createSheet("Sample Sheet");
    private static int rowNumber = 0;

    static {
        Row row = sheet.createRow(rowNumber);

        // Create cells and set values
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Name");

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Number");

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Tglink");
        rowNumber++;
    }
    public static void write(User user) {
        // Create a workbook and a sheet


        // Create a row and put some cells in it. Rows are 0-based.
        Row row = sheet.createRow(rowNumber);

        // Create cells and set values
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(user.getFullName());

        Cell cell2 = row.createCell(1);
        cell2.setCellValue(user.getPhoneNumber());

        Cell cell3 = row.createCell(2);
        cell3.setCellValue(user.getTgLink());

        // Write the output to a file
        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/Eko-zakovat.xlsx")) {
            workbook.write(fileOut);
            System.out.println("yozdi");
            rowNumber++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
