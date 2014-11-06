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
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 *
 * @author Benjamin
 */

public class SchedulingController {
    
    public static void createShipment() {
        int originatorID = 2222;
        String orig = ShipmentWindow.ORIG_DROPDOWN.getValue();
        String dest = ShipmentWindow.DEST_DROPDOWN.getValue();
        int priority = Integer.valueOf(ShipmentWindow.PRIORITY_TF.getText());
        
        Shipment shpmt = new Shipment(originatorID, orig, dest, priority);
        MainWindow.dbConn.insertShipment(shpmt);
        System.out.println("Shipment successfully added to database");
    }
    
    public static void openShipmentWindow() {
        /* Creating an instance object for a window class sets off the
           entire flow of events for the class; only create instance object
           if the window is not already open ("iconified," in this case)
        */
        if (! ShipmentWindow.shipmentWindow.isIconified()) {
            ShipmentWindow obj = new ShipmentWindow(); // "dummy" instance
        }
        
        MainWindow.mainWindow.close(); // First close main window
        Controller.openWindow(ShipmentWindow.shipmentWindow,
                              ShipmentWindow.aPane, 1342, 686);
        
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
    
    public static void populateOrigins() { 
        ArrayList<String> origList = new ArrayList<>();
        
        // "Converts" Location into String objects for later use
        for (int i = 0; i < MainWindow.dbConn.getLocations().size(); i++)
            origList.add(MainWindow.dbConn.getLocations().get(i).getLocationCode());
  
        ObservableList<String> origDropdownList
                = FXCollections.observableArrayList(origList);
        ShipmentWindow.ORIG_DROPDOWN.getItems().addAll(origDropdownList);
        ShipmentWindow.ORIG_DROPDOWN.setOnAction(e -> {
            origDropdownList.indexOf(ShipmentWindow.ORIG_DROPDOWN.getValue());
        });
    }
    
    public static void populateDestinations() { 
        ArrayList<String> destList = new ArrayList<>();
        
        // "Converts" Location into String objects for later use
        for (int i = 0; i < MainWindow.dbConn.getLocations().size(); i++)
            destList.add(MainWindow.dbConn.getLocations().get(i).getLocationCode());
  
        ObservableList<String> destDropdownList
                = FXCollections.observableArrayList(destList);
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
    
    public static void populateShipmentChart() {
        ShipmentWindow.X_AXIS.setLabel("Destination City");
        ShipmentWindow.Y_AXIS.setLabel("Number of Shipments");
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        ArrayList<Shipment> shipments = MainWindow.dbConn.getShipments();
        ArrayList<Location> locations = MainWindow.dbConn.getLocations();
        int[] counts = new int[locations.size()];
        for (int h = 0; h < shipments.size(); h++) {
            
            for (int i = 0; i < locations.size(); i++) {
                if (shipments.get(h).getDestination().equals(locations.get(i).getLocationCode())) {
                    counts[i]++;
                    break;
                }
            }
        }
        
        for (int i = 0; i < counts.length; i++) {
            series.getData().add(new XYChart.Data(locations.get(i).getCity(), counts[i]));
        }
        
        ShipmentWindow.DESTINATIONS_CHART.getData().add(series);
    }
}