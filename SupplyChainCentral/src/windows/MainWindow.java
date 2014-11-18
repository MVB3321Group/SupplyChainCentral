/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Main Window
 */

package windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import tools.Toolbar;

public class MainWindow extends Stage {
    public BorderPane mainPane = new BorderPane(); // TODO: BorderPane
    public HBox topPane = new HBox();
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

        Scene scene = new Scene(mainPane, 1342, 686);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setResizable(false);
        setTitle("Supply Chain Central");
    }
}