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
import java.util.Map;
import java.util.Scanner;

public class MainFunctions {
    // method used for accessing files
    public static boolean processFiles(Window primaryStage){
        // User Selects Directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        File trainDirectory = new File("./assignment1_data/data/train");

        if (mainDirectory==null){return false;} //Returns failed attempt when no directory

        // Parses through file and detects all words
        WordCounter wordCounter = new WordCounter(); // Creates an instance of WordCounter Class
        WordCounter trainCounter = new WordCounter();
        try{
            wordCounter.parseFile(mainDirectory); // Parses through the choosen directory files
            trainCounter.parseFile(trainDirectory); // Parses through train directory files
            trainCounter.outputWordCount(1, new File("output.txt")); // Outputs all single words into a txt file
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

    // method used for displaying data
    public static void displayData(Stage primaryStage){
        // initialize new data table
        BorderPane mainPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        TableView<DataRow> table = new TableView<DataRow>();
        GridPane calculatedData = new GridPane();
        Label accuracyText = new Label("Accuracy: ");
        Label precisionText = new Label("Precision: ");
        TextField accuracyData = new TextField("420");
        accuracyData.setDisable(true);
        TextField precisionData = new TextField("testing");
        precisionData.setDisable(true);

        // Construct new data table
        calculatedData.add(accuracyText, 0, 0);
        calculatedData.add(accuracyData, 1, 0);
        calculatedData.add(precisionText, 0, 1);
        calculatedData.add(precisionData, 1, 1);
        File outputFile = new File("output.txt");
        try {
            Scanner fileReader = new Scanner(outputFile);
            //setting up rows
            ObservableList<DataRow> allData = FXCollections.observableArrayList();
            while (fileReader.hasNextLine()) {
                String[] rowData = fileReader.nextLine().split(":");
                DataRow data = new DataRow();
                if (rowData[0]!=null){data.setFile(rowData[0]);}
                if (rowData[1]!=null){data.setActualClass(rowData[1]);}
                if (rowData.length>2){data.setProbability(Double.parseDouble(rowData[2]));}
                allData.add(data);
            }
            table.setItems(allData);

            //setting up columns
            TableColumn<DataRow, String> fileColumn = new TableColumn<DataRow, String>("File");
            fileColumn.setCellValueFactory(new PropertyValueFactory("file"));
            TableColumn<DataRow, String> actualClassColumn = new TableColumn<DataRow, String>("Actual Class");
            actualClassColumn.setCellValueFactory(new PropertyValueFactory("actualClass"));
            TableColumn<DataRow, String> probabilityColumn = new TableColumn<DataRow, String>("Spam Probability");
            probabilityColumn.setCellValueFactory(new PropertyValueFactory("probability"));
            table.getColumns().addAll(fileColumn, actualClassColumn, probabilityColumn);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

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
