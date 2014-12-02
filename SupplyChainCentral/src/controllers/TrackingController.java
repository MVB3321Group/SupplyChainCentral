/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    TrackingController
*/
package controllers;

import databaseconnection.DatabaseConnection;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tableobjects.Inventory;
import tableobjects.Location;
import tableobjects.User;
import windows.*;

/**
 *
 * @author Benjamin
 */
public class TrackingController {
    
    public InventoryWindow inventoryWindow;
    DatabaseConnection dbConn;
    User user;

    public TrackingController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        inventoryWindow = new InventoryWindow();
        populateLocations();
        populateInventoryTable();
        
        // Event handler; Filter inventory by location from dropdown
        inventoryWindow.LOCATION_DROPDOWN.setOnAction(e -> {
            populateInventoryTable();
            ArrayList<Inventory> inventoryFiltered = new ArrayList<>();
            for (Inventory i : inventoryWindow.INVENTORY_TABLE.getItems()) {
                if (i.getLocationCode().equals(inventoryWindow.LOCATION_DROPDOWN.getValue()))
                    inventoryFiltered.add(i);
            }
            inventoryWindow.INVENTORY_TABLE.getItems().clear();
            inventoryWindow.INVENTORY_TABLE.getItems().addAll(inventoryFiltered);
        });
    }
    
    private void populateInventoryTable() {
        ArrayList<Inventory> inventory = dbConn.getInventory();
        inventoryWindow.INVENTORY_TABLE.getItems().clear();
        inventoryWindow.INVENTORY_TABLE.getItems().addAll(inventory);
    }
    
    private void populateLocations() {
        ArrayList<String> locationList = new ArrayList<>();
        
       for (Location l : dbConn.getLocations())
            locationList.add(l.getLocationCode());
  
        ObservableList<String> locationDropdownList
                = FXCollections.observableArrayList(locationList);
        inventoryWindow.LOCATION_DROPDOWN.getItems().addAll(locationDropdownList);
        inventoryWindow.LOCATION_DROPDOWN.setOnAction(e -> {
            locationDropdownList.indexOf(inventoryWindow.LOCATION_DROPDOWN.getValue());
        });
    }

    public void setUser(User user) {
        this.user = user;
    }
}