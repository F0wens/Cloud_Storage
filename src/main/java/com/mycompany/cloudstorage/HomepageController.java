/**
 * @file HomepageController.java
 * @brief This is the controller class for the homepage of a cloud storage application.
 * 
 * The class handles events triggered by the user interface components and executes the corresponding actions.
 * 
 * @author [Your Name]
 * @date [Date]
 */

package com.mycompany.cloudstorage;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.sql.Connection;
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

/**
 * @class HomepageController
 * @brief This is the controller class for the homepage of a cloud storage application.
 */
public class HomepageController implements Initializable {
    
    @FXML 
    private Button button_log_out; ///< A Button component that is used to log out the user.
    
    @FXML
    private AnchorPane mainAnchorPane; ///< An AnchorPane component that is used as the main container for the user interface.
    
    @FXML
    private Button button_terminal; ///< A Button component that is used to open a new terminal process.
    
    @FXML
    private TextField textField_command; ///< A TextField component that is used to input commands to the terminal.
    
    @FXML
    private TextArea ta_terminal; ///< A TextArea component that displays the output of the terminal.
    
    @FXML
    private Button bt_files; ///< A Button component that is used to navigate to the file management section of the application.
    
    @FXML
    private TextArea textArea_output; ///< A TextArea component that displays the output of executed commands.
    
    private List<String> commands = new ArrayList<String>(); ///< A List of allowed commands for the terminal.
    
    private Connection conn = null; ///< A JDBC Connection object for communicating with the database.
    
    /**
     * @brief This method loads the login scene and closes the current scene.
     * 
     * This method is triggered when the user clicks the log out button.
     */
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

    
    /**
     * @brief This method initializes the components and variables of the class.
     * 
     * This method is called when the class is loaded.
     * It initializes the list of allowed commands for the terminal.
     * 
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
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
    
    /**
     * Handles the "handleRunCommand" button click event.
     * 
     * @param event The ActionEvent that triggered the event.
     */
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
