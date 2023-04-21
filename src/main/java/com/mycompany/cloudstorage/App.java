//Colours used for design:
// Purple :  #7113ab
// Grey :    #bababa

/**
 * @file
 * This file contains the definition of the App class, which is the main class of the Cloud Storage application.
 */

package com.mycompany.cloudstorage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The App class is the main class of the Cloud Storage application. It extends the JavaFX Application class and
 * defines the start() method, which is called when the application is launched. The start() method loads the login
 * user interface defined in login.fxml and displays it in a new Stage.
 */
public class App extends Application {
    
    /**
     * The start() method is called when the JavaFX application is launched. It loads the login.fxml file using FXMLLoader
     * and displays the user interface defined in the file in a new Scene, which is then displayed in a new Stage.
     *
     * @param stage The primary Stage object for the JavaFX application.
     * @throws IOException If an I/O error occurs while loading the login.fxml file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is the entry point for the JavaFX application. It simply calls the launch() method of the
     * Application class to start the JavaFX application.
     *
     * @param args The command line arguments passed to the JavaFX application.
     */
    public static void main(String[] args) {
        launch();
    }
}
    