package database;


import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class DbHandler {
    private static DbHandler dbHandler = new DbHandler("database.properties");
    private Properties config;
    private String uri = null;
    private Random random = new Random();

    private DbHandler(String propertiesFile){
        this.config = loadConfigFile(propertiesFile);
        this.uri = "jdbc:mysql://"+ config.getProperty("hostname") + "/" + config.getProperty("db") + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    }

    public Properties loadConfigFile(String propertyFile) {
        Properties config = new Properties();
        try (FileReader fr = new FileReader(propertyFile)) {
            config.load(fr);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return config;
    }

    public static DbHandler getInstance() {
        return dbHandler;
    }

    public void createTable(String command) {
        Statement statement;
        try (Connection dbConnection = DriverManager.getConnection(uri, config.getProperty("username"), config.getProperty("password"))) {
            System.out.println("dbConnection successful");
            statement = dbConnection.createStatement();
            statement.executeUpdate(command);
            statement.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static String encodeHex(byte[] bytes, int length) {
        BigInteger bigint = new BigInteger(1, bytes);
        String hex = String.format("%0" + length + "X", bigint);

        assert hex.length() == length;
        return hex;
    }


    public static String getHash(String password, String salt) {
        String salted = salt + password;
        String hashed = salted;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salted.getBytes());
            hashed = encodeHex(md.digest(), 64);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }

        return hashed;
    }

    public void register(String username, String password) {
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);

        String usersalt = encodeHex(saltBytes, 32); // salt
        String passhash = getHash(password, usersalt); // hashed password

        PreparedStatement statement;
        try (Connection connection = DriverManager.getConnection(uri, config.getProperty("username"), config.getProperty("password"))) {
            System.out.println("dbConnection successful");
            try {
                statement = connection.prepareStatement("INSERT INTO users (username, password, usersalt) VALUES (?, ?, ?);");
                statement.setString(1, username);
                statement.setString(2, passhash);
                statement.setString(3, usersalt);
                statement.executeUpdate();
                statement.close();
            }
            catch(SQLException e) {
                System.out.println(e);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private String getSalt(Connection connection, String user) {
        String salt = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT usersalt FROM users WHERE username = ?;")) {
            statement.setString(1, user);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                salt = results.getString("usersalt");
                return salt;
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return salt;
    }

    public boolean authenticate(String username, String password) {
        PreparedStatement statement;
        try (Connection connection = DriverManager.getConnection(uri, config.getProperty("username"), config.getProperty("password"))) {
            statement = connection.prepareStatement("SELECT * FROM users WHERE username=? AND password=?;");
            String usersalt = getSalt(connection, username);
            String passhash = getHash(password, usersalt);

            statement.setString(1, username);
            statement.setString(2, passhash);
            ResultSet results = statement.executeQuery();
            boolean flag = results.next();
            System.out.println(flag);
            return flag;
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public boolean existedUser(String username) {
        boolean existed = false;
        try (Connection connection = DriverManager.getConnection(uri, config.getProperty("username"), config.getProperty("password"))) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=?;");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                String user = results.getString("username");
                if(user.equals(username)) existed = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existed;
    }


    public static void main(String[] args) {
        String createTable =
                "CREATE TABLE users (" +
                        "userid INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                        "username VARCHAR(32) NOT NULL UNIQUE, " +
                        "password CHAR(64) NOT NULL, " +
                        "usersalt CHAR(32) NOT NULL);";
        DbHandler dbHandler = DbHandler.getInstance();
        dbHandler.createTable(createTable);
    }

}
