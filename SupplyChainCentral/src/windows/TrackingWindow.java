package windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class TrackingWindow {

//    @Override
//    public void start(Stage primaryStage) {
//        BorderPane pane = new BorderPane();
//
//        pane.setTop(getHBox());
//        pane.setLeft(getVBox());
//
//        Scene scene = new Scene(pane, 800, 600);
//        primaryStage.setTitle("SCC Tracking Module");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    private HBox getHBox() {
        HBox hBox = new HBox(15);
        hBox.setPadding(new Insets(15, 15, 15, 15));
        hBox.setStyle("-fx-background-color: #0099FF");
        hBox.getChildren().add(new Button("Load Shipment"));
        hBox.getChildren().add(new Button("View Shipments"));
//    ImageView imageView = new ImageView(new Image("CTLogo.png"));
//    hBox.getChildren().add(imageView);
        return hBox;
    }

    private VBox getVBox() {
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(15, 5, 5, 5));
        vBox.getChildren().add(new Label("Current Shipments"));

        Label[] courses = {new Label("Shipment 1"), new Label("Shipment 2"),
            new Label("Shipment 3"), new Label("Shipment 4")};

        for (Label course : courses) {
            VBox.setMargin(course, new Insets(0, 0, 0, 15));
            vBox.getChildren().add(course);
        }

        return vBox;
    }
}