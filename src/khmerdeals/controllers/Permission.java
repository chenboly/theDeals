package khmerdeals.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.tools.corba.se.idl.constExpr.BooleanAnd;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import khmerdeals.models.dao.UserManagementDAO;
import khmerdeals.models.dto.UserRoleDTO;

import java.util.List;

public class Permission {

    private UserManagementDAO userManagementDAO = new UserManagementDAO();

    public Boolean hasRoleAdmin(){
        boolean isAdmin = false;
        List<UserRoleDTO> roleDTOList = userManagementDAO.userLogedin.getUserRoleDTOList();
        for (UserRoleDTO userRoleDTO : roleDTOList){
            //search admin role
            if(userRoleDTO.getRole_name().equalsIgnoreCase("admin")){
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    public Boolean hasRoleUser() {
        boolean isUser = false;
        List<UserRoleDTO> roleList = userManagementDAO.userLogedin.getUserRoleDTOList();
        for (UserRoleDTO userRoleDTO : roleList) {
            //search user role
            if (userRoleDTO.getRole_name().equalsIgnoreCase("user")) {
                isUser = true;
            }

        }
        return isUser;
    }


    public void setButtonVisibility(Boolean status, Object ...buttons) {
        for (Object button : buttons) {
            if (button instanceof JFXButton) {
                ((JFXButton) button).setVisible(status);
            }
        }
    }

    public void setTextFieldVisibility(Boolean status, Object ...textFields) {
        for (Object textField : textFields) {
            if (textField instanceof JFXTextField) {
                ((JFXTextField) textField).setVisible(status);
            }
        }
    }
    public void setPasswordFieldVisibility(Boolean status, Object ...passwordFields){
        for(Object passwordField : passwordFields){
            if(passwordField instanceof JFXPasswordField){
                ((JFXPasswordField) passwordField).setVisible(status);
            }
        }
    }

    public void setImageViewVisibility(Boolean status, Object ...imageViews){
        for (Object imageView : imageViews){
            if(imageView instanceof ImageView){
                ((ImageView) imageView).setVisible(status);
            }
        }
    }

    public void setTableViewVisibility(Boolean status, Object ...tableViews){
        for(Object tableView : tableViews){
            if(tableView instanceof TableView){
                ((TableView) tableView).setVisible(status);
            }
        }
    }
    public void setLabelVisibility(Boolean status, Object ...labels){
        for(Object label : labels){
            if (label instanceof Label){
                ((Label) label).setVisible(status);
            }
        }
    }
    public void setComboBoxVisibilty(Boolean status, Object ...comboBoxs){
        for(Object comboBox : comboBoxs){
            if(comboBox instanceof JFXComboBox){
                ((JFXComboBox) comboBox).setVisible(status);
            }
        }
    }
    public void setHboxVisibility (Boolean status, Object ...hBoxs){
        for(Object hBox : hBoxs){
            if(hBox instanceof HBox){
                ((HBox) hBox).setVisible(status);
            }
        }
    }
    public void setVboxBisibility(Boolean status, Object ...vBoxs){
        for (Object vBox : vBoxs){
            if(vBox instanceof VBox){
                ((VBox) vBox).setVisible(status);
            }
        }
    }
}

