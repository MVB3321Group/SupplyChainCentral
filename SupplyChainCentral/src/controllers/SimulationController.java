/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    SimulationController
*/

package controllers;

import databaseconnection.DatabaseConnection;
import java.util.ArrayList;
import tableobjects.Location;
import tableobjects.User;
import windows.SimulationWindow;
import tools.JSONHelper;


/**
 *
 * @author Vasily
 */

public class SimulationController {
    public SimulationWindow simWindow;
    
    DatabaseConnection dbConn;
    User user;
    
    static Double locLat;
    static Double locLng;
    
    public SimulationController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        simWindow = new SimulationWindow();
        
        JSONHelper jh = new JSONHelper();   

        simWindow.SHOW_MAP_BUTTON.setOnAction(e -> {
            ArrayList<String> locationList = new ArrayList<>(); // create an arraylist for cities
        
            for (Location l : dbConn.getLocations())
                locationList.add(l.getCity()); // put city in the arraylist 
            
            for (int i = 0; i< locationList.size(); i++){
                locLat = jh.getGPSlat(locationList.get(i));
                locLng = jh.getGPSlon(locationList.get(i));
                simWindow.newMarker(locLat,locLng,locationList.get(i));
            }
            
            simWindow.showMap();
        });
        
        simWindow.CREATE_SIM_BUTTON.setOnAction(e -> {   
            
        });
        
                
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
