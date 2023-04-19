/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cloudstorage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Button button_sign_up;
    
    @FXML
    private Button button_change_page;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML 
    private Button button_login;

    private Connection conn = null;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:/home/ntu-user/NetBeansProjects/CloudStorage/Cloud_Storage/lib/UsersDB.db");
            System.out.println("Database connected!");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database connection error: " + ex.getMessage());
        }
    }
    

    @FXML
    public void handleLoginButtonAction(ActionEvent event) {
        String username = tf_username.getText();
        String password = tf_password.getText();
        System.out.println("handleLoginButtonAction method executed");


        // Query the database for the user with the given username and password
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            // If a user with the given username and password exists, open the homepage
            if (rs.next()) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    System.out.println("homepage.fxml loaded successfully");


                    // Hide the login window
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Show an error message if the username or password is incorrect
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid username or password");
                alert.setContentText("Please try again.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
 private void openRegister() {
    try {
        // get a reference to the current stage
        Stage currentStage = (Stage) button_sign_up.getScene().getWindow();
        
        // load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        // close the current stage
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
 
 @FXML
 private void openChange() {
    try {
        // get a reference to the current stage
        Stage currentStage = (Stage) button_change_page.getScene().getWindow();
        
        // load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("change.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        // close the current stage
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}