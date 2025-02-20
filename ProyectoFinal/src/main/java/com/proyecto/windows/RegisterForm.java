package com.proyecto.windows;

import com.proyecto.controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterForm extends Application {

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

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Label messageLabel = new Label(); // Mensaje de error o éxito

        Button registerButton = new Button("Register");

        grid.add(usernameField, 0, 0);
        grid.add(emailField, 0, 1);
        grid.add(passwordField, 0, 2);
        grid.add(confirmPasswordField, 0, 3);
        grid.add(messageLabel, 0, 4);
        grid.add(registerButton, 0, 5);

        // Acción del botón de registro
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String email = emailField.getText();

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match.");
                return;
            }

            boolean success = UserController.registerUser(username, password, email);
            if (success) {
                messageLabel.setText("Registration Successful!");
                
            } else {
                messageLabel.setText("Error during registration.");
            }
        });

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setTitle("Register Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
