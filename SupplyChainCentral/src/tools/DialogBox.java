/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Dialog Box
 */
package tools;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
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
    public Button btn;
    
    public DialogBox(String message, String title, String buttonText, double width, double height) {
        label = new Label();
        btn = new Button();
        btn.setText(buttonText);
        btn.setDefaultButton(true);
        setMessage(message);
        BorderPane bp = new BorderPane();
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, btn);
        bp.setBottom(vBox);
        bp.setPadding(new Insets(20));
        Scene scene = new Scene(bp, width, height);
        
        scene.getStylesheets().add
                (DialogBox.class.getResource("DialogBoxCSS.css").toExternalForm());
        
        setTitle(title);
        setScene(scene);
        setResizable(false);
    }
    
    public void setMessage(String message) {
        this.message = message;
        label.setText(message);
    }
}