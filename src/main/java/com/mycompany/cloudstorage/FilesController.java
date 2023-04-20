/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cloudstorage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FilesController implements Initializable {
    
    @FXML
    private Button bt_go_back;
    @FXML
    private Button bt_create_file;
    @FXML
    private TextField tf_fileName;
    @FXML
    private TextArea ta_fileText;
    
    private Connection conn = null;
    
    @FXML
    private void createNewFile(ActionEvent event) {
        try {
            // Create a new file using the file name from the text field
            String fileName = tf_fileName.getText().trim();
            String fileText = ta_fileText.getText().trim();
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(fileText);
            writer.close();
            
            // Get the file size in bytes
            long fileSize = Files.size(Paths.get(fileName));
            
            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String creationDate = now.format(formatter);
            String modificationDate = creationDate;
            
            // Insert the file information into the database
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO userFiles (FileName, FileContent, Size, CreationDate, ModificationDate, user_id) VALUES (?, ?, ?, ?, ?,?)");
            pstmt.setString(1, fileName);
            pstmt.setString(2, fileText);
            pstmt.setLong(3, fileSize);
            pstmt.setString(4, creationDate);
            pstmt.setString(5, modificationDate);
            pstmt.executeUpdate();
            
            // Close the current stage
            Stage stage = (Stage) bt_create_file.getScene().getWindow();
            stage.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:/home/ntu-user/NetBeansProjects/CloudStorage/Cloud_Storage/lib/UsersDB.db");
            System.out.println("Database connected!");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database connection error: " + ex.getMessage());
        }
    }

    
@FXML
private void openHomepage() {
    try {
        // get a reference to the current stage
        Stage currentStage = (Stage) bt_go_back.getScene().getWindow();
        
        // load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
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
