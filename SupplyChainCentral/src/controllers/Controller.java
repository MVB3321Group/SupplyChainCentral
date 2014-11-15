/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Controller
    Contains main method of the supply chain central application
*/

package controllers;

import databaseconnection.*;
import java.sql.SQLException;
import java.util.ArrayList;
import tableobjects.*;
import windows.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tools.DialogBox;
import tools.Toolbar;

/**
 *
 * @author Benjamin
 */
public class Controller extends Application {
    //Will get a user object based on input from login window.
    private final int MAX_LOGIN_ATTEMPTS = 5;
    private int loginAttempts;
    private TrackingController tController;
    private SchedulingController sController;
    private User user;//user for this session
    public LoginWindow loginWindow;
    public MainWindow mainWindow;
    private DatabaseConnection dbConn;
    
    private boolean isValidUser(int employeeID, String password) {
       User vUser = null;
        if (dbConn.getUser(employeeID, password) != null)
            vUser = dbConn.getUser(employeeID, password);
        return (vUser != null);
    }
    
    //To be called on any exit event
    public void exit() {
        dbConn.close();
    }


    // TODO
    public static void showSuccess(Stage window, Pane pane,
                                   double width, double height) {
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            dbConn = new DatabaseConnection(0);        
            tController = new TrackingController();
            sController = new SchedulingController(dbConn);
            loginAttempts = 0;
            mainWindow = new MainWindow();
            loginWindow = new LoginWindow();
            loginWindow.show();
            loginWindow.btnLogin.setOnAction(e -> {
                if (isValidUser(Integer.parseInt(loginWindow.employeeIDField.getText()),
                        loginWindow.pwBox.getText())) {
                    loginAttempts = 0;
                    user = dbConn.getUser(Integer.parseInt(loginWindow.employeeIDField.getText()),
                            loginWindow.pwBox.getText());
                    try {
                        dbConn.switchUser(user.getRoleID());
                    }
                    catch (SQLException sqlE) {
                        loginWindow.close();
                        showFailedConnection();
                    }
                    loginWindow.close();
                    mainWindow.show();
                } else {
                    loginAttempts++;
                    //TODO: indicate incorrect login
                    if (loginAttempts > MAX_LOGIN_ATTEMPTS) {
                        loginWindow.close();
                        //TODO: indicate too many incorrect logins
                    }
                }
            });

            loginWindow.pwBox.setOnKeyPressed(e -> {
                if (e.getCode().equals(KeyCode.ENTER)) {
                    loginWindow.btnLogin.fire();
                }
            });
            
            mainWindow.toolbar.FILE_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.FILE_DROPDOWN.getValue()) {
                    case "New Shipment":
                        /* Simple fix to not allow selected option to
                           change dropdown title */
                        mainWindow.toolbar.FILE_DROPDOWN.setValue("File");
                        mainWindow.close();
                        sController.shipmentWindow.show();
                        break;
                }
            });
        }
        catch (SQLException sqlE) {
            showFailedConnection();
        }
    }

    private void showFailedConnection() {
        DialogBox dialog = new DialogBox("Unable to connect to database."
                + "\nPlease contact the system administrator.");
        dialog.show();
        dialog.btnOk.setOnAction(f -> {
            try {
                dialog.close();
            }
            catch (Exception ex) { }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
