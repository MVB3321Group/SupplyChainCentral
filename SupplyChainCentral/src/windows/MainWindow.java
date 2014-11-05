/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Main Window
 */

package windows;

import controllers.*;
import databaseconnection.*;
import tableobjects.*;
import tools.Toolbar;
import windows.*;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    public static BorderPane bPane = new BorderPane();
    public static Stage mainWindow = new Stage();
    
    @Override
    public void start(Stage window) {
        Controller.openMainWindow();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}