package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.*;

public class Main extends Application{

    // Called from the main method
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Prints a JavaFX Stage
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spam Master 3000"); // Sets title of stage
        primaryStage.setScene(new Scene(root, 500, 350)); // Sets size of stage
        primaryStage.show(); // Displays stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}
