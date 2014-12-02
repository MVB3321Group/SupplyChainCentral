/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Dialog Box
 */
package tools;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Benjamin
 */
public class DialogBox extends Stage {
    private String message;
    public Label label;
    public Button btn;
    public Button btn2;
    
    public DialogBox(String message, String title, String buttonText, double width, double height) {
        label = new Label();
        btn = new Button();
        btn.setText(buttonText);
        btn.setPrefWidth(50);
        btn.setDefaultButton(true);
        setMessage(message);
        BorderPane bp = new BorderPane();
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(btn);
        bp.setCenter(label);
        bp.setBottom(hBox);
        bp.setPadding(new Insets(15));
        Scene scene = new Scene(bp, width, height);
        
        scene.getStylesheets().add
                (DialogBox.class.getResource("DialogBoxCSS.css").toExternalForm());
        
        setTitle(title);
        setScene(scene);
        setResizable(false); 
    }
    
    public DialogBox(String message, String title, String buttonText,
                     String button2Text, double width, double height) {
        label = new Label();
        label.setId("generic");
        btn = new Button();
        btn.setText(buttonText);
        btn.setPrefWidth(50);
        btn.setDefaultButton(true);
        btn2 = new Button();
        btn2.setText(button2Text);
        btn2.setPrefWidth(50);
        setMessage(message);
        BorderPane bp = new BorderPane();
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btn, btn2);
        bp.setCenter(label);
        bp.setBottom(hBox);
        bp.setPadding(new Insets(15));
        Scene scene = new Scene(bp, width, height);
        
        scene.getStylesheets().add
                (DialogBox.class.getResource("DialogBoxCSS.css").toExternalForm());
        
        setTitle(title);
        setScene(scene);
         
    }
    
    private void setMessage(String message) {
        this.message = message;
        label.setText(message);
    }
}
