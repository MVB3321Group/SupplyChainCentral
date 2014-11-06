/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Main Window
 */

package windows;

import controllers.*;
import databaseconnection.*;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    public static AnchorPane mainPane = new AnchorPane(); // TODO: BorderPane
    public static Stage mainWindow = new Stage();
    public static DatabaseConnection dbConn;
    
    @Override
    public void start(Stage window) {
        dbConn = new DatabaseConnection(0); // Establish database connection!
        Controller.openMainWindow();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}