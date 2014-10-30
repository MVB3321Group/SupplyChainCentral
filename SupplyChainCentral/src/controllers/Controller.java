/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Controller
    Contains main method of the supply chain central application
*/

package controllers;

import databaseconnection.DatabaseConnection;
import java.sql.SQLException;
import tableobjects.User;

/**
 *
 * @author Benjamin
 */
public class Controller {
    //Will get a user object based on input from login window.
    private final int MAX_LOGIN_ATTEMPTS = 5;
    DatabaseConnection dbConn;
    User user;//user for this session
    
    public Controller() {
        //in order to get the connection, you need the role.
        //in order to get the role, you need the user
        //in order to get the user, you need the connection
        //therefore, the role implementation will not work as written
        try {
            dbConn = new DatabaseConnection(0);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isValidUser(int employeeID, String password) {
        try {
            User vUser = dbConn.getUser(1111, "opensesame");//"dummy" values
            if (vUser != null)
                return true;
            return false;
        }
        catch (SQLException e) {
            return false;
        }
    }
    
    //To be called on any exit event
    public void exit() {
        try {
            dbConn.close();
        }
        catch (SQLException e) {
            //TODO: Display message about connection not closing
        }
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
        //controller.validateUser(employeeID, password);
        //TODO: Show main page
        controller.exit();
    }
}