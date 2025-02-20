package com.proyecto.controller;

import com.proyecto.model.Sell;
import com.proyecto.repository.DatabaseOperations;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SellController {

    // Método para cargar ventas
    public static void loadSells(ObservableList<Sell> sells) {
        try {
            sells.clear();
            ResultSet rs = DatabaseOperations.getSells();
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
            showError("Error al cargar ventas", e.getMessage());
        }
    }

    // Método para insertar una venta
    public static void insertSell(int idEmploy, int idProduct, int quantity, String sellDate, double total) {
        try {
            DatabaseOperations.insertSell(idEmploy, idProduct, quantity, sellDate, total);
            showSuccess("Venta insertada correctamente");
        } catch (SQLException e) {
            showError("Error al insertar venta", e.getMessage());
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

