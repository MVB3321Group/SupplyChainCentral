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
import java.util.ArrayList;
import java.util.Properties;
import tableobjects.*;
/**
 *
 * @author Benjamin
 */

public class DataGeneratorUtility {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DatabaseConnection conn = new DatabaseConnection(1);
            ArrayList<Location> locations = conn.getLocations();
            ArrayList<Product> products = conn.getProducts();
            for (Location l : locations) {
                for (Product p : products) {
                    Inventory i = new Inventory(l.getLocationCode(),
                        p.getProductID(), (int)(Math.random() * 100.0));
                    conn.upsertInventory(i);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}