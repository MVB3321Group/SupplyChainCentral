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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ShipmentWindow {

    private final TextArea TA = new TextArea();
    private final TextField PRODUCT_SHIPPED_TF = new TextField();
    private final TextField QUANTITY_TF = new TextField();
    private final TextField PRIORITY_TF = new TextField();
    private final Button CREATE_SHIPMENT_BUTTON = new Button("Create Shipment");
        
    public static final ComboBox<String> PROD_DROPDOWN = new ComboBox<>();
    public static final ComboBox<String> DEST_DROPDOWN = new ComboBox<>();
    public static final TableView<Shipment> SHIPMENTS_TABLE = new TableView<>();
    public static final NumberAxis Y_AXIS = new NumberAxis();
    public static final CategoryAxis X_AXIS = new CategoryAxis();
    public static final BarChart DESTINATIONS_CHART = new BarChart(X_AXIS, Y_AXIS);
    public static final NumberAxis Y_AXIS2 = new NumberAxis();
    public static final CategoryAxis X_AXIS2 = new CategoryAxis();
    public static final BarChart ORIGINS_CHART = new BarChart(X_AXIS2, Y_AXIS2);

    public static Stage shipmentWindow = new Stage();
    
    public static AnchorPane aPane = new AnchorPane();
    
    GridPane gPane = new GridPane();
    
    public ShipmentWindow() {
        
        gPane.setHgap(10);
        gPane.setVgap(10);
        gPane.add(new Label("Product(s) to Ship:"), 0, 0);
        gPane.add(PROD_DROPDOWN, 1, 0);
        gPane.add(new Label("Quantity:"), 0, 1);
        gPane.add(QUANTITY_TF, 1, 1);
        gPane.add(new Label("Priority:"), 0, 2);
        gPane.add(PRIORITY_TF, 1, 2);
        gPane.add(new Label("Destination:"), 0, 3);
        gPane.add(DEST_DROPDOWN, 1, 3);
        gPane.add(CREATE_SHIPMENT_BUTTON, 1, 6);
        gPane.add(SHIPMENTS_TABLE, 1, 7);
        gPane.add(DESTINATIONS_CHART, 2, 7);
        
        PROD_DROPDOWN.setPrefWidth(150);
        DEST_DROPDOWN.setPrefWidth(150);
        
        PROD_DROPDOWN.setPromptText("Select product(s).");
        DEST_DROPDOWN.setPromptText("Select destination.");
        
        SchedulingController.populateProducts();
        SchedulingController.populateDestinations();
        SchedulingController.populateShipmentsTable();
        SchedulingController.populateShipmentChart();

        // Set properties for UI
        QUANTITY_TF.setAlignment(Pos.BOTTOM_RIGHT);
        QUANTITY_TF.setPromptText("Select shipment quantity.");
        PRIORITY_TF.setAlignment(Pos.BOTTOM_RIGHT);
        PRIORITY_TF.setPromptText("Select shipment priority.");
        
        GridPane.setHalignment(CREATE_SHIPMENT_BUTTON, HPos.RIGHT);
        aPane.getChildren().add(gPane);
        AnchorPane.setRightAnchor(gPane, 10.0);
        
        shipmentWindow.setIconified(true);
    }
}
