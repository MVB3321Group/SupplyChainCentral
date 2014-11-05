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

public class LoginWindow extends Application {
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Welcome to SCM");

        primaryStage.show();

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

        TextField employeeIDField = new TextField();
        grid.add(employeeIDField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Label out = new Label("Output:");
        grid.add(out, 0, 3);
        
        TextField outputField = new TextField();
        grid.add(outputField, 1, 3);

        Button btn1 = new Button("Log in");
        Button btn2 = new Button("Help");
        HBox hbBtn = new HBox(10); //spacing is 10.
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        hbBtn.getChildren().addAll(btn1, btn2);
        grid.add(hbBtn, 1, 4);

        
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//                if(controller.isValidUser(Integer.parseInt(employeeIDField.getText()),pwBox.getText())){
//                    outputField.setText("Correct");
//                }              
            }
        });

        Scene scene1 = new Scene(grid, 800, 600);
        scene1.getStylesheets().add
                (LoginWindow.class.getResource("LoginCSS.css").toExternalForm());
        primaryStage.setScene(scene1);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
    





