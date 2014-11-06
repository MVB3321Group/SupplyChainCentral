/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    SchedulingController
*/

package controllers;

import tableobjects.*;
import windows.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;


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
        for (int i = 0; i < MainWindow.dbConn.getProducts().size(); i++)
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
        for (int i = 0; i < MainWindow.dbConn.getLocations().size(); i++)
            locationList.add(MainWindow.dbConn.getLocations().get(i).getCity());
  
        ObservableList<String> destDropdownList
                = FXCollections.observableArrayList(locationList);
        ShipmentWindow.DEST_DROPDOWN.getItems().addAll(destDropdownList);
        ShipmentWindow.DEST_DROPDOWN.setOnAction(e -> {
                destDropdownList.indexOf(ShipmentWindow.DEST_DROPDOWN.getValue());
        });
    }
    
    public static void populateShipmentsTable() {
        ObservableList<Shipment> shipmentList
                = FXCollections.observableArrayList(MainWindow.dbConn.getShipments());
        TableView<Shipment> shipTable = ShipmentWindow.SHIPMENTS_TABLE;
        shipTable.setItems(shipmentList);
        
        TableColumn<Shipment, String> riginatorCol = new TableColumn<>("Originator");
        riginatorCol.setCellValueFactory(new PropertyValueFactory<Shipment,String>("originatorID"));
        TableColumn<Shipment, String> originCol = new TableColumn<>("Origin");
        originCol.setCellValueFactory(new PropertyValueFactory<Shipment,String>("origin"));
        TableColumn<Shipment, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<Shipment,String>("destination"));
        TableColumn<Shipment, String> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<Shipment,String>("priority"));
        
        shipTable.getColumns().setAll(riginatorCol, originCol, destCol, priorityCol);
    }
}
