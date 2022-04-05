package Planner;

import DataStructure.Data.Artist;
import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalTime;
import java.util.ArrayList;

public class AddShowScene extends StandardScene {


    private ArrayList<TextField> time;

    private Artist selectedArtist;
    private TextField showName = new TextField();
    private ComboBox<String> performerName = new ComboBox<>();
    private ComboBox<String> locations = new ComboBox<>();
    private TextField beginTimeHour = new TextField("00");
    private TextField beginTimeMinute = new TextField("00");
    private TextField endTimeHour = new TextField("00");
    private TextField endTimeMinute = new TextField("00");
    private PerformerController controller;
    private Button save = new Button("add show");
    private GuiCallback callback;

    public AddShowScene(PerformerController controller, GuiCallback callback, Artist artist) {
        this.controller = controller;
        if (artist != null) {
            this.scene = createScene(artist.getPerformerName());
        }else{
            this.scene = createScene("");
        }
        this.selectedArtist = artist;
        this.callback = callback;
    }

    public Scene createScene(String selectedItem) {
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

        Label betweenBegin = new Label(":");
        Label betweenEnd = new Label(":");
        Label begintime = new Label("Begintime: ");
        Label endtime = new Label(" EndTime: ");
        Label performers = new Label("performer:");
        Label showNameText = new Label("show name:");
        Label location = new Label("location:");
        performerName.getItems().removeAll(performerName.getSelectionModel().getSelectedItem());
        performerName.getItems().addAll(this.controller.getPerformersString());
        performerName.getSelectionModel().select(selectedItem);
        locations.getItems().removeAll(locations.getSelectionModel().getSelectedItem());
        locations.getItems().addAll(this.controller.getLocationsString());


        HBox times = new HBox();
        GridPane names = new GridPane();
        names.add(performers, 0, 0);
        names.add(performerName, 1, 0);
        names.add(location, 0, 1);
        names.add(locations, 1, 1);

        performerName.setMinWidth(200);
        locations.setMinWidth(200);
        times.getChildren().addAll(begintime, beginTimeHour, betweenBegin, beginTimeMinute, endtime, endTimeHour, betweenEnd, endTimeMinute);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(showNameText, showName, names, times, save);

        Scene scene = new Scene(vBox);
        save.setOnAction(e -> {
            saveShow();
            callback.updateLists();
        });

        return scene;
    }

    public void checkTimes() {
        for (int i = 0; i < time.size(); i++) {
            TextField textField = time.get(i);
            try {
                int time = Integer.parseInt(textField.getText());
                if ((time >= 24 && i < 2) || (time >= 60 && i > 1)) {
                    textField.setText("");
                }
            } catch (NumberFormatException e) {
                textField.setText("");
            }

        }
    }


    public void saveShow() {
        LocalTime theBeginTime = LocalTime.of(Integer.parseInt(beginTimeHour.getText()), Integer.parseInt(beginTimeMinute.getText()));
        LocalTime theEndTime = LocalTime.of(Integer.parseInt(endTimeHour.getText()), Integer.parseInt(endTimeMinute.getText()));
        controller.addShow(showName.getText(),
                locations.getSelectionModel().getSelectedItem(),
                performerName.getSelectionModel().getSelectedItem(),
                theBeginTime,
                theEndTime);
    }

}
