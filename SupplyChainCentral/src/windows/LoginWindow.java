/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Login Window
 */

package windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow extends Stage {
    public Button btnLogin = new Button("Log in");
    public Button btnHelp = new Button("Help");
    public Label lblInvalid = new Label("Invalid username or password");
    public Label lblAttempts = new Label();
    public TextField employeeIDField = new TextField();
    public PasswordField pwField = new PasswordField();
    public GridPane grid = new GridPane();
    
    public LoginWindow() {

        btnLogin.setDefaultButton(true);
        setTitle("Welcome to SCM!");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Please log in.");
        scenetitle.setId("log-in-text");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Employee ID:");
        grid.add(userName, 0, 1);

        employeeIDField.setId("errormessage");
        grid.add(employeeIDField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        grid.add(pwField, 1, 2);
        
        lblInvalid.setId("errormessage");
        grid.add(lblInvalid, 1, 3);
        
        grid.add(lblAttempts, 1, 4);
        lblInvalid.setVisible(false); // By default
        lblAttempts.setVisible(false); // By default

        HBox hbBtn = new HBox(10); //spacing is 10.
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        hbBtn.getChildren().addAll(btnLogin, btnHelp);
        grid.add(hbBtn, 1, 5);

        Scene scene1 = new Scene(grid, 400, 280);
        scene1.getStylesheets().add
                (LoginWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene1);
        setResizable(false);
    }
}