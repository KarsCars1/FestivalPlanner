package sample;

import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AddPerformanceScene extends StandardScene {

    private String performer;
    private ArrayList<TextField> time;

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
        Label betweenBegin = new Label(":");
        Label betweenEnd = new Label(":");
        TextField endTimeMinute = new TextField("00");
        time = new ArrayList<>();
        time.add(beginTimeHour);
        time.add(endTimeHour);
        time.add(beginTimeMinute);
        time.add(endTimeMinute);
        beginTimeHour.setOnKeyReleased(E -> {
            checkTimes();
        });
        beginTimeMinute.setOnKeyReleased(E -> {
            checkTimes();
        });
        endTimeHour.setOnKeyReleased(E -> {
            checkTimes();
        });
        endTimeMinute.setOnKeyReleased(E -> {
            checkTimes();
        });
        beginTimeHour.setMaxWidth(30);
        beginTimeMinute.setMaxWidth(30);
        endTimeHour.setMaxWidth(30);
        endTimeMinute.setMaxWidth(30);


        Button save = new Button("add Performance");
        HBox times = new HBox();
        performerName.getItems().addAll(controller.getPerformersString());
        performerName.getSelectionModel().select(performer);
        times.getChildren().addAll(beginTimeHour, betweenBegin, beginTimeMinute, endTimeHour, betweenEnd, endTimeMinute);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(performerName, times);

        Scene scene = new Scene(vBox);
        return scene;
    }

    public void checkTimes() {
        for (int i = 0; i < time.size(); i++) {
            TextField textField = time.get(i);
            try {
                int time = Integer.parseInt(textField.getText());
                if ((time >=24 && i<2)||(time >=60 && i>1)){
                    textField.setText("");
                }
            } catch (NumberFormatException e) {
                textField.setText("");
            }

        }
    }
}
