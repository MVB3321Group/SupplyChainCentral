package windows;

import controllers.Controller;
import java.sql.*;
import java.util.Properties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow extends Stage {
    public Button btnLogin = new Button("Log in");
    public Button btnHelp = new Button("Help");
    public TextField employeeIDField = new TextField();
    public PasswordField pwBox = new PasswordField();
    public TextField outputField = new TextField();
    
    public LoginWindow() {

        setTitle("Welcome to SCM");

        show();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Please log in.");
        scenetitle.setId("log-in-text");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Employee ID:");
        grid.add(userName, 0, 1);

        
        grid.add(employeeIDField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        
        grid.add(pwBox, 1, 2);
        
        Label out = new Label("Output:");
        grid.add(out, 0, 3);
        
        
        grid.add(outputField, 1, 3);
        
        HBox hbBtn = new HBox(10); //spacing is 10.
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        hbBtn.getChildren().addAll(btnLogin, btnHelp);
        grid.add(hbBtn, 1, 4);

        Scene scene1 = new Scene(grid, 800, 600);
        scene1.getStylesheets().add
                (LoginWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene1);
    }
}
    





