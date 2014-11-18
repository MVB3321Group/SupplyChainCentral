/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Controller
    Contains main method of the supply chain central application
*/

package controllers;

import databaseconnection.*;
import java.sql.SQLException;
import tableobjects.*;
import windows.*;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tools.DialogBox;

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
    
    private boolean isValidUser(String employeeID, String password) {
        User vUser = null;
        int employeeIDInt;
        
        try {
            employeeIDInt = Integer.valueOf(employeeID);
        } catch (NumberFormatException ex) {
            return false;
        }
        
        if (dbConn.getUser(employeeIDInt, password) != null)
            vUser = dbConn.getUser(employeeIDInt, password);
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
                if (isValidUser(loginWindow.employeeIDField.getText(),
                    loginWindow.pwField.getText())) {
                    
                    loginAttempts = 0;
                    user = dbConn.getUser(Integer.parseInt(loginWindow.employeeIDField.getText()),
                            loginWindow.pwField.getText());
                    sController.setUser(user);
                    try {
                        dbConn.switchUser(user.getRoleID());
                    }
                    catch (SQLException sqlE) {
                        loginWindow.close();
                        showFailedConnection();
                    }
                    loginWindow.close();
                    mainWindow.welcomeLabel.setText("Welcome, " +
                            user.getfName() + " " + user.getlName());
                    sController.shipmentWindow.welcomeLabel.setText("Welcome, " +
                            user.getfName() + " " + user.getlName());
                    mainWindow.show();
                } else {
                    loginAttempts++;
                    
                    if (loginAttempts == MAX_LOGIN_ATTEMPTS) {
                        showExceededAttempts();
                        loginWindow.close();
                    }
                    
                    loginWindow.lblInvalid.setVisible(true);
                    loginWindow.lblAttempts.setVisible(true);
                    loginWindow.lblAttempts.setTextFill(Color.RED);
                    loginWindow.employeeIDField.clear();
                    loginWindow.pwField.clear();
                    loginWindow.employeeIDField.requestFocus();
                    
                    loginWindow.lblAttempts.setText((MAX_LOGIN_ATTEMPTS - loginAttempts) +
                                                    " attempt(s) remaining");
                }
            });

            /* The following two blocks make "ENTER" a valid login key for both
               the username field and the password field */
            loginWindow.pwField.setOnKeyPressed(e -> {
                    if (e.getCode().equals(KeyCode.ENTER))
                        loginWindow.btnLogin.fire();
                }
            );
            
            loginWindow.employeeIDField.setOnKeyPressed(e -> {
                    if (e.getCode().equals(KeyCode.ENTER))
                        loginWindow.btnLogin.fire();
                }
            );
            
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
            
            mainWindow.buttons[0].setOnAction(e -> {
                mainWindow.toolbar.FILE_DROPDOWN.setValue("File");
                mainWindow.close();
                sController.shipmentWindow.show();
            });
        }
        
        catch (SQLException sqlE) {
            showFailedConnection();
        }
    }

    private void showFailedConnection() {
        DialogBox dialog = new DialogBox("Unable to connect to database."
                + "\nPlease contact system administrator.");
        dialog.show();
        dialog.btnOK.setOnAction(e -> dialog.close());
        
        // Covers alternate case of pressing "ENTER"
        dialog.btnOK.setOnKeyPressed(e -> {
                if (e.getCode().equals(KeyCode.ENTER))
                    dialog.close();
            }
        );
    }
    
    private void showExceededAttempts() {
        DialogBox dialog = new DialogBox("Maximum login attempts reached."
                                        + "\nYou are denied system access.");
        dialog.show();
        dialog.btnOK.setOnAction(e -> dialog.close());
        
        // Covers alternate case of pressing "ENTER"
        dialog.btnOK.setOnKeyPressed(e -> {
                if (e.getCode().equals(KeyCode.ENTER))
                    dialog.close();
            }
        );
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}