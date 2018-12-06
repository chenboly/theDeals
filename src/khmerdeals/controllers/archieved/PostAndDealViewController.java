//package khmerdeals.controllers.archieved;
//
//import com.jfoenix.controls.*;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.ToggleGroup;
//import javafx.scene.image.ImageView;
//import khmerdeals.controllers.WriteImageFile;
//import khmerdeals.models.dao.PostAndDealDAO;
//import khmerdeals.models.dto.PostAndDealDTO;
//
//import java.net.URL;
//import java.sql.Date;
//import java.util.ResourceBundle;
//
//public class PostAndDealViewController implements Initializable {
//
//    @FXML private JFXTreeTableView<?> newDealsTableView;
//    @FXML private JFXTreeTableView<?> expiringDealTableView;
//    @FXML private JFXTextField postTitleTextField;
//    @FXML private JFXTextArea postDescriptionTextField;
//    @FXML private JFXRadioButton hasPromoRadioButton;
//    @FXML private ToggleGroup radioPromoGroup;
//    @FXML private JFXTextField postPriceTextField;
//    @FXML private JFXTextField postPromoCodeTextField;
//    @FXML private JFXTextField postMaxVoteTextField;
//    @FXML private JFXDatePicker startDatePicker;
//    @FXML private JFXDatePicker endDatePicker;
//    @FXML private JFXButton uploadImageButton;
//    @FXML private ImageView postImageView;
//    @FXML private JFXButton postNewButton;
//    @FXML private JFXButton postSaveButton;
//    @FXML private JFXButton postEditButton;
//    @FXML private JFXButton postDeleteButton;
//    @FXML private JFXTreeTableView<?> postTableView;
//
//    private WriteImageFile writeImageFile = new WriteImageFile();
//    private PostAndDealDTO postAndDealDTO = new PostAndDealDTO();
//    private PostAndDealDAO postAndDealDAO = new PostAndDealDAO();
//
//
//
//    @FXML
//    void postDeleteButtonPressed(ActionEvent event) {
//
//    }
//
//    @FXML
//    void postEditButton(ActionEvent event) {
//
//    }
//
//    @FXML
//    void postNewButtonPressed(ActionEvent event) {
//
//    }
//
//    @FXML
//    void postSaveButtonPressed() {
//        postAndDealDTO.setTitle(postTitleTextField.getText().trim());
//        postAndDealDTO.setDescription(postDescriptionTextField.getText().trim());
//        if (hasPromoRadioButton.isSelected()){
//            postPromoCodeTextField.setDisable(false);
//            postPromoCodeTextField.clear();
//            postAndDealDTO.setPromotion_code(postPromoCodeTextField.getText().trim());
//        }else{
//             postAndDealDTO.setPromotion_code("No Promotion Code");
//        }
//        postAndDealDTO.setPrice(Double.valueOf(postPriceTextField.getText()));
//        postAndDealDTO.setMaxVotes(Integer.valueOf(postMaxVoteTextField.getText()));
//        postAndDealDTO.setStartDate(Date.valueOf(startDatePicker.getValue()));
//        postAndDealDTO.setEndDate(Date.valueOf(endDatePicker.getValue()));
//        //writeImageFile.writeImageDealFilePath(postAndDealDTO);
//
//
//
//
//
//
//    }
//
////    @FXML
////    void hasPromoRadioButtonPressed(){
////        if (hasPromoRadioButton.isSelected()){
////            postPromoCodeTextField.setDisable(false);
////        }
////    }
//
//    @FXML
//    void uploadImageButtonPressed(ActionEvent event) {
//        writeImageFile.uploadImage(postImageView);
//
//
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        postPromoCodeTextField.setDisable(true);
//
//    }
//}
