/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    DataGeneratorUtility
    This class generates random records for the supply chain database
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

public class DataGeneratorUtility {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection conn = getConnection();
            stmt = conn.createStatement();
//            pstmt = conn.prepareStatement("INSERT INTO users " +
//                    "VALUES (?, ?, ?, ?, ?, ?)");
//            pstmt.setString(1, "Jerry");
//            pstmt.setString(2, "Williamson");
//            pstmt.setInt(3, 2223);
//            pstmt.setInt(4, 1111);
//            pstmt.setInt(5, 2);
//            pstmt.setString(6, "SAV");
//            pstmt.execute();
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                int employeeID = rs.getInt("employeeID");
                int managerID = rs.getInt("managerID");
                int roleID = rs.getInt("roleID");
                String locationCode = rs.getString("locationCode");
                System.out.println("(" + fName + "," + lName
                        + "," + employeeID + "," + managerID
                        + "," + roleID + "," + locationCode + ")");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
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