/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Dialog Box
 */
package tools;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        VBox vb = new VBox();
        vb.getChildren().addAll(label, btnOk);
        Scene scene = new Scene(vb, 300, 200);
        setScene(scene);
    }
    
    public void setMessage(String message) {
        this.message = message;
        label.setText(message);
    }
}