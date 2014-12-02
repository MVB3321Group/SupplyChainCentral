/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Inventory Window
 */

package windows;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tableobjects.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InventoryWindow extends Stage {

    public Button VIEW_INVENTORY_BUTTON = new Button("View Inventory");
    public ComboBox<String> LOCATION_DROPDOWN = new ComboBox<>();
    public TableView<Inventory> INVENTORY_TABLE = new TableView<>();

    public Stage inventoryWindow = new Stage();
    public Label welcomeLabel = new Label();

    public BorderPane bPane = new BorderPane();
    public GridPane gPane = new GridPane();
    public GridPane masterPane = new GridPane();
    public HBox headerPane = new HBox();

    public InventoryWindow() {
        headerPane.getChildren().add(welcomeLabel);
        welcomeLabel.setPadding(new Insets(5, 20, 5, 5));
        headerPane.setAlignment(Pos.TOP_RIGHT);
        bPane.setTop(headerPane);

        gPane.add(new Label("Location"), 0, 0);
        gPane.add(LOCATION_DROPDOWN, 1, 0);
        gPane.setAlignment(Pos.CENTER);
        gPane.setHgap(10);
        gPane.setVgap(10);

        LOCATION_DROPDOWN.setPrefWidth(150);
        LOCATION_DROPDOWN.setPromptText("Select a location.");

        masterPane.add(gPane, 0, 0);
        masterPane.add(INVENTORY_TABLE, 0, 1);
        GridPane.setValignment(INVENTORY_TABLE, VPos.CENTER);
        
        masterPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(20, 20, 20, 0));
        bPane.setCenter(masterPane);
        
        initializeInventoryTable();
        
        Scene scene = new Scene(bPane, 700, 450);
        setResizable(false);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setTitle("View Inventory");
    }
    
    private void initializeInventoryTable() {
        INVENTORY_TABLE.setMaxWidth(160);
        INVENTORY_TABLE.setMaxHeight(200);
        
        ObservableList<Inventory> inventory
                = FXCollections.observableArrayList(new ArrayList<Inventory>());
        INVENTORY_TABLE.setItems(inventory);
        
        TableColumn<Inventory, String> productCol
                = new TableColumn("Product");
        productCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        TableColumn<Inventory, String> quantityCol
                = new TableColumn("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        INVENTORY_TABLE.getColumns().setAll(productCol, quantityCol);
    }
}