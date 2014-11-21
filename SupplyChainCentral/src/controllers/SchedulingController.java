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
import java.util.*;
/**
 *
 * @author Benjamin
 */

public class SchedulingController {
    public ShipmentWindow shipmentWindow;
    TableView<Shipment> shipTable;
    TableView<Shipment> schdTable;
    DatabaseConnection dbConn;
    User user;

    public SchedulingController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        shipmentWindow = new ShipmentWindow();
        
        // Population of shipment data
        populateProducts();
        populateOrigins();
        populateDestinations();
        populateShipmentChart();
        
        shipmentWindow.addProductsButton.setOnAction(e -> {
            String productName = shipmentWindow.PROD_DROPDOWN.getValue();
            int productID = dbConn.getProductIDByName(productName);
            int quantity = Integer.valueOf(shipmentWindow.QUANTITY_TF.getText());
            ProductShipped ps = new ProductShipped(productID, quantity);
            ps.setProductName(productName);
            shipmentWindow.productsTable.getItems().add(ps);
        });
        
        shipmentWindow.CREATE_SHIPMENT_BUTTON.setOnAction(e -> {
            shipmentWindow.DESTINATIONS_CHART.getData().clear();
            createShipment();
            //TODO add products in createShipment method
            populateShipmentsTable();
            populateShipmentChart();
        });
        
        shipmentWindow.SCHEDULE_SHIPMENTS_BUTTON.setOnAction(e -> {
            scheduleShipments();
            //getScheduledShipments();
            
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
        ArrayList<ProductShipped> products = 
                new ArrayList(shipmentWindow.productsTable.getItems());
        dbConn.insertShipment(shpmt, products);//switch back to insertShipment
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
        ArrayList<Shipment> shipments = new ArrayList<>();
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
        // starttime will be given to the shipment. Remember that you must 
        // sort the queue to get the proper queue values.
        Queue<Shipment> schedulePriorityQueue = new PriorityQueue<>(10, scheduleComparator);

        for (Shipment s : shipments) {
            //check ShipID, if the shipment has not been scheduled, then add it
            if (s.getShipID() == 0){
                schedulePriorityQueue.add(s);
            }
        }
    }

     //Comparator anonymous class implementation
    public static Comparator<Shipment> scheduleComparator = new Comparator<Shipment>(){
    
        @Override
        public int compare(Shipment s1, Shipment s2) {
            int s1P = s1.getPriority();
            int s2P = s2.getPriority();
            
            Date d1 = s1.getETA();
            Date d2 = s2.getETA();
            
            //convert all dates to milliseconds
            long current = System.currentTimeMillis( );
            long d1Time = d1.getTime();
            long d2Time = d2.getTime();
            
            //86,400,000 milliseconds in a day. Too many for long
            int d1Current = (int)(d1Time - current)/1000;
            int d2Current = (int)(d2Time - current)/1000;
            
            //uses priority values to weigh the times.
            int ETAvalue = s1P * d1Current - s2P * d2Current;
            return ETAvalue;
        }
    };
    
//    public void populateScheduleTable(){
//        
//         while (true) {
//            Shipment shpmt = schedulePriorityQueue.poll();
//            if (shpmt == null) break;
//            System.out.println("Processing Shipment with Priority " + shpmt.getPriority());
//        }
//    ArrayList<Shipment> shipments = new ArrayList<>();
//        for (Shipment s : dbConn.getShipments()) {
//            if (s.getOriginatorID() == user.getEmployeeID()) {
//                shipments.add(s);
//            }
//        }
//        ObservableList<Shipment> shipmentList
//                = FXCollections.observableArrayList(shipments);
//        schdTable = shipmentWindow.SCHEDULE_TABLE;
//        schdTable.setItems(shipmentList);
//        
//        TableColumn<Shipment, String> riginatorCol = new TableColumn<>("Originator");
//        riginatorCol.setCellValueFactory(new PropertyValueFactory<>("originatorID"));
//        TableColumn<Shipment, String> originCol = new TableColumn<>("Origin");
//        originCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
//        TableColumn<Shipment, String> destCol = new TableColumn<>("Destination");
//        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
//        TableColumn<Shipment, String> priorityCol = new TableColumn<>("Priority");
//        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
//        
//        schdTable.getColumns().setAll(riginatorCol, originCol, destCol, priorityCol);
//    }
}
