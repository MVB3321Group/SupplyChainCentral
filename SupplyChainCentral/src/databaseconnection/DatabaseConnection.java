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
    public DatabaseConnection(int role) {
        try {
            connection = getConnection(role);
        }
        catch (SQLException e) {
            System.err.println("Unable to connect to database.");//change
            e.printStackTrace();
        }
    }
    
    public void switchUser(int newRole) {
        try {
            connection.close();
            connection = getConnection(newRole);
        }
        catch (SQLException e) {
            System.err.println("Unable to switch users.");
            e.printStackTrace();
        }
    }
    
    public void close() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.err.println("Unable to properly close connection.");
            e.printStackTrace();
        }
    }
    
    private Connection getConnection(int role) throws SQLException {
        Connection conn;
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
     */
    public boolean insertShipment(Shipment shipment) {
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement("INSERT INTO shipments" + 
                    "(shipID, origin, destination, priority) VALUES" +
                    "(?, ?, ?, ?)");
            pstmt.setInt(1, shipment.getShipID());
            pstmt.setString(2, shipment.getOrigin());
            pstmt.setString(3, shipment.getDestination());
            pstmt.setInt(4, shipment.getPriority());
            return pstmt.execute();
        }
        catch (SQLException e) {
            System.err.println("Unable to insert shipment.");
            e.printStackTrace();
            return false;
        }
    }
    
//    public Shipment[] getShipments() {
//        Statement stmt;
//        ResultSet rs;
//        try {
//            stmt = connection.createStatement();
//            rs = stmt.executeQuery("SELECT * FROM Shipments");
//        }
//        catch (SQLException e) {
//            System.err.println("Unable to retrive shipment list.");
//            e.printStackTrace();
//        }
//    }
    
    /**
     * either getUser == null indicates invalid user, or there is a boolean
     * method isUser(employeeID, password)
     * @param employeeID
     * @param password
     * @return
     */
    public User getUser(int employeeID, String password) {
        PreparedStatement pstmt;
        ResultSet rs;
        User user = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM users "
                    + "WHERE employeeID = ? AND password = ?");
            pstmt.setInt(1, employeeID);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            String fName; String lName; int employeeID2;
            int managerID;
            int roleID;
            String locationCode;
            String password2;
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
        } catch (SQLException e) {
            System.err.println("Could not validate user.");
            e.printStackTrace();
        }
        return user;
    }
}
