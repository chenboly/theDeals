package khmerdeals.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import khmerdeals.models.dao.ChangePasswordDAO;
import khmerdeals.models.dao.UserManagementDAO;
import khmerdeals.models.dao.UserRoleDAO;
import khmerdeals.models.dto.ChangePasswordDTO;
import khmerdeals.models.dto.UserManagementDTO;
import khmerdeals.models.dto.UserRoleDTO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ResourceBundle;


public class UserManagementViewController implements Initializable {

    @FXML private JFXButton changePasswordButton;
    @FXML private JFXButton deleteButton;
    @FXML private JFXTextField phoneTextField;
    @FXML private ImageView imageViewField;
    @FXML private ImageView imageProfileDisplay;
    @FXML private JFXPasswordField loginPasswordField;
    @FXML private JFXPasswordField newPasswordField;
    @FXML private JFXTextField emailTextField;
    @FXML private Label registerConfirmPasswordNotMatchLabel;
    @FXML private Label newConfirmPasswordNotMatchLabel;
    @FXML private JFXPasswordField currentPasswordField;
    @FXML private JFXPasswordField confirmPasswordField;
    @FXML private Label currentPasswordNotCorrectLabel;
    @FXML private Label usernameExistingLabel;
    @FXML private Label emailAddressExistedLabel;
    @FXML private JFXTextField loginUsernameTextField;
    @FXML private Label loginIncorrectUsernameOrPasswordLabel;
    @FXML private JFXTextField fullnameTextField;
    @FXML private JFXButton loginButton;
    @FXML private JFXButton logoutButton;
    @FXML private JFXButton newButton;
    @FXML private JFXPasswordField passwordField;
    @FXML private TableView<UserManagementDTO> userListTableView;
    @FXML private TableColumn<UserManagementDTO, String> fullnameCol;
    @FXML private TableColumn<UserManagementDTO, String> usernameCol;
    @FXML private TableColumn<UserManagementDTO, String> phoneCol;
    @FXML private TableColumn<UserManagementDTO, String> emailCol;
    @FXML private JFXButton saveButton;
    @FXML private JFXButton uploadImageButton;
    @FXML private JFXPasswordField newConfirmPasswordField;
    @FXML private JFXTextField usernameTextField;
    @FXML private JFXButton usernameProfileField;
    @FXML private JFXComboBox <String> userRoleComboBox;
    @FXML private AnchorPane userProfileAnchorPane;
    @FXML private HBox userRegisterHbox;
    @FXML private HBox userListHbox;
    @FXML private Label fullnameProfileShowLabel;
    @FXML private Label usernameProfileShowLabel;
    @FXML private Label phoneProfileShowLabel;
    @FXML private Label emailProfileShowLabel;
    @FXML private Label createdDateProfileShowLabel;

    public  Boolean isLoginSuccess = false;
    private UserManagementDTO userManagementDTO = new UserManagementDTO();
    private UserManagementDAO userManagementDAO = new UserManagementDAO();
    private UserRoleDTO userRoleDTO = new UserRoleDTO();
    private UserRoleDAO userRoleDAO = new UserRoleDAO();
    private Permission permission = new Permission();
    private ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
    private ChangePasswordDAO changePasswordDAO = new ChangePasswordDAO();
    private UserManagementDTO selectedUserManagementDTO = new UserManagementDTO();
    private Boolean isAdminLogedIn = false;
    private WriteImageFile writeImageFile = new WriteImageFile();
    private AlertMessage alertMessage = new AlertMessage();


    @FXML
    void logoutButtonPressed(){

    }

    @FXML
    void changePasswordButtonPressed() {
        if(isAdminLogedIn){
            //admin change password on each user
            if(!newPasswordField.getText().trim().equals(newConfirmPasswordField.getText().trim())){
                permission.setLabelVisibility(true, newConfirmPasswordNotMatchLabel);
                newConfirmPasswordField.requestFocus();
            }else{
                permission.setLabelVisibility(false, newConfirmPasswordNotMatchLabel);
                changePasswordDTO.setNewPassword(newPasswordField.getText().trim());
                changePasswordDTO.setUserId(selectedUserManagementDTO.getId());
                System.out.println(selectedUserManagementDTO.getId());
                //call changepassword DAO
                changePasswordDAO.changeAdminPassword(changePasswordDTO);
                newPasswordField.clear();
                newConfirmPasswordField.clear();
            }
        }else{
            changePasswordDTO.setCurrentPassword(currentPasswordField.getText().trim());
            changePasswordDTO.setNewPassword(newPasswordField.getText().trim());
            changePasswordDTO.setUserId(userManagementDAO.userLogedin.getId());
            System.out.println(userManagementDAO.userLogedin.getId());
            if(!newPasswordField.getText().trim().equals(newConfirmPasswordField.getText().trim())){
                permission.setLabelVisibility(true, newConfirmPasswordNotMatchLabel);
                newConfirmPasswordField.requestFocus();
            }else{
                permission.setLabelVisibility(false, newConfirmPasswordNotMatchLabel);
                //call change user password
                changePasswordDAO.userChangeOwnPassword(changePasswordDTO);
                currentPasswordField.clear();
                newPasswordField.clear();
                newConfirmPasswordField.clear();
            }

        }
    }

    @FXML void getUserIdOnTableViewClicked(){
        selectedUserManagementDTO = userListTableView.getSelectionModel().getSelectedItem();
        changePasswordButton.setText("Reset");
        permission.setButtonVisibility(true, changePasswordButton);
        permission.setPasswordFieldVisibility(true, newPasswordField, newConfirmPasswordField);


    }
    @FXML void uploadImageButtonPressed(){
        writeImageFile.uploadImage(imageViewField);

    }

    @FXML
    void newButtonPressed() {
        setFieldDisable(false);
        uploadImageButton.setDisable(false);

    }

    @FXML
    void saveButtonPressed() {
        userManagementDTO.setFullName(fullnameTextField.getText().trim());
        String username = usernameTextField.getText().trim();
        userManagementDTO.setUserName(username);
        boolean usernameExisted = userManagementDAO.checkUserName(username);
        userManagementDTO.setPassWord(passwordField.getText().trim());
        userManagementDTO.setPhone(phoneTextField.getText().trim());
        String email = emailTextField.getText().trim();
        userManagementDTO.setEmail(email);
        boolean emailAddressExisted = userManagementDAO.checkEmailAddress(email);

        String user_Role = userRoleComboBox.getSelectionModel().getSelectedItem();

        //write image file
        writeImageFile.writeImageFilePath(userManagementDTO);

        if(!passwordField.getText().trim().equals(confirmPasswordField.getText().trim())){
            permission.setLabelVisibility(true, registerConfirmPasswordNotMatchLabel);
            confirmPasswordField.requestFocus();

        }else {
            if(userManagementDAO.insertNewUser(userManagementDTO, user_Role)){
                permission.setLabelVisibility(false, registerConfirmPasswordNotMatchLabel, usernameExistingLabel, emailAddressExistedLabel);
                alertMessage.alertSaveMessageDialogBox();
                clearInsertTextField(true);
                writeImageFile.setDefaultImage(imageViewField);
                setFieldDisable(true);
                newButton.setDisable(false);
                saveButton.setDisable(true);
                uploadImageButton.setDisable(true);
                userListTableView.setItems(userManagementDAO.userLoginDTOObservableList());
            }else {
                //TODO:Check
                {
                    if(usernameExisted && emailAddressExisted){
                        permission.setLabelVisibility(true, usernameExistingLabel, emailAddressExistedLabel);
                    }
                    else if (usernameExisted) {
                        permission.setLabelVisibility(false, emailAddressExistedLabel);
                        permission.setLabelVisibility(true, usernameExistingLabel);
                    }else{
                        permission.setLabelVisibility(false, usernameExistingLabel);
                        permission.setLabelVisibility(true, emailAddressExistedLabel);
                    }
                }

                }


            }
    }

    @FXML
    void deleteButtonPressed() {

    }

    void userProfileInformationDisplay(){
        userProfileAnchorPane.setVisible(true);
        fullnameProfileShowLabel.setText(userManagementDAO.userLogedin.getFullName());
        usernameProfileShowLabel.setText(userManagementDAO.userLogedin.getUserName());
        phoneProfileShowLabel.setText(userManagementDAO.userLogedin.getPhone());
        emailProfileShowLabel.setText(userManagementDAO.userLogedin.getEmail());
        createdDateProfileShowLabel.setText(String.valueOf(userManagementDAO.userLogedin.getCreated_date()));
        //createdDateProfileShowLabel.setText(String.valueOf(Date.valueOf(userManagementDAO.userLogedin.getCreated_date().toString())));
        //set image to the imageProfileDisplay box
        String oldImageUrl = userManagementDAO.userLogedin.getImage_url();
        if (oldImageUrl == null || oldImageUrl == "")
            oldImageUrl = "/Users/chenboly/opt/TheDeals/default_image/image-not-available.png";
        File file = new File(oldImageUrl);

        //check if imageURL is null or image is deleted
        if (!file.exists())
            file = new File("/Users/chenboly/opt/TheDeals/default_image/image-not-available.png");
        //convert image to string and set into the imageView
        Image image = new Image(file.toURI().toString());
        imageProfileDisplay.setImage(image);

//        System.out.println("print: "+userManagementDTO);
//        System.out.println("print 2: "+userManagementDAO.userLogedin);
//        System.out.println("image url: " + oldImageUrl);
    }

    void clearInsertTextField(Boolean status){
        fullnameTextField.clear();
        usernameTextField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        phoneTextField.clear();
        emailTextField.clear();
        userRoleComboBox.getSelectionModel().clearSelection();
    }
    void setFieldDisable(Boolean status){
        fullnameTextField.setDisable(status);
        usernameTextField.setDisable(status);
        passwordField.setDisable(status);
        confirmPasswordField.setDisable(status);
        phoneTextField.setDisable(status);
        emailTextField.setDisable(status);
        userRoleComboBox.setDisable(status);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        userListTableView.setItems(userManagementDAO.userLoginDTOObservableList());
        userRoleComboBox.getItems().addAll(userRoleDAO.roleList());
        userProfileAnchorPane.setVisible(true);
        userProfileInformationDisplay();

        if(permission.hasRoleAdmin()){
            isAdminLogedIn = true;
            permission.setLabelVisibility(false, usernameExistingLabel, registerConfirmPasswordNotMatchLabel, emailAddressExistedLabel, currentPasswordNotCorrectLabel, newConfirmPasswordNotMatchLabel);
            permission.setPasswordFieldVisibility(false, currentPasswordField);

        }else{
            permission.setLabelVisibility(false, currentPasswordNotCorrectLabel, newConfirmPasswordNotMatchLabel);
            permission.setHboxVisibility(false, userListHbox, userRegisterHbox);

        }
    }
}


