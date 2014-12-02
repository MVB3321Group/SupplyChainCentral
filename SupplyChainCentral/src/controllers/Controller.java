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
    private SimulationController simController;
    private ReportingController rController;
    private User user; //user for this session
    private User systemAdmin;
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
    public void start(Stage primaryStage) throws SQLException {
        try {
            dbConn = new DatabaseConnection(0);
            tController = new TrackingController(dbConn);
            sController = new SchedulingController(dbConn);
            simController = new SimulationController(dbConn);
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
                    tController.setUser(user);
                    simController.setUser(user);

                    loginWindow.close();
                    mainWindow.show();
                    mainWindow.welcomeLabel.setText("Logged in as " +
                            user.getfName() + " " + user.getlName());
                    sController.shipmentWindow.welcomeLabel.setText("Logged in as " +
                            user.getfName() + " " + user.getlName());
                    tController.inventoryWindow.welcomeLabel.setText("Logged in as " +
                            user.getfName() + " " + user.getlName());
                    simController.simWindow.welcomeLabel.setText("Logged in as " +
                            user.getfName() + " " + user.getlName());
                } else {
                    loginAttempts++;
                    
                    if (loginAttempts == MAX_LOGIN_ATTEMPTS) {
                        showExceededAttempts();
                        loginWindow.close();
                    }

                    loginWindow.lblInvalid.setVisible(true);
                    loginWindow.lblAttempts.setVisible(true);
                    loginWindow.employeeIDField.clear();
                    loginWindow.pwField.clear();
                    loginWindow.employeeIDField.requestFocus();

                    loginWindow.lblAttempts.setText((MAX_LOGIN_ATTEMPTS - loginAttempts)
                            + " attempt(s) remaining");
                }
            });
            
            // System admin login (as Fred Smith, by default)
            loginWindow.btnLoginAdmin.setOnAction(e -> {
                systemAdmin = new User("Fred", "Smith", 4444, 3333, 4, "LA", "fsmith");
                sController.setUser(systemAdmin);
                tController.setUser(systemAdmin);
                simController.setUser(systemAdmin);

                loginWindow.close();
                mainWindow.show();
                mainWindow.welcomeLabel.setText("Logged in as System Administrator");
                mainWindow.welcomeLabel.setId("errormessage");
                sController.shipmentWindow.welcomeLabel.setText("Logged in as "
                                                              + "System Administrator");
                sController.shipmentWindow.welcomeLabel.setId("errormessage");
                tController.inventoryWindow.welcomeLabel.setText("Logged in as "
                                                              + "System Administrator");
                tController.inventoryWindow.welcomeLabel.setId("errormessage");
                simController.simWindow.welcomeLabel.setText("Logged in as "
                                                              + "System Administrator");
                simController.simWindow.welcomeLabel.setId("errormessage");
            });

            mainWindow.toolbar.FILE_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.FILE_DROPDOWN.getValue()) {
                    case "New Shipment":
                        sController.shipmentWindow.show();
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.FILE_DROPDOWN.setValue("File");
            });
            
            mainWindow.toolbar.VIEW_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.VIEW_DROPDOWN.getValue()) {
                    case "View Inventory":
                        tController.inventoryWindow.show();
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.VIEW_DROPDOWN.setValue("View");
            });
            
            mainWindow.toolbar.RUN_DROPDOWN.setOnAction(e -> {
                switch (mainWindow.toolbar.RUN_DROPDOWN.getValue()) {
                    case "Run Simulation":
                        simController.simWindow.show();
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
                        aboutSCC();
                        break;
                }
                
            // Simple fix to not allow selected option to change dropdown title
                mainWindow.toolbar.HELP_DROPDOWN.setValue("Help");
            });
            
            // Actions for navPane buttons
            mainWindow.buttons[0].setOnAction(e -> {
                sController.shipmentWindow.show();
            });
            mainWindow.buttons[3].setOnAction(e -> {
                if (!tController.inventoryWindow.isShowing())
                    tController.inventoryWindow.show();
            });
            mainWindow.buttons[4].setOnAction(e -> {
                if (!simController.simWindow.isShowing())
                    simController.simWindow.show();
            });
            mainWindow.buttons[5].setOnAction(e -> {
                if (!rController.reportingWindow.isShowing())
                    rController.reportingWindow.show();
            });
            mainWindow.buttons[8].setOnAction(e -> {
                aboutSCC();
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
    
    private void aboutSCC() {
        DialogBox dialog = new DialogBox("Supply Chain Central (SCC) is a supply chain company\n" +
                                         "headquartered in Savannah, Georgia.\n" +
                                         "\nApplications Software Developers:\n" + "\nBenjamin Chopson" +
                                         "\nMichael Bernard" + "\nVasily Kushakov",
                                         "About SCC", "Close", 400, 200);
        dialog.show();
        dialog.label.setTextFill(Color.WHITE);
        dialog.btn.setOnAction(e -> dialog.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
