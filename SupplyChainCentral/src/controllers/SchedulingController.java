/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    SchedulingController
*/

package controllers;

import tableobjects.*;
import windows.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;



/**
 *
 * @author Benjamin
 */

public class SchedulingController extends Application {
    public ShipmentWindow shipmentWindow;
    
    
    public void createShipment() {
        int originatorID = 2222;//dummy test code
        String orig = shipmentWindow.ORIG_DROPDOWN.getValue();
        String dest = shipmentWindow.DEST_DROPDOWN.getValue();
        int priority = Integer.valueOf(shipmentWindow.PRIORITY_TF.getText());
        
        Shipment shpmt = new Shipment(originatorID, orig, dest, priority);
        //MainWindow.dbConn.insertShipment(shpmt);
        
        // Confirm success
        shipmentWindow.gPane.add(shipmentWindow.success, 1, 6);
    }
    
    public void populateProducts() { 
        ArrayList<String> productList = new ArrayList<>();
        
        // "Converts" Location into String objects for later use
//        for (int i = 0; i < MainWindow.dbConn.getProducts().size(); i++)
//            productList.add(MainWindow.dbConn.getProducts().get(i).getPName());
  
        ObservableList<String> prodDropdownList
                = FXCollections.observableArrayList(productList);
        shipmentWindow.PROD_DROPDOWN.getItems().addAll(prodDropdownList);
        shipmentWindow.PROD_DROPDOWN.setOnAction(e -> {
                prodDropdownList.indexOf(shipmentWindow.PROD_DROPDOWN.getValue());
        });
    }
    
    public void populateOrigins() { 
        ArrayList<String> origList = new ArrayList<>();
        
        // "Converts" Location into String objects for later use
//        for (int i = 0; i < MainWindow.dbConn.getLocations().size(); i++)
//            origList.add(MainWindow.dbConn.getLocations().get(i).getLocationCode());
  
        ObservableList<String> origDropdownList
                = FXCollections.observableArrayList(origList);
        shipmentWindow.ORIG_DROPDOWN.getItems().addAll(origDropdownList);
        shipmentWindow.ORIG_DROPDOWN.setOnAction(e -> {
            origDropdownList.indexOf(shipmentWindow.ORIG_DROPDOWN.getValue());
        });
    }
    
    public void populateDestinations() { 
        ArrayList<String> destList = new ArrayList<>();
        
        // "Converts" Location into String objects for later use
//        for (int i = 0; i < MainWindow.dbConn.getLocations().size(); i++)
//            destList.add(MainWindow.dbConn.getLocations().get(i).getLocationCode());
  
        ObservableList<String> destDropdownList
                = FXCollections.observableArrayList(destList);
        shipmentWindow.DEST_DROPDOWN.getItems().addAll(destDropdownList);
        shipmentWindow.DEST_DROPDOWN.setOnAction(e -> {
                destDropdownList.indexOf(shipmentWindow.DEST_DROPDOWN.getValue());
        });
    }
    
//    public static void populateShipmentsTable() {
//        ObservableList<Shipment> shipmentList
//                = FXCollections.observableArrayList(MainWindow.dbConn.getShipments());
//        TableView<Shipment> shipTable = shipmentWindow.SHIPMENTS_TABLE;
//        shipTable.setItems(shipmentList);
//        
//        TableColumn<Shipment, String> riginatorCol = new TableColumn<>("Originator");
//        riginatorCol.setCellValueFactory(new PropertyValueFactory<Shipment,String>("originatorID"));
//        TableColumn<Shipment, String> originCol = new TableColumn<>("Origin");
//        originCol.setCellValueFactory(new PropertyValueFactory<Shipment,String>("origin"));
//        TableColumn<Shipment, String> destCol = new TableColumn<>("Destination");
//        destCol.setCellValueFactory(new PropertyValueFactory<Shipment,String>("destination"));
//        TableColumn<Shipment, String> priorityCol = new TableColumn<>("Priority");
//        priorityCol.setCellValueFactory(new PropertyValueFactory<Shipment,String>("priority"));
//        
//        shipTable.getColumns().setAll(riginatorCol, originCol, destCol, priorityCol);
//    }
    
//    public static void populateShipmentChart() {
//        shipmentWindow.X_AXIS.setLabel("Destination City");
//        shipmentWindow.Y_AXIS.setLabel("Number of Shipments");
//        XYChart.Series<String, Integer> series = new XYChart.Series<>();
//        ArrayList<Shipment> shipments = MainWindow.dbConn.getShipments();
//        ArrayList<Location> locations = MainWindow.dbConn.getLocations();
//        int[] counts = new int[locations.size()];
//        for (int h = 0; h < shipments.size(); h++) {
//            
//            for (int i = 0; i < locations.size(); i++) {
//                if (shipments.get(h).getDestination().equals(locations.get(i).getLocationCode())) {
//                    counts[i]++;
//                    break;
//                }
//            }
//        }
//        
//        for (int i = 0; i < counts.length; i++) {
//            series.getData().add(new XYChart.Data(locations.get(i).getCity(), counts[i]));
//        }
//        
//        shipmentWindow.DESTINATIONS_CHART.getData().add(series);
//    }

    @Override
    public void start(Stage start) throws Exception {
    //public static void doScheduleShipments() {
        // get an arraylist of the current shipments.
        //ArrayList<Shipment> shipments = MainWindow.dbConn.getShipments();
        // create a priorityqueue. This PQ will be accessed to see 
        // what shipment is to be sent out next. By this I mean a 
        // starttime will be given to the shipment.
        Queue<Shipment> schedulePriorityQueue = new PriorityQueue<>(5, priorityComparator);
        
//        for(int i = 0; i < shipments.size(); i++){
//            schedulePriorityQueue.add(shipments.get(i));
//        }
    }
    
    
     //Comparator anonymous class implementation
    public static Comparator<Shipment> priorityComparator = new Comparator<Shipment>(){
        @Override
        public int compare(Shipment s1, Shipment s2) {
            return (int) (s1.getPriority() - s2.getPriority());
        }
    };
    
    public static void getScheduledShipments (Queue<Shipment> schedulePriorityQueue){
         while(true){
            Shipment shpm = schedulePriorityQueue.poll();
            if(shpm == null) break;
            System.out.println("Processing Shipment with Priority="+shpm.getPriority());
        }
    }

    
    
    public static void doScheduleShipments() {
        // get an arraylist of the current shipments.
       //ArrayList<Shipment> shipments = MainWindow.dbConn.getShipments();
        // create a priorityqueue. This PQ will be accessed to see 
        // what shipment is to be sent out next. By this I mean a 
        // starttime will be given to the shipment.
        Queue<Shipment> schedulePriorityQueue = new PriorityQueue<>(5, priorityComparator);
        
//        for(int i = 0; i < shipments.size(); i++){
//            schedulePriorityQueue.add(shipments.get(i));
//        }
    }
    
    
     //Comparator anonymous class implementation
//    public Comparator<Shipment> priorityComparator = new Comparator<Shipment>(){
//        @Override
//        public int compare(Shipment s1, Shipment s2) {
//            return (int) (s1.getPriority() - s2.getPriority());
//        }
//    };
    
//    public static void getScheduledShipments (Queue<Shipment> schedulePriorityQueue){
//         while(true){
//            Shipment shpm = schedulePriorityQueue.poll();
//            if(shpm == null) break;
//            System.out.println("Processing Shipment with Priority="+shpm.getPriority());
//        }
//    }

    
    
    public void populateShipmentChart() {
        shipmentWindow.X_AXIS.setLabel("Destination City");
        shipmentWindow.Y_AXIS.setLabel("Number of Shipments");
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
//        ArrayList<Shipment> shipments = MainWindow.dbConn.getShipments();
//        ArrayList<Location> locations = MainWindow.dbConn.getLocations();
//        int[] counts = new int[locations.size()];
//        for (int h = 0; h < shipments.size(); h++) {
//            
//            for (int i = 0; i < locations.size(); i++) {
//                if (shipments.get(h).getDestination().equals(locations.get(i).getLocationCode())) {
//                    counts[i]++;
//                    break;
//                }
//            }
//        }
        
    }
}
