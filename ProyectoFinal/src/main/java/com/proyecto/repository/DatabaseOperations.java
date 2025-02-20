package com.proyecto.repository;

import com.proyecto.model.Product;
import com.proyecto.model.Employ;
import com.proyecto.model.Sell;
import com.proyecto.model.User;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseOperations {

    // Método para consultar productos
    public static ResultSet getProducts() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Productos";
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    // Método para insertar un producto
    public static void insertProduct(String name, String category, double price, int stock) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO Productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, category);
        stmt.setDouble(3, price);
        stmt.setInt(4, stock);
        stmt.executeUpdate();
    }

    // Método para consultar empleados
    public static ResultSet getEmploys() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Empleados";
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    // Método para insertar un empleado
    public static void insertEmploy(String name, String position, String hireDate) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO Empleados (nombre, cargo, fecha_contratacion) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, position);
        stmt.setString(3, hireDate);
        stmt.executeUpdate();
    }

    // Método para consultar ventas
    public static ResultSet getSells() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Ventas";
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    // Método para insertar una venta
    public static void insertSell(int idEmploy, int idProduct, int quantity, String sellDate, double total) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO Ventas (id_empleado, id_producto, cantidad, fecha_venta, total_venta) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, idEmploy);
        stmt.setInt(2, idProduct);
        stmt.setInt(3, quantity);
        stmt.setString(4, sellDate);
        stmt.setDouble(5, total);
        stmt.executeUpdate();
    }

    // Método para consultar usuarios
    public static ResultSet getUsers() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT id_usuario, usuario, email FROM Usuarios";
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    // Método para insertar un usuario con contraseña encriptada
    public static boolean insertUser(String username, String password, String email) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO Usuarios (usuario, contrasena, email) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, encryptPassword(password));
        stmt.setString(3, email);
        stmt.executeUpdate();
        return true;
    }

    // Método para encriptar contraseñas con SHA-256
    private static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    // Método para autenticar usuario
    public static User authenticateUser(String username, String password) {
        String query = "SELECT * FROM Usuarios WHERE usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id_usuario"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("email")
                );
                // Verificar la contraseña
                if (user.validatePassword(encryptPassword(password)))
                    return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
