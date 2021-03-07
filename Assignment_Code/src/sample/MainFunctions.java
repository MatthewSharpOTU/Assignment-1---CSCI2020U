package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class MainFunctions {
    private static WordCounter trainCounter; //Used to parse through the train file and create test file probabilities

    /**
     *
     * @param primaryStage -   The stage set up
     * @return true - that the function runs accordingly (without errors caught)
     */
    // method used for accessing files
    public static boolean processFiles(Window primaryStage){
        // User Selects Directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);
        if (mainDirectory==null){return false;} //Returns failed attempt when no directory

        File trainDirectory = new File("./assignment1_data/data/train"); //Gets the train directory automatically

        // Parses through file and detects all words
        trainCounter = new WordCounter(); // Creates an instance of WordCounter Class
        try{
            trainCounter.parseFile(trainDirectory); // Parses through train directory files
            trainCounter.parseTestFile(mainDirectory); // Parses through test directory files, labelling them each with a spam percentage per file
        }catch(FileNotFoundException e) {
            System.err.println("Invalid input dir: " + mainDirectory.getAbsolutePath()); // catch block if error for incorrect input director
            e.printStackTrace();
        }
        return true;
    }

    // method used for training program data

    /**
     * Function used to confirm the existence of the train data
     * @return true statement that the train data is present
     */
    public static boolean trainData(){
        return true;
    }

    /**
     *  Class used to store the data that will be displayed
     *  Differs from TestFile in that the decimal formatted version of the probability is stored
     */
    public static class finalPercentages{
        private String filename;
        private String spamProbability;
        private String actualClass;

        // Constructor
        public finalPercentages(String f, String p, String c){
            filename = f;
            spamProbability = p;
            actualClass = c;
        }

        // Member Functions
        public String getActualClass() { return actualClass; }
        public String getSpamProbability() { return spamProbability; }
        public String getFilename() { return filename; }
    }

    // method used for displaying data
    /**
     * Function sets the stage for the table
     */
    public static void displayData(Stage primaryStage){
        ArrayList<TestFile> testPercentages = trainCounter.getTotalPercentages(); //Retrieves the calculated spam percentages and TestFile information

        ArrayList<finalPercentages> tablePercentages = new ArrayList<finalPercentages>(testPercentages.size());
        double truePositives = 0.0; // Used to store the amount of files that are spam and calculated to be spam
        double trueNegatives = 0.0; // Used to store the amount of files that are ham and calculated to be ham
        double falsePositives = 0.0; // Used to store the amount of files that are ham and calculated to be spam
        for (TestFile file: testPercentages){ // For loop iterates for each file evaluated in the test file
            if (file.getActualClass().equals("ham") && file.getSpamProbability() < 0.5){ // Conditional to see if a file is ham and calculated to be ham
                trueNegatives++;
            }
            else if (file.getActualClass().equals("ham") && file.getSpamProbability() > 0.5){ // Conditional to see if a file is ham and calculated to be spam
                falsePositives++;
            }
            else if (file.getActualClass().equals("spam") && file.getSpamProbability() > 0.5){ // Conditional to see if a file is spam and calculated to be spam
                truePositives++;
            }
            tablePercentages.add(new finalPercentages(file.getFilename(), file.getSpamProbRounded(),file.getActualClass())); // Adds the current files attributes to the arrayList that is used on displaying the table data
        }
        double accuracy = (truePositives+trueNegatives)/trainCounter.getFileCounts(); // Calculates accuracy of the spam probabilities
        double precision = truePositives/(falsePositives+truePositives); // Calculates the precision of spam files out of all calculated spam files
        DecimalFormat df = new DecimalFormat("0.00000"); //Formatting for the accuracy and precision

        // initialize new data table
        BorderPane mainPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        TableView<finalPercentages> table = new TableView<finalPercentages>();
        GridPane calculatedData = new GridPane();
        Label accuracyText = new Label("Accuracy: ");
        Label precisionText = new Label("Precision: ");
        TextField accuracyData = new TextField(df.format(accuracy));
        accuracyData.setDisable(true);
        TextField precisionData = new TextField(df.format(precision));
        precisionData.setDisable(true);

        // Construct new data table
        calculatedData.add(accuracyText, 0, 0);
        calculatedData.add(accuracyData, 1, 0);
        calculatedData.add(precisionText, 0, 1);
        calculatedData.add(precisionData, 1, 1);

        //File outputFile = new File("output.txt");
        ObservableList<finalPercentages> data = FXCollections.observableArrayList(tablePercentages);
        table.setItems(data);

        //setting up columns
        TableColumn<finalPercentages, String> fileColumn = new TableColumn<finalPercentages, String>("File");
        fileColumn.setCellValueFactory(new PropertyValueFactory("filename"));
        TableColumn<finalPercentages, String> actualClassColumn = new TableColumn<finalPercentages, String>("Actual Class");
        actualClassColumn.setCellValueFactory(new PropertyValueFactory("actualClass"));
        TableColumn<finalPercentages, String> probabilityColumn = new TableColumn<finalPercentages, String>("Spam Probability");
        probabilityColumn.setCellValueFactory(new PropertyValueFactory("spamProbability"));
        table.getColumns().addAll(fileColumn, actualClassColumn, probabilityColumn); // Sets the columns of data in the table

        mainPane.setCenter(table);
        mainPane.setBottom(calculatedData);

        primaryStage.setScene(new Scene(mainPane, 600, 500));

        // Display new data table
        primaryStage.show();
    }
}
