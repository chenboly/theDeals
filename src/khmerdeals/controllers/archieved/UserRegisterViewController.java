//package khmerdeals.controllers;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXPasswordField;
//import com.jfoenix.controls.JFXTextField;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.image.ImageView;
//import khmerdeals.models.dao.UserManagementDAO;
//import khmerdeals.models.dto.UserManagementDTO;
//
//import java.net.URL;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ResourceBundle;
//
//public class UserRegisterViewController implements Initializable {
//    @FXML private JFXTextField registerPhoneTextField;
//    @FXML private ImageView registerImageViewField;
//    @FXML private JFXButton registerUploadImageButton;
//    @FXML private Label registerUsernameExistingLabel;
//    @FXML private JFXButton registerButton;
//    @FXML private JFXTextField registerFullnameTextField;
//    @FXML private JFXTextField registerEmailTextField;
//    @FXML private Label userRegisterConfirmPasswordNotMatchLabel;
//    @FXML private JFXPasswordField registerPasswordField;
//    @FXML private Label registerEmailAddressExisted;
//    @FXML private JFXTextField registerUsernameTextField;
//    @FXML private JFXPasswordField registerConfirmPasswordField;
//    UserManagementDTO userManagementDTO = new UserManagementDTO();
//    UserManagementDAO userManagementDAO = new UserManagementDAO();
//    WriteImageFile writeImageFile = new WriteImageFile();
//    AlertMessage alertMessage = new AlertMessage();
//
//
//
//    @FXML void uploadImageButtonPressed() {
//        writeImageFile.uploadImage(registerImageViewField);
//
//
//    }
//
//    @FXML
//    void registerButtonPressed() {
//        userManagementDTO.setFullName(registerFullnameTextField.getText().trim());
//        String inputUserName = registerUsernameTextField.getText().trim();
//        userManagementDTO.setUserName(inputUserName);
//        boolean usernameExisted = userManagementDAO.checkUserName(inputUserName);
//        //checkUserName(inputUserName);
//        userManagementDTO.setPassWord(registerPasswordField.getText().trim());
//        userManagementDTO.setPhone(registerPhoneTextField.getText().trim());
//        String inputEmailAddress = registerEmailTextField.getText().trim();
//        userManagementDTO.setEmail(inputEmailAddress);
//        boolean emailAddressExisted = userManagementDAO.checkEmailAddress(inputEmailAddress);
//        String defaultUserRole = "user";
//
//
//
//        //write image file
//        writeImageFile.writeImageFilePath(userManagementDTO);
//
//        if(!registerPasswordField.getText().trim().equals(registerConfirmPasswordField.getText().trim())){
//            userRegisterConfirmPasswordNotMatchLabel.setVisible(true);
//            registerConfirmPasswordField.requestFocus();
//
//        }else {
//            if(userManagementDAO.insertNewUser(userManagementDTO, defaultUserRole)){
//                registerUsernameExistingLabel.setVisible(false);
//                userRegisterConfirmPasswordNotMatchLabel.setVisible(false);
//                registerEmailAddressExisted.setVisible(false);
//                clearUserRegister(true);
//                writeImageFile.setDefaultImage(registerImageViewField);
//                alertMessage.alertSaveMessageDialogBox();
//
//            }else {
//                    if(usernameExisted && emailAddressExisted){
//                        registerUsernameExistingLabel.setVisible(true);
//                        registerEmailAddressExisted.setVisible(true);
//                    }
//                    else if (usernameExisted) {
//                        registerEmailAddressExisted.setVisible(false);
//                        registerUsernameExistingLabel.setVisible(true);
//                    }else{
//                        registerUsernameExistingLabel.setVisible(false);
//                        registerEmailAddressExisted.setVisible(true);
//                    }
//                }
//        }
//    }
//
//
//    void clearUserRegister(Boolean status){
//        registerFullnameTextField.clear();
//        registerUsernameTextField.clear();
//        registerPasswordField.clear();
//        registerConfirmPasswordField.clear();
//        registerPhoneTextField.clear();
//        registerEmailTextField.clear();
//
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        userRegisterConfirmPasswordNotMatchLabel.setVisible(false);
//        registerEmailAddressExisted.setVisible(false);
//        registerUsernameExistingLabel.setVisible(false);
//    }
//}
//
