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

    static int totalDistance;
    static int totalTime;

    public SimulationController(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        simulationWindow = new SimulationWindow();

        JSONHelper jh = new JSONHelper();

        ArrayList<String> locationList = new ArrayList<>(); // create an arraylist for cities
        ArrayList<Integer> distanceList = new ArrayList<>(); // create an arraylist for distances
        ArrayList<Integer> timeList = new ArrayList<>(); // create an arraylist for time

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        XYChart.Series<String, Integer> seriesT = new XYChart.Series<>();

        for (Location l : dbConn.getLocations()) {
            String A = l.getCity().replaceAll("\\s+", "");
            String B = l.getState().replaceAll("\\s+", "");
            locationList.add(A + ", " + B); // put city in the arraylist
        }
        
        simulationWindow.SHOW_MAP_BUTTON.setOnAction(e -> {
            try {
                for (int i = 0; i < locationList.size(); i++) {
                    String xyz = locationList.get(i);
                    locLat = jh.getGPSlat(xyz);
                    locLng = jh.getGPSlon(xyz);
                    simulationWindow.newMarker(locLat, locLng, xyz);
                }

                simulationWindow.showMap();
            } catch (Exception ex) {}
        });

        JSONHelper jh2 = new JSONHelper();

        simulationWindow.RUN_SIM_BUTTON.setOnAction(e -> {
            try {
                if (simulationWindow.newLocation.getText().isEmpty()) {
                    simulationWindow.newLocation.setText("Enter a location"); //do not run if the field is empty
                } else {
                    simulationWindow.showProgress();
                    String newCity = simulationWindow.newLocation.getText();
                    newCity = newCity.replaceAll("\\s+", "");
                    newLat = jh2.getGPSlat(newCity);
                    newLng = jh2.getGPSlon(newCity);
                    simulationWindow.newMarker(newLat, newLng, newCity);

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
                        timeList.add((totalTime / 3600));
                    }

                    totalDistance = 0;
                    totalTime = 0;
                    for (int i = 0; i < locationList.size(); i++) {
                        String cityA = locationList.get(i);
                        String cityB = newCity;
                        distance = jh.calcDistance(cityA, cityB); // returns miles
                        time = jh.calcTravelTime(cityA, cityB); // returns seconds
                        System.out.println("The distance between " + cityA + " and " + cityB + " is " + distance + " miles."
                                + "Travel time is an estimated " + secondsConversion(time));
                        totalDistance += distance;
                        totalTime += time;
                    }
                    distanceList.add(totalDistance);
                    timeList.add((totalTime / 3600));

                    //Below is the code for the distances chart in simwindow           
                    simulationWindow.Y_AXIS.setUpperBound(Math.ceil(25000));

                    int size = locationList.size();
                    
                    for (int i = 0; i < size; i++) {
                        series.getData().add(new XYChart.Data(locationList.get(i), distanceList.get(i)));
                    }
                    
                    series.getData().add(new XYChart.Data(newCity, distanceList.get(size++)));

                    simulationWindow.DISTANCES_CHART.getData().add(series); //end distances chart

                    //Below is the code for the time chart in simwindow
                    simulationWindow.YT_AXIS.setUpperBound(Math.ceil(220));
                    
                    for (int i = 0; i < locationList.size(); i++) {
                        seriesT.getData().add(new XYChart.Data(locationList.get(i), timeList.get(i)));
                    }
                    seriesT.getData().add(new XYChart.Data(newCity, timeList.get(size++)));

                    simulationWindow.TIME_CHART.getData().add(seriesT); //end time chart

                    simulationWindow.endProgress();
                }
            } catch (Exception ex) {}
        });

        simulationWindow.CLEAR_SIM_BUTTON.setOnAction(e -> {
            try {
                simulationWindow.removeMarker();
                timeList.clear();
                distanceList.clear();
                simulationWindow.DISTANCES_CHART.getData().remove(series);
                simulationWindow.TIME_CHART.getData().remove(seriesT);
            } catch (Exception ex) {}
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

        return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
    }
}