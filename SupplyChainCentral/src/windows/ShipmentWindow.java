/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Shipment Window
 */

package windows;

import tableobjects.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import tools.Toolbar;

public class ShipmentWindow extends Stage {

    public TextField QUANTITY_TF = new TextField();
    public Button CREATE_SHIPMENT_BUTTON = new Button("Create Shipment");
    public Button SCHEDULE_SHIPMENTS_BUTTON = new Button("Schedule Shipments");
        
    public ComboBox<String> PROD_DROPDOWN = new ComboBox<>();
    public ComboBox<String> PRTY_DROPDOWN = new ComboBox<>();
    public ComboBox<String> ORIG_DROPDOWN = new ComboBox<>();
    public ComboBox<String> DEST_DROPDOWN = new ComboBox<>();
    
    final String[] PRTY_OPTIONS = {"1", "2", "3", "4", "5"};
    
    public TableView<Shipment> SCHEDULE_TABLE = new TableView<>();
    public TableView<Shipment> SHIPMENTS_TABLE = new TableView<>();
    public NumberAxis Y_AXIS = new NumberAxis();
    public CategoryAxis X_AXIS = new CategoryAxis();
    public BarChart DESTINATIONS_CHART = new BarChart(X_AXIS, Y_AXIS);
    public NumberAxis Y_AXIS2 = new NumberAxis();
    public CategoryAxis X_AXIS2 = new CategoryAxis();
    public BarChart ORIGINS_CHART = new BarChart(X_AXIS2, Y_AXIS2);

    public Stage shipmentWindow = new Stage();
    
    public AnchorPane aPane = new AnchorPane();
    public GridPane gPane = new GridPane();
    public Toolbar toolbar;

    public Label success = new Label("Shipment added succesfully. ");  

    public ShipmentWindow() {
        toolbar = new Toolbar();
        toolbar.generateDropdowns(aPane);
        
        gPane.add(new Label("Product: "), 0, 0);
        gPane.add(PROD_DROPDOWN, 1, 0);
        gPane.add(new Label("Quantity: "), 0, 1);
        gPane.add(QUANTITY_TF, 1, 1);
        gPane.add(new Label("Priority: "), 0, 2);
        gPane.add(PRTY_DROPDOWN, 1, 2);
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
        
        PRTY_DROPDOWN.getItems().addAll(PRTY_OPTIONS);

        PROD_DROPDOWN.setPrefWidth(150);
        PRTY_DROPDOWN.setPrefWidth(150);
        ORIG_DROPDOWN.setPrefWidth(150);
        DEST_DROPDOWN.setPrefWidth(150);
        PROD_DROPDOWN.setPromptText("Select a product.");
        PRTY_DROPDOWN.setPromptText("Select a priority.");
        ORIG_DROPDOWN.setPromptText("Select an origin.");
        DEST_DROPDOWN.setPromptText("Select a destination.");
        
        QUANTITY_TF.setAlignment(Pos.BOTTOM_RIGHT);
        QUANTITY_TF.setPromptText("Select a quantity.");
        QUANTITY_TF.setAlignment(Pos.CENTER);

        GridPane.setHalignment(CREATE_SHIPMENT_BUTTON, HPos.RIGHT);
        aPane.getChildren().add(gPane);
        AnchorPane.setRightAnchor(gPane, 10.0);
        
        Scene scene = new Scene(aPane, 1342, 686);
        
        scene.getStylesheets().add
                (ShipmentWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setResizable(false);
    }
}