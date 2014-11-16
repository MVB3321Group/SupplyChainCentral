/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Main Window
 */

package windows;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tools.Toolbar;

public class MainWindow extends Stage {
    public AnchorPane mainPane = new AnchorPane(); // TODO: BorderPane
    public Toolbar toolbar;
    
    public MainWindow() {
        toolbar = new Toolbar();
        toolbar.generateDropdowns(mainPane);

        Scene scene = new Scene(mainPane, 1342, 686);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setResizable(false);
        setTitle("Supply Chain Central");
    }
}