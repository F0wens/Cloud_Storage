package com.mycompany.cloudstorage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

/**
 * JavaFX App
 */
public class App extends Application {
    
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    @FXML
    private Button button_login;
    
    @FXML
    private TextField tf_username;
    
    @FXML
    private PasswordField tf_password;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.setController(new LoginController());
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        con = DBConnection.ConnectionDB();
    }
       
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT * from user_db WHERE user LIKE ? AND Pass LIKE ?; ";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, tf_username.getText());
            pst.setString(2, tf_password.getText());
            rs = pst.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Login Successfull!");
                
            }
        }
        catch(Exception e){
            
        }
    }
            
public static void main(String[] args) {
    launch(args);
}

}