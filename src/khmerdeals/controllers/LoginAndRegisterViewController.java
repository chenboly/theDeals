package khmerdeals.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import khmerdeals.Main;
import khmerdeals.models.dao.UserManagementDAO;
import khmerdeals.models.dto.UserManagementDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginAndRegisterViewController implements Initializable {
    //
    @FXML private JFXTextField registerPhoneTextField;
    @FXML private ImageView registerImageViewField;
    @FXML private JFXButton registerUploadImageButton;
    @FXML private Label registerUsernameExistingLabel;
    @FXML private JFXButton registerButton;
    @FXML private JFXTextField registerFullnameTextField;
    @FXML private JFXTextField registerEmailTextField;
    @FXML private Label userRegisterConfirmPasswordNotMatchLabel;
    @FXML private JFXPasswordField registerPasswordField;
    @FXML private Label registerEmailAddressExisted;
    @FXML private JFXTextField registerUsernameTextField;
    @FXML private JFXPasswordField registerConfirmPasswordField;
    //
    @FXML private BorderPane borderPane;
    @FXML private AnchorPane loginForm;
    @FXML private Label incorrectLoginLabel;
    @FXML private JFXButton loginButton;
    //@FXML private JFXButton registerButton;
    @FXML private JFXTextField usernameLoginField;
    @FXML private JFXPasswordField passwordLoginField;
    Permission permission = new Permission();
    UserManagementDTO userManagementDTO = new UserManagementDTO();
    UserManagementDAO userManagementDAO = new UserManagementDAO();
    LoadScreen loadScreen = new LoadScreen();
//    UserManagementDTO userManagementDTO = new UserManagementDTO();
//    UserManagementDAO userManagementDAO = new UserManagementDAO();
    WriteImageFile writeImageFile = new WriteImageFile();
    AlertMessage alertMessage = new AlertMessage();

    @FXML
    void loginButtonPressed(ActionEvent event) {
        //TODO: assign getText file to local variable
        String loginUserName = usernameLoginField.getText().trim();
        String loginPassWord = passwordLoginField.getText().trim();

        //TODO: set local variable value to DTO
        userManagementDTO.setUserName(loginUserName);
        userManagementDTO.setPassWord(loginPassWord);

        //TODO: pass whole userDTO object to userDAO in order to check in SQL
        if(userManagementDAO.userLogin(userManagementDTO)!= null){
            //System.out.println(isLoginSuccess);
            //TODO:..
            incorrectLoginLabel.setVisible(false);
            passwordLoginField.requestFocus();
            System.out.println("correct");
            System.out.println(userManagementDTO);


            loadScreen.loadMainScreen("../views/MainView.fxml");
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

        }else{
            incorrectLoginLabel.setVisible(true);
            System.out.println("wrong");
        }
    }

//    @FXML
//    void registerButtonPressed(){
//        loadScreen.loadMainScreen("../views/UserRegisterView.fxml");
//        Stage stage = (Stage) loginButton.getScene().getWindow();
//        stage.close();
//
//    }




    @FXML void uploadImageButtonPressed() {
        writeImageFile.uploadImage(registerImageViewField);


    }

    @FXML
    void registerButtonPressed() {
        userManagementDTO.setFullName(registerFullnameTextField.getText().trim());
        String inputUserName = registerUsernameTextField.getText().trim();
        userManagementDTO.setUserName(inputUserName);
        boolean usernameExisted = userManagementDAO.checkUserName(inputUserName);
        //checkUserName(inputUserName);
        userManagementDTO.setPassWord(registerPasswordField.getText().trim());
        userManagementDTO.setPhone(registerPhoneTextField.getText().trim());
        String inputEmailAddress = registerEmailTextField.getText().trim();
        userManagementDTO.setEmail(inputEmailAddress);
        boolean emailAddressExisted = userManagementDAO.checkEmailAddress(inputEmailAddress);
        String defaultUserRole = "user";



        //write image file
        writeImageFile.writeImageFilePath(userManagementDTO);

        if(!registerPasswordField.getText().trim().equals(registerConfirmPasswordField.getText().trim())){
            userRegisterConfirmPasswordNotMatchLabel.setVisible(true);
            registerConfirmPasswordField.requestFocus();

        }else {
            if(userManagementDAO.insertNewUser(userManagementDTO, defaultUserRole)){
                registerUsernameExistingLabel.setVisible(false);
                userRegisterConfirmPasswordNotMatchLabel.setVisible(false);
                registerEmailAddressExisted.setVisible(false);
                clearUserRegister(true);
                writeImageFile.setDefaultImage(registerImageViewField);
                alertMessage.alertSaveMessageDialogBox();

            }else {
                if(usernameExisted && emailAddressExisted){
                    registerUsernameExistingLabel.setVisible(true);
                    registerEmailAddressExisted.setVisible(true);
                }
                else if (usernameExisted) {
                    registerEmailAddressExisted.setVisible(false);
                    registerUsernameExistingLabel.setVisible(true);
                }else{
                    registerUsernameExistingLabel.setVisible(false);
                    registerEmailAddressExisted.setVisible(true);
                }
            }
        }
    }


    void clearUserRegister(Boolean status){
        registerFullnameTextField.clear();
        registerUsernameTextField.clear();
        registerPasswordField.clear();
        registerConfirmPasswordField.clear();
        registerPhoneTextField.clear();
        registerEmailTextField.clear();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        permission.setLabelVisibility(false, incorrectLoginLabel, userRegisterConfirmPasswordNotMatchLabel, registerEmailAddressExisted,registerUsernameExistingLabel);

        //userRegisterConfirmPasswordNotMatchLabel.setVisible(false);
//        registerEmailAddressExisted.setVisible(false);
  //      registerUsernameExistingLabel.setVisible(false);
    }
}
