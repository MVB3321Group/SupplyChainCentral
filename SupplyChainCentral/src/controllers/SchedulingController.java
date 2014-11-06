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
    
    public static void populateProducts() { 
        ArrayList<String> productList = new ArrayList<>();
        
        // "Converts" Location into String objects for later use
        for (int i = 0; i < MainWindow.dbConn.getProducts().size() - 1; i++)
            productList.add(MainWindow.dbConn.getProducts().get(i).getPName());
  
        ObservableList<String> prodDropdownList
                = FXCollections.observableArrayList(productList);
        ShipmentWindow.PROD_DROPDOWN.getItems().addAll(prodDropdownList);
        ShipmentWindow.PROD_DROPDOWN.setOnAction(e -> {
                prodDropdownList.indexOf(ShipmentWindow.PROD_DROPDOWN.getValue());
        });
    }
    
    public static void populateDestinations() { 
        ArrayList<String> locationList = new ArrayList<>();
        
        // "Converts" Location into String objects for later use
        for (int i = 0; i < MainWindow.dbConn.getLocations().size() - 1; i++)
            locationList.add(MainWindow.dbConn.getLocations().get(i).getCity());
  
        ObservableList<String> destDropdownList
                = FXCollections.observableArrayList(locationList);
        ShipmentWindow.DEST_DROPDOWN.getItems().addAll(destDropdownList);
        ShipmentWindow.DEST_DROPDOWN.setOnAction(e -> {
                destDropdownList.indexOf(ShipmentWindow.DEST_DROPDOWN.getValue());
        });
    }
}