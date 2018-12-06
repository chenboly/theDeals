package khmerdeals.models.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {
    private DBConnection dbConnection = new DBConnection();
    private PreparedStatement preparedStatement;

    public List<String> roleList (){
        List<String> userRoleList = new ArrayList<>();
        dbConnection.openConnection();
        String roleListSQL = "SELECT role_name FROM d_user_role ORDER BY id";
        try {
            preparedStatement = dbConnection.connection.prepareStatement(roleListSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userRoleList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbConnection.closeConnection();
        }

    return userRoleList;
    }
}
