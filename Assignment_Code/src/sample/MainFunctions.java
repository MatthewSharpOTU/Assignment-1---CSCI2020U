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
    private static WordCounter trainCounter;

    // method used for accessing files
    public static boolean processFiles(Window primaryStage){
        // User Selects Directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        File trainDirectory = new File("./assignment1_data/data/train");

        if (mainDirectory==null){return false;} //Returns failed attempt when no directory

        // Parses through file and detects all words
        trainCounter = new WordCounter(); // Creates an instance of WordCounter Class

        try{
            trainCounter.parseFile(trainDirectory); // Parses through train directory files
            trainCounter.parseTestFile(mainDirectory);
        }catch(FileNotFoundException e) {
            System.err.println("Invalid input dir: " + mainDirectory.getAbsolutePath()); // catch block if error for incorrect input director
            e.printStackTrace();
        }
        return true;
    }

    // method used for training program data
    public static boolean trainData(){
        return true;
    }

    public static class finalPercentages{
        private String filename;
        private String spamProbability;
        private String actualClass;

        public finalPercentages(String f, String p, String c){
            filename = f;
            spamProbability = p;
            actualClass = c;
        }

        public String getActualClass() { return actualClass; }
        public String getSpamProbability() { return spamProbability; }
        public String getFilename() { return filename; }
    }

    // method used for displaying data
    public static void displayData(Stage primaryStage){
        ArrayList<TestFile> testPercentages = trainCounter.getTotalPercentages();
        ArrayList<finalPercentages> tablePercentages = new ArrayList<finalPercentages>(testPercentages.size());
        double truePositives = 0.0;
        double trueNegatives = 0.0;
        double falsePositives = 0.0;
        for (TestFile file: testPercentages){
            if (file.getActualClass().equals("ham") && file.getSpamProbability() < 0.5){
                trueNegatives++;
            }
            else if (file.getActualClass().equals("ham") && file.getSpamProbability() > 0.5){
                falsePositives++;
            }
            else if (file.getActualClass().equals("spam") && file.getSpamProbability() > 0.5){
                truePositives++;
            }
            tablePercentages.add(new finalPercentages(file.getFilename(), file.getSpamProbRounded(),file.getActualClass()));
        }
        double accuracy = (truePositives+trueNegatives)/trainCounter.getFileCounts();
        double precision = truePositives/(falsePositives+truePositives);
        DecimalFormat df = new DecimalFormat("0.00000");

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
        table.getColumns().addAll(fileColumn, actualClassColumn, probabilityColumn);

        mainPane.setCenter(table);
        mainPane.setBottom(calculatedData);

        primaryStage.setScene(new Scene(mainPane, 600, 500));

        // Display new data table
        primaryStage.show();
    }

    public static class DataRow{
        private StringProperty file = new SimpleStringProperty();
        private StringProperty actualClass = new SimpleStringProperty();
        private DoubleProperty probability = new SimpleDoubleProperty();

        public final String getFile(){return file.get();}
        public final void setFile(String value){file.set(value);}
        public StringProperty fileProperty(){return file;}

        public final String getActualClass(){return actualClass.get();}
        public final void setActualClass(String value){actualClass.set(value);}
        public StringProperty actualClassProperty(){return actualClass;}

        public final Double getProbability(){return probability.get();}
        public final void setProbability(double value){probability.set(value);}
        public DoubleProperty probabilityProperty(){return probability;}
    }
}
