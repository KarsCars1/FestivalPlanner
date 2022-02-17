package sample;

import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddPerformanceScene extends StandardScene {

    private String performer;

    public AddPerformanceScene(PerformerController controller, String performer) {
        this.performer = performer;
        this.scene = createScene(controller);
    }

    public AddPerformanceScene(PerformerController controller) {
        this.performer = "";
        this.scene = createScene(controller);
    }

    public Scene createScene(PerformerController controller) {
        TextField showName = new TextField();
        ComboBox<String> performerName = new ComboBox<>();
        ComboBox<String> locations = new ComboBox<>();
        TextField beginTimeHour = new TextField("00");
        TextField beginTimeMinute = new TextField("00");
        TextField endTimeHour = new TextField("00");
        TextField endTimeMinute = new TextField("00");
        Button save = new Button("add Performance");
        HBox times = new HBox();
        performerName.getItems().addAll(controller.getPerformersString());
        performerName.getSelectionModel().select(performer);
        times.getChildren().addAll(beginTimeHour,beginTimeMinute,endTimeHour,endTimeMinute);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(performerName,times);

        Scene scene = new Scene(vBox);
        return scene;
    }
}
