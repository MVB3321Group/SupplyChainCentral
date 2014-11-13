/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Controller
    Contains main method of the supply chain central application
*/

package controllers;

import databaseconnection.*;
import tableobjects.*;
import windows.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
        tController = new TrackingController();
        dbConn = new DatabaseConnection(0);
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
                dbConn.switchUser(user.getRoleID());
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
        mainWindow.toolbar.FILE_DROPDOWN.setOnAction(e -> {
            switch (mainWindow.toolbar.FILE_DROPDOWN.getValue()) {
                case "New Shipment":
                    mainWindow.close();
                    //sController.shipmentWindow.show();
                    break;
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}