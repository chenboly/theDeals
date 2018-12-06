package khmerdeals.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import khmerdeals.models.dao.PostAndDealDAO;
import khmerdeals.models.dao.ProductDAO;
import khmerdeals.models.dao.UserManagementDAO;
import khmerdeals.models.dto.*;
import sun.jvm.hotspot.jdi.IntegerTypeImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ResourceBundle;



public class PostAndDealViewController implements Initializable {

    @FXML private Tab newDealsTab;
    @FXML private Tab dealsExpiringSoon;
    @FXML private Tab postDealsTab;
    @FXML private JFXTreeTableView<PostAndDealDTO> newDealsTableView;
    @FXML private JFXTreeTableView<PostAndDealDTO> dealExpiringTable;
    @FXML private JFXComboBox<String> productComboBox;
    @FXML private JFXComboBox<String> storeComboBox;
    @FXML private JFXTextArea postDescriptionTextField;
    @FXML private JFXRadioButton hasPromoRadioButton;
    @FXML private JFXTextField postPromoCodeTextField;
    @FXML private JFXTextField postPriceTextField;
    @FXML private JFXTextField postMaxVoteTextField;
    @FXML private JFXDatePicker startDatePicker;
    @FXML private JFXDatePicker endDatePicker;
    @FXML private JFXTreeTableView<ProductImagesDTO> listProductImageTable;
    @FXML private ImageView productPostedImageView;
    @FXML private JFXButton postNewButton;
    @FXML private JFXButton postSaveButton;
    @FXML private JFXButton postEditButton;
    @FXML private JFXButton postDeleteButton;
    @FXML private JFXTreeTableView<PostAndDealDTO> postTableView;

    private StoreDTO storeDTO = new StoreDTO();
    private ProductDTO productDTO = new ProductDTO();
    private ProductDAO productDAO = new ProductDAO();
    private PostAndDealDTO postAndDealDTO = new PostAndDealDTO();
    private PostAndDealDAO postAndDealDAO = new PostAndDealDAO();
    private String storeNameSelected;
    private String productNameSelected;
    private AlertMessage alertMessage = new AlertMessage();
    private ObservableList<TreeItem<ProductImagesDTO>> productImageDTOList = FXCollections.observableArrayList();
    private ObservableList<TreeItem<PostAndDealDTO>> postAndDealDTOList = FXCollections.observableArrayList();
    private TreeItem<PostAndDealDTO> selectPostAndDealDTORow = new TreeItem<>();
    private TreeItem<ProductImagesDTO> selectProductImageDTORow = new TreeItem<>();
    private JFXTreeTableColumn<ProductImagesDTO, String> productImageUrlCol = new JFXTreeTableColumn<>("Image");
    private JFXTreeTableColumn<PostAndDealDTO, String> viewProductImagesCol = new JFXTreeTableColumn<>("View Images");
    private UserManagementDTO userManagementDTO = new UserManagementDTO();
    private UserManagementDAO userManagementDAO = new UserManagementDAO();
    public String productPostedName;
    private DisplayProductImageView displayProductImageController = new DisplayProductImageView();


    @FXML
    void postDeleteButtonPressed(ActionEvent event) {

    }

    @FXML
    void postEditButton(ActionEvent event) {

    }

    @FXML
    void postNewButtonPressed(ActionEvent event) {

    }
    @FXML
    void hasPromotionCodeClicked(){
        postPromoCodeTextField.setDisable(false);
        postMaxVoteTextField.setDisable(false);
        startDatePicker.setDisable(false);
        endDatePicker.setDisable(false);
        System.out.println("selected");
    }

    // TODO: get product name base on Store name
    @FXML
    private void storeComboBoxSelected() {
        storeNameSelected = storeComboBox.getSelectionModel().getSelectedItem();
        productComboBox.getItems().addAll(postAndDealDAO.listProductName(storeNameSelected));
    }

    // TODO: Get product image
    @FXML
    private void productComboBoxSelected() {

        productNameSelected = productComboBox.getSelectionModel().getSelectedItem();
        System.out.println(postAndDealDAO.listProductImageByProductName(productNameSelected));
        productImageUrlCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("imageFileName"));
        RecursiveTreeItem root = new RecursiveTreeItem<>(postAndDealDAO.listProductImageByProductName(productNameSelected), RecursiveTreeObject::getChildren);
        listProductImageTable.setRoot(root);
        listProductImageTable.setShowRoot(false);

    }

    @FXML
    void showImageFromImageTableView(){

        productImageDTOList = listProductImageTable.getSelectionModel().getSelectedItems();
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
        productPostedImageView.setImage(productImage);

    }

    // TODO: Save Post
    @FXML
    void postSaveButtonPressed() {
        storeDTO.setName(storeNameSelected);
        postAndDealDTO.setStoreDTO(storeDTO);
        productDTO.setName(productComboBox.getSelectionModel().getSelectedItem());
        postAndDealDTO.setProductDTO(productDTO);
        postAndDealDTO.setDescription(postDescriptionTextField.getText().trim());
        //set the condition for radio button action
        if(hasPromoRadioButton.isSelected()){
            hasPromoRadioButton.setOnAction(event -> hasPromotionCodeClicked());
            //check empty input and set focus as well as color focus in fxml textfield
            String hasPromotionSelected = postPromoCodeTextField.getText().trim();
            if(hasPromotionSelected.isEmpty()){
                alertMessage.checkEmptySpaceInput();
                postPromoCodeTextField.requestFocus();
            }else{
                postAndDealDTO.setPromotion_code(hasPromotionSelected);
            }
        }else{
            postPromoCodeTextField.setDisable(true);
            postAndDealDTO.setPromotion_code("No Promotion");
        }
        postAndDealDTO.setPrice(Double.valueOf(postPriceTextField.getText().trim()));
        if (hasPromoRadioButton.isSelected()) {
            postAndDealDTO.setMaxVotes(Integer.valueOf(postMaxVoteTextField.getText().trim()));
            postAndDealDTO.setStartDate(Date.valueOf(startDatePicker.getValue()));
            postAndDealDTO.setEndDate(Date.valueOf(endDatePicker.getValue()));
        }

        //        Integer maxVote = (Integer.valueOf(postMaxVoteTextField.getText().trim()));
//        if (maxVote != null){
//            postAndDealDTO.setMaxVotes(maxVote);
//            System.out.println("not null");
//        }else {
//            postAndDealDTO.setMaxVotes(null);
//            System.out.println("null");
//        }
////        Date startDate = null;
////        Date endDate = null;
//        if (!startDatePicker.equals(null)){
//            postAndDealDTO.setStartDate(Date.valueOf(startDatePicker.getValue()));
//        }else {
//            postAndDealDTO.setStartDate(null);
//        }
//        if (!endDatePicker.equals(null)){
//            postAndDealDTO.setEndDate(Date.valueOf(endDatePicker.getValue()));
//        }else {
//            postAndDealDTO.setEndDate(null);
//        }
        userManagementDTO.setId(userManagementDAO.userLogedin.getId());
        postAndDealDTO.setUserManagementDTO(userManagementDTO);
        System.out.println(postAndDealDTO);
        postAndDealDAO.insertPost(postAndDealDTO);
        reloadInformationInAllTabs();



    }

//    @FXML
//    void getSelectPostValueFromTableClick(){
//        postAndDealDTOList = newDealsTableView.getSelectionModel().getSelectedItems();
//        selectPostAndDealDTORow = postAndDealDTOList.get(0);
//        productPostedName = selectPostAndDealDTORow.getValue().getProductDTO().getName();
//        System.out.println(productPostedName);
//
//
//    }

    void openImageOnceClickOnViewImageButton(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/DisplayProductImageView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
            //load another controller to display image
            displayProductImageController=loader.getController();
            postAndDealDTOList = newDealsTableView.getSelectionModel().getSelectedItems();
            selectPostAndDealDTORow = postAndDealDTOList.get(0);
            productPostedName = selectPostAndDealDTORow.getValue().getProductDTO().getName();
            displayProductImageController.showImageURLList(productPostedName);
            //postAndDealDAO.listProductImageByProductName(productPostedName);

            System.out.println(postAndDealDAO.listProductImageByProductName(productPostedName));
            System.out.println("Test");

            //end
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void viewImageCell(){

        viewProductImagesCol.setPrefWidth(100);
        Callback<TreeTableColumn<PostAndDealDTO, String>, TreeTableCell<PostAndDealDTO, String>> viewImageCellFactory =
                new Callback<TreeTableColumn<PostAndDealDTO, String>, TreeTableCell<PostAndDealDTO, String>>() {
                    @Override
                    public TreeTableCell call(final TreeTableColumn<PostAndDealDTO, String> param) {
                        final TreeTableCell<ProductDTO, String> viewImagesCell = new TreeTableCell<ProductDTO, String>() {

                            final JFXButton viewImageButton = new JFXButton();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else{
                                    viewImageButton.setButtonType(JFXButton.ButtonType.RAISED);
                                    viewImageButton.setPrefWidth(100);
                                    viewImageButton.setText("View Images");
                                    viewImageButton.setOnAction(event -> {
                                        //action here
                                        openImageOnceClickOnViewImageButton();

                                    });
                                    setGraphic(viewImageButton);
                                    setText(null);
                                }
                            }
                        };
                        return viewImagesCell;
                    }
                };
        viewProductImagesCol.setCellFactory(viewImageCellFactory);
    }

    void reloadInformationInAllTabs(){
        RecursiveTreeItem root = new RecursiveTreeItem<>(postAndDealDAO.postAndDealDTOObservableList(), RecursiveTreeObject::getChildren);
        postTableView.setRoot(root);
        postTableView.setShowRoot(false);

        RecursiveTreeItem newDealRoot = new RecursiveTreeItem<>(postAndDealDAO.newDealDTOObservableList(true, true), RecursiveTreeObject::getChildren);
        newDealsTableView.setRoot(newDealRoot);
        newDealsTableView.setShowRoot(false);

        RecursiveTreeItem expiringDeal = new RecursiveTreeItem<>(postAndDealDAO.newDealDTOObservableList(true, false), RecursiveTreeObject::getChildren);
        dealExpiringTable.setRoot(expiringDeal);
        dealExpiringTable.setShowRoot(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productImageUrlCol.setPrefWidth(452);
        listProductImageTable.getColumns().add(productImageUrlCol);
        postPromoCodeTextField.setDisable(true);
        postMaxVoteTextField.setDisable(true);
        startDatePicker.setDisable(true);
        endDatePicker.setDisable(true);
        storeComboBox.getItems().addAll(productDAO.storeListName());
        //TODO: For post tab
        JFXTreeTableColumn<PostAndDealDTO, String> storeNameCol = new JFXTreeTableColumn<>("Store");
        JFXTreeTableColumn<PostAndDealDTO, String> productNameCol = new JFXTreeTableColumn<>("Product");
        JFXTreeTableColumn<PostAndDealDTO, String> promotionCol = new JFXTreeTableColumn<>("Promotion");
        JFXTreeTableColumn<PostAndDealDTO, Integer> priceCol = new JFXTreeTableColumn<>("Price");
        JFXTreeTableColumn<PostAndDealDTO, Integer> maxVoteCol = new JFXTreeTableColumn<>("Max Votes");
        JFXTreeTableColumn<PostAndDealDTO, Date> startDateCol = new JFXTreeTableColumn<>("Start Date");
        JFXTreeTableColumn<PostAndDealDTO, Date> endDateCol = new JFXTreeTableColumn<>("End Date");
        JFXTreeTableColumn<PostAndDealDTO, Timestamp> createdDateCol = new JFXTreeTableColumn<>("Created Date");
        postTableView.getColumns().addAll(storeNameCol, productNameCol, promotionCol, priceCol, maxVoteCol, startDateCol, endDateCol,createdDateCol);

        storeNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getValue().getStoreDTO().getName()));
        productNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getValue().getProductDTO().getName()));
        promotionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("promotion_code"));
        priceCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        maxVoteCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxVotes"));
        startDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));
        createdDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("created_date"));
        RecursiveTreeItem root = new RecursiveTreeItem<>(postAndDealDAO.postAndDealDTOObservableList(), RecursiveTreeObject::getChildren);
        postTableView.setRoot(root);
        postTableView.setShowRoot(false);
        System.out.println(root);
        System.out.println(postAndDealDAO.postAndDealDTOObservableList());
        //TODO:End of post column tab

        //TODO New Deal Tab
        JFXTreeTableColumn<PostAndDealDTO, String> newDealStoreNameCol = new JFXTreeTableColumn<>("Store");
        JFXTreeTableColumn<PostAndDealDTO, String> newDealProductNameCol = new JFXTreeTableColumn<>("Product");
        JFXTreeTableColumn<PostAndDealDTO, String> newDealProductDescCol = new JFXTreeTableColumn<>("Description");
        JFXTreeTableColumn<PostAndDealDTO, String> newDealPromotionCol = new JFXTreeTableColumn<>("Promotion");
        JFXTreeTableColumn<PostAndDealDTO, Date> newDealEndDateCol = new JFXTreeTableColumn<>("Promotion End Date");
        JFXTreeTableColumn<PostAndDealDTO, Double> newDealPriceCol = new JFXTreeTableColumn<>("Price");
        JFXTreeTableColumn<PostAndDealDTO, Integer> newDealMaxVoteCol = new JFXTreeTableColumn<>("Max Votes");
        JFXTreeTableColumn<PostAndDealDTO, Timestamp> newDealCreatedDateCol = new JFXTreeTableColumn<>("Posted Date");
        newDealsTableView.getColumns().addAll(newDealStoreNameCol, newDealProductNameCol, newDealProductDescCol,viewProductImagesCol, newDealPromotionCol, newDealEndDateCol, newDealPriceCol, newDealMaxVoteCol, newDealCreatedDateCol);
        viewImageCell();

        newDealStoreNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getValue().getStoreDTO().getName()));
        newDealProductNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getValue().getProductDTO().getName()));
        newDealProductDescCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        newDealPromotionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("promotion_code"));
        newDealEndDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));
        newDealPriceCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        newDealMaxVoteCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxVotes"));
        newDealCreatedDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("created_date"));
        System.out.println(postAndDealDAO.newDealDTOObservableList(false, false));
        RecursiveTreeItem newDealRoot = new RecursiveTreeItem<>(postAndDealDAO.newDealDTOObservableList(true, false), RecursiveTreeObject::getChildren);
        newDealsTableView.setRoot(newDealRoot);
        newDealsTableView.setShowRoot(false);
        //TODO: end of new deal tab


        //TODO: Post Expiring Soon Tab
        JFXTreeTableColumn<PostAndDealDTO, String> expiringDealStoreNameCol = new JFXTreeTableColumn<>("Store");
        JFXTreeTableColumn<PostAndDealDTO, String> expiringDealProductNameCol = new JFXTreeTableColumn<>("Product");
        JFXTreeTableColumn<PostAndDealDTO, String> expiringDealProductDesCol = new JFXTreeTableColumn<>("Description");
        JFXTreeTableColumn<PostAndDealDTO, String> expiringDealPromotionCol = new JFXTreeTableColumn<>("Promotion");
        JFXTreeTableColumn<PostAndDealDTO, Date> expiringDealEndDateCol = new JFXTreeTableColumn<>("Promotion End Date");
        JFXTreeTableColumn<PostAndDealDTO, Double> expiringDealPriceCol = new JFXTreeTableColumn<>("Price");
        JFXTreeTableColumn<PostAndDealDTO, Integer> expiringDealMaxVoteCol = new JFXTreeTableColumn<>("Max Votes");
        JFXTreeTableColumn<PostAndDealDTO, Timestamp> expiringDealCreatedDateCol = new JFXTreeTableColumn<>("Posted Date");
        dealExpiringTable.getColumns().addAll(expiringDealStoreNameCol, expiringDealProductNameCol, expiringDealProductDesCol, viewProductImagesCol, expiringDealPromotionCol, expiringDealEndDateCol, expiringDealPriceCol, expiringDealMaxVoteCol, expiringDealCreatedDateCol);
        viewImageCell();

        expiringDealStoreNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getValue().getStoreDTO().getName()));
        expiringDealProductNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getValue().getProductDTO().getName()));
        expiringDealProductDesCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        expiringDealPromotionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("promotion_code"));
        expiringDealEndDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));
        expiringDealPriceCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        expiringDealMaxVoteCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxVotes"));
        expiringDealCreatedDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("created_date"));
        RecursiveTreeItem expiringDeal = new RecursiveTreeItem<>(postAndDealDAO.newDealDTOObservableList(true, false), RecursiveTreeObject::getChildren);
        dealExpiringTable.setRoot(expiringDeal);
        dealExpiringTable.setShowRoot(false);

        //TODO: END Of post Expiring soon tab







    }
}
