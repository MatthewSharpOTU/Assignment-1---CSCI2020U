package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;



public class Main extends Application{

    // Called from the main method
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Prints a JavaFX Stage
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab04 Solution");
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();

        // User Selects Directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        // Parses through file and detects all words
        WordCounter wordCounter = new WordCounter(); // Creates an instance of WordCounter Class
        try{
            wordCounter.parseFile(mainDirectory); // Parses through the directory files
            wordCounter.outputWordCount(1, new File("output.txt")); // Outputs all single words into a txt file
        }catch(FileNotFoundException e){
            System.err.println("Invalid input dir: " + mainDirectory.getAbsolutePath()); // catch block if error for incorrect input director
            e.printStackTrace();
        }catch(IOException e){ // catch block for output file already existing
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
