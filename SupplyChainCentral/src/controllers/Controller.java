/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Controller
*/

package controllers;

import databaseconnection.DatabaseConnection;
import java.sql.SQLException;

/**
 *
 * @author Benjamin
 */
public class Controller {
    //Will get a user object based on input from login window.
    DatabaseConnection dbConn;
    
    public Controller() {
        //in order to get the connection, you need the role.
        //in order to get the role, you need the user
        //in order to get the user, you need the connection
        //therefore, the role implementation will not work as written
        try {
            dbConn = new DatabaseConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}