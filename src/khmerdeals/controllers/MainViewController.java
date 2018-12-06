package khmerdeals.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML private BorderPane mainBorderPane;
    @FXML private JFXButton postsButton;
    @FXML private JFXButton productsButton;
    @FXML private JFXButton logoutButton;
    @FXML private JFXButton dealsButton;
    @FXML private JFXTextField searchTextField;
    @FXML private JFXButton userButton;
    private LoadScreen loadScreen = new LoadScreen();


    @FXML
    void dealsButtonPressed() {
        loadScreen.loadNewView("../views/PostAndDealView.fxml", mainBorderPane);
    }

    @FXML
    void storeButtonPressed() {
        loadScreen.loadNewView("../views/StoreView.fxml", mainBorderPane);
    }


    @FXML
    void productsButtonPressed() {
        loadScreen.loadNewView("../views/ProductView.fxml", mainBorderPane);
    }


    @FXML
    void userButtonPressed() {

        loadScreen.loadNewView("../views/UserManagementView.fxml", mainBorderPane);
    }


//    @FXML
//    public void loginButtonPressed() {
//        loadScreen.loadNewView("../views/UserLoginView.fxml", mainBorderPane);
//
//    }
    @FXML
    public void logoutButtonPressed(){

    }
    @FXML
    public void loginButtonPressed() {
//        loadScreen.loadNewView("../views/LoginView.fxml", mainBorderPane);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/archieved/LoginView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadScreen.loadNewView("../views/PostAndDealView.fxml", mainBorderPane);
    }

}
