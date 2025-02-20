package com.proyecto.controller;

import com.proyecto.model.Product;
import com.proyecto.repository.DatabaseOperations;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductController {

    // Método para cargar productos
    public static void loadProducts(ObservableList<Product> products) {
        try {
            products.clear();
            ResultSet rs = DatabaseOperations.getProducts();
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
            showError("Error al cargar productos", e.getMessage());
        }
    }

    // Método para insertar un nuevo producto
    public static void insertProduct(String name, String category, double price, int stock) {
        try {
            DatabaseOperations.insertProduct(name, category, price, stock);
            showSuccess("Producto insertado correctamente");
        } catch (SQLException e) {
            showError("Error al insertar producto", e.getMessage());
        }
    }

    // Método para mostrar un mensaje de error
    private static void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje de éxito
    private static void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

