/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Simulation Window
 */

package windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapType;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.paint.Color;

import javafx.scene.control.ProgressBar;

/**
 *
 * @author Vasily
 */

public class SimulationWindow extends Stage implements MapComponentInitializedListener {

    GoogleMapView mapView;
    GoogleMap map;

    public Button RUN_SIM_BUTTON = new Button("Run Simulation");
    public Button CLEAR_SIM_BUTTON = new Button("Clear Simulation");
    public Button SHOW_MAP_BUTTON = new Button("Show Map");

    public TextField newLocation = new TextField();
    public Label loggedInLabel = new Label();

    public BorderPane largePane = new BorderPane();
    public BorderPane midPane = new BorderPane();
    public GridPane actionPane = new GridPane();
    public GridPane chartPane = new GridPane();
    public HBox headerPane = new HBox();
        
    public Marker marker;
    
    public CategoryAxis X_AXIS = new CategoryAxis();
    public NumberAxis Y_AXIS = new NumberAxis();
    public BarChart DISTANCES_CHART = new BarChart(X_AXIS, Y_AXIS);
    
    public NumberAxis YT_AXIS = new NumberAxis();
    public CategoryAxis XT_AXIS = new CategoryAxis();
    public BarChart TIME_CHART = new BarChart(XT_AXIS, YT_AXIS);
    
    public ProgressBar progressBar = new ProgressBar();
    
    public SimulationWindow() {
        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);   
    
        headerPane.getChildren().add(loggedInLabel);
        loggedInLabel.setPadding(new Insets(5, 20, 5, 5));
        headerPane.setAlignment(Pos.TOP_RIGHT);
        midPane.setTop(headerPane);

        actionPane.add(new Label("Enter Location "), 0, 0);
        actionPane.add(newLocation, 1, 0);
        actionPane.add(RUN_SIM_BUTTON, 1, 1);
        actionPane.add(CLEAR_SIM_BUTTON, 1, 5);
        
        actionPane.setHgap(10);
        actionPane.setVgap(10);
        actionPane.setPadding(new Insets(10, 10, 10, 10));
        actionPane.setAlignment(Pos.TOP_CENTER);
               
        CLEAR_SIM_BUTTON.setId("systemAdmin");

        RUN_SIM_BUTTON.setPrefWidth(150);
        CLEAR_SIM_BUTTON.setPrefWidth(150);
        SHOW_MAP_BUTTON.setPrefSize(80, 50);      
        DISTANCES_CHART.setPrefSize(250, 400);
        TIME_CHART.setPrefSize(250, 400);
 
        X_AXIS.setLabel("Origin City");
        X_AXIS.setTickLabelFill(Color.WHITE);
        Y_AXIS.setLabel("Distance Traveled (Miles)");
        Y_AXIS.setTickLabelFill(Color.WHITE);
        Y_AXIS.setAutoRanging(false);
        Y_AXIS.setLowerBound(0);
        Y_AXIS.setTickUnit(1000);
        Y_AXIS.setMinorTickVisible(false);
               
        XT_AXIS.setLabel("Origin City");
        XT_AXIS.setTickLabelFill(Color.WHITE);
        YT_AXIS.setLabel("Travel Time (Hours)");
        YT_AXIS.setTickLabelFill(Color.WHITE);
        YT_AXIS.setAutoRanging(false);
        YT_AXIS.setLowerBound(0);
        YT_AXIS.setTickUnit(6);
        YT_AXIS.setMinorTickVisible(false);
        
        chartPane.add(DISTANCES_CHART, 0, 0);
        chartPane.add(TIME_CHART, 1, 0);
        chartPane.setPadding(new Insets(20, 20, 20, 20));
        
        actionPane.setAlignment(Pos.CENTER);
        chartPane.setAlignment(Pos.CENTER);
        midPane.setLeft(actionPane);
        midPane.setCenter(SHOW_MAP_BUTTON);
        midPane.setRight(chartPane);
        largePane.setTop(midPane);
        
        Scene scene = new Scene(largePane, 1050, 615);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setResizable(false);
        setTitle("Run Simulation");
    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(MapType.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(4);

        map = mapView.createMap(mapOptions);
    }
    
    public void showMap() {
        midPane.setBottom(mapView);
    }
    
    public void showProgress() {
        actionPane.add(progressBar, 1, 3);
    }
    
    public void endProgress() {
        actionPane.getChildren().remove(progressBar);
    }

    public void newMarker(double x, double y, String City) {
        //Add a marker to the map
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(new LatLong(x, y))
                .visible(Boolean.TRUE)
                .title(City);

        marker = new Marker(markerOptions);
        map.addMarker(marker);
    }
    
    //Be careful with this method. It deletes the previous marker added, but cannot delete those placed earlier.
    public void removeMarker() {
        map.removeMarker(marker);
    }
}
