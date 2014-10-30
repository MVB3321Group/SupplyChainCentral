package tools;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Toolbar extends Application {

    private final ComboBox<String> FILE_DROPDOWN = new ComboBox<>();
    private final ComboBox<String> VIEW_DROPDOWN = new ComboBox<>();
    private final ComboBox<String> RUN_DROPDOWN = new ComboBox<>();
    private final ComboBox<String> TRACK_DROPDOWN = new ComboBox<>();
    private final ComboBox<String> TOOLS_DROPDOWN = new ComboBox<>();
    private final ComboBox<String> HELP_DROPDOWN = new ComboBox<>();

    private final String[] FILE_DROPDOWN_OPTIONS = new String[4];
    private final String[] VIEW_DROPDOWN_OPTIONS = new String[4];
    private final String[] RUN_DROPDOWN_OPTIONS = new String[4];
    private final String[] TRACK_DROPDOWN_OPTIONS = new String[4];
    private final String[] TOOLS_DROPDOWN_OPTIONS = new String[4];
    private final String[] HELP_DROPDOWN_OPTIONS = new String[4];
    
    public Pane pane = new Pane();

    public Toolbar() {
        // No-arg constructor
    }

    public void generateDropdowns(Pane pane) {
        // "File" dropdown options
        FILE_DROPDOWN_OPTIONS[0] = "New Shipment";
        FILE_DROPDOWN_OPTIONS[1] = "New 1";
        FILE_DROPDOWN_OPTIONS[2] = "New 2";
        FILE_DROPDOWN_OPTIONS[3] = "New 3";
        
        FILE_DROPDOWN.setOnAction(e -> {
            switch (FILE_DROPDOWN_OPTIONS[5]) {
                case "New Shipment": System.out.println("Navigating to ShipmentWindow..."); break;
                case "New 1": /* */; break;
                case "New 2": /* */; break;
                case "New 3": /* */; break;
            }
        });

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

        // Simple fix to not allow selected option to change dropdown title
        FILE_DROPDOWN.setOnAction(e -> FILE_DROPDOWN.setValue("File"));

        ObservableList<String> fileDropdownList
                = FXCollections.observableArrayList(FILE_DROPDOWN_OPTIONS);
        FILE_DROPDOWN.getItems().addAll(fileDropdownList);

        // "View" dropdown list
        VIEW_DROPDOWN.setPrefWidth(68);
        VIEW_DROPDOWN.setValue("View");
        VIEW_DROPDOWN.setOnAction(e -> VIEW_DROPDOWN.setValue("View"));
        ObservableList<String> viewDropdownList
                = FXCollections.observableArrayList(VIEW_DROPDOWN_OPTIONS);
        VIEW_DROPDOWN.getItems().addAll(viewDropdownList);

        // "Run" dropdown list
        RUN_DROPDOWN.setPrefWidth(63);
        RUN_DROPDOWN.setValue("Run");
        RUN_DROPDOWN.setOnAction(e -> RUN_DROPDOWN.setValue("Run"));
        ObservableList<String> runDropdownList
                = FXCollections.observableArrayList(RUN_DROPDOWN_OPTIONS);
        RUN_DROPDOWN.getItems().addAll(runDropdownList);

        // "Track" dropdown list
        TRACK_DROPDOWN.setPrefWidth(71);
        TRACK_DROPDOWN.setValue("Track");
        TRACK_DROPDOWN.setOnAction(e -> TRACK_DROPDOWN.setValue("Track"));
        ObservableList<String> trackDropdownList
                = FXCollections.observableArrayList(TRACK_DROPDOWN_OPTIONS);
        TRACK_DROPDOWN.getItems().addAll(trackDropdownList);

        // "Tools" dropdown list
        TOOLS_DROPDOWN.setPrefWidth(71);
        TOOLS_DROPDOWN.setValue("Tools");
        TOOLS_DROPDOWN.setOnAction(e -> TOOLS_DROPDOWN.setValue("Tools"));
        ObservableList<String> toolsDropdownList
                = FXCollections.observableArrayList(TOOLS_DROPDOWN_OPTIONS);
        TOOLS_DROPDOWN.getItems().addAll(toolsDropdownList);

        // "Help" dropdown list
        HELP_DROPDOWN.setPrefWidth(67);
        HELP_DROPDOWN.setValue("Help");
        HELP_DROPDOWN.setOnAction(e -> HELP_DROPDOWN.setValue("Help"));
        ObservableList<String> helpDropdownList
                = FXCollections.observableArrayList(HELP_DROPDOWN_OPTIONS);
        HELP_DROPDOWN.getItems().addAll(helpDropdownList);

        // Toolbar HBox
        HBox hBox = new HBox();
        hBox.getChildren().add(FILE_DROPDOWN);
        hBox.getChildren().add(VIEW_DROPDOWN);
        hBox.getChildren().add(RUN_DROPDOWN);
        hBox.getChildren().add(TRACK_DROPDOWN);
        hBox.getChildren().add(TOOLS_DROPDOWN);
        hBox.getChildren().add(HELP_DROPDOWN);

        pane.getChildren().add(hBox);
    }

    @Override
    public void start(Stage mainWindow) {
        Toolbar toolbar = new Toolbar();
        toolbar.generateDropdowns(pane);

        // TODO: Auto-fit scene dimensions to user's max window dimensions
        Scene scene = new Scene(pane, 1344, 686);

        mainWindow.setTitle("Supply Chain Central");
        mainWindow.setScene(scene);
        mainWindow.show();
        mainWindow.setResizable(false);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}