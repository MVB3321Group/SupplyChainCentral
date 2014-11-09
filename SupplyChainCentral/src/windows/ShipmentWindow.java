/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Shipment Window
 */

package windows;

import controllers.*;
import tableobjects.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ShipmentWindow extends Stage {

    public static final TextField QUANTITY_TF = new TextField();
    public static final TextField PRIORITY_TF = new TextField();
    public static final Button CREATE_SHIPMENT_BUTTON = new Button("Create Shipment");
    public static final Button SCHEDULE_SHIPMENTS_BUTTON = new Button("Schedule Shipments");
        
    public static final ComboBox<String> PROD_DROPDOWN = new ComboBox<>();
    public static final ComboBox<String> ORIG_DROPDOWN = new ComboBox<>();
    public static final ComboBox<String> DEST_DROPDOWN = new ComboBox<>();
    
    public static final TableView<Shipment> SCHEDULE_TABLE = new TableView<>();
    public static final TableView<Shipment> SHIPMENTS_TABLE = new TableView<>();
    public static final NumberAxis Y_AXIS = new NumberAxis();
    public static final CategoryAxis X_AXIS = new CategoryAxis();
    public static final BarChart DESTINATIONS_CHART = new BarChart(X_AXIS, Y_AXIS);
    public static final NumberAxis Y_AXIS2 = new NumberAxis();
    public static final CategoryAxis X_AXIS2 = new CategoryAxis();
    public static final BarChart ORIGINS_CHART = new BarChart(X_AXIS2, Y_AXIS2);

    public static Stage shipmentWindow = new Stage();
    
    public static AnchorPane aPane = new AnchorPane();
    public static GridPane gPane = new GridPane();
            
    public static Label success = new Label("Shipment added succesfully. ");  

    public ShipmentWindow() {
        
        gPane.add(new Label("Product: "), 0, 0);
        gPane.add(PROD_DROPDOWN, 1, 0);
        gPane.add(new Label("Quantity: "), 0, 1);
        gPane.add(QUANTITY_TF, 1, 1);
        gPane.add(new Label("Priority: "), 0, 2);
        gPane.add(PRIORITY_TF, 1, 2);
        gPane.add(new Label("Origin: "), 0, 3);
        gPane.add(ORIG_DROPDOWN, 1, 3);
        gPane.add(new Label("Destination: "), 0, 4);
        gPane.add(DEST_DROPDOWN, 1, 4);
        gPane.add(CREATE_SHIPMENT_BUTTON, 1, 5);
        gPane.add(SCHEDULE_SHIPMENTS_BUTTON, 0, 5);
        gPane.add(SCHEDULE_TABLE, 1, 7);
        gPane.add(SHIPMENTS_TABLE, 1, 7);
        gPane.add(DESTINATIONS_CHART, 2, 7);
        gPane.setHgap(10);
        gPane.setVgap(10);

        PROD_DROPDOWN.setPrefWidth(150);
        ORIG_DROPDOWN.setPrefWidth(150);
        DEST_DROPDOWN.setPrefWidth(150);
        PROD_DROPDOWN.setPromptText("Select a product.");
        ORIG_DROPDOWN.setPromptText("Select an origin.");
        DEST_DROPDOWN.setPromptText("Select a destination.");
        
        SchedulingController.populateProducts();
        SchedulingController.populateOrigins();
        SchedulingController.populateDestinations();
//        SchedulingController.populateShipmentsTable();
       // SchedulingController.populateShipmentChart();

        QUANTITY_TF.setAlignment(Pos.BOTTOM_RIGHT);
        QUANTITY_TF.setPromptText("Select a quantity.");
        QUANTITY_TF.setAlignment(Pos.CENTER);
        PRIORITY_TF.setAlignment(Pos.BOTTOM_RIGHT);
        PRIORITY_TF.setPromptText("Select a priority.");
        PRIORITY_TF.setTooltip(new Tooltip("Must be an integer between 1 and 5"));
        PRIORITY_TF.setAlignment(Pos.CENTER);
        
        CREATE_SHIPMENT_BUTTON.setOnAction(e -> {
            SchedulingController.createShipment();
            DESTINATIONS_CHART.getData().clear();
            //SchedulingController.populateShipmentChart();
            SHIPMENTS_TABLE.getItems().clear();
            SHIPMENTS_TABLE.getColumns().clear();
            //SchedulingController.populateShipmentsTable();
        });
        
        SCHEDULE_SHIPMENTS_BUTTON.setOnAction(e -> {
            SchedulingController.doScheduleShipments();
            SCHEDULE_TABLE.getItems().clear();
            SCHEDULE_TABLE.getColumns().clear();
            SchedulingController.populateScheduleTable();          
        });
        
        GridPane.setHalignment(CREATE_SHIPMENT_BUTTON, HPos.RIGHT);
        aPane.getChildren().add(gPane);
        AnchorPane.setRightAnchor(gPane, 10.0);
        
        shipmentWindow.setIconified(true);
    }
}
