package com.proyecto.controller;

import com.proyecto.model.User;
import com.proyecto.repository.DatabaseOperations;

import java.sql.SQLException;

public class UserController {

    // Método para autenticar al usuario
    public static User authenticate(String username, String password) {
        return DatabaseOperations.authenticateUser(username, password);
    }

    // Método para registrar un nuevo usuario
    public static boolean registerUser(String username, String password, String email) {
        try {
            return DatabaseOperations.insertUser(username, password, email);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
