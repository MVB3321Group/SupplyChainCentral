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
    static String newCity;
    static Double newLat;
    static Double newLng;

    static Float distance;
    static int time;

    public SimulationController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        simWindow = new SimulationWindow();

        JSONHelper jh = new JSONHelper();

        ArrayList<String> locationList = new ArrayList<>(); // create an arraylist for cities

        for (Location l : dbConn.getLocations()) {
            locationList.add(l.getCity()); // put city in the arraylist
        }
        simWindow.SHOW_MAP_BUTTON.setOnAction(e -> {
            for (int i = 0; i < locationList.size(); i++) {
                locLat = jh.getGPSlat(locationList.get(i));
                locLng = jh.getGPSlon(locationList.get(i));
                simWindow.newMarker(locLat, locLng, locationList.get(i));
            }

            simWindow.showMap();
        });

        JSONHelper jh2 = new JSONHelper();

        simWindow.CREATE_SIM_BUTTON.setOnAction(ex -> {
            String newCity = simWindow.NewLocation.getText();
            newLat = jh2.getGPSlat(newCity);
            newLng = jh2.getGPSlon(newCity);
            simWindow.newMarker(newLat, newLng, newCity);

            for (int i = 0; i < locationList.size(); i++) {
                for (int j = 0; j < locationList.size(); j++) {
                    String cityA = locationList.get(i);
                    String cityB = locationList.get(j);
                    distance = jh.calcDistance(cityA, cityB); // returns miles
                    time = jh.calcTravelTime(cityA, cityB); // returns seconds
                    System.out.println("The distance between " + cityA + " and " + cityB + " is " + distance + "miles," +
                            " and travel time will be " + secondsConversion(time));
                }
            }
            
            for (int i = 0; i < locationList.size(); i++) {
                String cityA = locationList.get(i);
                String cityB = newCity;
                distance = jh.calcDistance(cityA, cityB); // returns miles
                time = jh.calcTravelTime(cityA, cityB); // returns seconds
                System.out.println("The distance between " + cityA + " and " + cityB + " is " + distance + "miles," +
                            " and travel time will be " + secondsConversion(time));
            }

        });

        simWindow.DELETE_SIM_BUTTON.setOnAction(ey -> {
            simWindow.removeMarker();
        });
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    private static String secondsConversion(int totalSeconds) {

    final int MINUTES_IN_AN_HOUR = 60;
    final int SECONDS_IN_A_MINUTE = 60;

    int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
    int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
    int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
    int hours = totalMinutes / MINUTES_IN_AN_HOUR;

    return hours + " hours " + minutes + " minutes " + seconds + " seconds";
}
}
