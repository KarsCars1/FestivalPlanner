package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        BorderPane agendaBorderpane = new BorderPane();
        VBox performerVBox = new VBox();
        Button addPerformer = new Button("Add performer");
        Button removePerformer = new Button("Remove performer");
        Button updatePerfomer = new Button("Update performer");
        ListView<String> performerlist = new ListView();
        performerlist.setOrientation(Orientation.HORIZONTAL);

        performerVBox.getChildren().addAll(performerlist, addPerformer,updatePerfomer,removePerformer);
        agendaBorderpane.setRight(performerVBox);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
