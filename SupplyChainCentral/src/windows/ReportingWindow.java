/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Reporting Window
 */

package windows;

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
    public ComboBox dataSetDropdown;
    public String[] dataSetList = {"Shipments", "Inventory List"};
    
    public ComboBox filterDropdown;
    public ComboBox chartTypeDropdown;
    public BarChart barChart;
    public LineChart lineChart;
    public PieChart pieChart;
    public BorderPane bPane;
    
    public ReportingWindow() {
        bPane = new BorderPane();
        GridPane gPane = new GridPane();
        dataSetDropdown = new ComboBox();
        dataSetDropdown.getItems().addAll(dataSetList);
        gPane.add(dataSetDropdown, 0, 0);
        
        bPane.setCenter(gPane);
        Scene scene = new Scene(bPane, 1050, 585);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setResizable(false);
        setTitle("New Shipment");
    }
}
