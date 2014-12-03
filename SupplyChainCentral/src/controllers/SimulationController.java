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

    public SimulationWindow simulationWindow;

    DatabaseConnection dbConn;
    User user;

    double locLat;
    double locLng;
    double newLat;
    double newLng;
    String newCity;
    float distance;
    int time;

    public SimulationController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        simulationWindow = new SimulationWindow();

        JSONHelper jh = new JSONHelper();

        ArrayList<String> locationList = new ArrayList<>(); // create an arraylist for cities

        for (Location l : dbConn.getLocations()) {
            locationList.add(l.getCity().replaceAll("\\s+","")); // put city in the arraylist
        }
        simulationWindow.SHOW_MAP_BUTTON.setOnAction(e -> {
            for (String location : locationList) {
                locLat = jh.getGPSlat(location);
                locLng = jh.getGPSlon(location);
                simulationWindow.newMarker(locLat, locLng, location);
            }

            simulationWindow.showMap();
        });

        JSONHelper jh2 = new JSONHelper();

        simulationWindow.CREATE_SIM_BUTTON.setOnAction(e -> {
            String city = simulationWindow.newLocation.getText().replaceAll("\\s+","");
            newLat = jh2.getGPSlat(city);
            newLng = jh2.getGPSlon(city);
            simulationWindow.newMarker(newLat, newLng, city);

            for (int i = 0; i < locationList.size(); i++) {
                for (int j = 0; j < locationList.size(); j++) {
                    String cityA = locationList.get(i);
                    String cityB = locationList.get(j);
                    distance = jh.calcDistance(cityA, cityB); // returns miles
                    time = jh.calcTravelTime(cityA, cityB); // returns seconds
                    System.out.println("The distance between " + cityA + " and " + cityB + " is " + distance + " miles. " +
                            "Travel time will be " + secondsConversion(time));
                }
            }
            
            for (String location : locationList) {
                String cityB = city;
                distance = jh.calcDistance(location, cityB); // returns miles
                time = jh.calcTravelTime(location, cityB); // returns seconds
                System.out.println("The distance between " + location + " and " + cityB + " is " + distance + "miles." +
                        "Travel time will be " + secondsConversion(time));
            }
        });

        simulationWindow.CLEAR_SIM_BUTTON.setOnAction(e -> {
            simulationWindow.removeMarker();
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