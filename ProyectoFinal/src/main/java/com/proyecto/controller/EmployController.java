package com.proyecto.controller;

import com.proyecto.model.Employ;
import com.proyecto.repository.DatabaseOperations;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployController {

    // Método para cargar empleados
    public static void loadEmploys(ObservableList<Employ> employs) {
        try {
            employs.clear();
            ResultSet rs = DatabaseOperations.getEmploys();
            while (rs.next()) {
                employs.add(new Employ(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getString("fecha_contratacion")
                ));
            }
        } catch (SQLException e) {
            showError("Error al cargar empleados", e.getMessage());
        }
    }

    // Método para insertar un nuevo empleado
    public static void insertEmploy(String name, String position, String hireDate) {
        try {
            DatabaseOperations.insertEmploy(name, position, hireDate);
            showSuccess("Empleado insertado correctamente");
        } catch (SQLException e) {
            showError("Error al insertar empleado", e.getMessage());
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

