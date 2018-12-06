package khmerdeals.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import khmerdeals.models.dao.StoreDAO;
import khmerdeals.models.dao.UserManagementDAO;
import khmerdeals.models.dto.StoreDTO;
import khmerdeals.models.dto.UserManagementDTO;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class StoreViewController implements Initializable {

    @FXML private JFXTextField storeNameTextField;
    @FXML private JFXTextField storeWebsiteTextField;
    @FXML private JFXTextField storeAddressTextField;
    @FXML private JFXTextField storePhoneTextField;
    @FXML private JFXButton uploadImageButton;
    @FXML private ImageView storeImageView;
    @FXML private JFXButton storeNewButton;
    @FXML private JFXButton storeSaveButton;
    @FXML private JFXButton storeEditButton;
    @FXML private JFXButton storeDeleteButton;
    @FXML private JFXTreeTableView<StoreDTO> storeTableView;
    private WriteImageFile writeImageFile = new WriteImageFile();
    private UserManagementDTO userManagementDTO = new UserManagementDTO();
    private UserManagementDAO userManagementDAO = new UserManagementDAO();
    private StoreDTO storeDTO = new StoreDTO();
    private TreeItem<StoreDTO> selectStoreDTORow = new TreeItem<>();
    private StoreDAO storeDAO = new StoreDAO();
    private Boolean saveStatus = true;
    ObservableList<TreeItem<StoreDTO>> storeDTOList = FXCollections.observableArrayList();
    AlertMessage alertMessage = new AlertMessage();

    @FXML
    void storeDeleteButtonPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete this record?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            storeDTOList.forEach(storeDTO->{storeDAO.deleteStore(selectStoreDTORow.getValue());});
            listInformationInStoreTable();
        }
    }

    @FXML
    void storeEditButtonPressed(ActionEvent event) {
        saveStatus=false;
        storeSaveButton.setDisable(false);
        storeSaveButton.setText("Update");
        storeNewButton.setDisable(true);
        storeDeleteButton.setDisable(true);
        uploadImageButton.setDisable(false);
        disableAllField(false);

    }

    @FXML
    void storeNewButtonPressed(ActionEvent event) {
        storeSaveButton.setDisable(false);
        uploadImageButton.setDisable(false);
        disableAllField(false);

    }

    @FXML
    void storeSaveButtonPressed(ActionEvent event) {
        String storeName = storeNameTextField.getText().trim();
        String storeAddress = storeAddressTextField.getText().trim();
        String storePhone = storePhoneTextField.getText().trim();
        String storeWebsite = storeWebsiteTextField.getText().trim();

        if (storeName.isEmpty() || storeAddress.isEmpty() || storePhone.isEmpty() || storeWebsite.isEmpty()) {
            alertMessage.checkEmptySpaceInput();
        } else {
            if (saveStatus == true){
                storeDTO.setName(storeName);
                storeDTO.setAddress(storeAddress);
                storeDTO.setPhone(storePhone);
                storeDTO.setWebsite(storeWebsite);

                //TODO: Write Image
                writeImageFile.writeImageStoreFilePath(storeDTO);
                //TODO Add user loged in id as the owner of store

                userManagementDTO.setId(userManagementDAO.userLogedin.getId());
                storeDTO.setUserManagementDTO(userManagementDTO);
                //TODO: call DAO
                storeDAO.insertStore(storeDTO);

                listInformationInStoreTable();
                disableAllField(true);
                writeImageFile.setDefaultImage(storeImageView);
                clearInfoAllField();
                storeSaveButton.setDisable(true);
                storeEditButton.setDisable(true);
                storeDeleteButton.setDisable(true);
                uploadImageButton.setDisable(true);
            }else{
                selectStoreDTORow.getValue().setName(storeName);
                selectStoreDTORow.getValue().setAddress(storeAddress);
                selectStoreDTORow.getValue().setPhone(storePhone);
                selectStoreDTORow.getValue().setWebsite(storeWebsite);
                //TODO: Write Image for update
                writeImageFile.writeImageStoreFilePath(selectStoreDTORow.getValue());
                System.out.println(selectStoreDTORow);
                storeDAO.updateStore(selectStoreDTORow.getValue());

                storeSaveButton.setText("Save");
                listInformationInStoreTable();
                disableAllField(true);
                writeImageFile.setDefaultImage(storeImageView);
                clearInfoAllField();
                storeNewButton.setDisable(false);
                storeSaveButton.setDisable(true);
                storeEditButton.setDisable(true);
                storeDeleteButton.setDisable(true);
            }

        }
    }

    @FXML
    void getSelectedValueFromTableClicked(){
        storeNewButton.setDisable(true);
        storeDTOList = storeTableView.getSelectionModel().getSelectedItems();
        selectStoreDTORow = storeDTOList.get(0);
        storeNameTextField.setText(selectStoreDTORow.getValue().getName());
        storeAddressTextField.setText(selectStoreDTORow.getValue().getAddress());
        storePhoneTextField.setText(selectStoreDTORow.getValue().getPhone());
        storeWebsiteTextField.setText(selectStoreDTORow.getValue().getWebsite());
        System.out.println(storeDTOList);

        //set image to authorViewImage box
        String oldImageUrl = selectStoreDTORow.getValue().getImage_url();
        //System.out.println(oldImageUrl);
        if (oldImageUrl == null || oldImageUrl == ""){
            oldImageUrl = "/Users/chenboly/opt/TheDeals/default_image/image-not-available.png";
        }

        File file = new File(oldImageUrl);
        //System.out.println(oldImageUrl);

        //check if imageURL is null or image is deleted
        if (!file.exists())
            file = new File("/Users/chenboly/opt/TheDeals/default_image/image-not-available.png");

        Image image = new Image(file.toURI().toString());
        System.out.println("print image:" + image);
        storeImageView.setImage(image);

        storeEditButton.setDisable(false);
        storeDeleteButton.setDisable(false);

    }
    @FXML
    void uploadImageButtonPressed(ActionEvent event) {
        writeImageFile.uploadImage(storeImageView);

    }

    void clearInfoAllField(){
        storeNameTextField.clear();
        storeAddressTextField.clear();
        storePhoneTextField.clear();
        storeWebsiteTextField.clear();
    }
    void disableAllField(Boolean status){
        storeNameTextField.setDisable(status);
        storeAddressTextField.setDisable(status);
        storePhoneTextField.setDisable(status);
        storeWebsiteTextField.setDisable(status);
    }
    void listInformationInStoreTable(){
        RecursiveTreeItem root = new RecursiveTreeItem<>(storeDAO.storeDTOObservableList(), RecursiveTreeObject::getChildren);
        storeTableView.setRoot(root);
        storeTableView.setShowRoot(false);
    }
    void disableAllButtons(Boolean status){
        storeSaveButton.setDisable(status);
        storeEditButton.setDisable(status);
        storeDeleteButton.setDisable(status);
        uploadImageButton.setDisable(status);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableAllField(true);
        writeImageFile.setDefaultImage(storeImageView);
        disableAllButtons(true);
        storeNewButton.setDisable(false);


        JFXTreeTableColumn<StoreDTO, Integer> storeId = new JFXTreeTableColumn<>("ID");
        JFXTreeTableColumn<StoreDTO, String> storeNameCol = new JFXTreeTableColumn<>("Store Name");
        JFXTreeTableColumn<StoreDTO, String> addressCol = new JFXTreeTableColumn("Address");
        JFXTreeTableColumn<StoreDTO, String> phoneCol = new JFXTreeTableColumn("Phone");
        JFXTreeTableColumn<StoreDTO, String> websiteCol = new JFXTreeTableColumn("Website");
        JFXTreeTableColumn<StoreDTO, String> storeOwnerCol = new JFXTreeTableColumn("Store Owner");
        storeTableView.getColumns().addAll(storeNameCol, addressCol, phoneCol, websiteCol, storeOwnerCol);

        storeId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        storeNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
        phoneCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("phone"));
        websiteCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("website"));

        storeOwnerCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getValue().getUserManagementDTO().getFullName()));

        RecursiveTreeItem root = new RecursiveTreeItem<>(storeDAO.storeDTOObservableList(), RecursiveTreeObject::getChildren);
        System.out.println(storeDAO.storeDTOObservableList());
        storeTableView.setRoot(root);
        storeTableView.setShowRoot(false);
    }
}
