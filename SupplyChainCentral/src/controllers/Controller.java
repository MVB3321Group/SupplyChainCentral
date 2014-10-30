/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Controller
    Contains main method of the supply chain central application
*/

package controllers;

import databaseconnection.DatabaseConnection;
import java.util.Scanner;
import tableobjects.User;

/**
 *
 * @author Benjamin
 */
public class Controller {
    //Will get a user object based on input from login window.
    private final int MAX_LOGIN_ATTEMPTS = 5;
    private DatabaseConnection dbConn;
    User user;//user for this session
    
    public Controller() {
        //in order to get the connection, you need the role.
        //in order to get the role, you need the user
        //in order to get the user, you need the connection
        //therefore, the role implementation will not work as written
        dbConn = new DatabaseConnection(0);
    }
    
    private boolean isValidUser(int employeeID, String password) {
        User vUser = dbConn.getUser(employeeID, password);
        return (vUser != null);
    }
    
    //To be called on any exit event
    public void exit() {
        dbConn.close();
    }
    
    public static void main(String [] args) {
        Controller controller = new Controller();
        //TODO: Show login page
        /*int numAttempts = 0;
        while (! isValidUser(loginPage.txtUsername.Text, loginPage.txtPassword.Text)) {
            if (numAttempts >= MAX_LOGIN_ATTEMPTS) {
                loginPage.showWarning();
                break;
            }
            loginPage.txtUsername.Text = "";
            loginPage.txtPassword.Text = "";
            numAttempts++;
        }*/
        //Begin simple test code
        int username = -1;
        String password = "";
        while (username != 0) {
            System.out.print("Enter username & password or 0 to quit: ");
            Scanner scan = new Scanner(System.in);
            username = scan.nextInt();
            password = scan.next();
            if (controller.isValidUser(username, password)) {
                controller.user = controller.dbConn.getUser(username, password);
                break;
            }
        }
        System.out.println("Welcome " + controller.user.getfName() + " "
                            + controller.user.getlName());
        //End simple test code
        //controller.validateUser(employeeID, password);
        //TODO: Show main page
        
        controller.exit();
    }
}