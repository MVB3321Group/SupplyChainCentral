/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Controller
*/

package databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Benjamin
 */
public class DatabaseConnection {
    private Connection connection;
    
    public DatabaseConnection()
    {
        try {
            this.connection = getConnection();
        }
        catch (SQLException e)
        {
            System.err.println("Connection not initialized!");
            //TODO: Must figure out how to handle this error...
        }
    }
    
    private Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "OtW@t&3kH1W");
        conn = DriverManager.getConnection(
                "jdbc:mysql://"
                + "localhost"
                + ":3306/supplychaincentral",
                connectionProps);
        System.out.println("Connected to database");
        return conn;
    }
}
