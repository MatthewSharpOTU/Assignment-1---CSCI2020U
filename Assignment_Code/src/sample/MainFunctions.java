package sample;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFunctions {
    // method used for accessing files
    public static boolean processFiles(Window primaryStage){
        // User Selects Directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        if (mainDirectory==null){return false;} //Returns failed attempt when no directory

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
        return true;
    }

    // method used for training program data
    public static boolean trainData(){
        return true;
    }
}
