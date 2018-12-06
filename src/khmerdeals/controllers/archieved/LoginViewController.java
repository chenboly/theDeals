//package khmerdeals.controllers;
//
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXPasswordField;
//import com.jfoenix.controls.JFXTextField;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import khmerdeals.models.dao.UserManagementDAO;
//import khmerdeals.models.dto.UserManagementDTO;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class LoginViewControllerCopy implements Initializable {
//
//    @FXML private BorderPane borderPane;
//    @FXML private AnchorPane loginForm;
//    @FXML private Label incorrectLoginLabel;
//    @FXML private JFXButton loginButton;
//    @FXML private JFXButton registerButton;
//    @FXML private JFXTextField usernameLoginField;
//    @FXML private JFXPasswordField passwordLoginField;
//    Permission permission = new Permission();
//    UserManagementDTO userManagementDTO = new UserManagementDTO();
//    UserManagementDAO userManagementDAO = new UserManagementDAO();
//    LoadScreen loadScreen = new LoadScreen();
//
//    @FXML
//    void loginButtonPressed(ActionEvent event) {
//        //TODO: assign getText file to local variable
//        String loginUserName = usernameLoginField.getText().trim();
//        String loginPassWord = passwordLoginField.getText().trim();
//
//        //TODO: set local variable value to DTO
//        userManagementDTO.setUserName(loginUserName);
//        userManagementDTO.setPassWord(loginPassWord);
//
//        //TODO: pass whole userDTO object to userDAO in order to check in SQL
//        if(userManagementDAO.userLogin(userManagementDTO)!= null){
//            //System.out.println(isLoginSuccess);
//            //TODO:..
//            incorrectLoginLabel.setVisible(false);
//            passwordLoginField.requestFocus();
//            System.out.println("correct");
//            System.out.println(userManagementDTO);
//
//            loadScreen.loadMainScreen("../views/MainView.fxml");
//            Stage stage = (Stage) loginButton.getScene().getWindow();
//            stage.close();
//
//        }else{
//            incorrectLoginLabel.setVisible(true);
//            System.out.println("wrong");
//        }
//    }
//
//    @FXML
//    void registerButtonPressed(){
//        loadScreen.loadMainScreen("../views/UserRegisterView.fxml");
//        Stage stage = (Stage) loginButton.getScene().getWindow();
//        stage.close();
//
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        permission.setLabelVisibility(false, incorrectLoginLabel);
//    }
//}
