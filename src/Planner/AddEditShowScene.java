package Planner;

import DataStructure.Data.Artist;
import DataStructure.Data.Show;
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

public class AddEditShowScene extends StandardScene {
    private ArrayList<TextField> time;

    private TextField showName;
    private ComboBox<String> performerName = new ComboBox<>();
    private ComboBox<String> locations = new ComboBox<>();
    private TextField beginTimeHour;
    private TextField beginTimeMinute;
    private TextField endTimeHour;
    private TextField endTimeMinute;
    private PerformerController controller;
    private Button save = new Button("save");
    private GuiCallback callback;
    private Show show;


    public AddEditShowScene(Show show, PerformerController performerController, GuiCallback callback) {
        this.controller = performerController;
        this.callback = callback;
        this.show = show;

        //pre insert the name
        this.showName = new TextField(this.show.getName());


        //pre insert the time
        if(show.getBeginTime().getHour() > 9) {
            this.beginTimeHour = new TextField("" + show.getBeginTime().getHour());
        } else{
            this.beginTimeHour = new TextField("0" + show.getBeginTime().getHour());
        }

        if(show.getBeginTime().getMinute() > 9){
            this.beginTimeMinute = new TextField(""+show.getBeginTime().getMinute());
        } else{
            this.beginTimeMinute = new TextField("0"+show.getBeginTime().getMinute());
        }

        if(show.getBeginTime().getHour() > 9) {
            this.endTimeHour = new TextField("" + show.getEndTime().getHour());
        } else{
            this.endTimeHour = new TextField("0" + show.getEndTime().getHour());
        }

        if(show.getBeginTime().getMinute() > 9){
            this.endTimeMinute = new TextField(""+show.getEndTime().getMinute());
        } else{
            this.endTimeMinute = new TextField("0"+show.getEndTime().getMinute());
        }

        this.save.setOnAction(e ->{
            saveShow();
            callback.updateLists();
        });


    }

    public Scene createScene(){
        this.time = new ArrayList<>();
        this.time.add(this.beginTimeHour);
        this.time.add(this.endTimeHour);
        this.time.add(this.beginTimeMinute);
        this.time.add(this.endTimeMinute);


        this.beginTimeHour.setOnKeyReleased(E -> {
            checkTimes();
        });
        this.beginTimeMinute.setOnKeyReleased(E -> {
            checkTimes();
        });
        this.endTimeHour.setOnKeyReleased(E -> {
            checkTimes();
        });
        this.endTimeMinute.setOnKeyReleased(E -> {
            checkTimes();
        });


        this.beginTimeHour.setMaxWidth(30);
        this.beginTimeMinute.setMaxWidth(30);
        this.endTimeHour.setMaxWidth(30);
        this.endTimeMinute.setMaxWidth(30);

        Label betweenBegin = new Label(":");
        Label betweenEnd = new Label(":");
        Label begintime = new Label("Begintime: ");
        Label endtime = new Label(" EndTime: ");
        Label performers = new Label("performer:");
        Label showNameText = new Label("show name:");
        Label location = new Label("location:");

        this.performerName.getItems().removeAll(this.performerName.getSelectionModel().getSelectedItem());
        this.performerName.getItems().addAll(this.controller.getPerformersString());

        //pre insert performer
        this.performerName.getSelectionModel().select(this.show.getPerformerName());

        this.locations.getItems().removeAll(this.locations.getSelectionModel().getSelectedItem());
        this.locations.getItems().addAll(this.controller.getLocationsString());

        //pre insert location
        this.locations.getSelectionModel().select(this.show.getLocationName());


        HBox times = new HBox();
        GridPane names = new GridPane();
        names.add(performers, 0, 0);
        names.add(this.performerName, 1, 0);
        names.add(location, 0, 1);
        names.add(this.locations, 1, 1);

        this.performerName.setMinWidth(200);
        this.locations.setMinWidth(200);
        times.getChildren().addAll(begintime, this.beginTimeHour, betweenBegin, this.beginTimeMinute, endtime, this.endTimeHour, betweenEnd, this.endTimeMinute);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(showNameText, this.showName, names, times, this.save);

        Scene scene = new Scene(vBox);


        return scene;
    }

    //check if the given time is valid
    public void checkTimes() {
        for (int i = 0; i < this.time.size(); i++) {
            TextField textField = this.time.get(i);
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

    //save the show
    public void saveShow() {
        LocalTime theBeginTime = LocalTime.of(Integer.parseInt(this.beginTimeHour.getText()), Integer.parseInt(this.beginTimeMinute.getText()));
        LocalTime theEndTime = LocalTime.of(Integer.parseInt(this.endTimeHour.getText()), Integer.parseInt(this.endTimeMinute.getText()));
        this.controller.editShow(this.showName.getText(), this.show,
                this.locations.getSelectionModel().getSelectedItem(),
                this.performerName.getSelectionModel().getSelectedItem(),
                theBeginTime,
                theEndTime);
    }
}
