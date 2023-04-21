/**
 * @file RegisterController.java
 * @brief This file contains the implementation of the RegisterController class.
 */

package com.mycompany.cloudstorage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * @class RegisterController
 * @brief This class is responsible for managing the registration screen.
 */
public class RegisterController implements Initializable {
    
    @FXML
    private Button button_go_back;
     
    @FXML
    private TextField tf_username2;

    @FXML
    private PasswordField tf_password2;
    
    private Connection conn = null;
    
    /**
     * @brief Initializes the controller class and sets up the database connection.
     * @param url The URL to the FXML file for the registration screen.
     * @param rb  The resource bundle for the registration screen.
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
     * @brief Handles the user clicking the "Create Account" button by inserting their username and password into the database.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        String usernameStr = tf_username2.getText();
        String passwordStr = tf_password2.getText();

        /*
        * Insert the new user's data into the database
        */
        try {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, usernameStr);
                pstmt.setString(2, passwordStr);
                pstmt.executeUpdate();
            }
            System.out.println("New user added to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
    
    /**
     * @brief Handles the user clicking the "Go back to login" button by opening the login screen.
     */
    @FXML
    private void openLogIn() {
        try {
            /*
            * get a reference to the current stage
            */
            Stage currentStage = (Stage) button_go_back.getScene().getWindow();
            
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







