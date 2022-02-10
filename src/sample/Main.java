package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        BorderPane agendaBorderpane = new BorderPane();

        TextField textField = new TextField();

        VBox performerVBox = new VBox();
        Label performerLabel = new Label("Performers:");
        Button addPerformer = new Button("Add performer");
        Button removePerformer = new Button("Remove performer");
        Button updatePerfomer = new Button("Update performer");

        ListView<String> performerlist = new ListView();
        performerlist.getItems().addAll("test", "test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test");
        performerlist.setOrientation(Orientation.VERTICAL);

        performerVBox.getChildren().addAll(performerLabel, performerlist, addPerformer, updatePerfomer, removePerformer);
        agendaBorderpane.setRight(performerVBox);
        agendaBorderpane.setLeft(textField);
        Scene agendaScene = new Scene(agendaBorderpane);
        primaryStage.setScene(agendaScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
