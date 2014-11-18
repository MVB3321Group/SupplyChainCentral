/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Main Window
 */

package windows;

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
    public BorderPane mainPane = new BorderPane(); // TODO: BorderPane
    public HBox topPane = new HBox();
    public GridPane navPane = new GridPane();
    public Button buttons[] = new Button[9];
    public Toolbar toolbar;
    public Label welcomeLabel;
    
    public MainWindow() {
        toolbar = new Toolbar();
        HBox.setHgrow(toolbar, Priority.ALWAYS);
        topPane.getChildren().add(toolbar);
        welcomeLabel = new Label();
        topPane.getChildren().add(welcomeLabel);
        topPane.setAlignment(Pos.CENTER);
        mainPane.setTop(topPane);

        // First, instantiate buttons...
        for (int i = 0; i < 9; i++) {
            buttons[i] = new Button();
            buttons[i].setPrefSize(100, 100);
        }
        
        // ... and then add them to navPane
        for (int i = 0, b = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                navPane.add(buttons[b], i, j);
                b++;
            }
        }

        navPane.setAlignment(Pos.CENTER); // Remember this alignment step!
        mainPane.setCenter(navPane);
                
        Scene scene = new Scene(mainPane, 1342, 686);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setResizable(false);
        setTitle("Supply Chain Central");
    }
}
