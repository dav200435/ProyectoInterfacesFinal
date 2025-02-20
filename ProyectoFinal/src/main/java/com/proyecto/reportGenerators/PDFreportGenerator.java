package com.proyecto.reportGenerators;

import com.proyecto.model.Product;
import com.proyecto.model.Employ;
import com.proyecto.model.Sell;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFreportGenerator {

    // Generar informe PDF para productos
    public static void generateProductReport(List<Product> products, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph("Product Report"));
            document.add(Chunk.NEWLINE);
            
            PdfPTable table = new PdfPTable(5);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Category");
            table.addCell("Price");
            table.addCell("Stock");

            for (Product product : products) {
                table.addCell(String.valueOf(product.getIdProducto()));
                table.addCell(product.getNombre());
                table.addCell(product.getCategoria());
                table.addCell(String.valueOf(product.getPrecio()));
                table.addCell(String.valueOf(product.getStock()));
            }
            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    // Generar informe PDF para empleados
    public static void generateEmployReport(List<Employ> employs, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph("Employ Report"));
            document.add(Chunk.NEWLINE);
            
            PdfPTable table = new PdfPTable(4);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Position");
            table.addCell("Hire Date");

            for (Employ employ : employs) {
                table.addCell(String.valueOf(employ.getIdEmpleado()));
                table.addCell(employ.getNombre());
                table.addCell(employ.getCargo());
                table.addCell(employ.getFechaContratacion());
            }
            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    // Generar informe PDF para ventas
    public static void generateSellReport(List<Sell> sells, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph("Sell Report"));
            document.add(Chunk.NEWLINE);
            
            PdfPTable table = new PdfPTable(6);
            table.addCell("ID");
            table.addCell("Employ ID");
            table.addCell("Product ID");
            table.addCell("Quantity");
            table.addCell("Sell Date");
            table.addCell("Total");

            for (Sell sell : sells) {
                table.addCell(String.valueOf(sell.getIdSell()));
                table.addCell(String.valueOf(sell.getIdEmploy()));
                table.addCell(String.valueOf(sell.getIdProduct()));
                table.addCell(String.valueOf(sell.getQuantity()));
                table.addCell(sell.getSellDate());
                table.addCell(String.valueOf(sell.getTotal()));
            }
            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}

