package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Controller {
    @FXML
    private Text actionDisplay;
    public void returnInputDirectory(ActionEvent actionEvent) {
        boolean success = MainFunctions.processFiles(actionDisplay.getScene().getWindow());

        if (success){

        }else{
            actionDisplay.setText("Invalid directory");
            actionDisplay.setTextAlignment(TextAlignment.RIGHT);
            actionDisplay.setFill(Color.rgb(200, 0, 0));
        }
    }
}
