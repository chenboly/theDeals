package khmerdeals.models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

        public Connection connection = null;

        public void openConnection() {
            String url = "jdbc:postgresql://localhost:5432/thedeals";

            try {
                connection = DriverManager.getConnection(url, "db_user", "123456789");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void closeConnection() {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}


