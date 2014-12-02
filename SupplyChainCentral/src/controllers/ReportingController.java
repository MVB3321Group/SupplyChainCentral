/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    ReportingController
 */
package controllers;

import databaseconnection.DatabaseConnection;
import tableobjects.User;
import windows.ReportingWindow;

/**
 *
 * @author Benjamin
 */
public class ReportingController {
    public ReportingWindow reportingWindow;
    public DatabaseConnection dbConn;
    User user;
    
    public ReportingController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        reportingWindow = new ReportingWindow();
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}