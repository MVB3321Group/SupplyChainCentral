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
    public Button btnOk;
    
    public DialogBox(String message) {
        label = new Label();
        btnOk = new Button();
        btnOk.setText("Ok");
        setMessage(message);
        BorderPane bp = new BorderPane();
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(label, btnOk);
        bp.setCenter(vb);
        Scene scene = new Scene(bp, 300, 200);
        
        scene.getStylesheets().add
                (DialogBox.class.getResource("DialogBoxCSS.css").toExternalForm());
        
        setScene(scene);
        setTitle("Warning!");     
    }
    
    public void setMessage(String message) {
        this.message = message;
        label.setText(message);
    }
}
