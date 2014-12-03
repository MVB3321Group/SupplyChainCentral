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

/**
 *
 * @author Vasily
 */

public class SimulationWindow extends Stage implements MapComponentInitializedListener {

    GoogleMapView mapView;
    GoogleMap map;

    public Button CREATE_SIM_BUTTON = new Button("Run Simulation");
    public Button CLEAR_SIM_BUTTON = new Button("Clear Simulation");
    public Button SHOW_MAP_BUTTON = new Button("Show Map");

    public TextField newLocation = new TextField();
    public Label loggedInLabel = new Label();

    public BorderPane bPane = new BorderPane();
    public GridPane gPane = new GridPane();
    public HBox headerPane = new HBox();
        
    public Marker marker;

    public SimulationWindow() {
        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
    
        headerPane.getChildren().add(loggedInLabel);
        loggedInLabel.setPadding(new Insets(5, 20, 5, 5));
        headerPane.setAlignment(Pos.TOP_RIGHT);
        bPane.setTop(headerPane);

        gPane.add(new Label("Enter Location "), 0, 0);
        gPane.add(newLocation, 1, 0);
        gPane.add(CREATE_SIM_BUTTON, 2, 0);
        gPane.add(CLEAR_SIM_BUTTON, 3, 0);
        gPane.add(SHOW_MAP_BUTTON, 4, 0);
        
        gPane.setHgap(10);
        gPane.setVgap(10);
        gPane.setPadding(new Insets(10, 10, 10, 10));
        gPane.setAlignment(Pos.TOP_CENTER);
        
        bPane.setCenter(gPane);        
        
        CREATE_SIM_BUTTON.setPrefWidth(150);
        CLEAR_SIM_BUTTON.setPrefWidth(150);       
        SHOW_MAP_BUTTON.setPrefWidth(150);
        
        Scene scene = new Scene(bPane, 1050, 615);
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
    
    public void showMap(){
        bPane.setBottom(mapView);
    }

    public void newMarker(double x, double y, String City){
        //Add a marker to the map
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(new LatLong(x, y))
                .visible(Boolean.TRUE)
                .title(City);

        marker = new Marker(markerOptions);
        map.addMarker(marker);
    }
    
    //Be careful with this method. It deletes the previous marker added, but cannot delete those placed earlier.
    public void removeMarker(){
        map.removeMarker(marker);
    }
}
