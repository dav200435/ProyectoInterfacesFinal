package com.proyecto.reportGenerators;

import com.proyecto.model.Product;
import com.proyecto.model.Employ;
import com.proyecto.model.Sell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelReportGenerator {

    // Generar informe Excel para productos
    public static void generateProductReport(List<Product> products, String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Category");
            headerRow.createCell(3).setCellValue("Price");
            headerRow.createCell(4).setCellValue("Stock");

            int rowNum = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getIdProducto());
                row.createCell(1).setCellValue(product.getNombre());
                row.createCell(2).setCellValue(product.getCategoria());
                row.createCell(3).setCellValue(product.getPrecio());
                row.createCell(4).setCellValue(product.getStock());
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generar informe Excel para empleados
    public static void generateEmployReport(List<Employ> employs, String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employs");
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Position");
            headerRow.createCell(3).setCellValue("Hire Date");

            int rowNum = 1;
            for (Employ employ : employs) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employ.getIdEmpleado());
                row.createCell(1).setCellValue(employ.getNombre());
                row.createCell(2).setCellValue(employ.getCargo());
                row.createCell(3).setCellValue(employ.getFechaContratacion());
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generar informe Excel para ventas
    public static void generateSellReport(List<Sell> sells, String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sells");
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Employ ID");
            headerRow.createCell(2).setCellValue("Product ID");
            headerRow.createCell(3).setCellValue("Quantity");
            headerRow.createCell(4).setCellValue("Sell Date");
            headerRow.createCell(5).setCellValue("Total");

            int rowNum = 1;
            for (Sell sell : sells) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sell.getIdSell());
                row.createCell(1).setCellValue(sell.getIdEmploy());
                row.createCell(2).setCellValue(sell.getIdProduct());
                row.createCell(3).setCellValue(sell.getQuantity());
                row.createCell(4).setCellValue(sell.getSellDate());
                row.createCell(5).setCellValue(sell.getTotal());
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

