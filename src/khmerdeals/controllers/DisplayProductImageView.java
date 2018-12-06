package khmerdeals.controllers;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import khmerdeals.models.dao.PostAndDealDAO;
import khmerdeals.models.dto.ProductImagesDTO;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DisplayProductImageView implements Initializable {


    @FXML private JFXTreeTableView<ProductImagesDTO> producImageURLList;

    @FXML private ImageView viewProductImageList;
    private ObservableList<TreeItem<ProductImagesDTO>> imageList = FXCollections.observableArrayList();
    private TreeItem<ProductImagesDTO> selectedImageList = new TreeItem<>();
    private JFXTreeTableColumn<ProductImagesDTO, String> productImageUrlCol = new JFXTreeTableColumn<>("Image");
    private PostAndDealDAO postAndDealDAO = new PostAndDealDAO();
    private String oldImageUrl;


    public void showImageURLList(String productPostedName){
        System.out.println("Display Product Images: " + productPostedName);
        System.out.println("From Display Product Image View Controller:" + postAndDealDAO.listProductImageByProductName(productPostedName));
        productImageUrlCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("imageFileName"));
        RecursiveTreeItem root = new RecursiveTreeItem<>(postAndDealDAO.listProductImageByProductName(productPostedName), RecursiveTreeObject ::getChildren);
        producImageURLList.setRoot(root);
        producImageURLList.setShowRoot(false);
    }

    @FXML
    void viewImageFromImageList(){

        imageList = producImageURLList.getSelectionModel().getSelectedItems();
        selectedImageList = imageList.get(0);
        oldImageUrl = selectedImageList.getValue().getImage_url();
        //System.out.println("Print oldimage:" + oldImageUrl);
        System.out.println(selectedImageList.getValue().getId());

        if(oldImageUrl == null || oldImageUrl == ""){
            oldImageUrl = "/Users/chenboly/opt/TheDeals/default_image/image-not-available.png";
        }
        File file = new File(oldImageUrl);

        if (!file.exists())
            file = new File("/Users/chenboly/opt/TheDeals/default_image/image-not-available.png");

        Image productImage = new Image(file.toURI().toString());
        viewProductImageList.setImage(productImage);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        producImageURLList.getColumns().add(productImageUrlCol);

    }
}
