/**
 * @file FilesController.java
 * @brief This is the controller class for the Files tab of the application.
 */

package com.mycompany.cloudstorage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * @brief Controller class for the Files tab of the application
 */
public class FilesController implements Initializable {
    
    @FXML
    private TableView<FileEntry> tbl_files;
    
    @FXML
    private TableColumn<FileEntry, String> file_name;
    
    @FXML
    private TableColumn<FileEntry, Long> file_size;
    
    @FXML
    private TableColumn<FileEntry, String> file_create_dt;
    
    @FXML
    private TableColumn<FileEntry, String> file_mod_dt;
    
    @FXML
    private Button bt_go_back;
    
    @FXML
    private Button bt_copy;
    
    @FXML
    private Button bt_create_file;
    
    @FXML
    private TextField tf_fileName;
    
    @FXML
    private TextField tf_new_fileName;
    
    @FXML
    private TextArea ta_fileText;
    
    private Connection conn = null;
    
    /**
     * @brief Handles renaming of a file
     * @param event The event that triggered this method
     */
    @FXML
    private void changeFileName(ActionEvent event) {
        System.out.println("Handling file rename...");
        String fileName = tf_fileName.getText();
        String newFileName = tf_new_fileName.getText();
        File file = new File(fileName);
        File newFile = new File(newFileName);
        if (file.renameTo(newFile)) {
            System.out.println("File renamed successfully!");
        } else {
            System.out.println("Failed to rename the file!");
        }
        
        //Verify the current file name that is to be replaced
        try {
            String query = "SELECT * FROM userFiles WHERE fileName = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fileName);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Incorrect file name.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        
        //Update the file name
        try {
            String query = "UPDATE userFiles SET fileName = ? WHERE fileName = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newFileName);
            pstmt.setString(2, fileName);
            pstmt.executeUpdate();
            System.out.println("File name updated.");
        } catch (SQLException e) {
            System.out.println("Error changing file name: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
    }
    
    /**
     * @brief Handles creation of a new file
     * @param event The event that triggered this method
     */
    @FXML
    private void createNewFile(ActionEvent event) {
        try {
            // Create a new file using the file name from the text field
            String fileName = tf_fileName.getText().trim();
            String fileText = ta_fileText.getText().trim();
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName));
            myWriter.write(fileText);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            
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
    
    /**
     * Deletes a file from the database and the file system.
     * @param event The event that triggered this method.
     */
    @FXML
private void deleteFile(ActionEvent event) {
    try {
        // Get the file name to delete from the text field
        String fileName = tf_fileName.getText().trim();
        
        // Delete the file from the database
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM userFiles WHERE FileName = ?");
        pstmt.setString(1, fileName);
        pstmt.executeUpdate();
        
        //Delete the file itself
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file.");
        }
        
        // Refresh the table view by reloading the data from the database
        ObservableList<FileEntry> data = FXCollections.observableArrayList();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM userFiles");
        while (rs.next()) {
            String name = rs.getString("FileName");
            long size = rs.getLong("Size");
            String creationDate = rs.getString("CreationDate");
            String modificationDate = rs.getString("ModificationDate");
            data.add(new FileEntry(name, size, creationDate, modificationDate));
        }
        tbl_files.setItems(data);
    } catch (SQLException ex) {
        System.out.println("Database query error: " + ex.getMessage());
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
        
         // set up the columns in the table
        file_name.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        file_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        file_create_dt.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        file_mod_dt.setCellValueFactory(new PropertyValueFactory<>("modificationDate"));
            
            // load the data into the table
        ObservableList<FileEntry> data = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM userFiles");
            while (rs.next()) {
                String fileName = rs.getString("FileName");
                long size = rs.getLong("Size");
                String creationDate = rs.getString("CreationDate");
                String modificationDate = rs.getString("ModificationDate");
                data.add(new FileEntry(fileName, size, creationDate, modificationDate));
            }
        } catch (SQLException ex) {
            System.out.println("Database query error: " + ex.getMessage());
        }
        
        tbl_files.setItems(data);
    }
    
    private static class FileEntry {
        private final String fileName;
        private final long size;
        private final String creationDate;
        private final String modificationDate;
        
        public FileEntry(String fileName, long size, String creationDate, String modificationDate) {
            this.fileName = fileName;
            this.size = size;
            this.creationDate = creationDate;
            this.modificationDate = modificationDate;
        }

        public String getFileName() {
            return fileName;
        }

        public long getSize() {
            return size;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public String getModificationDate() {
            return modificationDate;
        }
    }
        

   /**
     * @brief this method is used to open the homepage of the application. 
     * It retrieves a reference to the current stage, loads the homepage.fxml file, creates a new stage with the homepage scene, and shows the new stage. Finally, it closes the current stage.
     */ 
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
