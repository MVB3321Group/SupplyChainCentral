/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    SchedulingController
*/

package controllers;

import databaseconnection.DatabaseConnection;
import java.sql.Timestamp;
import tableobjects.*;
import windows.*;
import tools.DialogBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.*;
import javafx.scene.input.MouseButton;

/**
 *
 * @author Benjamin
 */

public class SchedulingController {
    public ShipmentWindow shipmentWindow;
    TableView<Shipment> shipTable;
    TableView<Shipment> schdTable;
    Tooltip noProducts = new Tooltip("No products added to shipment");
    Tooltip removeProduct = new Tooltip("Right-click to remove product");
    ArrayList<ProductShipped> productsShippedList = new ArrayList<>();
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

        shipmentWindow.ADD_PRODUCT_BUTTON.setOnAction(e -> {
            try {
                String productName = shipmentWindow.PROD_DROPDOWN.getValue();
                int quantity = Integer.valueOf(shipmentWindow.QUANTITY_TF.getText());
                boolean duplicate = false;
                
                // Below code provides corrective action for duplicate products
                for (ProductShipped existing : productsShippedList) {
                    if (productName.equals(existing.getProductName())) {
                        duplicate = true;
                        existing.setQuantity(existing.getQuantity() + quantity);
                        shipmentWindow.PRODUCTS_TABLE.getItems().clear(); // To "refresh" 
                        shipmentWindow.PRODUCTS_TABLE.getItems().addAll(productsShippedList); // the table
                        break;
                    }
                }

                if (!duplicate) {
                    int productID = dbConn.getProductIDByName(productName);
                    ProductShipped newProduct = new ProductShipped(productID, quantity);
                    newProduct.setProductName(productName);
                    productsShippedList.add(newProduct);
                    shipmentWindow.PRODUCTS_TABLE.getItems().add(newProduct);
                }
            } catch (Exception ex) {}
        });
        
        shipmentWindow.CREATE_SHIPMENT_BUTTON.setOnAction(e -> {
            try {
                if (productsShippedList.isEmpty())
                    promptAddProduct();
                else {
                    createShipment();
                    shipmentWindow.DESTINATIONS_CHART.getData().clear();
                    populateShipmentsTable();
                    populateShipmentChart();
                    productsShippedList.clear();
                    shipmentWindow.PRODUCTS_TABLE.getItems().clear();
                }
            } catch (Exception ex) {}
        });
        
        shipmentWindow.PRODUCTS_TABLE.setOnMouseEntered(e -> {
            if (productsShippedList.isEmpty())
                shipmentWindow.PRODUCTS_TABLE.setTooltip(noProducts);
            else
                shipmentWindow.PRODUCTS_TABLE.setTooltip(removeProduct);
        });

        shipmentWindow.PRODUCTS_TABLE.setOnMouseClicked(e -> {
            if (!productsShippedList.isEmpty()) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    int selectedIndex = shipmentWindow.PRODUCTS_TABLE.getSelectionModel().getSelectedIndex();

                    productsShippedList.remove(selectedIndex);
                    shipmentWindow.PRODUCTS_TABLE.getItems().remove(selectedIndex);
                }
            }
        });
        
        shipmentWindow.SCHEDULE_SHIPMENTS_BUTTON.setOnAction(e -> {
            try {
                if (productsShippedList.isEmpty())
                    noShipmentsCreated();
                else {
                    scheduleShipments();
                    //getScheduledShipments();
                }
            } catch (Exception ex) {}
        });
        
//        shipmentWindow.setOnCloseRequest(e -> {
//            shipmentWindow.show();
//            promptSaveShipment();
//        });
    }
    
    public void setUser(User user) {
        this.user = user;
        populateShipmentsTable();
    }
    
    private void createShipment() {
        int originatorID = user.getEmployeeID();
        String orig = shipmentWindow.ORIG_DROPDOWN.getValue();
        String dest = shipmentWindow.DEST_DROPDOWN.getValue();
        int prty = Integer.valueOf(shipmentWindow.PRTY_DROPDOWN.getValue());
        
        Shipment shpmt = new Shipment(originatorID, orig, dest, prty);
        dbConn.insertShipment(shpmt, productsShippedList); // switch back to insertShipment
    }
    
    public void populateProducts() { 
        ArrayList<String> productList = new ArrayList<>();
        
        for (Product p : dbConn.getProducts())
            productList.add(p.getPName());
  
        ObservableList<String> prodDropdownList
                = FXCollections.observableArrayList(productList);
        shipmentWindow.PROD_DROPDOWN.getItems().addAll(prodDropdownList);
        shipmentWindow.PROD_DROPDOWN.setOnAction(e -> {
                prodDropdownList.indexOf(shipmentWindow.PROD_DROPDOWN.getValue());
        });
    }
    
    public void populateOrigins() {
        ArrayList<String> origList = new ArrayList<>();
        
       for (Location l : dbConn.getLocations())
            origList.add(l.getLocationCode());
  
        ObservableList<String> origDropdownList
                = FXCollections.observableArrayList(origList);
        shipmentWindow.ORIG_DROPDOWN.getItems().addAll(origDropdownList);
        shipmentWindow.ORIG_DROPDOWN.setOnAction(e -> {
            origDropdownList.indexOf(shipmentWindow.ORIG_DROPDOWN.getValue());
        });
    }
    
    public void populateDestinations() { 
        ArrayList<String> destList = new ArrayList<>();
        
        for (Location l : dbConn.getLocations())
            destList.add(l.getLocationCode());
  
        ObservableList<String> destDropdownList
                = FXCollections.observableArrayList(destList);
        shipmentWindow.DEST_DROPDOWN.getItems().addAll(destDropdownList);
        shipmentWindow.DEST_DROPDOWN.setOnAction(e -> {
                destDropdownList.indexOf(shipmentWindow.DEST_DROPDOWN.getValue());
        });
    }
    
    private void promptSaveShipment() {
        DialogBox dialog = new DialogBox("Save shipment info?", "Save?", "Yes", "No", 300, 100);
        dialog.show();
        dialog.label.setId("generic");
        dialog.btn.setOnAction(e -> dialog.close());
        dialog.btn2.setOnAction(e -> {dialog.close(); shipmentWindow.close();});
    }
    
    private void promptAddProduct() {
        DialogBox dialog = new DialogBox("Please add a product to the shipment.",
                                         "SCC", "Close", 300, 100);
        dialog.show();
        dialog.label.setId("generic");
        dialog.btn.setDefaultButton(true);
        dialog.btn.setOnAction(e -> dialog.close());
    }
    
    private void noShipmentsCreated() {
        DialogBox dialog = new DialogBox("Please create a shipment for the shipping"
                                       + "queue.", "SCC", "Close", 300, 100);
        dialog.show();
        dialog.label.setId("generic");
        dialog.btn.setDefaultButton(true);
        dialog.btn.setOnAction(e -> dialog.close());
    }
    
    private void populateShipmentsTable() {
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
    
    private void populateShipmentChart() {
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

    private void scheduleShipments() {
        // get an arraylist of the currently pending shipments.
        ArrayList<Shipment> pendShipments = dbConn.getPendingShipments();
        // create a priorityqueue. This PQ will be accessed to see 
        // what shipment is to be sent out next. By this I mean a 
        // schedID and startTime will be given to the shipment. Remember that you must 
        // sort the queue to get the proper queue values.
        Queue<Shipment> schedulePriorityQueue = new PriorityQueue<>(20, scheduleComparator);

        for (Shipment s : pendShipments) {
            schedulePriorityQueue.add(s);           
        }
        
        // get an arraylist of the current shipments.
        ArrayList<Shipment> shipments = dbConn.getShipments();
        //get the biggest current ScheduleID 
        int bigID = 1000;
        for(Shipment s : shipments) {
            if(bigID < s.getScheduleID()){
                bigID = s.getScheduleID();
            }           
        }
        //now we create scheduleIDs
        while(true){
            bigID++;
            Shipment shpt = schedulePriorityQueue.poll();
            if(shpt == null) break;
            shpt.setScheduleID(bigID);
        }
        
    }

     //Comparator anonymous class implementation
    public static Comparator<Shipment> scheduleComparator = new Comparator<Shipment>(){
    
        @Override
        public int compare(Shipment s1, Shipment s2) {
            int s1P = s1.getPriority();
            int s2P = s2.getPriority();
            
            long current = System.currentTimeMillis( );
            Date d1 = new Date();
             //Timestamp d1 = new (114,11,25,0,0,0);
            if(s1.getETA()!= null){
                d1 = s1.getETA();
            }
            Date d2 = new Date();
            if(s1.getETA()!= null){
                d2 = s2.getETA();
            }
            //convert all dates to milliseconds
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
