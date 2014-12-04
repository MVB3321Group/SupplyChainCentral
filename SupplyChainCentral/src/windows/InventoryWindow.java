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
    public Label loggedInLabel = new Label();

    public BorderPane bPane = new BorderPane();
    public GridPane gPane = new GridPane();
    public GridPane iPane = new GridPane();
    public HBox headerPane = new HBox();

    public InventoryWindow() {
        headerPane.getChildren().add(loggedInLabel);
        loggedInLabel.setPadding(new Insets(5, 20, 5, 5));
        headerPane.setAlignment(Pos.TOP_RIGHT);
        bPane.setTop(headerPane);

        gPane.add(new Label("Location"), 0, 0);
        gPane.add(LOCATION_DROPDOWN, 1, 0);
        gPane.setHgap(10);
        gPane.setVgap(10);

        LOCATION_DROPDOWN.setPrefWidth(150);
        LOCATION_DROPDOWN.setPromptText("Select a location.");

        gPane.setAlignment(Pos.CENTER);

        iPane.add(INVENTORY_TABLE, 0, 0);
        iPane.setAlignment(Pos.CENTER);
        iPane.setPadding(new Insets(0, 20, 30, 20));
        
        bPane.setCenter(gPane);
        bPane.setBottom(iPane);

        initializeInventoryTable();
        
        Scene scene = new Scene(bPane, 350, 350);
        setResizable(false);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setTitle("Inventory");
    }
    
    private void initializeInventoryTable() {
        INVENTORY_TABLE.setMaxWidth(160);
        INVENTORY_TABLE.setMaxHeight(200);
        
        ObservableList<Inventory> inventory
                = FXCollections.observableArrayList(new ArrayList<Inventory>());
        INVENTORY_TABLE.setItems(inventory);
        
        TableColumn<Inventory, String> productCol
                = new TableColumn("Product");
        productCol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        TableColumn<Inventory, String> quantityCol
                = new TableColumn("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        INVENTORY_TABLE.getColumns().setAll(productCol, quantityCol);
    }
}
