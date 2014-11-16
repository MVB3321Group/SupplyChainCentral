/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Shipment Window
 */

package tools;

import controllers.*;
import databaseconnection.*;
import tableobjects.*;
import tools.Toolbar;
import windows.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class Toolbar {

    public ComboBox<String> FILE_DROPDOWN = new ComboBox<>();
    public ComboBox<String> VIEW_DROPDOWN = new ComboBox<>();
    public ComboBox<String> RUN_DROPDOWN = new ComboBox<>();
    public ComboBox<String> TRACK_DROPDOWN = new ComboBox<>();
    public ComboBox<String> TOOLS_DROPDOWN = new ComboBox<>();
    public ComboBox<String> HELP_DROPDOWN = new ComboBox<>();

    public String[] FILE_DROPDOWN_OPTIONS = new String[4];
    public String[] VIEW_DROPDOWN_OPTIONS = new String[4];
    public String[] RUN_DROPDOWN_OPTIONS = new String[4];
    public String[] TRACK_DROPDOWN_OPTIONS = new String[4];
    public String[] TOOLS_DROPDOWN_OPTIONS = new String[4];
    public String[] HELP_DROPDOWN_OPTIONS = new String[4];
    
    public Pane pane = new Pane();

    public Toolbar() {} // No-arg constructor

    public void generateDropdowns(Pane pane) {
        // "File" dropdown options
        FILE_DROPDOWN_OPTIONS[0] = "New Shipment";
        FILE_DROPDOWN_OPTIONS[1] = "New 1";
        FILE_DROPDOWN_OPTIONS[2] = "New 2";
        FILE_DROPDOWN_OPTIONS[3] = "New 3";

        // "View" dropdown options
        VIEW_DROPDOWN_OPTIONS[0] = "View Shipment";
        VIEW_DROPDOWN_OPTIONS[1] = "View 1";
        VIEW_DROPDOWN_OPTIONS[2] = "View 2";
        VIEW_DROPDOWN_OPTIONS[3] = "View 3";

        // "Run" dropdown options
        RUN_DROPDOWN_OPTIONS[0] = "Run Simulation";
        RUN_DROPDOWN_OPTIONS[1] = "Run 1";
        RUN_DROPDOWN_OPTIONS[2] = "Run 2";
        RUN_DROPDOWN_OPTIONS[3] = "Run 3";

        // "Track" dropdown options
        TRACK_DROPDOWN_OPTIONS[0] = "Track Shipment";
        TRACK_DROPDOWN_OPTIONS[1] = "Track 1";
        TRACK_DROPDOWN_OPTIONS[2] = "Track 2";
        TRACK_DROPDOWN_OPTIONS[3] = "Track 3";

        // "Tools" dropdown options
        TOOLS_DROPDOWN_OPTIONS[0] = "Shipment Tools";
        TOOLS_DROPDOWN_OPTIONS[1] = "Tools 1";
        TOOLS_DROPDOWN_OPTIONS[2] = "Tools 2";
        TOOLS_DROPDOWN_OPTIONS[3] = "Tools 3";

        // "Help" dropdown options
        HELP_DROPDOWN_OPTIONS[0] = "Help Contents";
        HELP_DROPDOWN_OPTIONS[1] = "Keyboard Shortcuts";
        HELP_DROPDOWN_OPTIONS[2] = "Start Page";
        HELP_DROPDOWN_OPTIONS[3] = "About";

        // "File" dropdown list
        FILE_DROPDOWN.setPrefWidth(60);
        FILE_DROPDOWN.setValue("File");
        FILE_DROPDOWN.getItems().addAll(FILE_DROPDOWN_OPTIONS);
        
        // "View" dropdown list
        VIEW_DROPDOWN.setPrefWidth(68);
        VIEW_DROPDOWN.setValue("View");
        ObservableList<String> viewDropdownList
                = FXCollections.observableArrayList(VIEW_DROPDOWN_OPTIONS);
        VIEW_DROPDOWN.getItems().addAll(viewDropdownList);
        VIEW_DROPDOWN.setOnAction(e -> {
                viewDropdownList.indexOf(VIEW_DROPDOWN.getValue());
                
            VIEW_DROPDOWN.setValue("View");    
        });

        // "Run" dropdown list
        RUN_DROPDOWN.setPrefWidth(63);
        RUN_DROPDOWN.setValue("Run");
        ObservableList<String> runDropdownList
                = FXCollections.observableArrayList(RUN_DROPDOWN_OPTIONS);
        RUN_DROPDOWN.getItems().addAll(runDropdownList);
        RUN_DROPDOWN.setOnAction(e -> {
                runDropdownList.indexOf(RUN_DROPDOWN.getValue());
                
            RUN_DROPDOWN.setValue("Run");
        });

        // "Track" dropdown list
        TRACK_DROPDOWN.setPrefWidth(71);
        TRACK_DROPDOWN.setValue("Track");
        ObservableList<String> trackDropdownList
                = FXCollections.observableArrayList(TRACK_DROPDOWN_OPTIONS);
        TRACK_DROPDOWN.getItems().addAll(trackDropdownList);
        TRACK_DROPDOWN.setOnAction(e -> {
                trackDropdownList.indexOf(TRACK_DROPDOWN.getValue());
                
            TRACK_DROPDOWN.setValue("Track");
        });

        // "Tools" dropdown list
        TOOLS_DROPDOWN.setPrefWidth(71);
        TOOLS_DROPDOWN.setValue("Tools");
        ObservableList<String> toolsDropdownList
                = FXCollections.observableArrayList(TOOLS_DROPDOWN_OPTIONS);
        TOOLS_DROPDOWN.getItems().addAll(toolsDropdownList);
        TOOLS_DROPDOWN.setOnAction(e -> {
                toolsDropdownList.indexOf(TOOLS_DROPDOWN.getValue());
                
            TOOLS_DROPDOWN.setValue("Tools");
        });

        // "Help" dropdown list
        HELP_DROPDOWN.setPrefWidth(67);
        HELP_DROPDOWN.setValue("Help");
        ObservableList<String> helpDropdownList
                = FXCollections.observableArrayList(HELP_DROPDOWN_OPTIONS);
        HELP_DROPDOWN.getItems().addAll(helpDropdownList);
        HELP_DROPDOWN.setOnAction(e -> {
                helpDropdownList.indexOf(HELP_DROPDOWN.getValue());
                
            HELP_DROPDOWN.setValue("Help");
        });

        // Toolbar HBox
        HBox hBox = new HBox();
        hBox.getChildren().add(FILE_DROPDOWN);
        hBox.getChildren().add(VIEW_DROPDOWN);
        hBox.getChildren().add(RUN_DROPDOWN);
        hBox.getChildren().add(TRACK_DROPDOWN);
        hBox.getChildren().add(TOOLS_DROPDOWN);
        hBox.getChildren().add(HELP_DROPDOWN);

        FILE_DROPDOWN.getStyleClass().add("ComboBoxCSS.css");

        hBox.getStylesheets().add
                (Toolbar.class.getResource("DialogBoxCSS.css").toExternalForm());
        pane.getChildren().add(hBox);
    }
}
