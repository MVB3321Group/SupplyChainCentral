/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    ReportingController
 */
package controllers;

import databaseconnection.DatabaseConnection;
import java.util.ArrayList;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import tableobjects.Inventory;
import tableobjects.Location;
import tableobjects.Product;
import tableobjects.User;
import windows.ReportingWindow;

/**
 *
 * @author Benjamin
 */
public class ReportingController {
    public ReportingWindow reportingWindow;
    public DatabaseConnection dbConn;
    User user;
    
    public ReportingController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        reportingWindow = new ReportingWindow();
        
        reportingWindow.GENERATE_REPORT_BUTTON.setOnAction(e -> {
            try {
                switch (reportingWindow.CHART_TYPE_DROPDOWN.getValue()) {
                    case "Bar Chart":
                        reportingWindow.X_AXIS.setLabel(reportingWindow.FILTER_DROPDOWN.getValue());
                        reportingWindow.X_AXIS.setTickLabelFill(Color.WHITE);
                        reportingWindow.Y_AXIS.setLabel("Quantity");
                        reportingWindow.Y_AXIS.setTickLabelFill(Color.WHITE);
                        reportingWindow.Y_AXIS.setAutoRanging(false);
                        reportingWindow.Y_AXIS.setLowerBound(0);
                        reportingWindow.Y_AXIS.setTickUnit(1);
                        reportingWindow.Y_AXIS.setMinorTickVisible(false);

                        reportingWindow.chartPane.setVisible(true);
                        
                        if (reportingWindow.DATA_SET_DROPDOWN.getValue().equals("Inventory") && 
                            reportingWindow.FILTER_DROPDOWN.getValue().equals("Product")) {
                            
                        }
                }
            } catch (NullPointerException ex) {}
        });
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    private void filterProductsByLocation() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        ArrayList<Product> products = dbConn.getProducts();
        ArrayList<Location> locations = dbConn.getLocations();
//
//        // Event handler; Filter inventory by location from dropdown
//        inventoryWindow.LOCATION_DROPDOWN.setOnAction(e -> {
//            populateInventoryTable();
//            ArrayList<Inventory> inventoryFiltered = new ArrayList<>();
//            for (Inventory i : inventoryWindow.INVENTORY_TABLE.getItems()) {
//                if (i.getLocationCode().equals(inventoryWindow.LOCATION_DROPDOWN.getValue()))
//                    inventoryFiltered.add(i);
//            }
//            inventoryWindow.INVENTORY_TABLE.getItems().clear();
//            inventoryWindow.INVENTORY_TABLE.getItems().addAll(inventoryFiltered);
//        
//        reportingWindow.Y_AXIS.setUpperBound(Math.ceil(max * 1.1));
//        
//        for (int i = 0; i < count.length; i++)
//            series.getData().add(new XYChart.Data(locations.get(i).getCity(), count[i]));
        
        reportingWindow.barChart.getData().add(series);
    }
}