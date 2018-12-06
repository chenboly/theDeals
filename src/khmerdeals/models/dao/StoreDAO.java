package khmerdeals.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import khmerdeals.models.dto.StoreDTO;
import khmerdeals.models.dto.UserManagementDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreDAO {
    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement preparedStatement;
    private UserManagementDAO userManagementDAO = new UserManagementDAO();

    public Boolean insertStore(StoreDTO storeDTO){
        boolean isStoreInserted = false;
        dbConnection.openConnection();
        String insertStoreSQL = "INSERT INTO d_stores (user_id, name, address, phone, image_url, website) " +
                "VALUES ((SELECT id FROM d_user WHERE d_user.id = ? AND d_user.status IS TRUE), ?,?, ?, ?, ?)";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(insertStoreSQL);
            preparedStatement.setInt(1, storeDTO.getUserManagementDTO().getId());
            preparedStatement.setString(2, storeDTO.getName());
            preparedStatement.setString(3, storeDTO.getAddress());
            preparedStatement.setString(4, storeDTO.getPhone());
            preparedStatement.setString(5, storeDTO.getImage_url());
            preparedStatement.setString(6, storeDTO.getWebsite());
            int numberOfRowInserted = preparedStatement.executeUpdate();
            if(numberOfRowInserted>0){
                isStoreInserted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return isStoreInserted;
    }

    public Boolean updateStore(StoreDTO storeDTO){
        boolean isStoreUpdated=false;
        dbConnection.openConnection();
        String updateStoreSQL = "UPDATE d_stores SET name=?, address=?,phone=?,image_url=?,website=? WHERE id=?";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(updateStoreSQL);
            preparedStatement.setString(1, storeDTO.getName());
            preparedStatement.setString(2, storeDTO.getAddress());
            preparedStatement.setString(3, storeDTO.getPhone());
            preparedStatement.setString(4, storeDTO.getImage_url());
            preparedStatement.setString(5, storeDTO.getWebsite());
            preparedStatement.setInt(6, storeDTO.getId());
            int numberOfRowUpdated = preparedStatement.executeUpdate();
            if (numberOfRowUpdated > 0){
                isStoreUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbConnection.closeConnection();
        return isStoreUpdated;
    }

    public Boolean deleteStore(StoreDTO storeDTO){
        boolean isStoreDeleted = false;
        dbConnection.openConnection();
        String storeDeleteSQL = "UPDATE d_stores SET status = false WHERE id=?";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(storeDeleteSQL);
            preparedStatement.setInt(1, storeDTO.getId());
            int numberOfRowEffeted = preparedStatement.executeUpdate();
            if(numberOfRowEffeted > 0){
                isStoreDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbConnection.closeConnection();
        return isStoreDeleted;
    }
    public ObservableList<StoreDTO> storeDTOObservableList(){
        ObservableList<StoreDTO> storeDTOS = FXCollections.observableArrayList();
        dbConnection.openConnection();
        String storeDTOListSQL = "SELECT d_stores.id, d_stores.name, d_stores.address, d_stores.phone, d_stores.image_url, d_stores.website, d_user.fullname  FROM d_stores " +
                "JOIN d_user ON d_stores.user_id = d_user.id " +
                "WHERE d_user.id = ? AND d_stores.status IS TRUE " +
                "ORDER BY d_stores.id";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(storeDTOListSQL);
            preparedStatement.setInt(1, userManagementDAO.userLogedin.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                StoreDTO storeDTO = new StoreDTO();
                storeDTO.setId(resultSet.getInt(1));
                storeDTO.setName(resultSet.getString(2));
                storeDTO.setAddress(resultSet.getString(3));
                storeDTO.setPhone(resultSet.getString(4));
                storeDTO.setImage_url(resultSet.getString(5));
                storeDTO.setWebsite(resultSet.getString(6));
                UserManagementDTO userManagementDTO = new UserManagementDTO();
                userManagementDTO.setFullName(resultSet.getString(7));
                storeDTO.setUserManagementDTO(userManagementDTO);
                storeDTOS.addAll(storeDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();

        }
        return storeDTOS;
    }
}
