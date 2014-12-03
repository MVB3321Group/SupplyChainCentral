/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    ReportingController
 */
package controllers;

import databaseconnection.DatabaseConnection;
import javafx.scene.paint.Color;
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
 
        reportingWindow.GENERATE_REPORT_BUTTON.setOnAction(e -> {
            try {
                switch (reportingWindow.CHART_TYPE_DROPDOWN.getValue()) {
                    case "Bar":
                        reportingWindow.X_AXIS.setLabel(reportingWindow.FILTER_DROPDOWN.getValue());
                        reportingWindow.X_AXIS.setTickLabelFill(Color.WHITE);
                        reportingWindow.Y_AXIS.setLabel("Quantity");
                        reportingWindow.Y_AXIS.setTickLabelFill(Color.WHITE);
                        reportingWindow.Y_AXIS.setAutoRanging(false);
                        reportingWindow.Y_AXIS.setLowerBound(0);
                        reportingWindow.Y_AXIS.setTickUnit(1);
                        reportingWindow.Y_AXIS.setMinorTickVisible(false);

                        reportingWindow.chartPane.setVisible(true);
                }
            } catch (NullPointerException ex) {}
        });
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}