package khmerdeals.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import khmerdeals.models.dto.ProductImagesDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductImagesDAO {

    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement preparedStatement;

    public Boolean deleteProductImage(ProductImagesDTO productImagesDTO){
        boolean isProductImageDeleted = false;
        dbConnection.openConnection();
        String deleteProductImageSQL = "UPDATE d_product_images SET status = false WHERE id =?";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(deleteProductImageSQL);
            preparedStatement.setInt(1,productImagesDTO.getId());
            int numOfRowUpdated = preparedStatement.executeUpdate();
            if (numOfRowUpdated>0){
                System.out.println("delete");
                isProductImageDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
        return isProductImageDeleted;
    }

    public Boolean updateProductImage(ProductImagesDTO productImagesDTO){
        boolean isProductUpdated = false;
        dbConnection.openConnection();
        String updateProductImageSQL = "UPDATE d_product_images SET images_url = ? WHERE id = ?";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(updateProductImageSQL);
            preparedStatement.setString(1, productImagesDTO.getImage_url());
            preparedStatement.setInt(2, productImagesDTO.getId());
            int numOfRowUpdated = preparedStatement.executeUpdate();
            if(numOfRowUpdated > 0){
                isProductUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return isProductUpdated;
    }

    public ObservableList<ProductImagesDTO> getProductImagesListByProductId(int productId){
        ObservableList<ProductImagesDTO> productImagesDTOS = FXCollections.observableArrayList();
        dbConnection.openConnection();
        String productImageListSQL = "SELECT id, images_url FROM d_product_images WHERE product_id = ? AND status IS TRUE ORDER BY id";
        //String productImageListSQL ="SELECT id, SUBSTRING(images_url from '%/#\"%#\"%' for '#') FROM d_product_images WHERE product_id = ? AND status IS TRUE ORDER BY id";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(productImageListSQL);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductImagesDTO productImagesDTO = new ProductImagesDTO();
                productImagesDTO.setId(resultSet.getInt(1));
                productImagesDTO.setImageFileName(resultSet.getString(2).substring(resultSet.getString(2).lastIndexOf("img")));
                productImagesDTO.setImage_url(resultSet.getString(2));
                productImagesDTOS.addAll(productImagesDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }


        return productImagesDTOS;
    }
}
