package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Controller {
    @FXML
    public Text actionDisplay;

    /**
     * Process' table layout if the parsing of the directory in the
     * processFiles function process' the files properly
     *
     * @param actionEvent - the input/selection of a directory
     */
    public void returnInputDirectory(ActionEvent actionEvent) {
        Stage mainWindow = (Stage) actionDisplay.getScene().getWindow();
        boolean success = MainFunctions.processFiles(mainWindow);

        if (success){
            MainFunctions.displayData(mainWindow);
        }else{
            actionDisplay.setText("Invalid directory");
            actionDisplay.setTextAlignment(TextAlignment.RIGHT);
            actionDisplay.setFill(Color.rgb(200, 0, 0));
        }
    }
}
