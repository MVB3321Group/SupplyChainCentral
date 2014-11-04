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
import java.util.ArrayList;
import java.util.Properties;
import tableobjects.*;

/**
 *
 * @author Benjamin
 */
public class DatabaseConnection {
    private int role;
    private Connection connection;
    
    public DatabaseConnection(int role) {
        this.role = role;
        try {
            connection = getConnection(role);
        }
        catch (SQLException e) {
            System.err.println("Unable to connect to database.");//change
            e.printStackTrace();
        }
    }
    
    public void switchUser(int newRole) {
        this.role = newRole;
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
                    "(originatorID, origin, destination, priority)" +
                    "VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, shipment.getOriginatorID());
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
    
    public boolean updateShipment(Shipment shipment) {
        PreparedStatement pstmt;
        StringBuilder command = new StringBuilder("UPDATE shipments SET ");
        command.append("originatorID = ?, origin = ?, destination = ?, ");
        command.append("priority = ?, scheduleID = ?, startTime = ?, ");
        command.append("endTime = ?, currentLocation = ?");
        command.append("WHERE shipID = ?");
        try {
            pstmt = connection.prepareStatement(command.toString());
            pstmt.setInt(1, shipment.getOriginatorID());
            pstmt.setString(2, shipment.getOrigin());
            pstmt.setString(3, shipment.getDestination());
            pstmt.setInt(4, shipment.getPriority());
            pstmt.setInt(5, shipment.getScheduleID());
            pstmt.setDate(6, shipment.getStartTime());
            pstmt.setDate(7, shipment.getEndTime());
            pstmt.setString(8, shipment.getCurrentLocation());
            pstmt.setInt(9, shipment.getShipID());
            return pstmt.execute();
        }
        catch (SQLException e) {
            System.err.println("Unable to update shipment.");
            e.printStackTrace();
            return false;
        }
    }
    
    //warning: not tested (at all)
    public ArrayList<Shipment> getShipments() {
        Statement stmt;
        ResultSet rs;
        ArrayList<Shipment> shipments = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Shipments");
            while (rs.next()) {
                Shipment s = new Shipment(
                        rs.getInt("shipID"),
                        rs.getInt("originatorID"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getInt("priority"),
                        rs.getInt("scheduleID"),
                        rs.getDate("startTime"),
                        rs.getDate("endTime"),
                        rs.getString("currentLocation")
                );
                shipments.add(s);
            }
        }
        catch (SQLException e) {
            System.err.println("Unable to retrieve shipment list.");
            e.printStackTrace();
        }
        return shipments;
    }
    
    //warning: not tested (at all)
    public ArrayList<Location> getLocations() {
        Statement stmt;
        ResultSet rs;
        ArrayList<Location> locations = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Locations");
            while (rs.next()) {
                Location l = new Location(
                        rs.getString("locationCode"),
                        rs.getInt("locationType"),
                        rs.getString("address"),
                        rs.getInt("zip"),
                        rs.getString("city"),
                        rs.getString("state")
                );
                locations.add(l);
            }
        }
        catch (SQLException e) {
            System.err.println("Unable to retrieve location list.");
            e.printStackTrace();
        }
        return locations;
    }
    
    //TODO: public ArrayList<Product>
    
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