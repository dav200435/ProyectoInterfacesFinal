package com.proyecto.windows;

import com.proyecto.repository.DatabaseConnection;
import com.proyecto.model.Product;
import com.proyecto.model.Employ;
import com.proyecto.model.Sell;
import com.proyecto.reportGenerators.ExcelReportGenerator;
import com.proyecto.reportGenerators.PDFreportGenerator;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainWindow extends Application {

    // Listas para almacenar los datos
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Employ> employs = FXCollections.observableArrayList();
    private ObservableList<Sell> sells = FXCollections.observableArrayList();

    // TableView para mostrar los datos
    private TableView<Product> tableViewProducts = new TableView<>();
    private TableView<Employ> tableViewEmploys = new TableView<>();
    private TableView<Sell> tableViewSells = new TableView<>();

    // Layout principal
    private BorderPane root = new BorderPane();

    public static void main(String[] args) {
        launch(args); // Lanza la aplicación JavaFX
    }

    @Override
    public void start(Stage primaryStage) {
        // Botones
        Button btnProducts = new Button("Products");
        Button btnEmploys = new Button("Employs");
        Button btnSells = new Button("Sells");
        Button btnExportExcel = new Button("Export Excel");
        Button btnExportPDF = new Button("Export PDF");

        // Configurar acciones de los botones
        btnProducts.setOnAction(event -> {
            loadProducts();
            configureProductTable();
            showTable(tableViewProducts);
        });

        btnEmploys.setOnAction(event -> {
            loadEmploys();
            configureEmployTable();
            showTable(tableViewEmploys);
        });

        btnSells.setOnAction(event -> {
            loadSells();
            configureSellTable();
            showTable(tableViewSells);
        });

        // Acciones para los botones de exportación
        btnExportExcel.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                if (tableViewProducts.isVisible()) {
                    ExcelReportGenerator.generateProductReport(products, file.getAbsolutePath());
                } else if (tableViewEmploys.isVisible()) {
                    ExcelReportGenerator.generateEmployReport(employs, file.getAbsolutePath());
                } else if (tableViewSells.isVisible()) {
                    ExcelReportGenerator.generateSellReport(sells, file.getAbsolutePath());
                }
            }
        });

        btnExportPDF.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                if (tableViewProducts.isVisible()) {
                    PDFreportGenerator.generateProductReport(products, file.getAbsolutePath());
                } else if (tableViewEmploys.isVisible()) {
                    PDFreportGenerator.generateEmployReport(employs, file.getAbsolutePath());
                } else if (tableViewSells.isVisible()) {
                    PDFreportGenerator.generateSellReport(sells, file.getAbsolutePath());
                }
            }
        });

        // Layout de botones en la parte inferior
        HBox buttonBox = new HBox(10, btnProducts, btnEmploys, btnSells, btnExportExcel, btnExportPDF);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setStyle("-fx-alignment: center; -fx-background-color: #f0f0f0;");

        VBox bottomLayout = new VBox(buttonBox);
        bottomLayout.setPadding(new Insets(10));

        // Agregar botones en la parte inferior
        root.setBottom(bottomLayout);

        // Mostrar la tabla de productos por defecto
        loadProducts();
        configureProductTable();
        root.setCenter(tableViewProducts);

        // Escena
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Data Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para mostrar la tabla seleccionada sin perder los botones
    private void showTable(TableView<?> table) {
        root.setCenter(table);  // Solo cambia el centro, sin modificar el resto
    }

    // Método para cargar productos desde la base de datos
    public ObservableList<Product> loadProducts() {
        products.clear();
        String query = "SELECT * FROM Productos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return products;
    }

    // Método para configurar las columnas de la tabla de productos
    public void configureProductTable() {
        tableViewProducts.getColumns().clear();

        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_producto"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableViewProducts.getColumns().addAll(idCol, nameCol, categoryCol, priceCol, stockCol);
        tableViewProducts.setItems(products);
    }

    // Método para cargar empleados desde la base de datos
    private void loadEmploys() {
        employs.clear();
        String query = "SELECT * FROM Empleados";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employs.add(new Employ(
                    rs.getInt("id_empleado"),
                    rs.getString("nombre"),
                    rs.getString("cargo"),
                    rs.getString("fecha_contratacion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para configurar las columnas de la tabla de empleados
    private void configureEmployTable() {
        tableViewEmploys.getColumns().clear();

        TableColumn<Employ, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_empleado"));

        TableColumn<Employ, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Employ, String> positionCol = new TableColumn<>("Position");
        positionCol.setCellValueFactory(new PropertyValueFactory<>("cargo"));

        TableColumn<Employ, String> hireDateCol = new TableColumn<>("Hire Date");
        hireDateCol.setCellValueFactory(new PropertyValueFactory<>("fecha_contratacion"));

        tableViewEmploys.getColumns().addAll(idCol, nameCol, positionCol, hireDateCol);
        tableViewEmploys.setItems(employs);
    }

    // Método para cargar ventas desde la base de datos
    private void loadSells() {
        sells.clear();
        String query = "SELECT * FROM Ventas";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sells.add(new Sell(
                    rs.getInt("id_venta"),
                    rs.getInt("id_empleado"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getString("fecha_venta"),
                    rs.getDouble("total_venta")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para configurar las columnas de la tabla de ventas
    private void configureSellTable() {
        tableViewSells.getColumns().clear();

        TableColumn<Sell, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_venta"));

        TableColumn<Sell, Integer> idEmployCol = new TableColumn<>("Employ ID");
        idEmployCol.setCellValueFactory(new PropertyValueFactory<>("id_empleado"));

        TableColumn<Sell, Integer> idProductCol = new TableColumn<>("Product ID");
        idProductCol.setCellValueFactory(new PropertyValueFactory<>("id_producto"));

        TableColumn<Sell, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<Sell, String> sellDateCol = new TableColumn<>("Sell Date");
        sellDateCol.setCellValueFactory(new PropertyValueFactory<>("fecha_venta"));

        TableColumn<Sell, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total_venta"));

        tableViewSells.getColumns().addAll(idCol, idEmployCol, idProductCol, quantityCol, sellDateCol, totalCol);
        tableViewSells.setItems(sells);
    }

	public TableView<Product> getTableViewProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
