package khmerdeals.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import khmerdeals.models.dto.ProductDTO;
import khmerdeals.models.dto.ProductImagesDTO;
import khmerdeals.models.dto.StoreDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement preparedStatement;
    private UserManagementDAO userManagementDAO = new UserManagementDAO();

    public List<String> storeListName(){
        List<String> storeNames = new ArrayList<>();
        dbConnection.openConnection();
        try {
            //String storeListNameSQL = "SELECT d_stores.name FROM d_stores WHERE status IS TRUE ORDER BY d_stores.id";
            String storeListNameSQL = "SELECT d_stores.name FROM d_stores " +
                    "JOIN d_user ON d_stores.user_id = d_user.id " +
                    "WHERE d_user.id = ? AND d_stores.status IS TRUE " +
                    "ORDER BY d_stores.id";
            preparedStatement = dbConnection.connection.prepareStatement(storeListNameSQL);
            preparedStatement.setInt(1, userManagementDAO.userLogedin.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                storeNames.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }

        return storeNames;
    }



    public Boolean insertProduct(ProductDTO productDTO){
        boolean isProductInserted = false;
        dbConnection.openConnection();
        String insertProductSQL = "INSERT INTO d_products(name, store_id) " +
                "VALUES (?, (SELECT id FROM d_stores WHERE d_stores.name = ? AND d_stores.status IS TRUE ))";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(insertProductSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, productDTO.getName());
            preparedStatement.setString(2, productDTO.getStoreDTO().getName());
            //preparedStatement.setInt(2, productDTO.getStoreDTO().getId());
            int numOfRowEffected = preparedStatement.executeUpdate();
            if (numOfRowEffected > 0){
                isProductInserted = true;
            }
            //Insert the Created Products ID into ProductImage
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                int productId = resultSet.getInt(1);
                String insertProductIdtoProductImageSQL = "INSERT INTO d_product_images (product_id, images_url) VALUES (?,?)";

                //loop in order to insert multiple images into the ProductImage table
                List<ProductImagesDTO> productImages = productDTO.getProductImgagesDTO();
                insertIntoProductImage(productId, insertProductIdtoProductImageSQL, productImages);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return isProductInserted;
    }

    public Boolean updateProduct(ProductDTO productDTO){
        boolean isProductUpdated = false;
        dbConnection.openConnection();
        String productUpdateSQL = "UPDATE d_products SET name = ?, store_id = (SELECT id FROM d_stores WHERE d_stores.name = ? AND d_stores.status IS TRUE) " +
                "WHERE id = ?";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(productUpdateSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, productDTO.getName());
            preparedStatement.setString(2, productDTO.getStoreDTO().getName());
            preparedStatement.setInt(3, productDTO.getId());
            int numOfRowUpdated = preparedStatement.executeUpdate();
            if (numOfRowUpdated > 0){
                isProductUpdated = true;
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                int selectedProductId = resultSet.getInt(1);
                System.out.println(selectedProductId);
                String insertProductIdtoProductImageSQL = "INSERT INTO d_product_images (product_id, images_url) VALUES (?,?)";

                //loop in order to insert multiple images into the ProductImage table
                List<ProductImagesDTO> productImages = productDTO.getProductImgagesDTO();
                insertIntoProductImage(selectedProductId, insertProductIdtoProductImageSQL, productImages);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return isProductUpdated;
    }

    public Boolean deleteProduct(ProductDTO productDTO){
        boolean isProductDeleted = false;
        dbConnection.openConnection();
        String productDeleteSQL ="UPDATE d_products SET status = FALSE WHERE id = ?";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(productDeleteSQL);
            preparedStatement.setInt(1, productDTO.getId());
            int numberOfRowEffected = preparedStatement.executeUpdate();
            if (numberOfRowEffected > 0){
                isProductDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }

        return isProductDeleted;
    }

    private void insertIntoProductImage(int selectedProductId, String insertProductIdtoProductImageSQL, List<ProductImagesDTO> productImages) throws SQLException {
        for (ProductImagesDTO productImagesDTO : productImages){
            preparedStatement = dbConnection.connection.prepareStatement(insertProductIdtoProductImageSQL);
            preparedStatement.setInt(1, selectedProductId);
            preparedStatement.setString(2, productImagesDTO.getImage_url());
            preparedStatement.executeUpdate();
        }
    }

    public ObservableList<ProductDTO> productDTOObservableList(){
        ObservableList<ProductDTO> productDTOS = FXCollections.observableArrayList();
        dbConnection.openConnection();
//        String productDTOListSQL = "SELECT d_products.id, d_products.name, d_stores.name FROM d_products " +
//                "JOIN d_stores ON d_products.store_id = d_stores.id " +
//                "WHERE d_products.status IS TRUE" +
//                "ORDER BY d_products.id";
        String productDTOListSQL = "SELECT d_products.id, d_products.name, d_stores.name FROM d_products " +
                "JOIN d_stores on d_products.store_id = d_stores.id " +
                "JOIN d_user on d_stores.user_id = d_user.id " +
                "WHERE d_user.id=? " +
                "AND d_products.status IS TRUE " +
                "ORDER BY d_products.id";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(productDTOListSQL);
            preparedStatement.setInt(1, userManagementDAO.userLogedin.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(resultSet.getInt(1));
                productDTO.setName(resultSet.getString(2));
                StoreDTO storeDTO = new StoreDTO();
                storeDTO.setName(resultSet.getString(3));
                productDTO.setStoreDTO(storeDTO);
                productDTOS.addAll(productDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }

        return productDTOS;
    }


}
