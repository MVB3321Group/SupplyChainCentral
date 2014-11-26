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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tableobjects.Location;
import tableobjects.Product;
import tableobjects.ProductShipped;
import tableobjects.Shipment;
import tableobjects.User;
import windows.*;

/**
 *
 * @author Benjamin
 */
public class TrackingController {
    
    public InventoryWindow inventoryWindow;
    SchedulingController sController;
    TableView<ProductShipped> inventoryTable;
    DatabaseConnection dbConn;
    User user;

    public TrackingController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        inventoryWindow = new InventoryWindow();
        populateLocations();
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
}