package khmerdeals.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import khmerdeals.models.dao.ProductDAO;
import khmerdeals.models.dao.ProductImagesDAO;
import khmerdeals.models.dto.ProductDTO;
import khmerdeals.models.dto.ProductImagesDTO;
import khmerdeals.models.dto.StoreDTO;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {

    @FXML private JFXTreeTableView<ProductDTO> productsTableView;
    @FXML private JFXTextField productNameTextField;
    @FXML private JFXComboBox<String> storeNameComboBox;
    @FXML private JFXButton productUploadButton;
    @FXML private JFXButton productNewButton;
    @FXML private JFXButton productSaveButton;
    @FXML private JFXButton productEditButton;
    @FXML private JFXButton productDeleteButton;
    @FXML private ImageView productImageView;
    @FXML private JFXTreeTableView<ProductImagesDTO>imageTableListView;


    private ProductDAO productDAO = new ProductDAO();
    private ProductDTO productDTO = new ProductDTO();
    private StoreDTO storeDTO = new StoreDTO();
    private ProductImagesDAO productImagesDAO = new ProductImagesDAO();
    private ProductImagesDTO productImagesDTO = new ProductImagesDTO();
    private WriteImageFile writeImageFile = new WriteImageFile();
    private AlertMessage alertMessage = new AlertMessage();
    private List<File> files = new ArrayList<>();
    private ObservableList<TreeItem<ProductDTO>> productDTOList = FXCollections.observableArrayList();
    private ObservableList<TreeItem<ProductImagesDTO>> productImageDTOList = FXCollections.observableArrayList();
    private TreeItem<ProductDTO> selectProductDTORow = new TreeItem<>();
    private TreeItem<ProductImagesDTO> selectProductImageDTORow = new TreeItem<>();
    private Integer productId;
    private JFXTreeTableColumn<ProductImagesDTO, String> productImageUrlCol = new JFXTreeTableColumn<>("Image File");
    private JFXTreeTableColumn<ProductImagesDTO, String> imageDeleteColumn = new JFXTreeTableColumn<>("Delete");
    private JFXTreeTableColumn<ProductImagesDTO, String> imageEditColumn = new JFXTreeTableColumn<>("Change Image");
    private JFXTreeTableColumn<ProductImagesDTO, String> imageSaveColumn = new JFXTreeTableColumn<>("Save Change");
    private Boolean saveProductStatus = true;


    @FXML
    void productDeleteButtonPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete this record?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            //delete multiple record
            productDTOList.forEach(productDTO -> {productDAO.deleteProduct(productDTO.getValue());});
            //delete 1 record
            //productDTOList.forEach(productDTO -> {productDAO.deleteProduct(selectProductDTORow.getValue());});
        }
        listInformationInProductTable();
        imageTableListView.setDisable(true);

    }

    @FXML
    void productEditButtonPressed(ActionEvent event) {
        saveProductStatus=false;
        disableAllButton(false);
        disableAllField(false);

    }

    @FXML
    void productNewButtonPressed(ActionEvent event) {
        clearAllFields();
        disableAllButton(false);
        disableAllField(false);
        imageTableListView.setDisable(false);
        productEditButton.setDisable(true);
        productDeleteButton.setDisable(true);
    }

    @FXML
    void productSaveButtonPressed(ActionEvent event) {
        String productName = productNameTextField.getText().trim();
        if (productName.isEmpty()) {
            alertMessage.checkEmptySpaceInput();
        }else {
            if (saveProductStatus == true){
                productDTO.setName(productName);
                storeDTO.setName(storeNameComboBox.getSelectionModel().getSelectedItem());
                productDTO.setStoreDTO(storeDTO);
                //TODO: Insert multiple product images.
                List<ProductImagesDTO> productImagesDTOS = writeImageFile.writeMultipleProductImages(files);
                //System.out.println("print file in save method:" + files);
                productDTO.setProductImgagesDTO(productImagesDTOS);
                //TODO: call sql in ProductDAO
                productDAO.insertProduct(productDTO);
                clearAllFields();
                listInformationInProductTable();
                disableAllField(true);
                disableAllButton(true);


            }else{
                selectProductDTORow.getValue().setName(productName);
                storeDTO.setName(storeNameComboBox.getSelectionModel().getSelectedItem());
                selectProductDTORow.getValue().setStoreDTO(storeDTO);
                //TODO: Write Image
                List<ProductImagesDTO> productImagesDTOS = writeImageFile.writeMultipleProductImages(files);
                selectProductDTORow.getValue().setProductImgagesDTO(productImagesDTOS);
                //TODO: call update SQL
                productDAO.updateProduct(selectProductDTORow.getValue());
                listInformationInProductTable();
                imageTableListView.refresh();
                listProductImageInImageTableView();
                disableAllButton(true);
                disableAllField(true);
                productNewButton.setDisable(false);

            }


        }

    }

    @FXML
    void productUploadButtonPressed() {
        files = writeImageFile.uploadMultipleImageFiles(productImageUrlCol, imageTableListView);
        //System.out.println("Print file from upload method: " + files);
    }

    @FXML
    void getSelectedValueFromTableClicked(MouseEvent event) {
        System.out.println("Hello");
        disableAllField(true);
        deleteCellButton();
        editCellButton();
        saveCellButton();
        productEditButton.setDisable(false);
        productSaveButton.setDisable(false);
        productSaveButton.setText("Update");
        productNewButton.setDisable(true);
        productDeleteButton.setDisable(false);
        imageTableListView.setDisable(false);
        writeImageFile.setDefaultImage(productImageView);

        productDTOList = productsTableView.getSelectionModel().getSelectedItems();
        selectProductDTORow = productDTOList.get(0);
        productNameTextField.setText(selectProductDTORow.getValue().getName());

        storeNameComboBox.getSelectionModel().select(selectProductDTORow.getValue().getStoreDTO().getName());

        productId = selectProductDTORow.getValue().getId();

        productImageUrlCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("imageFileName"));

        RecursiveTreeItem root = new RecursiveTreeItem<>(productImagesDAO.getProductImagesListByProductId(productId), RecursiveTreeObject::getChildren);
        System.out.println(productImagesDAO.getProductImagesListByProductId(productId));
        imageTableListView.setRoot(root);
        imageTableListView.setShowRoot(false);

    }

    @FXML
    void showImageFromImageTableClicked(){
        productUploadButton.setDisable(true);
        productImageDTOList = imageTableListView.getSelectionModel().getSelectedItems();
        selectProductImageDTORow = productImageDTOList.get(0);
        String oldImageUrl = selectProductImageDTORow.getValue().getImage_url();
        //System.out.println("Print oldimage:" + oldImageUrl);
        System.out.println(selectProductImageDTORow.getValue().getId());

        if(oldImageUrl == null || oldImageUrl == ""){
            oldImageUrl = "/Users/chenboly/opt/TheDeals/default_image/image-not-available.png";
        }
        File file = new File(oldImageUrl);

        if (!file.exists())
            file = new File("/Users/chenboly/opt/TheDeals/default_image/image-not-available.png");

        Image productImage = new Image(file.toURI().toString());
        productImageView.setImage(productImage);

    }


    void disableAllField(Boolean status){
        productNameTextField.setDisable(status);
        storeNameComboBox.setDisable(status);
    }
    void disableAllButton(Boolean status){
        productEditButton.setDisable(status);
        productDeleteButton.setDisable(status);
        productSaveButton.setDisable(status);
        productUploadButton.setDisable(status);
    }
    void clearAllFields(){
        productNameTextField.clear();
        storeNameComboBox.setDisable(true);
        storeNameComboBox.setValue(null);

    }

    void listInformationInProductTable(){
        RecursiveTreeItem root = new RecursiveTreeItem<>(productDAO.productDTOObservableList(), RecursiveTreeObject::getChildren);
        productsTableView.setRoot(root);
        productsTableView.setShowRoot(false);
    }

    void listProductImageInImageTableView(){
        RecursiveTreeItem root = new RecursiveTreeItem<>(productImagesDAO.getProductImagesListByProductId(productId), RecursiveTreeObject::getChildren);
        imageTableListView.setRoot(root);
        imageTableListView.setShowRoot(false);
    }

    public void deleteCellButton(){

        imageDeleteColumn.setMaxWidth(80);
        Callback<TreeTableColumn<ProductImagesDTO, String>, TreeTableCell<ProductImagesDTO, String>> cellFactory =
                new Callback<TreeTableColumn<ProductImagesDTO, String>, TreeTableCell<ProductImagesDTO, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<ProductImagesDTO, String> param) {
                final TreeTableCell<ProductDTO, String> cell = new TreeTableCell<ProductDTO, String>() {

                    final JFXButton imageDeleteButton = new JFXButton();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else{
                            imageDeleteButton.setButtonType(JFXButton.ButtonType.RAISED);
                            imageDeleteButton.setPrefWidth(70);
                            imageDeleteButton.setText("Delete");
                            imageDeleteButton.setOnAction(event -> {
                                //Button Action here
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure to delete this image?");

                                Optional<ButtonType> result = alert.showAndWait();

                                if(result.get() == ButtonType.OK){
                                    productImageDTOList.forEach(productImagesDTO -> {productImagesDAO.deleteProductImage(selectProductImageDTORow.getValue());});
                                    listProductImageInImageTableView();

                                }
                            });
                            setGraphic(imageDeleteButton);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        imageDeleteColumn.setCellFactory(cellFactory);
    }

    public void editCellButton(){

        imageEditColumn.setPrefWidth(110);
        Callback<TreeTableColumn<ProductImagesDTO, String>, TreeTableCell<ProductImagesDTO, String>> editCellFactory =
                new Callback<TreeTableColumn<ProductImagesDTO, String>, TreeTableCell<ProductImagesDTO, String>>() {
                    @Override
                    public TreeTableCell call(final TreeTableColumn<ProductImagesDTO, String> param) {
                        final TreeTableCell<ProductDTO, String> editCell = new TreeTableCell<ProductDTO, String>() {

                            final JFXButton imageEditButton = new JFXButton();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else{
                                    imageEditButton.setButtonType(JFXButton.ButtonType.RAISED);
                                    imageEditButton.setPrefWidth(110);
                                    imageEditButton.setText("Change Image");
                                    imageEditButton.setOnAction(event -> {
                                        //productUploadButtonPressed();
                                        writeImageFile.uploadChangeProductImage(productImageView);

                                    });
                                    setGraphic(imageEditButton);
                                    setText(null);
                                }
                            }
                        };
                        return editCell;
                    }
                };
        imageEditColumn.setCellFactory(editCellFactory);
    }

    public void saveCellButton(){

        imageSaveColumn.setPrefWidth(100);
        Callback<TreeTableColumn<ProductImagesDTO, String>, TreeTableCell<ProductImagesDTO, String>> saveCellFactory =
                new Callback<TreeTableColumn<ProductImagesDTO, String>, TreeTableCell<ProductImagesDTO, String>>() {
                    @Override
                    public TreeTableCell call(final TreeTableColumn<ProductImagesDTO, String> param) {
                        final TreeTableCell<ProductDTO, String> saveCell = new TreeTableCell<ProductDTO, String>() {

                            final JFXButton imageSaveButton = new JFXButton();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else{
                                    imageSaveButton.setButtonType(JFXButton.ButtonType.RAISED);
                                    imageSaveButton.setPrefWidth(100);
                                    imageSaveButton.setText("Save Change");
                                    imageSaveButton.setOnAction(event -> {
                                        //action here
                                        writeImageFile.saveChangeProductImage(selectProductImageDTORow.getValue());

                                        //System.out.println(selectProductImageDTORow.getValue().getId());
                                        productImagesDAO.updateProductImage(selectProductImageDTORow.getValue());
                                        productImageView.setImage(null);
                                        imageTableListView.refresh();

                                    });
                                    setGraphic(imageSaveButton);
                                    setText(null);
                                }
                            }
                        };
                        return saveCell;
                    }
                };
        imageSaveColumn.setCellFactory(saveCellFactory);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //deleteCellButton();
        //editCellButton();

        imageTableListView.setDisable(true);
        writeImageFile.setDefaultImage(productImageView);
        disableAllField(true);
        disableAllButton(true);
        storeNameComboBox.getItems().addAll(productDAO.storeListName());

        productImageUrlCol.setPrefWidth(325);
        imageTableListView.getColumns().addAll(productImageUrlCol, imageEditColumn, imageSaveColumn, imageDeleteColumn);

        //Product Table List

        JFXTreeTableColumn<ProductDTO, Integer> productIdCol = new JFXTreeTableColumn<>("ID");
        productIdCol.setPrefWidth(51);
        JFXTreeTableColumn<ProductDTO, String> productNameCol = new JFXTreeTableColumn<>("Product Name");
        productNameCol.setPrefWidth(300);
        JFXTreeTableColumn<ProductDTO, String> storeNameCol = new JFXTreeTableColumn<>("Store Name");
        storeNameCol.setPrefWidth(170);
        productsTableView.getColumns().addAll(productIdCol, productNameCol, storeNameCol);

        productIdCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        storeNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getValue().getStoreDTO().getName()));
        RecursiveTreeItem root = new RecursiveTreeItem<>(productDAO.productDTOObservableList(), RecursiveTreeObject::getChildren);
        System.out.println(productDAO.productDTOObservableList());
        productsTableView.setRoot(root);
        productsTableView.setShowRoot(false);

        productsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }
}
