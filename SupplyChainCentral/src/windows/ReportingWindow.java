/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Reporting Window
 */

package windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Benjamin
 */
public class ReportingWindow extends Stage {
    public ComboBox<String> DATA_SET_DROPDOWN = new ComboBox<>();
    public ComboBox<String> filterDropdown = new ComboBox<>();
    public ComboBox<String> chartTypeDropdown = new ComboBox<>();
    public BarChart barChart;
    public LineChart lineChart;
    public PieChart pieChart;
    public BorderPane bPane;
    
    public ReportingWindow() {
        bPane = new BorderPane();
        GridPane gPane = new GridPane();
        DATA_SET_DROPDOWN = new ComboBox();
        filterDropdown = new ComboBox();
        chartTypeDropdown = new ComboBox();
        //DATA_SET_DROPDOWN.getItems().addAll(dataSetList);
        gPane.add(DATA_SET_DROPDOWN, 0, 0);
        gPane.add(filterDropdown, 0, 1);
        gPane.add(chartTypeDropdown, 0, 2);
        
        
        DATA_SET_DROPDOWN.setPromptText("Select a data set.");
        DATA_SET_DROPDOWN.getItems().addAll("Shipments", "Inventory List");
        DATA_SET_DROPDOWN.setPrefWidth(150);
        
        bPane.setCenter(gPane);

        Scene scene = new Scene(bPane, 1050, 585);
        setResizable(false);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setTitle("Reports");
    }
}
