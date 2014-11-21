/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Shipment Window
 */

package windows;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tableobjects.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ShipmentWindow extends Stage {

    public TextField QUANTITY_TF = new TextField();
    public Button CREATE_SHIPMENT_BUTTON = new Button("Create Shipment");
    public Button SCHEDULE_SHIPMENTS_BUTTON = new Button("Schedule Shipments");
    public Button addProductsButton = new Button("Add Product to Shipment");
        
    public ComboBox<String> PROD_DROPDOWN = new ComboBox<>();
    public ComboBox<String> PRTY_DROPDOWN = new ComboBox<>();
    public ComboBox<String> ORIG_DROPDOWN = new ComboBox<>();
    public ComboBox<String> DEST_DROPDOWN = new ComboBox<>();
    
    public final String[] PRTY_OPTIONS = {"1", "2", "3", "4", "5"};
    
    public TableView<Shipment> SCHEDULE_TABLE = new TableView<>();
    public TableView<Shipment> SHIPMENTS_TABLE = new TableView<>();
    public TableView<ProductShipped> productsTable = new TableView();
    public NumberAxis Y_AXIS = new NumberAxis();
    public CategoryAxis X_AXIS = new CategoryAxis();
    public BarChart DESTINATIONS_CHART = new BarChart(X_AXIS, Y_AXIS);
    public NumberAxis Y_AXIS2 = new NumberAxis();
    public CategoryAxis X_AXIS2 = new CategoryAxis();
    public BarChart ORIGINS_CHART = new BarChart(X_AXIS2, Y_AXIS2);

    public Stage shipmentWindow = new Stage();
    public Label welcomeLabel = new Label();
    
    public BorderPane bPane = new BorderPane();
    public GridPane gPane = new GridPane();
    public GridPane layoutPane = new GridPane();
    HBox headerPane = new HBox();
    
    public Label success = new Label("Shipment added succesfully. ");  

    public ShipmentWindow() {
        headerPane.getChildren().add(welcomeLabel);
        welcomeLabel.setPadding(new Insets(5, 20, 5, 5));
        headerPane.setAlignment(Pos.TOP_RIGHT);
        bPane.setTop(headerPane);

        gPane.add(new Label("Product: "), 0, 0);
        gPane.add(PROD_DROPDOWN, 1, 0);
        gPane.add(new Label("Quantity: "), 0, 1);
        gPane.add(QUANTITY_TF, 1, 1);
        addProductsButton.setAlignment(Pos.CENTER_LEFT);
        gPane.add(addProductsButton, 1, 2);
        gPane.add(new Label("Priority: "), 0, 3);
        gPane.add(PRTY_DROPDOWN, 1, 3);
        gPane.add(new Label("Origin: "), 0, 4);
        gPane.add(ORIG_DROPDOWN, 1, 4);
        gPane.add(new Label("Destination: "), 0, 5);
        gPane.add(DEST_DROPDOWN, 1, 5);
        gPane.add(CREATE_SHIPMENT_BUTTON, 1, 6);
        gPane.add(SCHEDULE_SHIPMENTS_BUTTON, 0, 6);
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
        QUANTITY_TF.setMaxWidth(150);

        // FLAG
        GridPane.setHalignment(CREATE_SHIPMENT_BUTTON, HPos.RIGHT);
        gPane.setAlignment(Pos.CENTER);
        layoutPane.add(gPane, 0, 0);
        SHIPMENTS_TABLE.setMaxHeight(350);
        layoutPane.add(SHIPMENTS_TABLE, 0, 1);
        layoutPane.add(DESTINATIONS_CHART, 1, 1);
        initializeProductsTable();
        layoutPane.add(productsTable, 1, 0);
        layoutPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(20, 20, 20, 0));
        bPane.setCenter(layoutPane);
        
        X_AXIS.setLabel("Destination");
        X_AXIS.setTickLabelFill(Color.WHITE);
        Y_AXIS.setLabel("Number of Shipments");
        Y_AXIS.setTickLabelFill(Color.WHITE);
        Y_AXIS.setAutoRanging(false);
        Y_AXIS.setLowerBound(0);
        Y_AXIS.setTickUnit(1);
        Y_AXIS.setMinorTickVisible(false);
        
        Scene scene = new Scene(bPane, 1006, 515);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setResizable(false);
        setTitle("New Shipment");
    }
    
    private void initializeProductsTable() {
        productsTable.setMaxWidth(160);
        productsTable.setMaxHeight(200);
        
        ObservableList<ProductShipped> productList
                = FXCollections.observableArrayList(new ArrayList<ProductShipped>());
        productsTable.setItems(productList);
        
        TableColumn<ProductShipped, String> productCol
                = new TableColumn("Product");
        productCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        TableColumn<ProductShipped, String> quantityCol
                = new TableColumn("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productsTable.getColumns().setAll(productCol, quantityCol);
        ProductShipped pc = new ProductShipped(1, 5);
    }
}
