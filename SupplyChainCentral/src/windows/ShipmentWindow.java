/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Shipment Window
 */

package windows;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tools.Toolbar;

public class ShipmentWindow extends Application {
    Pane pane = new Pane();
    TextArea tA = new TextArea();

    public ShipmentWindow() {

        tA.setFont(new Font("Serif", 14));
        tA.setPrefWidth(315);
        tA.setPrefHeight(420);
        pane.getChildren().add(tA);
        
        
    }

    @Override
    public void start(Stage mainWindow) {
        Toolbar toolbar = new Toolbar();
        toolbar.generateDropdowns(pane);

        // TODO: Auto-fit scene dimensions to user's max window dimensions
        Scene scene = new Scene(pane, 1344, 686);
        
        // Centers text area in window
        tA.setLayoutX((scene.getWidth() - tA.getPrefWidth()) / 2);
        tA.setLayoutY((scene.getHeight() - tA.getPrefHeight()) / 2);
        
        mainWindow.setTitle("Supply Chain Central");
        mainWindow.setScene(scene);
        mainWindow.show();
        mainWindow.setResizable(false);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}