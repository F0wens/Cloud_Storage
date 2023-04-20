/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cloudstorage;


import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class HomepageController implements Initializable {
    
    @FXML 
    private Button button_log_out;
    
    @FXML
    private AnchorPane mainAnchorPane;
    
    @FXML
    private Button button_terminal;
    
    @FXML
    private TextField textField_command;
    
    @FXML
    private TextArea ta_terminal;
    
    @FXML
    private Button bt_files;
    
    @FXML
    private TextArea textArea_output;
    
    private List<String> commands = new ArrayList<String>();
    
    private Connection conn = null;
    
    
    
    
    
    
    
    @FXML
private void openLogOut() {
    try {
        // get a reference to the current stage
        Stage currentStage = (Stage) button_log_out.getScene().getWindow();
        
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // initialize any components and variables
        commands.add("mv");
        commands.add("cp");
        commands.add("ls");
        commands.add("mkdir");
        commands.add("ps");
        commands.add("whoami");
        commands.add("tree");
        commands.add("nano");
    } 
    
    
    @FXML
    private void handleRunCommand(ActionEvent event) {
        // get the command from the text field
        String command = textField_command.getText();
        
        // check if the command is in the list of allowed commands
        if (!commands.contains(command)) {
            textArea_output.appendText("Error: Command not allowed.\n");
            return;
        }
        
        // execute the command and get the output
        String output = executeCommand(command);
        
        // display the output in the text area
        textArea_output.appendText(output + "\n");
    }
    
    @FXML
private void handleTerminalButton(ActionEvent event) {
    try {
        // create a new terminal process
        ProcessBuilder pb = new ProcessBuilder("xterm");
        pb.start();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    private String executeCommand(String command) {
        // TODO: execute the command and return the output
        
        return "Command executed: " + command;
    }
    
    @FXML
    private void handleLogOut(ActionEvent event) {
        try {
            // get a reference to the current stage
            Stage currentStage = (Stage) mainAnchorPane.getScene().getWindow();
            
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
    
    @FXML
 private void handleFileManagement() {
    try {
        // get a reference to the current stage
        Stage currentStage = (Stage) bt_files.getScene().getWindow();
        
        // load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("files.fxml"));
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
