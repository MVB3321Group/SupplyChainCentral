/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Main Window
 */

package windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import tools.Toolbar;

public class MainWindow extends Stage {
    public BorderPane mainPane = new BorderPane();
    public HBox toolbarHBox = new HBox();
    public GridPane navPane = new GridPane();
    public Toolbar toolbar;
    public Button buttons[] = new Button[9];
    public Label loggedInLabel;
    
    public MainWindow() {
        toolbar = new Toolbar();
        HBox.setHgrow(toolbar, Priority.ALWAYS);
        toolbarHBox.getChildren().add(toolbar);
        loggedInLabel = new Label();
        loggedInLabel.setPadding(new Insets(5, 20, 5, 5));
        toolbarHBox.getChildren().add(loggedInLabel);
        toolbarHBox.setAlignment(Pos.CENTER);
        mainPane.setTop(toolbarHBox);

        // First, instantiate buttons for navPane...
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setPrefSize(100, 125);
        }

        // ... and then add them to navPane
        for (int i = 0, b = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++, b++) {
                navPane.add(buttons[b], i, j);
            }
        }
        
        // Set titles for buttons
        buttons[0].setText("New Shipment");
        buttons[1].setText("View Shipment");
        buttons[2].setText("Track Shipment");
        buttons[3].setText("View Inventory");
        buttons[4].setText("Run Simulation");
        buttons[5].setText("Reports");
        buttons[6].setText("User Options");
        buttons[7].setText("Help");
        buttons[8].setText("About SCC");

        navPane.setAlignment(Pos.CENTER); // Remember this alignment step!
        mainPane.setCenter(navPane);
                
        Scene scene = new Scene(mainPane, 1342, 686);
        setResizable(false);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setTitle("Supply Chain Central");
    }
}