package khmerdeals.models.dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import khmerdeals.models.dto.StoreDTO;
import khmerdeals.models.dto.UserManagementDTO;
import khmerdeals.models.dto.UserRoleDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserManagementDAO {
    //private UserManagementDTO userManagementDTO = new UserManagementDTO();
    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement preparedStatement;
    public static UserManagementDTO userLogedin;



    public UserManagementDTO userLogin(UserManagementDTO userManagementDTO) {
        dbConnection.openConnection();
        String userLoginSQL = "SELECT * FROM d_user WHERE username=? AND password=md5(?) AND status IS TRUE";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(userLoginSQL);
            preparedStatement.setString(1, userManagementDTO.getUserName());
            preparedStatement.setString(2, userManagementDTO.getPassWord());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userLogedin = new UserManagementDTO();
                userLogedin.setId(resultSet.getInt(1));
                userLogedin.setFullName(resultSet.getString(2));
                userLogedin.setUserName(resultSet.getString(3));
                userLogedin.setImage_url(resultSet.getString(5));
                userLogedin.setPhone(resultSet.getString(6));
                userLogedin.setEmail(resultSet.getString(7));
                userLogedin.setCreated_date(resultSet.getDate(9));

            }
            preparedStatement.close();

            //TODO: Select Role from d_user_role
            if (userLogedin != null) {
                //take userid to find role
                String sqlFindRole = "SELECT role_id, role_name " +
                        "FROM d_user_and_role " +
                        "JOIN d_user_role ON d_user_and_role.role_id = d_user_role.id " +
                        "WHERE user_id=?";
                preparedStatement = dbConnection.connection.prepareStatement(sqlFindRole);
                preparedStatement.setInt(1, userLogedin.getId());
                ResultSet roleResultSet = preparedStatement.executeQuery();
                UserRoleDTO userRoleDTO;
                List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
                while (roleResultSet.next()) {
                    userRoleDTO = new UserRoleDTO();
                    userRoleDTO.setId(roleResultSet.getInt(1));
                    userRoleDTO.setRole_name(roleResultSet.getString(2));
                    userRoleDTOList.add(userRoleDTO);
                }
                userLogedin.setUserRoleDTOList(userRoleDTOList);
            }

//            if(userLogedin != null){
//                //take userid to find store
//                String findStoreSQL = "SELECT d_user.id, d_stores.id " +
//                        "FROM d_user " +
//                        "JOIN d_stores ON d_user.id = d_stores.user_id " +
//                        "WHERE d_user.id = ? " +
//                        "AND d_user.status IS TRUE ORDER BY d_stores.id";
//                preparedStatement = dbConnection.connection.prepareStatement(findStoreSQL);
//                preparedStatement.setInt(1, userLogedin.getId());
//                ResultSet findStoreResultSet = preparedStatement.executeQuery();
//                StoreDTO storeDTO;
//                List<StoreDTO> storeDTOList = new ArrayList<>();
//                while (findStoreResultSet.next()){
//                    storeDTO = new StoreDTO();
//                    storeDTO.setId(findStoreResultSet.getInt(1));
//                    storeDTO.setName(findStoreResultSet.getString(2));
//                    storeDTOList.add(storeDTO);
//                }
//                userLogedin.setStoreDTOList(storeDTOList);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
        return userLogedin;
    }

    public ObservableList<UserManagementDTO> userLoginDTOObservableList() {
        ObservableList<UserManagementDTO> userManagementDTOS = FXCollections.observableArrayList();
        dbConnection.openConnection();
        String userListSQL = "SELECT * FROM d_user WHERE status IS TRUE ORDER BY id ";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(userListSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserManagementDTO userManagementDTO = new UserManagementDTO();
                userManagementDTO.setId(resultSet.getInt(1));
                userManagementDTO.setFullName(resultSet.getString(2));
                userManagementDTO.setUserName(resultSet.getString(3));
                userManagementDTO.setPhone(resultSet.getString(6));
                userManagementDTO.setEmail(resultSet.getString(7));
                userManagementDTOS.addAll(userManagementDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
        return userManagementDTOS;
    }

    public Boolean insertNewUser(UserManagementDTO userManagementDTO, String user_Role) {
        boolean isNewUserInserted = false;
        dbConnection.openConnection();
        String insertNewSQL = "INSERT INTO d_user (fullname, username, password, phone, email, image_url) VALUES (?,?,md5(?),?,?,?)";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(insertNewSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userManagementDTO.getFullName());
            preparedStatement.setString(2, userManagementDTO.getUserName());
            preparedStatement.setString(3, userManagementDTO.getPassWord());
            preparedStatement.setString(4, userManagementDTO.getPhone());
            preparedStatement.setString(5, userManagementDTO.getEmail());
            preparedStatement.setString(6, userManagementDTO.getImage_url());
            int numberOfRowInsert = preparedStatement.executeUpdate();
            if (numberOfRowInsert > 0) {
                //get user id to insert with User
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    String insertUserIdAndRoleId = "INSERT INTO d_user_and_role (user_id, role_id) VALUES " +
                            "(?, (SELECT id FROM d_user_role WHERE role_name=?))";
                    preparedStatement = dbConnection.connection.prepareStatement(insertUserIdAndRoleId);
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setString(2, user_Role);
                    int numberOfRowInsertToRole = preparedStatement.executeUpdate();
                    if (numberOfRowInsertToRole > 0) {
                        isNewUserInserted = true;
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
        return isNewUserInserted;
    }

    public Boolean registerNewUser(UserManagementDTO userManagementDTO, String defaultUserRole) {
        boolean isNewUserRegistered = false;
        dbConnection.openConnection();
        String registerNewUserSQL = "INSERT INTO d_user (fullname, username, password, phone, email, image_url) VALUES (?,?,md5(?),?,?,?)";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(registerNewUserSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userManagementDTO.getFullName());
            preparedStatement.setString(2, userManagementDTO.getUserName());
            preparedStatement.setString(3, userManagementDTO.getPassWord());
            preparedStatement.setString(4, userManagementDTO.getPhone());
            preparedStatement.setString(5, userManagementDTO.getEmail());
            preparedStatement.setString(6, userManagementDTO.getImage_url());
            int numOfRowNewUserRegister = preparedStatement.executeUpdate();
            if(numOfRowNewUserRegister>0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()){
                    int registerUserId = resultSet.getInt(1);
                    String insertUserIdAndRoleId = "INSERT INTO d_user_and_role (user_id, role_id) VALUES " +
                            "(?, 2)";
                    preparedStatement = dbConnection.connection.prepareStatement(insertUserIdAndRoleId);
                    preparedStatement.setInt(1, registerUserId);
                    preparedStatement.setString(2, defaultUserRole);
                    int numberOfRowInsertToRole = preparedStatement.executeUpdate();
                    if(numberOfRowInsertToRole > 0){
                        isNewUserRegistered = true;
                    }

                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();

        }
        finally {
            dbConnection.closeConnection();
        }
        return isNewUserRegistered;
    }

    public boolean checkUserName (String username){
        boolean isUsernameExisted = false;
        dbConnection.openConnection();
        String checkUserNameSQL = "SELECT * FROM d_user WHERE username = '"+username+"'";
        isUsernameExisted = isEmailAddressAndUserNameExisted(isUsernameExisted, checkUserNameSQL);
        return isUsernameExisted;
    }

    public boolean checkEmailAddress (String email){
        boolean isEmailAddressExisted = false;
        dbConnection.openConnection();
        String checkEmailAddressSQL = "SELECT * FROM d_user WHERE email = '"+email+"'";
        isEmailAddressExisted = isEmailAddressAndUserNameExisted(isEmailAddressExisted, checkEmailAddressSQL);
        return isEmailAddressExisted;
    }

    private boolean isEmailAddressAndUserNameExisted(boolean isEmailAddressExisted, String checkEmailAddressSQL) {
        try {
            preparedStatement = dbConnection.connection.prepareStatement(checkEmailAddressSQL);
            ResultSet resultSetEmail = preparedStatement.executeQuery();
            while (resultSetEmail.next()){
                isEmailAddressExisted = true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }
        return isEmailAddressExisted;
    }
}
