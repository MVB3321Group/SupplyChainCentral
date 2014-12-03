/*  Supply Chain Central Application
 Michael Bernard, Benjamin Chopson, Vasily Kushakov
 CSCI 3321
 SimulationController
 */
package controllers;

import databaseconnection.DatabaseConnection;
import java.util.ArrayList;
import javafx.scene.chart.XYChart;
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

    static int totalDistance;
    static int totalTime;

    public SimulationController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        simWindow = new SimulationWindow();

        JSONHelper jh = new JSONHelper();

        ArrayList<String> locationList = new ArrayList<>(); // create an arraylist for cities
        ArrayList<Integer> distanceList = new ArrayList<>(); // create an arraylist for distances
        ArrayList<Integer> timeList = new ArrayList<>(); // create an arraylist for time

        for (Location l : dbConn.getLocations()) {
            locationList.add(l.getCity().replaceAll("\\s+", "")); // put city in the arraylist
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
            String newCity = simWindow.newLocation.getText();
            newCity = newCity.replaceAll("\\s+", "");
            newLat = jh2.getGPSlat(newCity);
            newLng = jh2.getGPSlon(newCity);
            simWindow.newMarker(newLat, newLng, newCity);

            for (int i = 0; i < locationList.size(); i++) {
                totalDistance = 0;
                totalTime = 0;
                for (int j = 0; j < locationList.size(); j++) {
                    String cityA = locationList.get(i);
                    String cityB = locationList.get(j);
                    distance = jh.calcDistance(cityA, cityB); // returns miles
                    time = jh.calcTravelTime(cityA, cityB); // returns seconds
                    System.out.println("The distance between " + cityA + " and " + cityB + " is " + distance + "miles."
                            + " and travel time will be " + secondsConversion(time));
                    totalDistance += distance;
                    totalTime += time;
                }
                distanceList.add(totalDistance);
                timeList.add((totalTime / 60));
            }

            totalDistance = 0;
            totalTime = 0;
            for (int i = 0; i < locationList.size(); i++) {
                String cityA = locationList.get(i);
                String cityB = newCity;
                distance = jh.calcDistance(cityA, cityB); // returns miles
                time = jh.calcTravelTime(cityA, cityB); // returns seconds
                System.out.println("The distance between " + cityA + " and " + cityB + " is " + distance + "miles."
                        + " and travel time will be " + secondsConversion(time));
                totalDistance += distance;
                totalTime += time;
            }
            distanceList.add(totalDistance);
            timeList.add((totalTime / 60));

            //Below is the code for the distances chart in simwindow
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            simWindow.Y_AXIS.setUpperBound(Math.ceil(25000));

            int k;
            int size = locationList.size();
            for (k = 0; k < size; k++) {
                series.getData().add(new XYChart.Data(locationList.get(k), distanceList.get(k)));
            }
            series.getData().add(new XYChart.Data(newCity, distanceList.get(size++)));

            simWindow.DISTANCES_CHART.getData().add(series); //end distances chart

            //Below is the code for the time chart in simwindow
            XYChart.Series<String, Integer> seriesT = new XYChart.Series<>();

            simWindow.YT_AXIS.setUpperBound(Math.ceil(5000));

            int z;
            for (z = 0; z < 6; z++) {
                seriesT.getData().add(new XYChart.Data(locationList.get(z), timeList.get(z)));
            }
            seriesT.getData().add(new XYChart.Data(newCity, timeList.get(7)));

            simWindow.TIME_CHART.getData().add(seriesT); //end time chart

        });

        simWindow.CLEAR_SIM_BUTTON.setOnAction(ey -> {
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
