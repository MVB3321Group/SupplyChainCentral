/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Controller
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
    private final int MAX_LOGIN_ATTEMPTS = 5;//arbitary value for now
    DatabaseConnection dbConn;
    User user;
    
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
    
    public boolean isValidUser(int employeeID, String password) {
        try {
            User user = dbConn.getUser(1111, "opensesame");//"dummy" values
            if (user != null)
                return true;
            return false;
        }
        catch (SQLException e) {
            return false;
        }
    }
    
    public void validateUser(int employeeID, String password) {
        int numAttempts = 0;
        while (! isValidUser(employeeID, password)) {
            if (numAttempts >= MAX_LOGIN_ATTEMPTS)
            {
                //TODO: display warning message
                exit();
                //break; may be unnecessary
            }
            //TODO: display warning message and re-prompt for password
            numAttempts++;
        }
        try {
            user = dbConn.getUser(employeeID, password);
        }
        catch (SQLException e) {
            //TODO: display some warning message
        }
    }
    
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
        //controller.validateUser(employeeID, password);
        //TODO: Show main page
        controller.exit();
    }
}