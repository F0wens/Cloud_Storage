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
/**
 * ChangeController class that handles the user interface for changing login details.
 */
public class ChangeController implements Initializable {

    /**
     * Button for returning to the previous screen.
     */
    @FXML
    private Button button_go_back1;
    
    /**
     * Text field for entering the current username.
     */
    @FXML
    private TextField tf_username3;

    /**
     * Password field for entering the current password.
     */
    @FXML
    private PasswordField tf_password3;

    /**
     * Text field for entering the new username.
     */
    @FXML
    private TextField tf_new_username;

    /**
     * Password field for entering the new password.
     */
    @FXML
    private PasswordField tf_new_password;

    /**
     * Connection object for connecting to the SQLite database.
     */
    private Connection conn = null;

    /**
     * Initializes the controller by establishing a connection to the database.
     * 
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
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

    /**
     * Handles changing the user's login details by verifying the current login details and updating the database with the new login details.
     * 
     * @param event The action event that triggered the method.
     */
    @FXML
    private void handleChangeDetails(ActionEvent event) {
        System.out.println("Handling change details...");
        String username = tf_username3.getText();
        String password = tf_password3.getText();
        String newUsername = tf_new_username.getText();
        String newPassword = tf_new_password.getText();

        /*
        * Verify the user's current login details
        */ 
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

        /*
        * Update the user's login details
        */
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

    /**
     * Opens the login screen by loading the login.fxml file and creating a new stage.
     */
    
    
    @FXML
private void openLogIn1() {
    try {
        /*
        * get a reference to the current stage
        */
        Stage currentStage = (Stage) button_go_back1.getScene().getWindow();
        
        /*
        * load the new scene
        */
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        /*
        * close the current stage
        */
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}

