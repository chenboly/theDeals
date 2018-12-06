package khmerdeals.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import khmerdeals.models.dto.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostAndDealDAO {
    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement preparedStatement;
    private UserManagementDAO userManagementDAO = new UserManagementDAO();
    private StoreDTO storeDTO = new StoreDTO();

    public List<StoreDTO> listStoreName(){
        List<StoreDTO> storeNames = new ArrayList<>();
        dbConnection.openConnection();
        try {
            //String storeListNameSQL = "SELECT d_stores.name FROM d_stores WHERE status IS TRUE ORDER BY d_stores.id";
            String storeListNameSQL = "SELECT d_stores.id, d_stores.name FROM d_stores " +
                    "JOIN d_user ON d_stores.user_id = d_user.id " +
                    "WHERE d_user.id = ? AND d_stores.status IS TRUE " +
                    "ORDER BY d_stores.id";
            preparedStatement = dbConnection.connection.prepareStatement(storeListNameSQL);
            preparedStatement.setInt(1, userManagementDAO.userLogedin.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                StoreDTO storeDTO = new StoreDTO();
                storeDTO.setId(resultSet.getInt(1));
                storeDTO.setName(resultSet.getString(2));
                storeNames.add(storeDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }

        return storeNames;
    }

//    public List<String> listProductName(){
//        List<String> productName = new ArrayList<>();
//        dbConnection.openConnection();
//        try {
////            String listProductNameSQL = "SELECT d_products.name FROM d_products " +
////                    "WHERE d_products.status is TRUE ORDER BY d_products.id";
//            String listProductNameSQL = "SELECT d_products.name FROM d_products " +
//                    "JOIN d_stores on d_products.store_id = d_stores.id " +
//                    "JOIN d_user on d_stores.user_id = d_user.id " +
//                    "WHERE d_user.id=? " +
//                    "AND d_products.status is TRUE " +
//                    "ORDER BY d_products.id";
//            preparedStatement = dbConnection.connection.prepareStatement(listProductNameSQL);
//            preparedStatement.setInt(1, userManagementDAO.userLogedin.getId());
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                productName.add(resultSet.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            dbConnection.closeConnection();
//        }
//        return productName;
//    }

    public List<String> listProductName(String storeNameSelected){
        List<String> productName = new ArrayList<>();
        dbConnection.openConnection();
        try {
            String listProductNameSQL ="SELECT name FROM d_products " +
                    "WHERE store_id = (SELECT id FROM d_stores WHERE d_stores.name LIKE ?) AND status IS TRUE";
            preparedStatement = dbConnection.connection.prepareStatement(listProductNameSQL);
            //preparedStatement.setInt(1, userManagementDAO.userLogedin.getId());
            preparedStatement.setString(1, storeNameSelected);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                productName.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
        return productName;
    }

//    public List<String> listProductImageByProdutName(String productNameSelected){
//        List <String> productImages = new ArrayList<>();
//        dbConnection.openConnection();
//        String listProductImageSQL = "SELECT d_product_images.images_url FROM d_product_images " +
//                "WHERE product_id = (SELECT id FROM d_products WHERE d_products.name LIKE ?) AND status IS TRUE";
//        try {
//            preparedStatement = dbConnection.connection.prepareStatement(listProductImageSQL);
//            preparedStatement.setString(1, productNameSelected);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                productImages.add(resultSet.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            dbConnection.closeConnection();
//        }
//        return productImages;
//    }

    public ObservableList<ProductImagesDTO> listProductImageByProductName(String productNameSelected){
        ObservableList<ProductImagesDTO> productImagesDTOS = FXCollections.observableArrayList();
        dbConnection.openConnection();
        String listProductImageSQL = "SELECT d_product_images.images_url FROM d_product_images " +
                "WHERE product_id = (SELECT id FROM d_products WHERE d_products.name LIKE ? AND d_products.status IS TRUE ) AND status IS TRUE";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(listProductImageSQL);
            preparedStatement.setString(1, productNameSelected);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductImagesDTO productImagesDTO = new ProductImagesDTO();
                productImagesDTO.setImageFileName(resultSet.getString(1).substring(resultSet.getString(1).lastIndexOf("img")));
                productImagesDTO.setImage_url(resultSet.getString(1));
                productImagesDTOS.add(productImagesDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return productImagesDTOS;
    }

    public Boolean insertPost (PostAndDealDTO postAndDealDTO){
        boolean isPostInserted = false;
        dbConnection.openConnection();
        String insertPostSQL = "INSERT INTO d_posts(store_id, product_id, user_id, description, promotion_code, price, max_vote, start_date, end_date)" +
                "VALUES ((SELECT d_stores.id FROM d_stores WHERE d_stores.name = ?)," +
                "(SELECT d_products.id FROM d_products WHERE d_products.name = ?)," +
                "(SELECT d_user.id FROM d_user WHERE d_user.id = ?)," +
                "?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement=dbConnection.connection.prepareStatement(insertPostSQL);
            preparedStatement.setString(1, postAndDealDTO.getStoreDTO().getName());
            preparedStatement.setString(2, postAndDealDTO.getProductDTO().getName());
            preparedStatement.setInt(3, postAndDealDTO.getUserManagementDTO().getId());
            preparedStatement.setString(4, postAndDealDTO.getDescription());
            preparedStatement.setString(5, postAndDealDTO.getPromotion_code());
            preparedStatement.setDouble(6, postAndDealDTO.getPrice());
            preparedStatement.setInt(7, postAndDealDTO.getMaxVotes());
            preparedStatement.setDate(8, postAndDealDTO.getStartDate());
            preparedStatement.setDate(9, postAndDealDTO.getEndDate());
            int numOfInserted = preparedStatement.executeUpdate();
            if(numOfInserted > 0){
                isPostInserted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return isPostInserted;
    }

    public ObservableList<PostAndDealDTO> postAndDealDTOObservableList(){
        ObservableList<PostAndDealDTO> postAndDealDTOS = FXCollections.observableArrayList();
        dbConnection.openConnection();
        String postAndDealSQL = "SELECT d_posts.id, d_stores.name, d_products.name, d_posts.promotion_code, d_posts.price," +
                "d_posts.max_vote, d_posts.start_date, d_posts.end_date, d_posts.created_date FROM d_posts " +
                "JOIN d_stores on d_posts.store_id = d_stores.id  " +
                "JOIN d_products on d_posts.product_id = d_products.id  " +
                "JOIN d_user on d_posts.user_id = d_user.id " +
                "WHERE d_user.id = ?  AND d_posts.status IS TRUE ORDER BY d_posts.created_date";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(postAndDealSQL);
            preparedStatement.setInt(1, userManagementDAO.userLogedin.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                PostAndDealDTO postAndDealDTO = new PostAndDealDTO();
                postAndDealDTO.setId(resultSet.getInt(1));
                StoreDTO storeDTO = new StoreDTO();
                storeDTO.setName(resultSet.getString(2));
                postAndDealDTO.setStoreDTO(storeDTO);
                ProductDTO productDTO = new ProductDTO();
                productDTO.setName(resultSet.getString(3));
                postAndDealDTO.setProductDTO(productDTO);
//                UserManagementDTO userManagementDTO = new UserManagementDTO();
//                userManagementDTO.setFullName(resultSet.getString(4));
                postAndDealDTO.setPromotion_code(resultSet.getString(4));
                postAndDealDTO.setPrice(resultSet.getDouble(5));
                postAndDealDTO.setMaxVotes(resultSet.getInt(6));
                postAndDealDTO.setStartDate(resultSet.getDate(7));
                postAndDealDTO.setEndDate(resultSet.getDate(8));
                postAndDealDTO.setCreated_date(resultSet.getTimestamp(9));
                postAndDealDTOS.addAll(postAndDealDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }

        return postAndDealDTOS;
    }

    public ObservableList<PostAndDealDTO> newDealDTOObservableList(boolean sortedByExpireDate, boolean showIfExpired){
        ObservableList<PostAndDealDTO> newPostandDealDTOs = FXCollections.observableArrayList();
        dbConnection.openConnection();
        String sort = "d_posts.created_date";
        String showExpired = " >= ";

        if (sortedByExpireDate)
            sort = "d_posts.end_date DESC";

        if (showIfExpired)
            showExpired = " <= ";

        String newPostandDealSQL = "SELECT d_posts.id, d_stores.name, d_products.name, d_posts.description, d_posts.promotion_code, d_posts.end_date, " +
                "d_posts.price, d_posts.max_vote, d_posts.created_date FROM d_posts " +
                "JOIN d_stores on d_posts.store_id = d_stores.id " +
                "JOIN d_products on d_posts.product_id = d_products.id " +
                "WHERE d_posts.status IS TRUE  AND d_posts.end_date " + showExpired + "  now() ORDER BY " + sort;
        try {
            preparedStatement = dbConnection.connection.prepareStatement(newPostandDealSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                PostAndDealDTO postAndDealDTO = new PostAndDealDTO();
                postAndDealDTO.setId(resultSet.getInt(1));
                StoreDTO storeDTO = new StoreDTO();
                storeDTO.setName(resultSet.getString(2));
                postAndDealDTO.setStoreDTO(storeDTO);
                ProductDTO productDTO = new ProductDTO();
                productDTO.setName(resultSet.getString(3));
                postAndDealDTO.setProductDTO(productDTO);
                postAndDealDTO.setDescription(resultSet.getString(4));
                postAndDealDTO.setPromotion_code(resultSet.getString(5));
                postAndDealDTO.setEndDate(resultSet.getDate(6));
                postAndDealDTO.setPrice(resultSet.getDouble(7));
                postAndDealDTO.setMaxVotes(resultSet.getInt(8));
                postAndDealDTO.setCreated_date(resultSet.getTimestamp(9));
                newPostandDealDTOs.addAll(postAndDealDTO);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return newPostandDealDTOs;
    }



}
