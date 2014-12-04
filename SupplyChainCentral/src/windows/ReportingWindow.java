/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Reporting Window
 */

package windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Benjamin
 */
public class ReportingWindow extends Stage {
        
    public Label loggedInLabel = new Label();
    
    public ComboBox<String> ORIG_DROPDOWN = new ComboBox<>();
    public ComboBox<String> DATA_SET_DROPDOWN = new ComboBox<>();
    public ComboBox<String> FILTER_DROPDOWN = new ComboBox<>();
    public ComboBox<String> CHART_TYPE_DROPDOWN = new ComboBox<>();
    
    public Button GENERATE_REPORT_BUTTON = new Button("Generate Report");
    public Button CLEAR_REPORT_BUTTON = new Button("Clear Report");
  
    public CategoryAxis X_AXIS = new CategoryAxis(); 
    public NumberAxis Y_AXIS = new NumberAxis();
    public BarChart barChart = new BarChart(X_AXIS, Y_AXIS);

    public LineChart lineChart;
    public PieChart pieChart;
    
    public BorderPane bPane = new BorderPane();
    public GridPane gPane = new GridPane();
    public GridPane chartPane = new GridPane();
    public HBox headerPane = new HBox();
    
    public ReportingWindow() {
        headerPane.getChildren().add(loggedInLabel);
        loggedInLabel.setPadding(new Insets(5, 20, 5, 5));
        headerPane.setAlignment(Pos.TOP_RIGHT);
        bPane.setTop(headerPane);

        DATA_SET_DROPDOWN.getItems().addAll("Inventory", "Shipments");
        FILTER_DROPDOWN.getItems().addAll("Product");
        CHART_TYPE_DROPDOWN.getItems().addAll("Bar Chart", "Pie Chart", "Line Graph");
        
        DATA_SET_DROPDOWN.setPrefWidth(150);
        FILTER_DROPDOWN.setPrefWidth(150);
        CHART_TYPE_DROPDOWN.setPrefWidth(150);
        GENERATE_REPORT_BUTTON.setPrefWidth(150);
        CLEAR_REPORT_BUTTON.setPrefWidth(150);
        
        CLEAR_REPORT_BUTTON.setId("systemAdmin");
        
        DATA_SET_DROPDOWN.setPromptText("Select a data set.");
        FILTER_DROPDOWN.setPromptText("Filter by...");
        CHART_TYPE_DROPDOWN.setPromptText("Select a chart type.");

        gPane.add(new Label("Data Set"), 0, 0);
        gPane.add(DATA_SET_DROPDOWN, 1, 0);
        gPane.add(new Label("Filter"), 0, 1);
        gPane.add(FILTER_DROPDOWN, 1, 1);
        gPane.add(new Label("Chart Type"), 0, 2);
        gPane.add(CHART_TYPE_DROPDOWN, 1, 2);
        gPane.add(GENERATE_REPORT_BUTTON, 1, 3);
        gPane.add(CLEAR_REPORT_BUTTON, 1, 7);
        
        gPane.setHgap(10);
        gPane.setVgap(10);
        gPane.setPadding(new Insets(10, 10, 10, 10));

        chartPane.add(barChart, 0, 0);
        chartPane.setPadding(new Insets(20, 20, 20, 20));
        
        gPane.setAlignment(Pos.CENTER);
        chartPane.setAlignment(Pos.CENTER_RIGHT);
        bPane.setCenter(gPane);
        bPane.setRight(chartPane);
        chartPane.setVisible(false);
        
        Scene scene = new Scene(bPane, 775, 500);
        setResizable(false);
        scene.getStylesheets().add
                (MainWindow.class.getResource("LoginCSS.css").toExternalForm());
        setScene(scene);
        setTitle("Reports");
    }
}