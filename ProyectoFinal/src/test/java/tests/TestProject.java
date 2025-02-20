package tests;

import com.proyecto.controller.EmployController;
import com.proyecto.model.Employ;
import com.proyecto.model.Product;
import com.proyecto.model.Sell;
import com.proyecto.repository.DatabaseOperations;
import com.proyecto.reportGenerators.ExcelReportGenerator;
import com.proyecto.reportGenerators.PDFreportGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.pdf.PdfReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TestProject {

    @Mock
    private DatabaseOperations databaseOperations;

    @Mock
    private ResultSet resultSet;

    private ObservableList<Employ> employs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employs = FXCollections.observableArrayList();
    }

    // Pruebas para EmployController
    @Test
    void testLoadEmploys() throws SQLException {
        when(databaseOperations.getEmploys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id_empleado")).thenReturn(1);
        when(resultSet.getString("nombre")).thenReturn("John Doe");
        when(resultSet.getString("cargo")).thenReturn("Developer");
        when(resultSet.getString("fecha_contratacion")).thenReturn("2023-01-01");

        EmployController.loadEmploys(employs);

        assertEquals(1, employs.size());
        Employ employ = employs.get(0);
        assertEquals(1, employ.getIdEmpleado());
        assertEquals("John Doe", employ.getNombre());
        assertEquals("Developer", employ.getCargo());
        assertEquals("2023-01-01", employ.getFechaContratacion());
    }

    @Test
    void testInsertEmploy() throws SQLException {
        doNothing().when(databaseOperations).insertEmploy(anyString(), anyString(), anyString());

        EmployController.insertEmploy("Jane Doe", "Manager", "2023-02-01");

        verify(databaseOperations, times(1)).insertEmploy("Jane Doe", "Manager", "2023-02-01");
    }

    // Pruebas para ExcelReportGenerator
    @Test
    void testGenerateProductReport() throws IOException {
        List<Product> products = Arrays.asList(
                new Product(1, "Product1", "Category1", 10.0, 100),
                new Product(2, "Product2", "Category2", 20.0, 200)
        );

        String outputPath = "test_product_report.xlsx";
        ExcelReportGenerator.generateProductReport(products, outputPath);

        Workbook workbook = new XSSFWorkbook(outputPath);
        assertNotNull(workbook);
        assertEquals(1, workbook.getNumberOfSheets());
        assertEquals("Products", workbook.getSheetName(0));
    }

    @Test
    void testGenerateEmployReport() throws IOException {
        List<Employ> employs = Arrays.asList(
                new Employ(1, "John Doe", "Developer", "2023-01-01"),
                new Employ(2, "Jane Doe", "Manager", "2023-02-01")
        );

        String outputPath = "test_employ_report.xlsx";
        ExcelReportGenerator.generateEmployReport(employs, outputPath);

        Workbook workbook = new XSSFWorkbook(outputPath);
        assertNotNull(workbook);
        assertEquals(1, workbook.getNumberOfSheets());
        assertEquals("Employs", workbook.getSheetName(0));
    }

    @Test
    void testGenerateSellReport() throws IOException {
        List<Sell> sells = Arrays.asList(
                new Sell(1, 1, 1, 10, "2023-01-01", 100.0),
                new Sell(2, 2, 2, 20, "2023-02-01", 200.0)
        );

        String outputPath = "test_sell_report.xlsx";
        ExcelReportGenerator.generateSellReport(sells, outputPath);

        Workbook workbook = new XSSFWorkbook(outputPath);
        assertNotNull(workbook);
        assertEquals(1, workbook.getNumberOfSheets());
        assertEquals("Sells", workbook.getSheetName(0));
    }

    // Pruebas para PDFreportGenerator
    @Test
    void testGenerateProductReportPDF() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1, "Product1", "Category1", 10.0, 100),
                new Product(2, "Product2", "Category2", 20.0, 200)
        );

        String outputPath = "test_product_report.pdf";
        PDFreportGenerator.generateProductReport(products, outputPath);

        PdfReader reader = new PdfReader(outputPath);
        assertNotNull(reader);
        assertEquals(1, reader.getNumberOfPages());
    }

    @Test
    void testGenerateEmployReportPDF() throws Exception {
        List<Employ> employs = Arrays.asList(
                new Employ(1, "John Doe", "Developer", "2023-01-01"),
                new Employ(2, "Jane Doe", "Manager", "2023-02-01")
        );

        String outputPath = "test_employ_report.pdf";
        PDFreportGenerator.generateEmployReport(employs, outputPath);

        PdfReader reader = new PdfReader(outputPath);
        assertNotNull(reader);
        assertEquals(1, reader.getNumberOfPages());
    }

    @Test
    void testGenerateSellReportPDF() throws Exception {
        List<Sell> sells = Arrays.asList(
                new Sell(1, 1, 1, 10, "2023-01-01", 100.0),
                new Sell(2, 2, 2, 20, "2023-02-01", 200.0)
        );

        String outputPath = "test_sell_report.pdf";
        PDFreportGenerator.generateSellReport(sells, outputPath);

        PdfReader reader = new PdfReader(outputPath);
        assertNotNull(reader);
        assertEquals(1, reader.getNumberOfPages());
    }
}