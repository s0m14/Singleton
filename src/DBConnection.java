import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.*;
import java.net.URI;

public class DBConnection {
    private static DBConnection instance = null;
    private String URL;
    private String username;
    private String password;
    private Connection connection = null;
    private Statement statement = null;

    private DBConnection() {
        loadConfig();
    }

    private void loadConfig() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject config = (JSONObject) parser.parse(new FileReader("C:\\Users\\arsen\\IdeaProjects\\Singleton\\src\\config.json"));
            URL = (String) config.get("URL");
            username = (String) config.get("username");
            password = (String) config.get("password");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void init() throws SQLException {
        connection = DriverManager.getConnection(URL, username, password);
    }

    public Connection getConnection() throws SQLException {
        init();
        statement = connection.createStatement();
        return connection;
    }

    public static DBConnection getInstance(){
        if(instance == null){
            synchronized (DBConnection.class){
                if(instance == null){
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public void select(String table) throws SQLException {
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + table);
        ResultSetMetaData md = rs.getMetaData();
        int count = md.getColumnCount();

        while (rs.next()){
            for(int i = 1 ; i <= count ; i++){
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
        statement.close();
    }

    public void update(String table,String id,String title) throws SQLException {
        statement = connection.createStatement();
        int update = statement.executeUpdate("UPDATE " + table + " SET Title = '" + title + "'"+
                "WHERE USERID = " + id);
        statement.close();
    }

    public void insert(String table,String id,String firstName,String lastName, String title) throws SQLException {
        statement = connection.createStatement();
        int insert = statement.executeUpdate("INSERT INTO " + table + " VALUES" + "(" + id + "," + "'"+firstName+"'" + "," +
                "'"+lastName+"'" + "," + "'"+title+"'"+")");
        statement.close();
    }

    public void delete(String table, String id) throws SQLException {
        statement = connection.createStatement();
        int delete = statement.executeUpdate("DELETE FROM " + table + " WHERE userid = " + id);
        statement.close();
    }

    public String getDatabaseName() {
        int index = URL.lastIndexOf('/');
        if (index != -1 && index < URL.length() - 1) {
            return URL.substring(index + 1);
        }
        return null;
    }

    public String getURL(){
        return URL;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHostname() throws URISyntaxException {
        URI uri = new URI(URL.substring(5));
        return uri.getHost();
    }
}
