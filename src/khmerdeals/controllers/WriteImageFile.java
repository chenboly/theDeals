package khmerdeals.controllers;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import khmerdeals.models.dto.ProductImagesDTO;
import khmerdeals.models.dto.StoreDTO;
import khmerdeals.models.dto.UserManagementDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WriteImageFile {
    private File imageProfile = null;
    private File changeProductImageProfile = null;

    List<File> files;
    ProductImagesDTO productImagesDTO = new ProductImagesDTO();


    public void uploadImage(ImageView imageAuthorField){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter("Images:", "*.png", "*.jpg","*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(filter);
        imageProfile = fileChooser.showOpenDialog(null);
        if (imageProfile != null){
            Image image = new Image(imageProfile.toURI().toString());
            imageAuthorField.setImage(image);
            System.out.println(imageProfile);
        }
    }

    public List<File> uploadMultipleImageFiles(JFXTreeTableColumn<ProductImagesDTO, String> productImageUrlCol, JFXTreeTableView<ProductImagesDTO> imageTableListView){
        ObservableList<ProductImagesDTO> productImagesDTOObservableList = FXCollections.observableArrayList();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images:", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(filter);
        //List<File> files;
        files = fileChooser.showOpenMultipleDialog(null);
        if(!files.isEmpty()){
            productImageUrlCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("image_url"));
            files.forEach(file -> {
                productImagesDTO.setImage_url(file.getName());
                productImagesDTOObservableList.addAll(productImagesDTO);
                //System.out.println(productImagesDTOObservableList);
                RecursiveTreeItem<ProductImagesDTO> root = new RecursiveTreeItem<>(productImagesDTOObservableList, RecursiveTreeObject::getChildren);
                //System.out.println(root);
                imageTableListView.setRoot(root);
                imageTableListView.setShowRoot(false);
                //System.out.println(productImagesDTO.getImage_url());

            });
            //System.out.println("print: " + files);
        }
        return files;
    }

    public List<ProductImagesDTO> writeMultipleProductImages(List<File> files){
        List<ProductImagesDTO> productImagesDTOS = new ArrayList<>();
            if(!files.isEmpty() && files!=null){
                for(File file : files){
                    new Thread(() -> {
                        ProductImagesDTO productImagesDTO = new ProductImagesDTO();
                        System.out.println("Writing file....");
                        String fileName = file.getName();
                        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                        fileName = "img"+ UUID.randomUUID().toString() + fileExtension;
                        System.out.println(fileName);
                        String uploadFileLocation = "/Users/chenboly/opt/TheDeals/images/products/";
                        System.out.println(uploadFileLocation);
                        File path = new File(uploadFileLocation);
                        if (path.exists()) {
                            path.mkdirs();
                        }
                        productImagesDTO.setImage_url(uploadFileLocation + fileName);
                        productImagesDTOS.add(productImagesDTO);
                        try {
                            FileInputStream fileInputStream = new FileInputStream(file);
                            FileOutputStream fileOutputStream = new FileOutputStream(uploadFileLocation + fileName);
                            int c;
                            while ((c = fileInputStream.read()) != -1) {
                                fileOutputStream.write(c);
                            }
                            System.out.println("Finished writing...");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();

                }
            }
        return productImagesDTOS;
    }



    public void writeImageFilePath(UserManagementDTO userManagementDTO){

        if (imageProfile != null) { //start
            try{

                String oldImageProfileName = imageProfile.getName();
                String imageExtension = oldImageProfileName.substring(oldImageProfileName.lastIndexOf("."));
                String newImageProfileName = UUID.randomUUID().toString() + imageExtension;
                String fileLocation = "/Users/chenboly/opt/TheDeals/images/user/";
                File path = new File(fileLocation);
                if (!path.exists()) {
                    path.mkdirs();
                }

                FileOutputStream fileOutputStream = new FileOutputStream(fileLocation + newImageProfileName);

                userManagementDTO.setImage_url(fileLocation + newImageProfileName);

                writeImageThread(fileOutputStream, imageProfile);

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } //end

    }

    public void writeImageStoreFilePath(StoreDTO storeDTO){

        if (imageProfile != null) {
            try{

                String oldStoreImage = imageProfile.getName();
                String storeImageExtension = oldStoreImage.substring(oldStoreImage.lastIndexOf("."));
                String newStoreImage = UUID.randomUUID().toString() + storeImageExtension;
                String storeImageFileLocation = "/Users/chenboly/opt/TheDeals/images/store/";
                File path = new File(storeImageFileLocation);
                if (!path.exists()) {
                    path.mkdirs();
                }

                FileOutputStream fileOutputStream = new FileOutputStream(storeImageFileLocation + newStoreImage);

                storeDTO.setImage_url(storeImageFileLocation + newStoreImage);

                writeImageThread(fileOutputStream, imageProfile);

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } //end

    }

    private void writeImageThread(FileOutputStream fileOutputStream, File imageStore) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fileInputStream = new FileInputStream(imageStore);
                    int c;
                    while ((c=fileInputStream.read()) != -1){
                        fileOutputStream.write(c);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    void setDefaultImage(ImageView imageViewField){
        File file = new File("/Users/chenboly/opt/TheDeals/default_image/image-not-available.png");
        Image image = new Image(file.toURI().toString());
        imageViewField.setImage(image);

    }


    public void uploadChangeProductImage(ImageView productImageView){
        FileChooser productImageFileChooser = new FileChooser();
        FileChooser.ExtensionFilter productImageExtensionFilter = new FileChooser.ExtensionFilter("Images:", "*.png", "*.jpeg", "*.gif");
        productImageFileChooser.getExtensionFilters().add(productImageExtensionFilter);
        changeProductImageProfile = productImageFileChooser.showOpenDialog(null);
        if(changeProductImageProfile != null){
            Image changeProductImage = new Image(changeProductImageProfile.toURI().toString());
            productImageView.setImage(changeProductImage);
            System.out.println(changeProductImageProfile);

        }
    }

    public void saveChangeProductImage(ProductImagesDTO productImagesDTO){
        if (changeProductImageProfile != null) { //start
            try{

                String oldProductImageProfileName = changeProductImageProfile.getName();
                String productImageExtension = oldProductImageProfileName.substring(oldProductImageProfileName.lastIndexOf("."));
                String newProductImageProfileName = "img" + UUID.randomUUID().toString() + productImageExtension;
                String fileLocation = "/Users/chenboly/opt/TheDeals/images/products/";
                File path = new File(fileLocation);
                if (!path.exists()) {
                    path.mkdirs();
                }

                FileOutputStream fileProductImageOutputStream = new FileOutputStream(fileLocation + newProductImageProfileName);

                productImagesDTO.setImage_url(fileLocation + newProductImageProfileName);
                System.out.println("Product Image URL:"+ productImagesDTO.getImage_url());
                System.out.println("Product Image ID:"+ productImagesDTO.getId());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FileInputStream fileProductImageInputStream = new FileInputStream(changeProductImageProfile);
                            int c;
                            while ((c=fileProductImageInputStream.read())!= -1){
                                fileProductImageOutputStream.write(c);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } //end


    }
}
