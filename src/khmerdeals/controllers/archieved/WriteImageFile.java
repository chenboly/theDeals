//package khmerdeals.controllers.archieved;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.stage.FileChooser;
//import khmerdeals.models.dto.PostAndDealDTO;
//import khmerdeals.models.dto.UserManagementDTO;
//
//import java.io.*;
//import java.util.UUID;
//
//public class WriteImageFile {
//    private File imageProfile = null;
//    private File imageDealProfile = null;
//
//    public void uploadImage(ImageView imageAuthorField){
//        FileChooser fileChooser = new FileChooser();
//        FileChooser.ExtensionFilter filter =
//                new FileChooser.ExtensionFilter("Images:", "*.png", "*.jpg","*.jpeg", "*.gif");
//        fileChooser.getExtensionFilters().add(filter);
//        imageProfile = fileChooser.showOpenDialog(null);
//        if (imageProfile != null){
//            Image image = new Image(imageProfile.toURI().toString());
//            imageAuthorField.setImage(image);
//            System.out.println(imageProfile);
//        }
//    }
//
//    public void writeImageFilePath(UserManagementDTO userManagementDTO){
//
//        if (imageProfile != null) { //start
//            try{
//
//                String oldImageProfileName = imageProfile.getName();
//                String imageExtension = oldImageProfileName.substring(oldImageProfileName.lastIndexOf("."));
//                String newImageProfileName = UUID.randomUUID().toString() + imageExtension;
//                String fileLocation = "/Users/chenboly/opt/TheDeals/images/";
//                File path = new File(fileLocation);
//                if (!path.exists()) {
//                    path.mkdirs();
//                }
//
//                FileOutputStream fileOutputStream = new FileOutputStream(fileLocation + newImageProfileName);
//
//                userManagementDTO.setImage_url(fileLocation + newImageProfileName);
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            FileInputStream fileInputStream = new FileInputStream(imageProfile);
//                            int c;
//                            while ((c=fileInputStream.read()) != -1){
//                                fileOutputStream.write(c);
//                            }
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }).start();
//
//            }catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        } //end
//
//    }
//
//
//    void setDefaultImage(ImageView imageViewField){
//        File file = new File("/Users/chenboly/opt/TheDeals/default_image/image-not-available.png");
//        Image image = new Image(file.toURI().toString());
//        imageViewField.setImage(image);
//    }
//}
