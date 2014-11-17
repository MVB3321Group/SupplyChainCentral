/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Dialog Box
 */
package tools;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Benjamin
 */
public class DialogBox extends Stage {
    private String message;
    public Label label;
    public Button btnOK;
    
    public DialogBox(String message) {
        label = new Label();
        btnOK = new Button();
        btnOK.setText("OK");
        setMessage(message);
        BorderPane bp = new BorderPane();
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, btnOK);
        bp.setCenter(vBox);
        Scene scene = new Scene(bp, 300, 100);
        
        scene.getStylesheets().add
                (DialogBox.class.getResource("DialogBoxCSS.css").toExternalForm());
        
        setScene(scene);
        setTitle("Error!");     
        setResizable(false);
    }
    
    public void setMessage(String message) {
        this.message = message;
        label.setText(message);
    }
}