/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    SchedulingController
*/

package controllers;

import controllers.*;
import databaseconnection.*;
import tableobjects.*;
import tools.Toolbar;
import windows.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Benjamin
 */

public class SchedulingController {
    public static DatabaseConnection dbConn;
    
    public SchedulingController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
    }

    public static void openShipmentWindow() {
        // Below step instantiates class-specific object only when necessary
        if (! ShipmentWindow.shipmentWindow.isIconified()) {
            ShipmentWindow obj = new ShipmentWindow(); // "dummy" instance
        }
        
        MainWindow.mainWindow.close(); // First close main window
        Controller.openWindow(ShipmentWindow.shipmentWindow,
                              ShipmentWindow.aPane, 1344, 686);
        
        ShipmentWindow.shipmentWindow.setIconified(false);
    }
    
    public static void populateDestinations() { 
        Location[] locationList = new Location[dbConn.getLocations().size()];
        
        // "Converts" Location into String objects for later use
        for (int i = 0; i < dbConn.getLocations().size() - 1; i++)
            locationList[i] = dbConn.getLocations().get(i);
  
        ObservableList<Location> destDropdownList
                = FXCollections.observableArrayList(locationList);
        ShipmentWindow.DEST_DROPDOWN.getItems().addAll(destDropdownList);
        ShipmentWindow.DEST_DROPDOWN.setOnAction(e -> {
                destDropdownList.indexOf(ShipmentWindow.DEST_DROPDOWN.getValue());
        }); 
    }
    
    public static void populateProducts() {
        Product[] productList = new Product[dbConn.getProducts().size()];
        
        // "Converts" Location into String objects for later use
        for (int i = 0; i < dbConn.getProducts().size() - 1; i++)
            productList[i] = dbConn.getProducts().get(i);
  
        ObservableList<Product> prodDropdownList
                = FXCollections.observableArrayList(productList);
        ShipmentWindow.PROD_DROPDOWN.getItems().addAll(prodDropdownList);
        ShipmentWindow.PROD_DROPDOWN.setOnAction(e -> {
                prodDropdownList.indexOf(ShipmentWindow.PROD_DROPDOWN.getValue());
        }); 
    }
}