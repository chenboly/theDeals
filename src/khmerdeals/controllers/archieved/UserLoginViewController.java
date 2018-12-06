//package khmerdeals.controllers;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXComboBox;
//import com.jfoenix.controls.JFXPasswordField;
//import com.jfoenix.controls.JFXTextField;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import khmerdeals.models.dao.ChangePasswordDAO;
//import khmerdeals.models.dao.UserManagementDAO;
//import khmerdeals.models.dao.UserRoleDAO;
//import khmerdeals.models.dto.ChangePasswordDTO;
//import khmerdeals.models.dto.UserManagementDTO;
//import khmerdeals.models.dto.UserRoleDTO;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//
//public class UserLoginViewController implements Initializable {
//
//    @FXML private JFXButton changePasswordButton;
//    @FXML private JFXButton deleteButton;
//    @FXML private JFXTextField phoneTextField;
//    @FXML private ImageView imageViewField;
//    @FXML private JFXPasswordField loginPasswordField;
//    @FXML private JFXPasswordField newPasswordField;
//    @FXML private JFXTextField emailTextField;
//    @FXML private Label registerConfirmPasswordNotMatchLabel;
//    @FXML private Label newConfirmPasswordNotMatchLabel;
//    @FXML private JFXPasswordField currentPasswordField;
//    @FXML private JFXPasswordField confirmPasswordField;
//    @FXML private Label currentPasswordNotCorrectLabel;
//    @FXML private Label usernameExistingLabel;
//    @FXML private Label emailAddressExisted;
//    @FXML private JFXTextField loginUsernameTextField;
//    @FXML private Label loginIncorrectUsernameOrPasswordLabel;
//    @FXML private JFXTextField fullnameTextField;
//    @FXML private JFXButton loginButton;
//    @FXML private JFXButton logoutButton;
//    @FXML private JFXButton newButton;
//    @FXML private JFXPasswordField passwordField;
//    @FXML private TableView<UserManagementDTO> userListTableView;
//    @FXML private TableColumn<UserManagementDTO, String> fullnameCol;
//    @FXML private TableColumn<UserManagementDTO, String> usernameCol;
//    @FXML private TableColumn<UserManagementDTO, String> phoneCol;
//    @FXML private TableColumn<UserManagementDTO, String> emailCol;
//    @FXML private JFXButton saveButton;
//    @FXML private JFXButton uploadImageButton;
//    @FXML private JFXPasswordField newConfirmPasswordField;
//    @FXML private JFXTextField usernameTextField;
//    @FXML private JFXComboBox <String> userRoleComboBox;
//    @FXML private AnchorPane userProfileAnchorPane;
//    @FXML private Label fullnameProfileShowLabel;
//    @FXML private Label usernameProfileShowLabel;
//    @FXML private Label phoneProfileShowLabel;
//    @FXML private Label emailProfileShowLabel;
//    @FXML private Label createdDateProfileShowLabel;
//
//    public  Boolean isLoginSuccess = false;
//    private UserManagementDTO userManagementDTO = new UserManagementDTO();
//    private UserManagementDAO userManagementDAO = new UserManagementDAO();
//    private UserRoleDTO userRoleDTO = new UserRoleDTO();
//    private UserRoleDAO userRoleDAO = new UserRoleDAO();
//    private Permission permission = new Permission();
//    private ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
//    private ChangePasswordDAO changePasswordDAO = new ChangePasswordDAO();
//    private UserManagementDTO selectedUserManagementDTO = new UserManagementDTO();
//    private Boolean isAdminLogedIn = false;
//    private WriteImageFile writeImageFile = new WriteImageFile();
//
//
//    @FXML
//    void logoutButtonPressed(){
//
//    }
//    @FXML
//    void loginButtonPressed(ActionEvent event) throws IOException {
//
//        //TODO: assign getText file to local variable
//        String loginUserName = loginUsernameTextField.getText().trim();
//        String loginPassWord = loginPasswordField.getText().trim();
//
//        //TODO: set local variable value to DTO
//        userManagementDTO.setUserName(loginUserName);
//        userManagementDTO.setPassWord(loginPassWord);
//
//        //TODO: pass whole userDTO object to userDAO in order to check in SQL
//        if(userManagementDAO.userLogin(userManagementDTO)!= null){
//            isLoginSuccess = true;
//            //System.out.println(isLoginSuccess);
//            //TODO:..
//            loginIncorrectUsernameOrPasswordLabel.setVisible(false);
//            loginIncorrectUsernameOrPasswordLabel.requestFocus();
//            System.out.println("loged in");
//            if(permission.hasRoleAdmin()){
//                isAdminLogedIn = true;
//                permission.setButtonVisibility(true, newButton, deleteButton, saveButton, uploadImageButton);
//                permission.setTextFieldVisibility(true, fullnameTextField, usernameTextField, phoneTextField, emailTextField);
//                permission.setPasswordFieldVisibility(true, passwordField, confirmPasswordField);
//                permission.setComboBoxVisibilty(true, userRoleComboBox);
//                permission.setImageViewVisibility(true, imageViewField);
//                permission.setTableViewVisibility(true, userListTableView);
//                permission.setTextFieldVisibility(false, loginUsernameTextField);
//                permission.setPasswordFieldVisibility(false, loginPasswordField);
//                permission.setButtonVisibility(false, loginButton);
//                newButton.setDisable(true);
//                userProfileInformationDisplay();
//
//            }else{
//                permission.setButtonVisibility(false, newButton, deleteButton, saveButton);
//                permission.setButtonVisibility(true, changePasswordButton);
//                permission.setTextFieldVisibility(false, fullnameTextField, usernameTextField, phoneTextField, emailTextField);
//                permission.setPasswordFieldVisibility(true, currentPasswordField, newPasswordField, newConfirmPasswordField);
//                permission.setComboBoxVisibilty(false, userRoleComboBox);
//                permission.setImageViewVisibility(true, imageViewField);
//                permission.setTableViewVisibility(false, userListTableView);
//                permission.setTextFieldVisibility(false, loginUsernameTextField);
//                permission.setPasswordFieldVisibility(false, loginPasswordField);
//                permission.setButtonVisibility(false, loginButton);
//                userProfileInformationDisplay();
//                loginButton.setVisible(false);
//            }
//
//        }else{
//            loginIncorrectUsernameOrPasswordLabel.setVisible(true);
//        }
//
//    }
//
//    @FXML
//    void changePasswordButtonPressed() {
//        if(isAdminLogedIn){
//            if(!newPasswordField.getText().trim().equals(newConfirmPasswordField.getText().trim())){
//                permission.setLabelVisibility(true, newConfirmPasswordNotMatchLabel);
//                newConfirmPasswordField.requestFocus();
//            }else{
//                permission.setLabelVisibility(false, newConfirmPasswordNotMatchLabel);
//                changePasswordDTO.setNewPassword(newPasswordField.getText().trim());
//                changePasswordDTO.setUserId(selectedUserManagementDTO.getId());
//                System.out.println(selectedUserManagementDTO.getId());
//                //call changepassword DAO
//                changePasswordDAO.changeAdminPassword(changePasswordDTO);
//                newPasswordField.clear();
//                newConfirmPasswordField.clear();
//            }
//        }else{
//            changePasswordDTO.setCurrentPassword(currentPasswordField.getText().trim());
//            changePasswordDTO.setNewPassword(newPasswordField.getText().trim());
//            changePasswordDTO.setUserId(userManagementDAO.userLogedin.getId());
//            System.out.println(userManagementDAO.userLogedin.getId());
//            if(!newPasswordField.getText().trim().equals(newConfirmPasswordField.getText().trim())){
//                permission.setLabelVisibility(true, newConfirmPasswordNotMatchLabel);
//                newConfirmPasswordField.requestFocus();
//            }else{
//                permission.setLabelVisibility(false, newConfirmPasswordNotMatchLabel);
//                //call change user password
//                changePasswordDAO.userChangeOwnPassword(changePasswordDTO);
//                currentPasswordField.clear();
//                newPasswordField.clear();
//                newConfirmPasswordField.clear();
//            }
//
//        }
//    }
//
//    @FXML void getUserIdOnTableViewClicked(){
//        selectedUserManagementDTO = userListTableView.getSelectionModel().getSelectedItem();
//        changePasswordButton.setText("Reset");
//        permission.setButtonVisibility(true, changePasswordButton);
//        permission.setPasswordFieldVisibility(true, newPasswordField, newConfirmPasswordField);
//
//
//    }
//    @FXML void uploadImageButtonPressed(){
//        writeImageFile.uploadImage(imageViewField);
//
//    }
//
//    @FXML
//    void newButtonPressed() {
//
//    }
//
//    @FXML
//    void saveButtonPressed() {
//        userManagementDTO.setFullName(fullnameTextField.getText().trim());
//        userManagementDTO.setUserName(usernameTextField.getText().trim());
//        userManagementDTO.setPassWord(passwordField.getText().trim());
//        userManagementDTO.setPhone(phoneTextField.getText().trim());
//        userManagementDTO.setEmail(emailTextField.getText().trim());
//
//        String user_Role = userRoleComboBox.getSelectionModel().getSelectedItem();
//
//        //write image file
//        writeImageFile.writeImageFilePath(userManagementDTO);
//
//        if(!passwordField.getText().trim().equals(confirmPasswordField.getText().trim())){
//            permission.setLabelVisibility(true, registerConfirmPasswordNotMatchLabel);
//            confirmPasswordField.requestFocus();
//
//        }else {
//            if(userManagementDAO.insertNewUser(userManagementDTO, user_Role)){
//                permission.setLabelVisibility(false, registerConfirmPasswordNotMatchLabel, usernameExistingLabel);
//                alertSaveMessageDialogBox();
//                clearInsertTextField(true);
//                setDefaultImage();
//                setFieldDisable(true);
//                newButton.setDisable(false);
//                saveButton.setDisable(true);
//                uploadImageButton.setDisable(true);
//                userListTableView.setItems(userManagementDAO.userLoginDTOObservableList());
//            }else {
//                //TODO:Check
//                String error = userManagementDAO.errorMessage.substring(userManagementDAO.errorMessage.indexOf("d_user_email_uindex"));
//                String emailAddressError = userManagementDAO.errorMessage.substring(userManagementDAO.errorMessage.lastIndexOf("exists."));
//                if (error.equals("already")) {
//                    permission.setLabelVisibility(true, usernameExistingLabel);
//                    usernameTextField.requestFocus();
//                }else{
//                        emailAddressError.equals("exists.");
//                        permission.setLabelVisibility(true, emailAddressExisted);
//                        emailTextField.requestFocus();
//                    }
//
//                }
//
//
//            }
//    }
//
//    @FXML
//    void deleteButtonPressed() {
//
//    }
//
//    void userProfileInformationDisplay(){
//        userProfileAnchorPane.setVisible(true);
//        //usernameProfileShowLabel.getLabelFor().
//    }
//
//    void alertSaveMessageDialogBox(){
//        //Alert information dialog
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Information Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText("New record has been save");
//        alert.showAndWait();
//    }
//    void clearInsertTextField(Boolean status){
//        fullnameTextField.clear();
//        usernameTextField.clear();
//        passwordField.clear();
//        confirmPasswordField.clear();
//        phoneTextField.clear();
//        emailTextField.clear();
//        userRoleComboBox.getSelectionModel().clearSelection();
//    }
//    void setFieldDisable(Boolean isDisabled){
//        fullnameTextField.setDisable(isDisabled);
//        usernameTextField.setDisable(isDisabled);
//        passwordField.setDisable(isDisabled);
//        confirmPasswordField.setDisable(isDisabled);
//        phoneTextField.setDisable(isDisabled);
//        emailTextField.setDisable(isDisabled);
//        userRoleComboBox.setDisable(isDisabled);
//
//
//    }
//    void setDefaultImage(){
//        File file = new File("/Users/chenboly/opt/TheDeals/default_image/image-not-available.png");
//        Image image = new Image(file.toURI().toString());
//        imageViewField.setImage(image);
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
//        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
//        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//        userListTableView.setItems(userManagementDAO.userLoginDTOObservableList());
//        userRoleComboBox.getItems().addAll(userRoleDAO.roleList());
//        userProfileAnchorPane.setVisible(false);
//        permission.setButtonVisibility(false, newButton, deleteButton, saveButton, changePasswordButton, uploadImageButton);
//        permission.setTextFieldVisibility(false, fullnameTextField, usernameTextField, phoneTextField, emailTextField);
//        permission.setLabelVisibility(false, emailAddressExisted, currentPasswordNotCorrectLabel, newConfirmPasswordNotMatchLabel, registerConfirmPasswordNotMatchLabel,
//                usernameExistingLabel, loginIncorrectUsernameOrPasswordLabel);
//        permission.setPasswordFieldVisibility(false, passwordField, confirmPasswordField, currentPasswordField, newConfirmPasswordField, newPasswordField);
//        permission.setComboBoxVisibilty(false, userRoleComboBox);
//        permission.setImageViewVisibility(false, imageViewField);
//        permission.setTableViewVisibility(false, userListTableView);
//
//    }
//}
//
//
