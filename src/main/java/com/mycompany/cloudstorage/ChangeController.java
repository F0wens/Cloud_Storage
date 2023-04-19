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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class ChangeController implements Initializable {
    
    @FXML
    private Button button_go_back1;
    
    @FXML
private TextField tf_username3;

@FXML
private PasswordField tf_password3;

@FXML
private TextField tf_new_username;

@FXML
private PasswordField tf_new_password;

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
private void handleChangeDetails(ActionEvent event) {
    System.out.println("Handling change details...");
    String username = tf_username3.getText();
    String password = tf_password3.getText();
    String newUsername = tf_new_username.getText();
    String newPassword = tf_new_password.getText();

    // Verify the user's current login details
    try {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            System.out.println("Incorrect login details.");
            return;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return;
    }

    // Update the user's login details
    try {
        String query = "UPDATE users SET username = ?, password = ? WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newUsername);
        pstmt.setString(2, newPassword);
        pstmt.setString(3, username);
        pstmt.setString(4, password);
        pstmt.executeUpdate();
        System.out.println("Login details updated.");
    } catch (SQLException e) {
        System.out.println("Error executing SQL query: " + e.getMessage());
        e.printStackTrace();
        return;
    }
}

    
    
    @FXML
private void openLogIn1() {
    try {
        // get a reference to the current stage
        Stage currentStage = (Stage) button_go_back1.getScene().getWindow();
        
        // load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
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
