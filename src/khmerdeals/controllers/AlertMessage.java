package khmerdeals.controllers;

import javafx.scene.control.Alert;

public class AlertMessage {
    void alertSaveMessageDialogBox(){
        //Alert information dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("New record has been save");
        alert.showAndWait();
    }

    void checkEmptySpaceInput(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please input character and number!");
        alert.showAndWait();
    }

    void deleteRecordAlertMessage(){

    }
}
