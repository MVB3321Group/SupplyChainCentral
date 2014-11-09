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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    
    public Controller() {
        //in order to get the connection, you need the role.
        //in order to get the role, you need the user
        //in order to get the user, you need the connection
        //therefore, the role implementation will not work as written
//        dbConn = new DatabaseConnection(0);
        tController = new TrackingController();
        
        dbConn = new DatabaseConnection(0);
        loginAttempts = 0;
        loginWindow = new LoginWindow();
        
    }
    
    private boolean isValidUser(int employeeID, String password) {
       User vUser = null;
        if (dbConn.getUser(employeeID, password) != null)
            vUser = dbConn.getUser(employeeID, password);
        return (vUser != null);
    }
    
    //To be called on any exit event
    public void exit() {
        //dbConn.close();
    }


    // TODO
    public static void showSuccess(Stage window, Pane pane,
                                   double width, double height) {
        
    }
    
//    public static void main(String [] args) {
//        Controller controller = new Controller();
//        //TODO: Show login page
//        /*int numAttempts = 0;
//        while (! isValidUser(loginPage.txtUsername.Text, loginPage.txtPassword.Text)) {
//            if (numAttempts >= MAX_LOGIN_ATTEMPTS) {
//                loginPage.showWarning();
//                break;
//            }
//            loginPage.txtUsername.Text = "";
//            loginPage.txtPassword.Text = "";
//            numAttempts++;
//        }*/
//
//        //Begin simple test code
//        int username = -1;
//        String password = "";
////        while (username != 0) {
////            System.out.print("Enter username & password or 0 to quit: ");
////            Scanner scan = new Scanner(System.in);
////            username = scan.nextInt();
////            password = scan.next();
////            if (controller.isValidUser(username, password)) {
////                controller.user = controller.dbConn.getUser(username, password);
////                break;
////            }
////        }
//        System.out.println("Welcome " + controller.user.getfName() + " "
//                            + controller.user.getlName());
//        //End simple test code
//        //controller.validateUser(employeeID, password);
//        //TODO: Show main page
//        
//        controller.exit();
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loginWindow.btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (isValidUser(Integer.parseInt(loginWindow.employeeIDField.getText()),
                    loginWindow.pwBox.getText())){
                    loginAttempts = 0;
                    user = dbConn.getUser(Integer.parseInt(loginWindow.employeeIDField.getText()),
                            loginWindow.pwBox.getText());
                    dbConn.switchUser(user.getRoleID());
                    loginWindow.close();
                    mainWindow = new MainWindow();
                }
                else
                {
                    loginAttempts++;
                    //TODO: indicate incorrect login
                    if (loginAttempts > MAX_LOGIN_ATTEMPTS) {
                        loginWindow.close();
                        //TODO: indicate too many incorrect logins
                    }
                }
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}