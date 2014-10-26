/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    DatabaseConnection
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
    private int role;
    
    public DatabaseConnection(int role)
    {
        this.role = role;
    }
    
//    public Connection getConnection() throws SQLException {
//        Connection conn = null;
//        Properties connectionProps = new Properties();
//        String user;
//        String password;
//        //Manage SQL access permissions
//        switch (role)
//        {
//            case 1: user = "spmanager"; password = "spmanager"; break;
//            case 2: user = "material"; password = "material"; break;
//            case 3: user = "dispatcher"; password = "dispatcher"; break;
//            case 4: user = "warehouse"; password = "warehouse"; break;
//            default: user = "dispatcher"; password = "dispatcher"; break;
//        }
//        //REPLACE Temporary code for present
//        user = "root";
//        password = "OtW@t&3kH1W";
//        //
//        connectionProps.put("user", user);
//        connectionProps.put("password", password);//"OtW@t&3kH1W"
//        conn = DriverManager.getConnection(
//                "jdbc:mysql://"
//                + "localhost:3306/supplychaincentral",
//                connectionProps);
//        System.out.println("Connected to database");
//        return conn;
//    }
}
