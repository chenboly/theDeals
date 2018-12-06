package khmerdeals.models.dao;

import javafx.scene.control.Alert;
import khmerdeals.models.dto.ChangePasswordDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordDAO {
    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement preparedStatement;

    public Boolean changeAdminPassword (ChangePasswordDTO changePasswordDTO){
        boolean isAdminPasswordChanged = false;
        dbConnection.openConnection();
        String changeAdminPasswordSQL = "UPDATE d_user SET password = md5(?) WHERE id = ?";

        try {
            preparedStatement = dbConnection.connection.prepareStatement(changeAdminPasswordSQL);
            preparedStatement.setString(1, changePasswordDTO.getNewPassword());
            preparedStatement.setInt(2, changePasswordDTO.getUserId());
            int numberOfRowUpdate = preparedStatement.executeUpdate();
            if (numberOfRowUpdate > 0){
                isAdminPasswordChanged = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return isAdminPasswordChanged;
    }

    public Boolean userChangeOwnPassword(ChangePasswordDTO changePasswordDTO){
        boolean isUserPasswordChanged = false;
        dbConnection.openConnection();
        String changeUserPasswordSQL = "SELECT password FROM d_user WHERE id = ? AND password = md5(?) AND status IS TRUE";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(changeUserPasswordSQL);
            preparedStatement.setInt(1, changePasswordDTO.getUserId());
            preparedStatement.setString(2, changePasswordDTO.getCurrentPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String setNewUserPasswordSQL = "UPDATE d_user SET password = md5(?) WHERE id = ?";
                preparedStatement = dbConnection.connection.prepareStatement(setNewUserPasswordSQL);
                preparedStatement.setString(1, changePasswordDTO.getNewPassword());
                preparedStatement.setInt(2, changePasswordDTO.getUserId());
                int numberOfRowUpdate = preparedStatement.executeUpdate();
                if(numberOfRowUpdate>0){
                    isUserPasswordChanged = true;
                }
            }else{
                //Alert information dialog
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Current password not correct");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return isUserPasswordChanged;
    }
}
