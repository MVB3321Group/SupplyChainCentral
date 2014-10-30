/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    DatabaseConnection
    Interface between controllers and database
*/

package databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import tableobjects.*;

/**
 *
 * @author Benjamin
 */
public class DatabaseConnection {
    private int role;
    private Connection connection;
    
    //If connection is unsuccessful, propagate exception
    public DatabaseConnection(int role) throws SQLException {
        connection = getConnection(role);
    }
    
    public void switchUser(int newRole) throws SQLException {
        connection.close();
        connection = getConnection(newRole);
    }
    
    public void close() throws SQLException {
        connection.close();
    }
    
    private Connection getConnection(int role) throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        String user;
        String password;
        //Manage SQL access permissions
        switch (role)
        {
            case 0: user = "uservalidator"; password = "uservalidator"; break;
            case 1: user = "spmanager"; password = "spmanager"; break;
            case 2: user = "material"; password = "material"; break;
            case 3: user = "dispatcher"; password = "dispatcher"; break;
            case 4: user = "warehouse"; password = "warehouse"; break;
            default: user = "dispatcher"; password = "dispatcher"; break;
        }
        //REPLACE Temporary code for present
        user = "root";
        password = "OtW@t&3kH1W";
        //END REPLACE
        connectionProps.put("user", user);
        connectionProps.put("password", password);//"OtW@t&3kH1W"
        conn = DriverManager.getConnection(
                "jdbc:mysql://"
                + "localhost:3306/" +
                  "supplychaincentral",
                connectionProps);
        System.out.println("Connected to database");
        return conn;
    }
    
    /**
     * 
     * @param shipment
     * @return
     * @throws SQLException 
     */
    public boolean insertShipment(Shipment shipment) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        pstmt = connection.prepareStatement("INSERT INTO shipments" + 
                "(shipID, origin, destination, priority) VALUES" +
                "(?, ?, ?, ?)");
        pstmt.setInt(1, shipment.getShipID());
        pstmt.setString(2, shipment.getOrigin());
        pstmt.setString(3, shipment.getDestination());
        pstmt.setInt(4, shipment.getPriority());
        return pstmt.execute();
    }
    /**
     * either getUser == null indicates invalid user, or there is a boolean
     * method isUser(employeeID, password)
     * @param employeeID
     * @param password
     * @return
     * @throws java.sql.SQLException
     */
    public User getUser(int employeeID, String password) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        pstmt = connection.prepareStatement("SELECT * FROM users" +
                "WHERE employeeID = ? AND password = ?");
        pstmt.setInt(1, employeeID);
        pstmt.setString(2, password);
        rs = pstmt.executeQuery();
        String fName; String lName; int employeeID2; int managerID;
        int roleID; String locationCode; String password2;
        User user = null;
        while (rs.next()) {
            fName = rs.getString("fName");
            lName = rs.getString("lName");
            employeeID2 = rs.getInt("employeeID");
            managerID = rs.getInt("managerID");
            roleID = rs.getInt("roleID");
            locationCode = rs.getString("locationCode");
            password2 = rs.getString("password");
            user = new User(fName, lName, employeeID2, managerID,
                    roleID, locationCode, password2);
        }
        return user;
    }
}
