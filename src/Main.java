import java.net.URISyntaxException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, URISyntaxException {
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();

        dbConnection.select("userslab5");

        System.out.println("Database name:"+ dbConnection.getDatabaseName());
        System.out.println("Username:" + dbConnection.getUsername());
        System.out.println("Password:" + dbConnection.getPassword() );
        System.out.println("Hostname:" + dbConnection.getHostname() );
    }
}
