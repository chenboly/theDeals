package khmerdeals.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadScreen {
    public void loadNewView(String location, BorderPane borderPane){
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResource(location));
            borderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMainScreen(String location){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage();
            stage.setTitle("Hello World");
            stage.setScene(new Scene(root));
            //primaryStage.setMinHeight(760);
            //primaryStage.setMinWidth(1180);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public void loadMainScreen(String location){
//        Parent root = null;
//        try {
//            root = FXMLLoader.load(getClass().getResource(location));
//            Stage stage = new Stage();
//            stage.setTitle("Hello World");
//            stage.setScene(new Scene(root));
//            //primaryStage.setMinHeight(760);
//            //primaryStage.setMinWidth(1180);
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
