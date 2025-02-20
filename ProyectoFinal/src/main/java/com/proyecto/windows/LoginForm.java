package com.proyecto.windows;

import com.proyecto.controller.UserController;
import com.proyecto.model.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label messageLabel = new Label(); // Mensaje de error

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        grid.add(usernameField, 0, 0);
        grid.add(passwordField, 0, 1);
        grid.add(messageLabel, 0, 2);
        grid.add(loginButton, 0, 3);
        grid.add(registerButton, 0, 4);

        // Acción del botón de login
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User user = UserController.authenticate(username, password);
            if (UserController.authenticate(username, password) != null) {
                messageLabel.setText("Login Successful!");
                MainWindow mainWindow = new MainWindow();
                mainWindow.start(new Stage());
                primaryStage.close();
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        });

        // Acción del botón de registro
        registerButton.setOnAction(event -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.start(new Stage()); 
        });

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
