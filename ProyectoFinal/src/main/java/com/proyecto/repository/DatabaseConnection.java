package com.proyecto.repository;

import java.sql.*;
import java.io.File;

public class DatabaseConnection {
    private static final String DB_PATH = "database.db"; // Ruta relativa de la base de datos
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    // Datos predeterminados para cada tabla
    private static final String INSERT_USUARIOS = 
        "INSERT INTO Usuarios (usuario, contrasena, email) VALUES " +
        "('usuario1', 'password1', 'usuario1@example.com')," +
        "('usuario2', 'password2', 'usuario2@example.com')," +
        "('usuario3', 'password3', 'usuario3@example.com')," +
        "('usuario4', 'password4', 'usuario4@example.com')," +
        "('usuario5', 'password5', 'usuario5@example.com');";

    private static final String INSERT_PRODUCTOS = 
        "INSERT INTO Productos (nombre, categoria, precio, stock) VALUES " +
        "('Producto1', 'Categoria1', 10.0, 100)," +
        "('Producto2', 'Categoria2', 20.0, 150)," +
        "('Producto3', 'Categoria3', 30.0, 200)," +
        "('Producto4', 'Categoria4', 40.0, 250)," +
        "('Producto5', 'Categoria5', 50.0, 300);";

    private static final String INSERT_EMPLEADOS = 
        "INSERT INTO Empleados (nombre, cargo, fecha_contratacion) VALUES " +
        "('Empleado1', 'Gerente', '2022-01-01')," +
        "('Empleado2', 'Vendedor', '2022-02-01')," +
        "('Empleado3', 'Contador', '2022-03-01')," +
        "('Empleado4', 'Asistente', '2022-04-01')," +
        "('Empleado5', 'Gerente', '2022-05-01');";

    private static final String INSERT_VENTAS = 
        "INSERT INTO Ventas (id_empleado, id_producto, cantidad, fecha_venta, total_venta) VALUES " +
        "(1, 1, 2, '2023-02-14', 20.0)," +
        "(2, 2, 3, '2023-02-14', 60.0)," +
        "(3, 3, 1, '2023-02-14', 30.0)," +
        "(4, 4, 5, '2023-02-14', 200.0)," +
        "(5, 5, 10, '2023-02-14', 500.0);";

    public static Connection getConnection() throws SQLException {
        File dbFile = new File(DB_PATH);
        boolean dbExists = dbFile.exists();

        if (!dbExists) {
            System.out.println("Creando base de datos...");
        }

        Connection connection = DriverManager.getConnection(DB_URL);

        try (Statement stmt = connection.createStatement()) {
            // Crear tablas si no existen
            createTableIfNotExists(stmt, "Usuarios",
                "CREATE TABLE IF NOT EXISTS Usuarios (" +
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario VARCHAR(100) NOT NULL UNIQUE, " +
                "contrasena VARCHAR(256) NOT NULL, " +
                "email VARCHAR(150) NOT NULL UNIQUE);");

            createTableIfNotExists(stmt, "Productos",
                "CREATE TABLE IF NOT EXISTS Productos (" +
                "id_producto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre VARCHAR(255) NOT NULL, " +
                "categoria VARCHAR(100) NOT NULL, " +
                "precio REAL NOT NULL, " +
                "stock INTEGER NOT NULL);");

            createTableIfNotExists(stmt, "Empleados",
                "CREATE TABLE IF NOT EXISTS Empleados (" +
                "id_empleado INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre VARCHAR(150) NOT NULL, " +
                "cargo VARCHAR(100) NOT NULL, " +
                "fecha_contratacion TEXT NOT NULL);");

            createTableIfNotExists(stmt, "Ventas",
                "CREATE TABLE IF NOT EXISTS Ventas (" +
                "id_venta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_empleado INTEGER NOT NULL, " +
                "id_producto INTEGER NOT NULL, " +
                "cantidad INTEGER NOT NULL, " +
                "fecha_venta TEXT NOT NULL, " +
                "total_venta REAL NOT NULL, " +
                "FOREIGN KEY (id_empleado) REFERENCES Empleados(id_empleado), " +
                "FOREIGN KEY (id_producto) REFERENCES Productos(id_producto));");

            // Insertar datos predeterminados si las tablas están vacías
            insertDefaultDataIfEmpty(connection, "Usuarios", INSERT_USUARIOS);
            insertDefaultDataIfEmpty(connection, "Productos", INSERT_PRODUCTOS);
            insertDefaultDataIfEmpty(connection, "Empleados", INSERT_EMPLEADOS);
            insertDefaultDataIfEmpty(connection, "Ventas", INSERT_VENTAS);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al conectar a la base de datos.");
        }

        return connection;
    }

    // Método para crear una tabla solo si no existe
    private static void createTableIfNotExists(Statement stmt, String tableName, String createSQL) throws SQLException {
        String checkTableSQL = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';";
        try (ResultSet rs = stmt.executeQuery(checkTableSQL)) {
            if (!rs.next()) { // Si no hay resultado, la tabla no existe
                System.out.println("Creando tabla " + tableName + "...");
                stmt.execute(createSQL);
                System.out.println("Tabla " + tableName + " creada correctamente.");
            }
        }
    }

    // Método para insertar datos predeterminados si la tabla está vacía
    private static void insertDefaultDataIfEmpty(Connection connection, String tableName, String insertSQL) throws SQLException {
        String countSQL = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("La tabla " + tableName + " está vacía. Insertando datos predeterminados...");
                try (Statement insertStmt = connection.createStatement()) {
                    insertStmt.execute(insertSQL); // Insertar los datos predeterminados
                    System.out.println("Datos insertados en la tabla " + tableName + ".");
                }
            }
        }
    }

	public void insertEmploy(String anyString, String anyString2, String anyString3) {
		// TODO Auto-generated method stub
		
	}
}
