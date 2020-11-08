package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static final String DB_URL = "jdbc:mysql://loginapp.c6zrixw9dnvf.us-east-1.rds.amazonaws.com:3306/LoginApp?useSSL=false";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "admin";
    static final String DB_PASSWD = "H6TWCpw3RbKVhKWf4ZUa";

    public static Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
        return connection;
    }
    
}
