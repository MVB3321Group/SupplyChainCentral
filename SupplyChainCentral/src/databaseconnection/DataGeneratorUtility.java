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
        DatabaseConnection conn = new DatabaseConnection(1);
        ArrayList<Location> locations = conn.getLocations();
//        for (int i = 0; i < 10; i++) {
//            int origIndex = (int) (Math.random() * locations.size());
//            int destIndex = (int) (Math.random() * locations.size());
//            if (destIndex == origIndex) {
//                origIndex = (destIndex < locations.size() - 1) ? ++origIndex : --origIndex;                    
//            }
//            Location origin = locations.get(origIndex);
//            Location dest = locations.get(destIndex);
//            int priority = (int) ((Math.random() * 5) + 1);
//            Shipment s = new Shipment(2223, origin.getLocationCode(), dest.getLocationCode(), priority);
//            conn.insertShipment(s);
//        }
    }
}