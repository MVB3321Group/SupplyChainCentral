/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    SchedulingController
*/

package controllers;

import databaseconnection.DatabaseConnection;
import tableobjects.*;
import windows.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import javafx.scene.paint.Color;

/**
 *
 * @author Benjamin
 */

public class SchedulingController {
    public ShipmentWindow shipmentWindow;
    TableView<Shipment> shipTable;
    DatabaseConnection dbConn;
    User user;

    public SchedulingController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        shipmentWindow = new ShipmentWindow();
        
        /* At this time, because toolbar is an object of each window, toolbar
           logic has to be re-implemented in each controller; this should not be
           the case */
        
//        shipmentWindow.toolbar.FILE_DROPDOWN.setOnAction(e -> {
//            switch (shipmentWindow.toolbar.FILE_DROPDOWN.getValue()) {
//                case "New Shipment":
//                    shipmentWindow.close();
//                    shipmentWindow.show();
//                    break;
//            }
//            
//            // Simple fix to not allow selected option to change dropdown title
//            shipmentWindow.toolbar.FILE_DROPDOWN.setValue("File");
//        });

        // Population for dropdown
        populateProducts();
        populateOrigins();
        populateDestinations();
        populateShipmentChart();
        
        shipmentWindow.CREATE_SHIPMENT_BUTTON.setOnAction(e -> {
            shipmentWindow.DESTINATIONS_CHART.getData().clear();
            createShipment();
            populateShipmentsTable();
            populateShipmentChart();
        });
    }
    
    public void setUser(User user) {
        this.user = user;
        populateShipmentsTable();
    }
    
    public void createShipment() {
        int originatorID = user.getEmployeeID();
        String orig = shipmentWindow.ORIG_DROPDOWN.getValue();
        String dest = shipmentWindow.DEST_DROPDOWN.getValue();
        int prty = Integer.valueOf(shipmentWindow.PRTY_DROPDOWN.getValue());
        
        Shipment shpmt = new Shipment(originatorID, orig, dest, prty);
        dbConn.insertShipment(shpmt);
    }
    
    public void populateProducts() { 
        ArrayList<String> productList = new ArrayList<>();
        
        // "Converts" Product objects into String objects for later use
        for (int i = 0; i < dbConn.getProducts().size(); i++)
            productList.add(dbConn.getProducts().get(i).getPName());
  
        ObservableList<String> prodDropdownList
                = FXCollections.observableArrayList(productList);
        shipmentWindow.PROD_DROPDOWN.getItems().addAll(prodDropdownList);
        shipmentWindow.PROD_DROPDOWN.setOnAction(e -> {
                prodDropdownList.indexOf(shipmentWindow.PROD_DROPDOWN.getValue());
        });
    }
    
    public void populateOrigins() { 
        ArrayList<String> origList = new ArrayList<>();
        
        // "Converts" Location objects into String objects for later use
        for (int i = 0; i < dbConn.getLocations().size(); i++)
            origList.add(dbConn.getLocations().get(i).getLocationCode());
  
        ObservableList<String> origDropdownList
                = FXCollections.observableArrayList(origList);
        shipmentWindow.ORIG_DROPDOWN.getItems().addAll(origDropdownList);
        shipmentWindow.ORIG_DROPDOWN.setOnAction(e -> {
            origDropdownList.indexOf(shipmentWindow.ORIG_DROPDOWN.getValue());
        });
    }
    
    public void populateDestinations() { 
        ArrayList<String> destList = new ArrayList<>();
        
        // "Converts" Destination objects into String objects for later use
        for (int i = 0; i < dbConn.getLocations().size(); i++)
            destList.add(dbConn.getLocations().get(i).getLocationCode());
  
        ObservableList<String> destDropdownList
                = FXCollections.observableArrayList(destList);
        shipmentWindow.DEST_DROPDOWN.getItems().addAll(destDropdownList);
        shipmentWindow.DEST_DROPDOWN.setOnAction(e -> {
                destDropdownList.indexOf(shipmentWindow.DEST_DROPDOWN.getValue());
        });
    }
    
    public void populateShipmentsTable() {
        ArrayList<Shipment> shipments = new ArrayList<Shipment>();
        for (Shipment s : dbConn.getShipments()) {
            if (s.getOriginatorID() == user.getEmployeeID()) {
                shipments.add(s);
            }
        }
        ObservableList<Shipment> shipmentList
                = FXCollections.observableArrayList(shipments);
        shipTable = shipmentWindow.SHIPMENTS_TABLE;
        shipTable.setItems(shipmentList);
        
        TableColumn<Shipment, String> riginatorCol = new TableColumn<>("Originator");
        riginatorCol.setCellValueFactory(new PropertyValueFactory<>("originatorID"));
        TableColumn<Shipment, String> originCol = new TableColumn<>("Origin");
        originCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
        TableColumn<Shipment, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        TableColumn<Shipment, String> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        
        shipTable.getColumns().setAll(riginatorCol, originCol, destCol, priorityCol);
    }
    
    public void populateShipmentChart() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        ArrayList<Shipment> shipments = dbConn.getShipments();
        ArrayList<Location> locations = dbConn.getLocations();
        
        int[] count = new int[locations.size()];    
        int max = 0;
        
        for (Shipment s : shipments) {
            for (int i = 0; i < locations.size(); i++) {
                if (s.getDestination().equals(locations.get(i).getLocationCode())) {
                    count[i]++;
                    if (count[i] > max)
                        max = count[i];
                    break;
                }
            }
        }
        shipmentWindow.Y_AXIS.setUpperBound(Math.ceil(max * 1.1));
        
        for (int i = 0; i < count.length; i++)
            series.getData().add(new XYChart.Data(locations.get(i).getCity(), count[i]));
        
        shipmentWindow.DESTINATIONS_CHART.getData().add(series);
    }

    public void scheduleShipments() {
        // get an arraylist of the current shipments.
        ArrayList<Shipment> shipments = dbConn.getShipments();
        // create a priorityqueue. This PQ will be accessed to see 
        // what shipment is to be sent out next. By this I mean a 
        // starttime will be given to the shipment.
        Queue<Shipment> schedulePriorityQueue = new PriorityQueue<>(5, priorityComparator);

        for (Shipment s : shipments) {
            schedulePriorityQueue.add(s);
        }
    }

     //Comparator anonymous class implementation
    public Comparator<Shipment> priorityComparator =
      (Shipment s1, Shipment s2) -> (int) (s1.getPriority() - s2.getPriority());
    
    public void getScheduledShipments (Queue<Shipment> schedulePriorityQueue){
         while (true) {
            Shipment shpmt = schedulePriorityQueue.poll();
            if (shpmt == null) break;
            System.out.println("Processing Shipment with Priority " + shpmt.getPriority());
        }
    }
}
