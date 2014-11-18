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
    private User user; //user for this session
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

        if (dbConn.getUser(employeeIDInt, password) != null) {
            vUser = dbConn.getUser(employeeIDInt, password);
        }
        return (vUser != null);
    }

    //To be called on any exit event
    public void exit() {
        dbConn.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            dbConn = new DatabaseConnection(0);
            tController = new TrackingController();
            sController = new SchedulingController(dbConn);
            mainWindow = new MainWindow();
            loginWindow = new LoginWindow();
            loginWindow.show();
            loginAttempts = 0;
            loginWindow.btnLogin.setOnAction(e -> {
                if (isValidUser(loginWindow.employeeIDField.getText(),
                                loginWindow.pwField.getText())) {

                    loginAttempts = 0;
                    user = dbConn.getUser(Integer.parseInt(loginWindow.employeeIDField.getText()),
                            loginWindow.pwField.getText());
                    sController.setUser(user);
                    try {
                        dbConn.switchUser(user.getRoleID());
                    } catch (SQLException sqlE) {
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

                    loginWindow.lblAttempts.setText((MAX_LOGIN_ATTEMPTS - loginAttempts)
                            + " attempt(s) remaining");
                }
            });

            mainWindow.toolbar.FILE_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.FILE_DROPDOWN.getValue()) {
                    case "New Shipment":
                        mainWindow.close();
                        sController.shipmentWindow.show();
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.FILE_DROPDOWN.setValue("File");
            });
            
            mainWindow.toolbar.VIEW_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.VIEW_DROPDOWN.getValue()) {
                    case "":
                        
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.VIEW_DROPDOWN.setValue("View");
            });
            
            mainWindow.toolbar.RUN_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.RUN_DROPDOWN.getValue()) {
                    case "":
                        
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.RUN_DROPDOWN.setValue("Run");
            });
            
            mainWindow.toolbar.TRACK_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.TRACK_DROPDOWN.getValue()) {
                    case "":
                        
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.TRACK_DROPDOWN.setValue("Track");
            });
            
            mainWindow.toolbar.TOOLS_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.TOOLS_DROPDOWN.getValue()) {
                    case "":
                        
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.TOOLS_DROPDOWN.setValue("Tools");
            });
            
            mainWindow.toolbar.HELP_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.HELP_DROPDOWN.getValue()) {
                    case "About SCC":
                        aboutUs();
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.HELP_DROPDOWN.setValue("Help");
            });
            
            // Actions for navPane buttons
            mainWindow.buttons[0].setOnAction(e -> {
                mainWindow.close();
                sController.shipmentWindow.show();
            });
            mainWindow.buttons[8].setOnAction(e -> {
                aboutUs();
            });
            
        } catch (SQLException sqlE) {
            showFailedConnection();
        }
    }

    private void showFailedConnection() {
        DialogBox dialog = new DialogBox("Unable to connect to database."
                + "\nPlease contact system administrator.", "Error!", "Close", 300, 100);
        dialog.show();
        dialog.btn.setOnAction(e -> dialog.close());
    }

    private void showExceededAttempts() {
        DialogBox dialog = new DialogBox("Maximum login attempts reached."
                + "\nYou are denied system access.", "Error!", "Close", 300, 100);
        dialog.show();
        dialog.btn.setOnAction(e -> dialog.close());
    }
    
    private void aboutUs() {
        DialogBox dialog = new DialogBox("Supply Chain Central (SCC) is a supply chain company\n" +
                                         "headquartered in Savannah, Georgia.\n" +
                                         "\nApplication Software Developers:\n" + "\nBenjamin Chopson" +
                                         "\nMichael Bernard" + "\nVasily Kushakov",
                                         "About Us", "Close", 400, 200);
        dialog.show();
        dialog.label.setTextFill(Color.WHITE);
        dialog.btn.setOnAction(e -> {
            dialog.close();
            mainWindow.show();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
