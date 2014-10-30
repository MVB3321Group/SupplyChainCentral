/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Main Window
 */

package windows;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tools.Toolbar;

public class MainWindow extends Application {
    Pane pane = new Pane();
    
    @Override
    public void start(Stage mainWindow) {
        Toolbar toolbar = new Toolbar();
        toolbar.generateDropdowns(pane);

        // TODO: Auto-fit scene dimensions to user's max window dimensions
        Scene scene = new Scene(pane, 1344, 686);

        mainWindow.setTitle("Supply Chain Central");
        mainWindow.setScene(scene);
        mainWindow.show();
        mainWindow.setResizable(false);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}